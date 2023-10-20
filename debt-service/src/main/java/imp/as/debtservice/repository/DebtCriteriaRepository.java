package imp.as.debtservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import imp.as.debtservice.model.DebtCriteria;

public interface DebtCriteriaRepository extends JpaRepository<DebtCriteria, Integer>{
	@Query(value = "select max(preassignId) from DebtCriteria where modeId = :modeId and preassignId like :key")
	Optional<String> getCurrentPreassignId(@Param("modeId") String modeId, @Param("key") String key);
}
