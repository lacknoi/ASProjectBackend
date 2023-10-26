package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.AccountBalance;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {

}
