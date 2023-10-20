package imp.as.debtservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imp.as.debtservice.dto.request.DebtCriteriaRequest;
import imp.as.debtservice.dto.response.ApiResponse;
import imp.as.debtservice.exception.BusinessException;
import imp.as.debtservice.service.DebtService;

@RestController
@RequestMapping("/api/debt")
public class DebtController extends AbsController{
	@Autowired
	private DebtService debtService;
	
	@PostMapping("/criteria")
	public ResponseEntity<ApiResponse> createCriteria(@RequestBody DebtCriteriaRequest criteriaRequest) {
		debtService.createCriteria(criteriaRequest);
		
		return responseOK("OK");
	}
	
	@GetMapping("/criterias")
	public ResponseEntity<ApiResponse> getAllCriterias(){
		return responseOK(debtService.getAllCriterias());
	}
	
	@PutMapping("/criteria/{criteriaId}")
	public ResponseEntity<ApiResponse> updateCriteria(
							@PathVariable Integer criteriaId
							,@RequestBody DebtCriteriaRequest criteriaRequest) throws BusinessException{
		return responseOK(debtService.updateCriteria(criteriaRequest));
	}
	
	@GetMapping("/criteria/{criteriaId}")
	public ResponseEntity<ApiResponse> getCriteriaById(
							@PathVariable Integer criteriaId) throws BusinessException{
		return responseOK(debtService.getCriteriaById(criteriaId));
	}
	
	@DeleteMapping("/criteria/{userId}")
	public ResponseEntity<ApiResponse> deleteCriteriaById(
							@PathVariable Integer criteriaId) throws BusinessException{
		return responseOK(debtService.deleteCriteriaById(criteriaId));
	}
}
