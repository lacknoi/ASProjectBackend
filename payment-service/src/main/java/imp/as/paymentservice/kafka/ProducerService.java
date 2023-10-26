package imp.as.paymentservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imp.as.paymentservice.dto.request.AccountBalanceTopicRequest;

@Service
public class ProducerService {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplete;
	
	private static final String TOPIC_ACCOUNT_BALANCE = "account-balance-topic";
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public void sendMessageAccountBalanceTopic(AccountBalanceTopicRequest accountBalanceTopicRequest) throws JsonProcessingException {
    	String message = objectMapper.writeValueAsString(accountBalanceTopicRequest);
    	
    	kafkaTemplete.send(TOPIC_ACCOUNT_BALANCE, message);
    }
}
