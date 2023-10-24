package imp.as.paymentservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import common.CreateAccountTopicRequest;
import imp.as.paymentservice.service.PaymentService;

@Component
public class KafkaConsumer {
	@Autowired
	private PaymentService paymentService;
	
	@KafkaListener(topics = "create-account", groupId = "payment-group")
    public void consumeMessage(CreateAccountTopicRequest accountTopicRequest) {
        System.out.println("Received message: " + accountTopicRequest);
        
        paymentService.initAccountBalance(accountTopicRequest);
    }
}
