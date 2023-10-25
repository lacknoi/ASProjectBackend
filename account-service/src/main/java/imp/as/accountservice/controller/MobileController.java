package imp.as.accountservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imp.as.accountservice.dto.request.MobileRequest;
import imp.as.accountservice.dto.response.ApiResponse;
import imp.as.accountservice.dto.response.MobileResponse;
import imp.as.accountservice.exception.BusinessException;
import imp.as.accountservice.service.MobileService;

@RestController
@RequestMapping("/api/accountservice/mobile")
public class MobileController extends AbsController{
	@Autowired
	private MobileService mobileService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> registerMobile(@RequestBody MobileRequest mobileRequest) throws BusinessException {
		MobileResponse mobileResponse = mobileService.registerMobile(mobileRequest);
		
		return responseOK(mobileResponse);
	}
	
	@GetMapping("/mobileActive")
	public ResponseEntity<ApiResponse> registerMobile(@RequestParam("accountNo") String accountNo) {
		List<MobileResponse> mobileResponse = mobileService.getMobileActiveByAccountNo(accountNo);
		
		return responseOK(mobileResponse);
	}
	
	@GetMapping("/mobiles")
	public ResponseEntity<ApiResponse> getAllMobiles() {
		List<MobileResponse> mobileResponse = mobileService.getAllMobiles();
		
		return responseOK(mobileResponse);
	}
}
