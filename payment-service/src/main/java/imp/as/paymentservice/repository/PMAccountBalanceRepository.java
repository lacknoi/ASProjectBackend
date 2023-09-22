package imp.as.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.paymentservice.model.AccountBalance;

public interface PMAccountBalanceRepository extends JpaRepository<AccountBalance, String> {
}
