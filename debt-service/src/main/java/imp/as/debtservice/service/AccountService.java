package imp.as.debtservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.CreateAccountTopicRequest;
import common.MobileTopicRequest;
import imp.as.debtservice.exception.BusinessException;
import imp.as.debtservice.model.Account;
import imp.as.debtservice.model.Mobile;
import imp.as.debtservice.repository.AccountRepository;
import imp.as.debtservice.repository.MobileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MobileRepository mobileRepository;
	
	public void createAccount(CreateAccountTopicRequest accountRequest) {
		Account account = new Account();
		account.setAccountId(accountRequest.getAccountId());
		account.setAccountNo(accountRequest.getAccountNo());
		account.setAccountName(accountRequest.getAccountName());
		account.setStatusCd(accountRequest.getStatusCd());
		account.setCreated(accountRequest.getCreated());
		account.setCreatedBy(accountRequest.getCreatedBy());
		account.setLastUpd(accountRequest.getLastUpd());
		account.setLastUpdBy(accountRequest.getLastUpdBy());
		
		accountRepository.save(account);
	}
	
	public Account getAccountById(Integer criteriaId) throws BusinessException {
		Optional<Account> criteriaOpt = accountRepository.findById(criteriaId);
		
		if (criteriaOpt.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return criteriaOpt.get();
	}
	
	public void createMobile(MobileTopicRequest mobileTopicRequest) throws BusinessException {
		Account account = getAccountById(mobileTopicRequest.getAccountId());
		
		Mobile mobile = new Mobile();
		mobile.setMobileId(mobileTopicRequest.getMobileId());
		mobile.setAccount(account);
		mobile.setMobileNo(mobileTopicRequest.getMobileNo());
		mobile.setMobileStatus(mobileTopicRequest.getMobileStatus());
		mobile.setCreated(mobileTopicRequest.getCreated());
		mobile.setCreatedBy(mobileTopicRequest.getCreatedBy());
		mobile.setLastUpd(mobileTopicRequest.getLastUpd());
		mobile.setLastUpdBy(mobileTopicRequest.getLastUpdBy());
		
		mobileRepository.save(mobile);
	}
}
