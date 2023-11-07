package imp.as.debtservice.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebtCriteriaRequest {
	private Integer criteriaId;
	private String modeId;
	private String description;
	private BigDecimal debtMnyFrom;
	private BigDecimal debtMnyTo;
	private Integer debtAgeFrom;
	private Integer debtAgeTo;
	private String accountStatusList;
	private String mobileStatusList;
	private String userName;
}
