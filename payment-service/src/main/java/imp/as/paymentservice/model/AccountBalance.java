package imp.as.paymentservice.model;

import java.math.BigDecimal;
import java.util.Date;

import imp.as.paymentservice.dto.request.AccountBalanceTopicRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_PM_ACCOUNT_BALANCE", schema = "USRDEBT")
public class AccountBalance {
	@Id
	private String accountNo;
	private BigDecimal totalBalance;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	public AccountBalanceTopicRequest getAccountBalanceTopicRequest() {
		return AccountBalanceTopicRequest.builder()
									.accountNo(accountNo)
									.totalBalance(totalBalance)
									.lastUpd(lastUpd)
									.build();
	}
}
