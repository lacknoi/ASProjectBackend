package imp.as.debtservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.TempTransaction;
import imp.as.debtservice.model.TempTransactionKey;

public interface TempTransactionRepository extends JpaRepository<TempTransaction, TempTransactionKey> {
	Optional<List<TempTransaction>> findByModeIdAndPreassignId(String modeId, String preassignId);
}
