package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
