package imp.as.paymentservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imp.as.paymentservice.dto.request.AccountTopicRequest;
import imp.as.paymentservice.exception.BusinessException;
import imp.as.paymentservice.service.AccountBalanceService;

@Component
public class KafkaConsumer {
	@Autowired
	private AccountBalanceService accountBalanceService;
	
	private static final String GROUPID = "payment-group";
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = "account-topic", groupId = GROUPID)
    public void consumeMessage(String message) throws JsonMappingException, JsonProcessingException, BusinessException {
        System.out.println("Received message: " + message);
        
        AccountTopicRequest accountTopicRequest = objectMapper.readValue(message, AccountTopicRequest.class);
        
        accountBalanceService.initAccountBalance(accountTopicRequest);
    }
}
