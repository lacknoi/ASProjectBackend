package imp.as.debtservice.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DCC_SMS_TRANSACTION", schema = "USRDEBT")
@SequenceGenerator(name = "AS_SMS_TRANSACTION_SEQ", sequenceName = "AS_SMS_TRANSACTION_SEQ", allocationSize = 1)
@Builder
public class SMSTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_SMS_TRANSACTION_SEQ")
    private Integer smsTransactionId;
	private String accountNo;
	private String mobileNo;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
