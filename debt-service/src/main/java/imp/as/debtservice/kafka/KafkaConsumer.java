package imp.as.debtservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.CreateAccountTopicRequest;
import common.MobileTopicRequest;
import imp.as.debtservice.service.AccountService;

@Component
public class KafkaConsumer {
	@Autowired
	private AccountService accountService;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = "account-topic", groupId = "debt-group")
    public void consumeMessage(String message){
		try {
	        System.out.println("Received message: " + message);
	        
	        CreateAccountTopicRequest accountTopicRequest = objectMapper.readValue(message, CreateAccountTopicRequest.class);
	        
	        accountService.createAccount(accountTopicRequest);
		}catch (Exception e) {

		}
    }
	
	@KafkaListener(topics = "mobile-topic", groupId = "debt-group")
    public void consumeMobileTopicMessage(String message){
		try {
	        System.out.println("Received message: " + message);
	        
	        MobileTopicRequest mobileTopicRequest = objectMapper.readValue(message, MobileTopicRequest.class);
	        
	        accountService.createMobile(mobileTopicRequest);
		}catch (Exception e) {

		}
    }
}
