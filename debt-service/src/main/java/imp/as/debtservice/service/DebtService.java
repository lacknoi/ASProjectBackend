package imp.as.debtservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.debtservice.constant.AppConstant;
import imp.as.debtservice.dto.request.DebtCriteriaRequest;
import imp.as.debtservice.dto.response.DebtCriteriaResponse;
import imp.as.debtservice.exception.BusinessException;
import imp.as.debtservice.model.DebtCriteria;
import imp.as.debtservice.repository.DebtCriteriaRepository;
import imp.as.debtservice.utils.DateUtil;
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
	
	public String getNextPreassignId(DebtCriteriaRequest criteriaRequest) {
		String key = DateUtil.convertDateToString(new Date(), AppConstant.PREASSIGN_ID_KEY);
		
		Optional<String> preassignOpt = criteriaRepository.getCurrentPreassignId(criteriaRequest.getModeId(), key + "%");
		
		if(!preassignOpt.isEmpty()) {
			Integer nextId = Integer.parseInt(preassignOpt.get().substring(preassignOpt.get().length() - 4)) + 1;
			
			return key + String.format("%04d", nextId);
		}else {
			return key + "0001";
		}
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
			
		criteria.setPreassignId(getNextPreassignId(criteriaRequest));
		criteria.setAssignStatus(AppConstant.ASSIGN_STATUS_PREASSIGN);
		criteria.setDebtAmtFrom(criteriaRequest.getDebtMnyFrom());
		criteria.setDebtAmtTo(criteriaRequest.getDebtMnyTo());
		criteria.setDebtAgeFrom(criteriaRequest.getDebtAgeFrom());
		criteria.setDebtAgeTo(criteriaRequest.getDebtAgeTo());
		criteria.setAccountStatusList(criteriaRequest.getAccountStatusList());
		criteria.setMobileStatusList(criteriaRequest.getMobileStatusList());
		criteria.setCreatedBy(criteriaRequest.getUserName());
		criteria.setLastUpdBy(criteriaRequest.getUserName());
			
		criteriaRepository.save(criteria);
	}
	
	public DebtCriteria getDebtCriteriaById(Integer criteriaId) throws BusinessException {
		Optional<DebtCriteria> criteriaOpt = criteriaRepository.findById(criteriaId);
		
		if (criteriaOpt.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return criteriaOpt.get();
	}
	
	public DebtCriteriaResponse getCriteriaById(Integer criteriaId) throws BusinessException {
		return getDebtCriteriaById(criteriaId).getDebtCriteriaResponse();
	}
	
	public List<DebtCriteria> getCriteriaByModeId(String modeId) throws BusinessException {
		Optional<List<DebtCriteria>> optional = criteriaRepository.findByModeId(modeId);
		
		return optional.get();
	}
	
	public List<DebtCriteria> getCriteriaByModeIdAndPreassignId(String modeId, String preassignId) throws BusinessException {
		Optional<List<DebtCriteria>> optional = criteriaRepository.findByModeIdAndPreassignId(modeId, preassignId);
		
		return optional.get();
	}
	
	public List<DebtCriteriaResponse> getCriteriaResponseByModeId(String modeId) throws BusinessException {
		return getCriteriaByModeId(modeId).stream()
						.map(DebtCriteria::getDebtCriteriaResponse)
						.toList();
	}
	
	public Integer deleteCriteriaById(Integer criteriaId) throws BusinessException {
		DebtCriteria debtCriteria = getDebtCriteriaById(criteriaId);
		
		criteriaRepository.delete(debtCriteria);
		
		return criteriaId;
	}
	
	public DebtCriteriaResponse updateCriteria(DebtCriteriaRequest request) throws BusinessException {
		DebtCriteria criteria = getDebtCriteriaById(request.getCriteriaId());
		
		criteria.setCriteriaDescription(request.getDescription());
		criteria.setDebtAmtFrom(request.getDebtMnyFrom());
		criteria.setDebtAmtTo(request.getDebtMnyTo());
		criteria.setLastUpd(new Date());
		criteria.setLastUpdBy(request.getUserName());
		
		return criteria.getDebtCriteriaResponse();
	}
}
