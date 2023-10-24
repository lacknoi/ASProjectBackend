package imp.as.paymentservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.CreateAccountTopicRequest;
import imp.as.paymentservice.dto.response.AccountBalanceResponce;
import imp.as.paymentservice.model.AccountBalance;
import imp.as.paymentservice.repository.PMAccountBalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService{
	@Autowired
	private final PMAccountBalanceRepository pmAccountBalanceRepository;
	
	public AccountBalanceResponce initAccountBalance(CreateAccountTopicRequest accountRequest) {
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setAccountNo(accountRequest.getAccountNo());
		accountBalance.setTotalBalance(BigDecimal.ZERO);
		accountBalance.setCreated(new Date());
		accountBalance.setCreatedBy(accountRequest.getLastUpdBy());
		accountBalance.setLastUpd(new Date());
		accountBalance.setLastUpdBy(accountRequest.getLastUpdBy());
		
		pmAccountBalanceRepository.save(accountBalance);
		
		return AccountBalanceResponce.builder()
					.accountNo(accountRequest.getAccountNo())
					.build();
	}
	
	public AccountBalanceResponce mapAccountBalanceToAccountBalanceResponce(AccountBalance accountBalance) {
		return AccountBalanceResponce.builder()
						.accountNo(accountBalance.getAccountNo())
						.totalBalance(accountBalance.getTotalBalance())
						.build();
	}
	
	public List<AccountBalanceResponce> getAccountBalanceDebt() {
		List<AccountBalance> accountBalances = pmAccountBalanceRepository.getAccountBalanceDebt();
		
		return accountBalances.stream().map(accountBalance -> mapAccountBalanceToAccountBalanceResponce(accountBalance))
				.toList();
	}
}
