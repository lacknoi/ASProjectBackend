package imp.as.debtservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import common.CreateAccountTopicRequest;
import imp.as.debtservice.service.AccountService;

@Component
public class KafkaConsumer {
	@Autowired
	private AccountService accountService;
	
	@KafkaListener(topics = "create-account", groupId = "debt-group")
    public void consumeMessage(CreateAccountTopicRequest accountTopicRequest) {
        System.out.println("Received message: " + accountTopicRequest);
        
        accountService.createAccount(accountTopicRequest);
    }
}
