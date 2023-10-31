package imp.as.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.accountservice.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query(value = "SELECT max(accountNo) accountNo from Account")
	Optional<String> getNextValAccountNoSequence();
	
	Optional<Account> findByAccountNo(String accountNo);
}
