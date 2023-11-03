package imp.as.paymentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imp.as.paymentservice.dto.request.AccountBalanceRequest;
import imp.as.paymentservice.dto.response.AccountBalanceResponce;
import imp.as.paymentservice.dto.response.ApiResponse;
import imp.as.paymentservice.dto.response.InvoiceResponse;
import imp.as.paymentservice.exception.BusinessException;
import imp.as.paymentservice.service.AccountBalanceService;
import imp.as.paymentservice.service.InvoiceService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController extends AbsController{
	@Autowired
	private AccountBalanceService accountBalanceService;
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/accountBalanceDebt")
	public ResponseEntity<ApiResponse> getAccountBalanceDebt() {
		List<AccountBalanceResponce> accountResponses = accountBalanceService.getAccountBalanceDebt();
		
		return responseOK(accountResponses);
	}
	
	@PostMapping("/generateInvoices")
	public ResponseEntity<ApiResponse> generateInvoices(@RequestBody List<AccountBalanceRequest> accountBalanceRequests
														, @RequestParam("userName") String userName
														, @RequestParam("year") Integer year
														, @RequestParam("month") Integer month) throws BusinessException{
		List<InvoiceResponse> invoiceResponses = invoiceService.generateInvoices(accountBalanceRequests, userName, year, month);
		
		return responseOK(invoiceResponses);
	}
}
