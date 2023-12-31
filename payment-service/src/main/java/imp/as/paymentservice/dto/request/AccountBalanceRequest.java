package imp.as.paymentservice.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceRequest {
	private String accountNo;
	private BigDecimal totalBalance;
}
