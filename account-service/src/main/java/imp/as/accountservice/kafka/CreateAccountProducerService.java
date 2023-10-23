package imp.as.accountservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import common.CreateAccountTopicRequest;

@Service
public class CreateAccountProducerService {
	@Autowired
	private KafkaTemplate<String, CreateAccountTopicRequest> kafkaCreateAccountTemplete;
	
    private static final String TOPIC = "create-account"; 

    public void sendMessage(String message) {
    	CreateAccountTopicRequest accountTopic = new CreateAccountTopicRequest();
    	accountTopic.setAccountNo("ACC01");
    	accountTopic.setUserName("UserName");
    	
    	kafkaCreateAccountTemplete.send(TOPIC, accountTopic);
    }
}
