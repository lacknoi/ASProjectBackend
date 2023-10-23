package imp.as.paymentservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import common.CreateAccountTopicRequest;

@Component
public class KafkaConsumer {
	@KafkaListener(topics = "create-account", groupId = "payment-group")
    public void consumeMessage(CreateAccountTopicRequest message) {
        System.out.println("Received message: " + message);
    }
}
