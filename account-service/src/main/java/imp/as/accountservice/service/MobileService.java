package imp.as.accountservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.accountservice.constant.AppConstant;
import imp.as.accountservice.dto.request.MobileRequest;
import imp.as.accountservice.dto.response.MobileResponse;
import imp.as.accountservice.exception.BusinessException;
import imp.as.accountservice.kafka.ProducerService;
import imp.as.accountservice.model.Account;
import imp.as.accountservice.model.AssetPromotion;
import imp.as.accountservice.model.Mobile;
import imp.as.accountservice.model.Product;
import imp.as.accountservice.repository.AssetPromotionRepository;
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
	private final MobileRepository mobileRepository;
	@Autowired
	private final AssetPromotionRepository assetPromotionRepository;
	
	@Autowired
	private ProducerService producerService;
	
	//Service
	@Autowired 
	private final AccountService accountService;
	@Autowired
	private final ProductService productService;
	
	public String updateStatusMobile(MobileRequest mobileRequest) throws BusinessException {
		try {
			Optional<Mobile> optional = mobileRepository.getMobileByAccountAndMobileNo(mobileRequest.getAccountNo(), mobileRequest.getMobileNo());
		
			if(!optional.isEmpty()) {
				Mobile mobile = optional.get();
				
				mobile.setStatus(mobileRequest.getStatus());
				mobile.setStatusDate(new Date());
				
				mobileRepository.save(mobile);
				
				producerService.sendMessageMobileTopic(mobile.getMobileTopicRequest());
				
				return "Success";
			}
			
			return "Not found";
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public MobileResponse registerMobile(MobileRequest mobileRequest) throws BusinessException {
		try {
			Account account = accountService.getAccountByAccountNo(mobileRequest.getAccountNo());
			
			Mobile mobile = new Mobile();
			mobile.setAccount(account);
			mobile.setMobileNo(mobileRequest.getMobileNo());
			mobile.setStatus(AppConstant.MOBILE_STATUS_ACTIVE);
			mobile.setStatusDate(new Date());
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
	
	public Mobile getMobileByMobileId(Integer mobileId) throws BusinessException {
		Optional<Mobile> optional = mobileRepository.findById(mobileId);
		
		if (optional.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return optional.get();
	}
	
	public Mobile getMobileByMobileNo(String mobileNo) throws BusinessException {
		Optional<Mobile> optional = mobileRepository.findByMobileNo(mobileNo);
		
		if (optional.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return optional.get();
	}
	
	public void mobileAddPromotion(MobileRequest mobileRequest) throws BusinessException {
		try {
			Mobile mobile = getMobileByMobileId(mobileRequest.getMobileId());
			Product product = productService.getProductByProductId(mobileRequest.getProductId());
			
			AssetPromotion promotion = new AssetPromotion();
			promotion.setMobile(mobile);
			promotion.setProduct(product);
			promotion.setStatus(AppConstant.STATUS_ACTIVE);
			promotion.setStatusDate(new Date());
			promotion.setCreated(new Date());
			promotion.setCreatedBy(mobileRequest.getUserName());
			promotion.setLastUpd(new Date());
			promotion.setLastUpdBy(mobileRequest.getUserName());
			
			assetPromotionRepository.save(promotion);
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}
}
