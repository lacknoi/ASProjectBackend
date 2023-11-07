package imp.as.paymentservice.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AS_PM_AR_TRANSACTION", schema = "USRDEBT")
@SequenceGenerator(name = "AS_PM_AR_TRANSACTION_ID_SEQ", sequenceName = "AS_PM_AR_TRANSACTION_ID_SEQ", allocationSize = 1)
public class ARTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_PM_AR_TRANSACTION_ID_SEQ")
	private Integer transactionId;
	private String accountNo;
	private String invoiceNum;
	private Integer seqNum;
	private String movementFlag;
	private Date movementDate;
	private Integer refId;
	private BigDecimal totalMny;
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
