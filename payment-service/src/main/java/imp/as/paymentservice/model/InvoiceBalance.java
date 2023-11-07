package imp.as.paymentservice.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_PM_INVOICE_BALANCE", schema = "USRDEBT")
public class InvoiceBalance {
	@Id
	private String invoiceNum;
	private String accountNo;
	private String invoiceType;
	private Integer billSeq;
	private Date invoiceDate;
	private Date dueDate;
	private BigDecimal invoiceMny;
	private BigDecimal totalBalance;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	@PrePersist
    protected void onCreate() {
		created = new Date();
		lastUpd = new Date();
    }
	
	@PreUpdate
    protected void onUpdate() {
		lastUpd = new Date();
    }
}
