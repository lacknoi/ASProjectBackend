package imp.as.paymentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imp.as.paymentservice.dto.request.AccountBalanceRequest;
import imp.as.paymentservice.dto.response.AccountBalanceResponce;
import imp.as.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payment")
	public String test() {
		return "payment";
	}
	
	@PostMapping("/initAccountBalance")
	public ResponseEntity<AccountBalanceResponce> initAccountBalance(@RequestBody AccountBalanceRequest accountBalanceRequest) {
		AccountBalanceResponce accountResponse = paymentService.initAccountBalance(accountBalanceRequest);
		
		return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/accountBalanceDebt")
	public ResponseEntity<List<AccountBalanceResponce>> getAccountBalanceDebt() {
		List<AccountBalanceResponce> accountResponses = paymentService.getAccountBalanceDebt();
		
		return new ResponseEntity<>(accountResponses, HttpStatus.OK);
	}
}
