package imp.as.paymentservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.paymentservice.constant.AppConstant;
import imp.as.paymentservice.dto.request.AccountTopicRequest;
import imp.as.paymentservice.dto.response.AccountBalanceResponce;
import imp.as.paymentservice.exception.BusinessException;
import imp.as.paymentservice.kafka.ProducerService;
import imp.as.paymentservice.model.AccountBalance;
import imp.as.paymentservice.repository.PMAccountBalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService{
	@Autowired
	private final ProducerService producerService;
	
	@Autowired
	private final PMAccountBalanceRepository pmAccountBalanceRepository;
	
	public AccountBalanceResponce initAccountBalance(AccountTopicRequest accountRequest) throws BusinessException {
		try {
			AccountBalance accountBalance = new AccountBalance();
			accountBalance.setAccountNo(accountRequest.getAccountNo());
			accountBalance.setTotalBalance(BigDecimal.ZERO);
			accountBalance.setCreated(new Date());
			accountBalance.setCreatedBy(AppConstant.INIT_USER);
			accountBalance.setLastUpd(new Date());
			accountBalance.setLastUpdBy(AppConstant.INIT_USER);
			
			pmAccountBalanceRepository.save(accountBalance);
			
			producerService.sendMessageAccountBalanceTopic(accountBalance.getAccountBalanceTopicRequest());
			
			return AccountBalanceResponce.builder()
						.accountNo(accountRequest.getAccountNo())
						.build();
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public List<AccountBalanceResponce> getAccountBalanceDebt() {
		List<AccountBalance> accountBalances = pmAccountBalanceRepository.getAccountBalanceDebt();
		
		return accountBalances.stream()
						.map(AccountBalance::getAccountBalanceResponce)
						.toList();
	}
}
