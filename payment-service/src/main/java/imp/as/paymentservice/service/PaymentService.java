package imp.as.paymentservice.service;

import java.util.List;

import imp.as.paymentservice.dto.AccountBalanceRequest;
import imp.as.paymentservice.dto.AccountBalanceResponce;

public interface PaymentService {
	public AccountBalanceResponce initAccountBalance(AccountBalanceRequest accountBalanceRequest);
	public List<AccountBalanceResponce> getAccountBalanceDebt();
}
