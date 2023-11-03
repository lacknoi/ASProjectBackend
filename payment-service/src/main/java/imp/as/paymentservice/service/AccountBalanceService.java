package imp.as.paymentservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imp.as.paymentservice.constant.AppConstant;
import imp.as.paymentservice.dto.request.AccountTopicRequest;
import imp.as.paymentservice.dto.request.TransactionRequest;
import imp.as.paymentservice.dto.response.AccountBalanceResponce;
import imp.as.paymentservice.exception.BusinessException;
import imp.as.paymentservice.kafka.ProducerService;
import imp.as.paymentservice.model.ARTransaction;
import imp.as.paymentservice.model.AccountBalance;
import imp.as.paymentservice.repository.ARTransactionRepository;
import imp.as.paymentservice.repository.AccountBalanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountBalanceService{
	@Autowired
	private final ProducerService producerService;
	
	@Autowired
	private final AccountBalanceRepository accountBalanceRepository;
	@Autowired
	private final ARTransactionRepository arTransactionRepository;
	
	public AccountBalanceResponce initAccountBalance(AccountTopicRequest accountRequest) throws BusinessException {
		try {
			AccountBalance accountBalance = new AccountBalance();
			accountBalance.setAccountNo(accountRequest.getAccountNo());
			accountBalance.setTotalBalance(BigDecimal.ZERO);
			accountBalance.setCreated(new Date());
			accountBalance.setCreatedBy(AppConstant.INIT_USER);
			accountBalance.setLastUpd(new Date());
			accountBalance.setLastUpdBy(AppConstant.INIT_USER);
			
			accountBalanceRepository.save(accountBalance);
			
			producerService.sendMessageAccountBalanceTopic(accountBalance.getAccountBalanceTopicRequest());
			
			return AccountBalanceResponce.builder()
						.accountNo(accountRequest.getAccountNo())
						.build();
		}catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public List<AccountBalanceResponce> getAccountBalanceDebt() {
		List<AccountBalance> accountBalances = accountBalanceRepository.getAccountBalanceDebt();
		
		return accountBalances.stream()
						.map(AccountBalance::getAccountBalanceResponce)
						.toList();
	}
	
	public AccountBalance getAccountByAccountNo(String accountNo) throws BusinessException {
		Optional<AccountBalance> optional = accountBalanceRepository.findByAccountNo(accountNo);
		
		if (optional.isEmpty()) {
            throw new BusinessException("Data not found");
        }
		
		return optional.get();
	}
	
	private void generateARTransaction(TransactionRequest request) {
		ARTransaction transaction = new ARTransaction();
		transaction.setAccountNo(request.getAccountNo());
		transaction.setInvoiceNum(request.getInvoiceNum());
		
		if(request.getSeqNum() != null)
			transaction.setSeqNum(request.getSeqNum());
		else
			transaction.setSeqNum(arTransactionRepository.getMaxSeqNumByInvoiceNum(request.getInvoiceNum()).get() + 1);
		
		transaction.setMovementFlag(request.getMovementType());
		transaction.setMovementDate(new Date());
		transaction.setRefId(request.getRefId());
		transaction.setTotalMny(request.getMny());
		transaction.setCreated(new Date());
		transaction.setCreatedBy(request.getUserName());
		transaction.setLastUpd(new Date());
		transaction.setLastUpdBy(request.getUserName());
		
		arTransactionRepository.save(transaction);
	}
	
	public void updateAccountBalance(TransactionRequest request) throws BusinessException {
		generateARTransaction(request);
		
		AccountBalance account = getAccountByAccountNo(request.getAccountNo());
		
		if(StringUtils.equals(request.getMovementType(), AppConstant.MOVEMENT_TYPE_IN))
			account.setTotalBalance(account.getTotalBalance().add(request.getMny()));
		
		accountBalanceRepository.save(account);
	}
}
