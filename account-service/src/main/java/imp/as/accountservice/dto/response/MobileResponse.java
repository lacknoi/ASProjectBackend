package imp.as.accountservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MobileResponse {
	private Integer mobileId;
	private String accountNo;
	private String mobileNo;
}
