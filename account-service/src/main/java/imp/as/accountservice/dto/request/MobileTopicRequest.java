package imp.as.accountservice.dto.request;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MobileTopicRequest {
	private Integer mobileId;
	private Integer accountId;
	private String mobileNo;
	private String status;
	private Date statusDate;
	private Date lastUpd;
}
