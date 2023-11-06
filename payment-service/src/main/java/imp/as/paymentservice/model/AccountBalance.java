package imp.as.paymentservice.model;

import java.math.BigDecimal;
import java.util.Date;

import imp.as.paymentservice.dto.request.AccountBalanceTopicRequest;
import imp.as.paymentservice.dto.response.AccountBalanceResponce;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_PM_ACCOUNT_BALANCE", schema = "USRDEBT")
@SequenceGenerator(name = "AS_PM_ACCOUNT_BALANCE_ID_SEQ", sequenceName = "AS_PM_ACCOUNT_BALANCE_ID_SEQ", allocationSize = 1)
public class AccountBalance {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_PM_ACCOUNT_BALANCE_ID_SEQ")
	private Integer accountBalanceId;
	private String accountNo;
	private BigDecimal totalBalance;
	private Date minInvoiceDueDate;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	public AccountBalanceTopicRequest getAccountBalanceTopicRequest() {
		return AccountBalanceTopicRequest.builder()
									.accountBalanceId(accountBalanceId)
									.accountNo(accountNo)
									.totalBalance(totalBalance)
									.minInvoiceDueDate(minInvoiceDueDate)
									.lastUpd(lastUpd)
									.build();
	}
	
	public AccountBalanceResponce getAccountBalanceResponce() {
		return AccountBalanceResponce.builder()
						.accountNo(accountNo)
						.totalBalance(totalBalance)
						.build();
	}
}
