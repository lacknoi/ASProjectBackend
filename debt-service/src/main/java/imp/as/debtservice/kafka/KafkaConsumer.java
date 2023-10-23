package imp.as.debtservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import common.CreateAccountTopicRequest;

@Component
public class KafkaConsumer {
	@KafkaListener(topics = "create-account", groupId = "debt-group")
    public void consumeMessage(CreateAccountTopicRequest message) {
        System.out.println("Received message: " + message);
    }
}
