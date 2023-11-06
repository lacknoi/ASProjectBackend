package imp.as.debtservice.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class AccountBalanceTopicRequest {
	private Integer accountBalanceId;
	private String accountNo;
	private BigDecimal totalBalance;
	private Date minInvoiceDueDate;
	private Date lastUpd;
}
