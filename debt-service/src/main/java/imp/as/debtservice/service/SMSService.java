package imp.as.debtservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.debtservice.constant.AppConstant;
import imp.as.debtservice.dto.request.DebtCriteriaRequest;
import imp.as.debtservice.dto.request.MessageRequest;
import imp.as.debtservice.dto.response.MessageResponse;
import imp.as.debtservice.model.Account;
import imp.as.debtservice.model.DebtCriteria;
import imp.as.debtservice.model.Message;
import imp.as.debtservice.model.Mobile;
import imp.as.debtservice.model.SMSTransaction;
import imp.as.debtservice.model.TempTransaction;
import imp.as.debtservice.model.TransactionBacklog;
import imp.as.debtservice.repository.DebtCriteriaRepository;
import imp.as.debtservice.repository.MessageRepository;
import imp.as.debtservice.repository.MobileRepository;
import imp.as.debtservice.repository.SMSTransactionRepository;
import imp.as.debtservice.repository.TempTransactionRepository;
import imp.as.debtservice.repository.TransactionBacklogRepository;
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

	public final static String SUBJECT = "Debt payment reminder";
	
	@Autowired
	private final SMSTransactionRepository smsTransactionRepository;
	@Autowired
	private final TempTransactionRepository tempTransactionRepository;
	@Autowired
	private final DebtCriteriaRepository criteriaRepository;
	@Autowired
	private final MessageRepository messageRepository;
	@Autowired
	private final MobileRepository mobileRepository;
	@Autowired
	private final TransactionBacklogRepository backlogRepository;
	
	@Autowired
	private final EntityManager em;
	@Autowired
	private final DebtService debtService;
	@Autowired
	private final EmailService emailService;
	
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
	
	public void sendEmailSMS(List<SMSTransaction> smsTransactions) throws Exception {
		List<String> accounts = new ArrayList<>();
		
		for(SMSTransaction smsTransaction : smsTransactions) {
			if(!accounts.contains(smsTransaction.getAccountNo())) {
				emailService.sendEmail(smsTransaction.getEmail(), SUBJECT, "XX");
				
				accounts.add(smsTransaction.getAccountNo());
			}
		}
		
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
				transaction.setAccount(account);
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
	
	public boolean assignCheckCriteria(DebtCriteria criteria, TempTransaction transaction) {
		List<String> accountStatusList = Arrays.asList(criteria.getAccountStatusList().split(DELIMITED));
		List<String> mobileStatusList = Arrays.asList(criteria.getMobileStatusList().split(DELIMITED));
		
		//Account Status
		if(!accountStatusList.contains(transaction.getAccount().getStatus())) {
			backlogRepository.save(debtService.generateTransactionBacklog(criteria, transaction, 1));
			return false;
		}
		
		//Mobile Status
		boolean mobileBoo = false;
		for(Mobile mobile : transaction.getAccount().getMobiles()) {
			if(mobileStatusList.contains(mobile.getStatus())) {
				mobileBoo = true;
			}
		}
		if(!mobileBoo) {
			backlogRepository.save(debtService.generateTransactionBacklog(criteria, transaction, 2));
			return false;
		}
		
		//Debt amt
		if(!(criteria.getDebtAmtFrom().compareTo(transaction.getDebtMny()) <= 0
				&& criteria.getDebtAmtTo().compareTo(transaction.getDebtMny()) >= 0)) {
			backlogRepository.save(debtService.generateTransactionBacklog(criteria, transaction, 3));
			return false;
		}
		
		//Debt Age
		Date minDue = transaction.getAccount().getAccountBalance().getMinInvoiceDueDate();
		Date fromDue = DateTimeUtils.addDay(new Date(), -1 * criteria.getDebtAgeTo());
		Date toDue = DateTimeUtils.addDay(new Date(), -1 * criteria.getDebtAgeFrom());

		if(!(fromDue.compareTo(minDue) <= 0 && toDue.compareTo(minDue) >= 0)) {
			backlogRepository.save(debtService.generateTransactionBacklog(criteria, transaction, 4));
			return false;
		}
		
		return true;
	}
	
	public void assignSMS(String preassignId) throws Exception {
		List<DebtCriteria> criterias = debtService.getCriteriaByModeIdAndPreassignId(MODE_ID, preassignId);
		
		for(DebtCriteria criteria : criterias) {
			Optional<List<TempTransaction>> optional = tempTransactionRepository.findByModeIdAndPreassignId(MODE_ID, preassignId);
			
			if(!optional.isEmpty()) {
				List<SMSTransaction> smsTrans = new ArrayList<>();
				
				List<TempTransaction> tempTransactions = optional.get();
				
				for(TempTransaction tempTran : tempTransactions) {
					boolean res = assignCheckCriteria(criteria, tempTran);
					
					if(res) {
//						List<Mobile> mobiles = mobileRepository.getMobileActiveByAccountNo(tempTran.getAccount().getAccountNo());
						
						for(Mobile mobile : tempTran.getAccount().getMobiles()) {
							if(StringUtils.equalsIgnoreCase(mobile.getMobileNo(), ALL)) {
								SMSTransaction smsTransaction = new SMSTransaction();
								smsTransaction.setAccountNo(mobile.getAccount().getAccountNo());
								smsTransaction.setMobileNo(mobile.getMobileNo());
								smsTransaction.setEmail(mobile.getAccount().getEmail());
								smsTransaction.setCreatedBy(AppConstant.USER);
								smsTransaction.setLastUpdBy(AppConstant.USER);
								
								smsTrans.add(smsTransaction);
							}
						}
					}
				}
				
				smsTransactionRepository.saveAll(smsTrans);
				
				tempTransactionRepository.deleteAllInBatch(tempTransactions);
				
				if(!smsTrans.isEmpty()) {
					generateFileSMS(smsTrans);
					sendEmailSMS(smsTrans);
				}
			}
			
			String assignId = debtService.getNextAssignId(DebtCriteriaRequest.builder()
											.modeId(MODE_ID)
											.build());
			
			criteria.setAssignDate(new Date());
			criteria.setAssignId(assignId);
			criteria.setAssignStatus(AppConstant.ASSIGN_STATUS_ASSIGN);
			criteria.setUnassignDate(DateTimeUtils.addDay(new Date(), AppConstant.ASSIGN_DURETION_TS));
		}
		
		criteriaRepository.saveAll(criterias);
	}
}
