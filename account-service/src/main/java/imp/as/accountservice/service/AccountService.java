package imp.as.accountservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import imp.as.accountservice.constant.AppConstant;
import imp.as.accountservice.dto.request.AccountRequest;
import imp.as.accountservice.dto.response.AccountResponse;
import imp.as.accountservice.kafka.CreateAccountProducerService;
import imp.as.accountservice.model.Account;
import imp.as.accountservice.repository.AccountRepository;
import imp.as.accountservice.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService{
	@Autowired
	private final WebClient.Builder webClientBuilder;
	@Autowired
	private final AccountRepository accountRepository;
	@Autowired
	private CreateAccountProducerService createAccountProducerService;
	
	public String getNextAccountNo() {
		String dateFor = DateTimeUtils.formatDate(AppConstant.ACCOUNT_NO_FORMAT, DateTimeUtils.currentDate());
		
		Optional<String> preassignOpt = accountRepository.getNextValAccountNoSequence();
		
		if(!preassignOpt.isEmpty()) {
			Integer nextId = Integer.parseInt(preassignOpt.get().substring(preassignOpt.get().length() - 4)) + 1;
			
			return dateFor + String.format("%04d", nextId);
		}else {
			return dateFor + "0001";
		}
	}
	
	private Account generateAccount(AccountRequest accountRequest) {
		Account account = new Account();
		account.setAccountNo(getNextAccountNo());
		account.setAccountName(accountRequest.getAccountName());
		account.setCreated(new Date());
		account.setCreatedBy(accountRequest.getUserName());
		account.setLastUpd(new Date());
		account.setLastUpdBy(accountRequest.getUserName());
		
		return account;
	}
	
	public AccountResponse createAccount(AccountRequest accountRequest) {
		Account account = generateAccount(accountRequest);
		
		accountRepository.save(account);
		
		createAccountProducerService.sendMessage(account.getCreateAccountTopicRequest());
		
		return AccountResponse.builder()
					.accountNo(account.getAccountNo())
					.accountId(account.getAccountId())
					.build();
	}
	
	public List<AccountResponse> getAccount() {
		List<Account> accounts = accountRepository.findAll();
		
		return accounts.stream().map(Account::getAccountResponse).toList();
	}
}
