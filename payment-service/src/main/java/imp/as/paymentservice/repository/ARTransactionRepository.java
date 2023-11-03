package imp.as.paymentservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.paymentservice.model.ARTransaction;

public interface ARTransactionRepository extends JpaRepository<ARTransaction, Integer>{
	@Query("select max(seqNum) from ARTransaction where invoiceNum = :invoiceNum")
	public Optional<Integer> getMaxSeqNumByInvoiceNum(String invoiceNum);
}
