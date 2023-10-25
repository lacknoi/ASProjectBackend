package common;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MobileTopicRequest {
	private Integer mobileId;
	private Integer accountId;
	private String mobileNo;
	private String mobileStatus;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
