package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.TempTransaction;
import imp.as.debtservice.model.TempTransactionKey;

public interface TempTransactionRepository extends JpaRepository<TempTransaction, TempTransactionKey> {

}
