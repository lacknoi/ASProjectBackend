package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.SMSTransaction;

public interface SMSTransactionRepository extends JpaRepository<SMSTransaction, String> {
}
