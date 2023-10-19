package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.DebtCriteria;

public interface DebtCriteriaRepository extends JpaRepository<DebtCriteria, Integer>{

}
