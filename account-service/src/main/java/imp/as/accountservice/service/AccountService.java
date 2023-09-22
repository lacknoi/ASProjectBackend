package imp.as.accountservice.service;

import imp.as.accountservice.dto.AccountRequest;
import imp.as.accountservice.dto.AccountResponse;

public interface AccountService {
	public AccountResponse createAccount(AccountRequest accountRequest);
}
