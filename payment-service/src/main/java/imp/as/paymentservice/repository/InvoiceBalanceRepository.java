package imp.as.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.paymentservice.model.InvoiceBalance;

public interface InvoiceBalanceRepository extends JpaRepository<InvoiceBalance, String>{
	@Query("select max(invoiceNum) from InvoiceBalance")
	String getInvoiceCurrentSeq();
}
