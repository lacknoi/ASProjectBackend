package imp.as.accountservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileRequest {
	private String accountNo;
	private String mobileNo;
	private String status;
	private Integer mobileId;
	private Integer productId;
	private String userName;
}
