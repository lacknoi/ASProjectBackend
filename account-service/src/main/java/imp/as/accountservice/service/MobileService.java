package imp.as.accountservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.accountservice.constant.AppConstant;
import imp.as.accountservice.dto.request.MobileRequest;
import imp.as.accountservice.dto.response.MobileResponse;
import imp.as.accountservice.exception.BusinessException;
import imp.as.accountservice.kafka.ProducerService;
import imp.as.accountservice.model.Account;
import imp.as.accountservice.model.Mobile;
import imp.as.accountservice.repository.AccountRepository;
import imp.as.accountservice.repository.MobileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MobileService{
	@Autowired
	private final WebClient.Builder webClientBuilder;
	
	//Repository
	@Autowired
	private final AccountRepository accountRepository;
	@Autowired
	private final MobileRepository mobileRepository;
	
	@Autowired
	private ProducerService producerService;
	
	//Service
	@Autowired final AccountService accountService;
	
	public MobileResponse registerMobile(MobileRequest mobileRequest) throws BusinessException {
		try {
			Account account = accountRepository.getByAccountNo(mobileRequest.getAccountNo());
			
			Mobile mobile = new Mobile();
			mobile.setAccount(account);
			mobile.setMobileNo(mobileRequest.getMobileNo());
			mobile.setMobileStatus(AppConstant.MOBILE_STATUS_ACTIVE);
			mobile.setCreated(new Date());
			mobile.setCreatedBy(mobileRequest.getUserName());
			mobile.setLastUpd(new Date());
			mobile.setLastUpdBy(mobileRequest.getUserName());
			
			mobileRepository.save(mobile);
			
			producerService.sendMessageMobileTopic(mobile.getMobileTopicRequest());
			
			return MobileResponse.builder()
								.accountNo(mobileRequest.getAccountNo())
								.mobileNo(mobileRequest.getMobileNo()).build();
		}catch (Exception e) {
			throw new BusinessException("Data not found");
		}
	}
	
	public List<MobileResponse> getMobileActiveByAccountNo(String accountNo){
		List<Mobile> mobiles = mobileRepository.getMobileActiveByAccountNo(accountNo);
		return mobiles.stream().map(Mobile::getMobileResponse)
				.toList();
	}
	
	public List<MobileResponse> getAllMobiles(){
		List<Mobile> mobiles = mobileRepository.findAll();
		
		return mobiles.stream().map(Mobile::getMobileResponse).toList();
	}
}
