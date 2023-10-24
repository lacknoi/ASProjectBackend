package imp.as.debtservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.CreateAccountTopicRequest;
import imp.as.debtservice.model.Account;
import imp.as.debtservice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	public void createAccount(CreateAccountTopicRequest accountRequest) {
		System.out.println("--accountRequest--");
		
		Account account = new Account();
		account.setAccountId(accountRequest.getAccountId());
		account.setAccountNo(accountRequest.getAccountNo());
		account.setAccountName(accountRequest.getAccountName());
		account.setCreated(accountRequest.getCreated());
		account.setCreatedBy(accountRequest.getCreatedBy());
		account.setLastUpd(accountRequest.getLastUpd());
		account.setLastUpdBy(accountRequest.getLastUpdBy());
		
		accountRepository.save(account);
	}
}
