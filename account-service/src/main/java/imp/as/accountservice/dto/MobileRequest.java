package imp.as.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileRequest {
	private String accountNo;
	private String mobileNo;
	private String userName;
}
