package imp.as.debtservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.debtservice.dto.request.AccountTopicRequest;
import imp.as.debtservice.dto.request.MobileTopicRequest;
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
	
	public void createAccount(AccountTopicRequest topicRequest) {
		Account account = new Account();
		account.setAccountId(topicRequest.getAccountId());
		account.setAccountNo(topicRequest.getAccountNo());
		account.setAccountName(topicRequest.getAccountName());
		account.setEmail(topicRequest.getEmail());
		account.setStatus(topicRequest.getStatus());
		account.setStatusDate(topicRequest.getStatusDate());
		account.setLastUpd(topicRequest.getLastUpd());
		
		accountRepository.save(account);
	}
	
	public Account getAccountById(Integer accountId) throws BusinessException {
		Optional<Account> criteriaOpt = accountRepository.findById(accountId);
		
		if (criteriaOpt.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return criteriaOpt.get();
	}
	
	public Account getAccountByNo(String accountNo) throws BusinessException {
		Optional<Account> criteriaOpt = accountRepository.findByAccountNo(accountNo);
		
		if (criteriaOpt.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return criteriaOpt.get();
	}
	
	public void createMobile(MobileTopicRequest topicRequest) throws BusinessException {
		Account account = getAccountById(topicRequest.getAccountId());
		
		Mobile mobile = new Mobile();
		mobile.setMobileId(topicRequest.getMobileId());
		mobile.setAccount(account);
		mobile.setMobileNo(topicRequest.getMobileNo());
		mobile.setStatus(topicRequest.getStatus());
		mobile.setStatusDate(topicRequest.getStatusDate());
		mobile.setLastUpd(topicRequest.getLastUpd());
		
		mobileRepository.save(mobile);
	}
}
