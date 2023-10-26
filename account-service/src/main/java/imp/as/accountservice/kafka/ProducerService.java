package imp.as.accountservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import imp.as.accountservice.dto.request.AccountTopicRequest;
import imp.as.accountservice.dto.request.MobileTopicRequest;

@Service
public class ProducerService {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplete;
	
    private static final String TOPIC_ACCOUNT = "account-topic";
    private static final String TOPIC_MOBILE = "mobile-topic";
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessageAccountTopic(AccountTopicRequest accountTopicRequest) throws JsonProcessingException {
    	String message = objectMapper.writeValueAsString(accountTopicRequest);
    	
    	kafkaTemplete.send(TOPIC_ACCOUNT, message);
    }
    
    public void sendMessageMobileTopic(MobileTopicRequest mobileTopicRequest) throws JsonProcessingException {
    	String message = objectMapper.writeValueAsString(mobileTopicRequest);
    	
    	kafkaTemplete.send(TOPIC_MOBILE, message);
    }
}
