package imp.as.debtservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imp.as.debtservice.dto.request.MessageRequest;
import imp.as.debtservice.dto.response.ApiResponse;
import imp.as.debtservice.service.SMSService;

@RestController
@RequestMapping("/api/debt")
public class SMSController extends AbsController{
	@Autowired
	private SMSService smsService;

	@PostMapping("/saveMessage")
	public ResponseEntity<ApiResponse> saveMessage(@RequestBody MessageRequest messageRequest) {
		return responseOK(smsService.saveMessage(messageRequest));
	}
	
	@GetMapping("/assignSMS")
	public void assignSMS() {
		try {
			smsService.assignSMS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/preassignSMS")
	public void preassignSMS() {
		try {
			smsService.preassignSMS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
