package imp.as.debtservice.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class MobileTopicRequest {
	private Integer mobileId;
	private String accountNo;
	private String mobileNo;
	private String status;
	private Date statusDate;
	private Date lastUpd;
}
