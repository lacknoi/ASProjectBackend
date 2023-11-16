package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.TransactionBacklog;

public interface TransactionBacklogRepository extends JpaRepository<TransactionBacklog, TransactionBacklogRepository>{

}
