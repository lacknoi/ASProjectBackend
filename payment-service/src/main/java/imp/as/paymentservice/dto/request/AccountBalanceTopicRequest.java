package imp.as.paymentservice.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBalanceTopicRequest {
	private Integer accountBalanceId;
	private String accountNo;
	private BigDecimal totalBalance;
	private Date lastUpd;
}
