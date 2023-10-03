package imp.as.paymentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.paymentservice.model.AccountBalance;

public interface PMAccountBalanceRepository extends JpaRepository<AccountBalance, String> {
	@Query("SELECT e FROM AccountBalance e WHERE e.totalBalance > 0")
    List<AccountBalance> getAccountBalanceDebt();
}
