package imp.as.debtservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.debtservice.dto.request.AccountBalanceTopicRequest;
import imp.as.debtservice.exception.BusinessException;
import imp.as.debtservice.model.AccountBalance;
import imp.as.debtservice.repository.AccountBalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
	@Autowired
	private AccountBalanceRepository accountBalanceRepository;
	
	@Autowired
	private AccountService accountService;
	
	public void saveAccountBalance(AccountBalanceTopicRequest topicRequest) throws BusinessException {
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setAccountBalanceId(topicRequest.getAccountBalanceId());
		accountBalance.setAccountNo(accountService.getAccountByNo(topicRequest.getAccountNo()));
		accountBalance.setTotalBalance(topicRequest.getTotalBalance());
		accountBalance.setLastUpd(topicRequest.getLastUpd());
		
		accountBalanceRepository.save(accountBalance);
	}
}
