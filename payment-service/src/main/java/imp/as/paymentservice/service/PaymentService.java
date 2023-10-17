package imp.as.paymentservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.paymentservice.dto.request.AccountBalanceRequest;
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
	
	public AccountBalanceResponce initAccountBalance(AccountBalanceRequest accountBalanceRequest) {
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setAccountNo(accountBalanceRequest.getAccountNo());
		accountBalance.setTotalBalance(BigDecimal.ZERO);
		accountBalance.setCreated(new Date());
		accountBalance.setCreatedBy(accountBalanceRequest.getUserName());
		accountBalance.setLastUpd(new Date());
		accountBalance.setLastUpdBy(accountBalanceRequest.getUserName());
		
		pmAccountBalanceRepository.save(accountBalance);
		
		return AccountBalanceResponce.builder()
					.accountNo(accountBalanceRequest.getAccountNo())
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
