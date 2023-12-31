package imp.as.debtservice.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DCC_ACCOUNT_BALANCE", schema = "USRDEBT")
public class AccountBalance {
	@Id
	private Integer accountBalanceId;
	@OneToOne
	@JoinColumn(name = "account_no")
	private Account accountNo;
	private BigDecimal totalBalance;
	private Date minInvoiceDueDate;
	private Date lastUpd;
}
