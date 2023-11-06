package imp.as.paymentservice.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.paymentservice.model.InvoiceBalance;

public interface InvoiceBalanceRepository extends JpaRepository<InvoiceBalance, String>{
	@Query("select max(invoiceNum) from InvoiceBalance")
	String getInvoiceCurrentSeq();
	@Query("SELECT MIN(dueDate) FROM InvoiceBalance e where e.accountNo = :accountNo and totalBalance > 0")
	Optional<Date> getMinInvoiceDueDate(String accountNo);
	
}
