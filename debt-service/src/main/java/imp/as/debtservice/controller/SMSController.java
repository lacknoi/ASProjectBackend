package imp.as.debtservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import imp.as.debtservice.service.SMSService;

@RestController
@RequestMapping("/api/debt")
public class SMSController {
	@Autowired
	private SMSService smsService;

	@GetMapping("/test")
	public void test() {
		System.out.println("--test--");
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
//			smsService.preassignSMS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
