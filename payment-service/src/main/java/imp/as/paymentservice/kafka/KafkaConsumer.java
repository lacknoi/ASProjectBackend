package imp.as.paymentservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.CreateAccountTopicRequest;
import imp.as.paymentservice.service.PaymentService;

@Component
public class KafkaConsumer {
	@Autowired
	private PaymentService paymentService;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = "account-topic", groupId = "payment-group")
    public void consumeMessage(String message) throws JsonMappingException, JsonProcessingException {
        System.out.println("Received message: " + message);
        
        CreateAccountTopicRequest accountTopicRequest = objectMapper.readValue(message, CreateAccountTopicRequest.class);
        
        paymentService.initAccountBalance(accountTopicRequest);
    }
}
