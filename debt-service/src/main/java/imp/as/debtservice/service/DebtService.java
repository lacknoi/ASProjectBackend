package imp.as.debtservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.debtservice.constant.AppConstant;
import imp.as.debtservice.dto.request.DebtCriteriaRequest;
import imp.as.debtservice.dto.response.DebtCriteriaResponse;
import imp.as.debtservice.model.DebtCriteria;
import imp.as.debtservice.repository.DebtCriteriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DebtService {
	@Autowired
	private DebtCriteriaRepository criteriaRepository;
	
	public List<DebtCriteriaResponse> getAllCriterias() {
		List<DebtCriteria> criterias = criteriaRepository.findAll();
		
		return criterias.stream().map(DebtCriteria::getDebtCriteriaResponse).toList();
	}
	
	public void createCriteria(DebtCriteriaRequest criteriaRequest) {
//		System.out.println("Filename : " + criteriaRequest.getFile().getOriginalFilename());
		
		DebtCriteria criteria = new DebtCriteria();
		criteria.setModeId(criteriaRequest.getModeId());
		criteria.setCriteriaDescription(criteriaRequest.getDescription());
//		if(criteriaRequest.getFile() != null)
//			criteria.setCriteriaType(AppConstant.CRITERIA_TYPE_CRITERIA);
//		else
			criteria.setCriteriaType(AppConstant.CRITERIA_TYPE_LOAD);
		criteria.setAssignStatus(AppConstant.ASSIGN_STATUS_PREASSIGN);
		criteria.setDebtAmtFrom(criteriaRequest.getDebtMnyFrom());
		criteria.setDebtAmtTo(criteriaRequest.getDebtMnyTo());
		criteria.setCreated(new Date());
		criteria.setCreatedBy(criteriaRequest.getUserName());
		criteria.setLastUpd(new Date());
		criteria.setLastUpdBy(criteriaRequest.getUserName());
			
		criteriaRepository.save(criteria);
	}
	
}
