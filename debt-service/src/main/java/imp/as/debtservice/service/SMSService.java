package imp.as.debtservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.debtservice.constant.EndpointConstant;
import imp.as.debtservice.dto.request.MessageRequest;
import imp.as.debtservice.dto.response.MessageResponse;
import imp.as.debtservice.dto.response.MobileResponse;
import imp.as.debtservice.model.Account;
import imp.as.debtservice.model.DebtCriteria;
import imp.as.debtservice.model.Message;
import imp.as.debtservice.model.Mobile;
import imp.as.debtservice.model.SMSTransaction;
import imp.as.debtservice.model.TempTransaction;
import imp.as.debtservice.repository.DebtCriteriaRepository;
import imp.as.debtservice.repository.MessageRepository;
import imp.as.debtservice.repository.SMSTransactionRepository;
import imp.as.debtservice.repository.TempTransactionRepository;
import imp.as.debtservice.utils.CsvWriter;
import imp.as.debtservice.utils.DateTimeUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SMSService{
	public final static String MODE_ID = "TS";
	
	public final static String ALL = "ALL";
	public final static String DELIMITED = ",";
	
	@Autowired
	private final WebClient.Builder webClientBuilder;
	@Autowired
	private final SMSTransactionRepository smsTransactionRepository;
	@Autowired
	private final TempTransactionRepository tempTransactionRepository;
	@Autowired
	private final DebtCriteriaRepository criteriaRepository;
	@Autowired
	private final MessageRepository messageRepository;
	@Autowired
	private final EntityManager em;
	@Autowired
	private final DebtService debtService;
	
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
	
	public void preassignSMS(String preassignId) throws Exception {
		List<DebtCriteria> criterias = debtService.getCriteriaByModeIdAndPreassignId(MODE_ID, preassignId);
		
		List<TempTransaction> transactions = new ArrayList<>();
				
		for(DebtCriteria criteria : criterias) {
			List<Account> accounts = queryPreassignSMS(criteria);
			
			for(Account account : accounts) {
				for(Mobile mobile : account.getMobiles()) {
					System.out.println("MobileNo : " + mobile.getMobileNo());
				}
				
				TempTransaction transaction = new TempTransaction();
				transaction.setModeId(criteria.getModeId());
				transaction.setPreassignId(criteria.getPreassignId());
				transaction.setAccountNo(account.getAccountNo());
				transaction.setCreatedBy("DEBTBATCH");
				transaction.setCreated(new Date());
				transaction.setLastUpdBy("DEBTBATCH");
				transaction.setDebtMny(account.getAccountBalance().getTotalBalance());
				transaction.setDebtAge(DateTimeUtils.getDayDifference(account.getAccountBalance().getMinInvoiceDueDate(), new Date()));
				transaction.setStatus(account.getStatus());
				transaction.setStatusDate(account.getStatusDate());
				transactions.add(transaction);
			}
			
			criteria.setPreassignDate(new Date());
		}
		
		criteriaRepository.saveAll(criterias);
		tempTransactionRepository.saveAll(transactions);
	}
	
	public List<Account> queryPreassignSMS(DebtCriteria debtCriteria) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        root.fetch("mobiles", JoinType.INNER);
        root.fetch("accountBalance", JoinType.INNER);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if(!StringUtils.equals(debtCriteria.getAccountStatusList(), ALL)) {
        	List<String> stringList = Arrays.asList(debtCriteria.getAccountStatusList().split(DELIMITED));
        	
        	// Create a list of predicates for each value in userIds
            Predicate[] predicat = stringList.stream()
                    .map(id -> cb.equal(root.get("status"), id))
                    .toArray(Predicate[]::new);
            
        	predicates.add(cb.or(predicat));
        }
        if(!StringUtils.equals(debtCriteria.getMobileStatusList(), ALL)) {
        	List<String> stringList = Arrays.asList(debtCriteria.getMobileStatusList().split(DELIMITED));
        	
        	// Create a list of predicates for each value in userIds
            Predicate[] predicat = stringList.stream()
                    .map(id -> cb.equal(root.get("mobiles").get("status"), id))
                    .toArray(Predicate[]::new);
            
        	predicates.add(cb.or(predicat));
        }
        predicates.add(cb.between(root.get("accountBalance").get("totalBalance")
        							, debtCriteria.getDebtAmtFrom(), debtCriteria.getDebtAmtTo()));
        predicates.add(cb.between(root.get("accountBalance").get("minInvoiceDueDate")
				, DateTimeUtils.addDay(new Date(), -1 * debtCriteria.getDebtAgeTo())
				, DateTimeUtils.addDay(new Date(), -1 * debtCriteria.getDebtAgeFrom())));

        query.where(predicates.toArray(new Predicate[0]));
        List<Account> accounts = em.createQuery(query).getResultList();
        
        return accounts;
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
