package imp.as.debtservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imp.as.debtservice.dto.request.DebtCriteriaRequest;
import imp.as.debtservice.dto.response.ApiResponse;
import imp.as.debtservice.service.DebtService;

@RestController
@RequestMapping("/api/debt")
public class DebtController extends AbsController{
	@Autowired
	private DebtService debtService;
	
	@PostMapping("/createCriteria")
	public ResponseEntity<ApiResponse> createCriteria(@RequestBody DebtCriteriaRequest criteriaRequest) {
		debtService.createCriteria(criteriaRequest);
		
		return responseOK("OK");
	}
	
	@GetMapping("/criterias")
	public ResponseEntity<ApiResponse> getAllCriterias(){
		return responseOK(debtService.getAllCriterias());
	}
}
