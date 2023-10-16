package imp.as.debtservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.debtservice.constant.EndpointConstant;
import imp.as.debtservice.dto.AccountBalanceResponce;
import imp.as.debtservice.dto.MobileResponse;
import imp.as.debtservice.model.SMSTransaction;
import imp.as.debtservice.model.TempTransaction;
import imp.as.debtservice.repository.SMSTransactionRepository;
import imp.as.debtservice.repository.TempTransactionRepository;
import imp.as.debtservice.service.SMSService;
import imp.as.debtservice.utils.CsvWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SMSServicempl implements SMSService{
	@Autowired
	private final WebClient.Builder webClientBuilder;
	
	@Autowired
	private final SMSTransactionRepository smsTransactionRepository;
	@Autowired
	private final TempTransactionRepository tempTransactionRepository;
	
	public SMSTransaction mapMobileResponseToSMSTransaction(MobileResponse mobileResponse) {
		return SMSTransaction.builder()
						.accountNo(mobileResponse.getAccountNo())
						.mobileNo(mobileResponse.getMobileNo())
						.createdBy("DebtService")
						.lastUpdBy("DebtService")
						.build();
	}
	
	public void generateFileSMS(List<SMSTransaction> smsTransactions) throws Exception {
		CsvWriter<SMSTransaction> csvWriter = new CsvWriter<>("D:\\Test\\uploads\\SMS", "sms.txt", "|");
		String[] attrNameBody = { "accountNo", "mobileNo"};
		
		csvWriter.setDatas(smsTransactions);
		csvWriter.setAttrNameBody(attrNameBody);
		
		csvWriter.writeCsvFile();
	}
	
	public void preassignSMS() throws Exception {
		ResponseEntity<List<AccountBalanceResponce>> response = webClientBuilder.build().get()
				.uri(EndpointConstant.ENDPOINT_PAYMENT + EndpointConstant.METHOD_GET_ACCOUNT_BALANCE_DEBT)
				.retrieve()
				.toEntityList(AccountBalanceResponce.class)
				.block();
		
		HttpStatus statusCode = (HttpStatus) response.getStatusCode();
		if (statusCode == HttpStatus.OK) {
			List<TempTransaction> tempTransactions = new ArrayList<>();
 			
			List<AccountBalanceResponce> balanceResponces = response.getBody();
			
			for(AccountBalanceResponce balanceResponce : balanceResponces) {
				tempTransactions.add(TempTransaction.builder()
								.modeId("TS")
								.preassignId("66100001")
								.accountNo(balanceResponce.getAccountNo())
								.created(new Date())
								.createdBy("DebtService")
								.lastUpd(new Date())
								.lastUpdBy("DebtService")
								.build());
			}
			
			tempTransactionRepository.saveAll(tempTransactions);
		}
	}
	
	public void assignSMS() throws Exception {
		List<SMSTransaction> smsTransactions = new ArrayList<>();
		
		List<TempTransaction> tempTransactions = tempTransactionRepository.findAll();
			
		for(TempTransaction tempTransaction : tempTransactions) {
			ResponseEntity<List<MobileResponse>> responseMobile = webClientBuilder.build().get()
											.uri(EndpointConstant.ENDPOINT_ACCOUNT + EndpointConstant.ENDPOINT_ACCOUNT_GET_MOBILE_ACTIVE,
													uriBuilder -> 
														uriBuilder.queryParam("accountNo", tempTransaction.getAccountNo())
											.build())
											.retrieve()
											.toEntityList(MobileResponse.class)
											.block();
											
			HttpStatus activeCode = (HttpStatus) responseMobile.getStatusCode();
			if (activeCode == HttpStatus.OK) {
				List<MobileResponse> mobileResponses = responseMobile.getBody();
				
				List<SMSTransaction> smsTrans = mobileResponses.stream()
										.map(mobile -> mapMobileResponseToSMSTransaction(mobile))
										.toList();
				
				smsTransactionRepository.saveAll(smsTrans);
				smsTransactions.addAll(smsTrans);
			}
		}
		
		if(!smsTransactions.isEmpty())
			generateFileSMS(smsTransactions);
	}
}
