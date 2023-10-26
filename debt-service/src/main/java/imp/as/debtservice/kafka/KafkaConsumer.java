package imp.as.debtservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import imp.as.debtservice.dto.request.AccountBalanceTopicRequest;
import imp.as.debtservice.dto.request.AccountTopicRequest;
import imp.as.debtservice.dto.request.MobileTopicRequest;
import imp.as.debtservice.service.AccountService;
import imp.as.debtservice.service.PaymentService;

@Component
public class KafkaConsumer {
	@Autowired
	private AccountService accountService;
	@Autowired
	private PaymentService paymentService;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String GROUPID = "debt-group";
	private static final String TOPIC_ACCOUNT = "account-topic";
    private static final String TOPIC_MOBILE = "mobile-topic";
	private static final String TOPIC_ACCOUNT_BALANCE = "account-balance-topic";
	
	@KafkaListener(topics = TOPIC_ACCOUNT, groupId = GROUPID)
    public void consumeAccountTopicMessage(String message){
		try {
	        System.out.println("Received message: " + message);
	        
	        AccountTopicRequest accountTopicRequest = objectMapper.readValue(message, AccountTopicRequest.class);
	        
	        accountService.createAccount(accountTopicRequest);
		}catch (Exception e) {

		}
    }
	
	@KafkaListener(topics = TOPIC_MOBILE, groupId = GROUPID)
    public void consumeMobileTopicMessage(String message){
		try {
	        System.out.println("Received message: " + message);
	        
	        MobileTopicRequest mobileTopicRequest = objectMapper.readValue(message, MobileTopicRequest.class);
	        
	        accountService.createMobile(mobileTopicRequest);
		}catch (Exception e) {

		}
    }
	
	@KafkaListener(topics = TOPIC_ACCOUNT_BALANCE, groupId = GROUPID)
    public void consumeAccountBalanceTopicMessage(String message){
		try {
	        System.out.println("Received message: " + message);
	        
	        AccountBalanceTopicRequest topicRequest = objectMapper.readValue(message, AccountBalanceTopicRequest.class);
	        
	        paymentService.saveMobileTopicRequest(topicRequest);
		}catch (Exception e) {

		}
    }
}
