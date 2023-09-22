package imp.as.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.accountservice.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query(value = "SELECT nvl(max(accountId) + 1, 1) accountId from Account")
	Integer getNextValAccountNoSequence();
	
	Account getByAccountNo(String accountNo);
}
