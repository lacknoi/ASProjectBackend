package imp.as.debtservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.debtservice.dto.request.AccountBalanceTopicRequest;
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
	
	public void saveMobileTopicRequest(AccountBalanceTopicRequest topicRequest) {
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setAccountNo(topicRequest.getAccountNo());
		accountBalance.setTotalBalance(topicRequest.getTotalBalance());
		accountBalance.setLastUpd(topicRequest.getLastUpd());
		
		accountBalanceRepository.save(accountBalance);
	}
}
