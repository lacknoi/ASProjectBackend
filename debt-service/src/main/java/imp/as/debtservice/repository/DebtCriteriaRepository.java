package imp.as.debtservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import imp.as.debtservice.model.DebtCriteria;

public interface DebtCriteriaRepository extends JpaRepository<DebtCriteria, Integer>{
	@Query(value = "select max(preassignId) from DebtCriteria where modeId = :modeId and preassignId like :key")
	Optional<String> getCurrentPreassignId(@Param("modeId") String modeId, @Param("key") String key);
	
	@Query(value = "select max(assignId) from DebtCriteria where modeId = :modeId and assignId like :key")
	Optional<String> getCurrentAssignId(@Param("modeId") String modeId, @Param("key") String key);
	
	Optional<List<DebtCriteria>> findByModeId(String modeId);
	Optional<List<DebtCriteria>> findByModeIdAndPreassignId(String modeId, String preassignId);
}
