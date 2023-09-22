package imp.as.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imp.as.accountservice.dto.MobileRequest;
import imp.as.accountservice.dto.MobileResponse;
import imp.as.accountservice.service.MobileService;

@RestController
@RequestMapping("/api/mobile")
public class MobileController {
	@Autowired
	private MobileService mobileService;
	
	@PostMapping("/registerMobile")
	public ResponseEntity<MobileResponse> registerMobile(@RequestBody MobileRequest mobileRequest) {
		MobileResponse mobileResponse = mobileService.registerMobile(mobileRequest);
		
		return new ResponseEntity<>(mobileResponse, HttpStatus.CREATED);
	}
}
