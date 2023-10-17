package imp.as.accountservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imp.as.accountservice.dto.request.MobileRequest;
import imp.as.accountservice.dto.response.MobileResponse;
import imp.as.accountservice.service.MobileService;

@RestController
@RequestMapping("/api/accountservice/mobile")
public class MobileController extends AbsController{
	@Autowired
	private MobileService mobileService;
	
	@PostMapping("/registerMobile")
	public ResponseEntity<MobileResponse> registerMobile(@RequestBody MobileRequest mobileRequest) {
		MobileResponse mobileResponse = mobileService.registerMobile(mobileRequest);
		
		return new ResponseEntity<>(mobileResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/mobileActive")
	public ResponseEntity<List<MobileResponse>> registerMobile(@RequestParam("accountNo") String accountNo) {
		List<MobileResponse> mobileResponse = mobileService.getMobileActiveByAccountNo(accountNo);
		
		return new ResponseEntity<>(mobileResponse, HttpStatus.OK);
	}
}
