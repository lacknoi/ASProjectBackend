package imp.as.accountservice.controller;

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
import imp.as.accountservice.service.AccountService;

@RestController
@RequestMapping("/api/accountservice/account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/account")
	public ResponseEntity<AccountResponse> getAccount() {
		AccountResponse accountResponse = new AccountResponse();
		
//		iAccountService.getAccount();

		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}
	
	@PostMapping("/createAccount")
	public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
		AccountResponse accountResponse = accountService.createAccount(accountRequest);

		return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
	}
}
