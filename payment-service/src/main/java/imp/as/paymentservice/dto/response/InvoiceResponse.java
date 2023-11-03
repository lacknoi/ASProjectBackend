package imp.as.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
	private String invoiceNum;
	private String accountNo;
}
