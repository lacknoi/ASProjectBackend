package imp.as.accountservice.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.accountservice.constant.AppConstant;
import imp.as.accountservice.dto.AccountRequest;
import imp.as.accountservice.dto.AccountResponse;
import imp.as.accountservice.dto.MobileRequest;
import imp.as.accountservice.dto.MobileResponse;
import imp.as.accountservice.model.Account;
import imp.as.accountservice.model.Mobile;
import imp.as.accountservice.repository.AccountRepository;
import imp.as.accountservice.repository.MobileRepository;
import imp.as.accountservice.service.AccountService;
import imp.as.accountservice.service.MobileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MobileServiceImpl implements MobileService{
	@Autowired
	private final WebClient.Builder webClientBuilder;
	
	//Repository
	@Autowired
	private final AccountRepository accountRepository;
	@Autowired
	private final MobileRepository mobileRepository;
	
	//Service
	@Autowired final AccountService accountService;
	
	public MobileResponse registerMobile(MobileRequest mobileRequest) {
		Account account = accountRepository.getByAccountNo(mobileRequest.getAccountNo());
		
		if(account == null) {
			AccountResponse accountResponse = accountService.createAccount(AccountRequest.builder().accountName(mobileRequest.getAccountNo())
																.userName(mobileRequest.getUserName()).build());
			
			account = new Account();
			account.setAccountId(accountResponse.getAccountId());
		}
		
		Integer mobileId = mobileRepository.getNextValMobileSequence();
		
		Mobile mobile = new Mobile();
		mobile.setMobileId(mobileId);
		mobile.setAccount(account);
		mobile.setMobileNo(mobileRequest.getMobileNo());
		mobile.setMobileStatus(AppConstant.MOBILE_STATUS_ACTIVE);
		mobile.setCreated(new Date());
		mobile.setCreatedBy(mobileRequest.getUserName());
		mobile.setLastUpd(new Date());
		mobile.setLastUpdBy(mobileRequest.getUserName());
		
		mobileRepository.save(mobile);
		
		return MobileResponse.builder()
							.accountNo(mobileRequest.getAccountNo())
							.mobileNo(mobileRequest.getMobileNo()).build();
	}
}