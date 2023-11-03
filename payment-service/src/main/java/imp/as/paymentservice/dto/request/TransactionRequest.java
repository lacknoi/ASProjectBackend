package imp.as.paymentservice.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequest {
	private String accountNo;
	private String invoiceNum;
	private String movementType;
	private Integer seqNum;
	private Integer refId;
	private BigDecimal mny;
	private String userName;
}
