package imp.as.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imp.as.accountservice.dto.request.AccountRequest;
import imp.as.accountservice.dto.response.AccountResponse;
import imp.as.accountservice.dto.response.ApiResponse;
import imp.as.accountservice.exception.BusinessException;
import imp.as.accountservice.service.AccountService;

@RestController
@RequestMapping("/api/accountservice/account")
public class AccountController extends AbsController{
	@Autowired
	private AccountService accountService;
	
	@GetMapping
	public ResponseEntity<ApiResponse> getAccount() {

		return responseOK(accountService.getAccount());
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse> createAccount(@RequestBody AccountRequest accountRequest) throws BusinessException {
		AccountResponse accountResponse = accountService.createAccount(accountRequest);

		return responseOK(accountResponse);
	}
	
	@GetMapping("genInvoice")
	public ResponseEntity<ApiResponse> generateInvoice(@RequestParam("year") Integer year,
												@RequestParam("month") Integer month,
												@RequestParam("userName") String userName) throws BusinessException{
		accountService.generateInvoice(year, month, userName);
		
		return responseOK("Generate Invoice");
	}
}
