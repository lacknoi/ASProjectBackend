package imp.as.accountservice.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imp.as.accountservice.dto.request.AccountRequest;
import imp.as.accountservice.dto.response.AccountResponse;
import imp.as.accountservice.dto.response.ApiResponse;
import imp.as.accountservice.kafka.CreateAccountProducerService;
import imp.as.accountservice.service.AccountService;

@RestController
@RequestMapping("/api/accountservice/account")
public class AccountController extends AbsController{
	@Autowired
	private AccountService accountService;
	@Autowired
	private CreateAccountProducerService createAccountProducerService;
	
	@GetMapping("/account")
	public ResponseEntity<ApiResponse> getAccount() {

		return responseOK(accountService.getAccount());
	}
	
	@PostMapping("/createAccount")
	public ResponseEntity<ApiResponse> createAccount(@RequestBody AccountRequest accountRequest) {
		AccountResponse accountResponse = accountService.createAccount(accountRequest);

		return responseOK(accountResponse);
	}
	
	@GetMapping("/test-write")
	public ResponseEntity<ApiResponse> testWriteKafka() {
		createAccountProducerService.sendMessage("AC");

		return responseOK("testWriteKafka");
	}
	
	@GetMapping("/test-read")
	public ResponseEntity<ApiResponse> testReadKafka() {
		String TOPIC = "create-account";
		
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList(TOPIC));
		
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
		for(ConsumerRecord<String, String> record : records) {
			System.out.println(record.offset() + ":" + record.key());
		}
		
		consumer.close();
		
		return responseOK("testReadKafka");
	}
}
