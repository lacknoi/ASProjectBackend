package imp.as.debtservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.debtservice.constant.EndpointConstant;
import imp.as.debtservice.dto.request.MessageRequest;
import imp.as.debtservice.dto.response.AccountBalanceResponse;
import imp.as.debtservice.dto.response.MessageResponse;
import imp.as.debtservice.dto.response.MobileResponse;
import imp.as.debtservice.model.Message;
import imp.as.debtservice.model.SMSTransaction;
import imp.as.debtservice.model.TempTransaction;
import imp.as.debtservice.repository.MessageRepository;
import imp.as.debtservice.repository.SMSTransactionRepository;
import imp.as.debtservice.repository.TempTransactionRepository;
import imp.as.debtservice.utils.CsvWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SMSService{
	@Autowired
	private final WebClient.Builder webClientBuilder;
	@Autowired
	private final SMSTransactionRepository smsTransactionRepository;
	@Autowired
	private final TempTransactionRepository tempTransactionRepository;
	@Autowired
	private final MessageRepository messageRepository;
	@Autowired
	private final EntityManager em;
	
	public MessageResponse mapMessageToMessageResponse(Message message) {
		return MessageResponse.builder()
								.messageId(message.getMessageId())
								.message(message.getMessage())
								.build();
	}
	
	public MessageResponse getMessageById(Integer messageId){
		return mapMessageToMessageResponse(messageRepository.findById(messageId).get());
	}
	
	public List<MessageResponse> getAllMessage(){
		List<Message> messages = messageRepository.findAll();
		
		return messages.stream()
					.map(message -> mapMessageToMessageResponse(message))
					.toList();
	}
	
	public MessageResponse saveMessage(MessageRequest messageRequest) {
		Message mess = Message.builder()
								.message(messageRequest.getMessage())
								.created(new Date())
								.createdBy(messageRequest.getUserName())
								.lastUpd(new Date())
								.lastUpdBy(messageRequest.getUserName()).build();
		
		messageRepository.save(mess);
		
		return MessageResponse.builder()
						.messageId(mess.getMessageId()).build();
	}
	
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
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
