package imp.as.accountservice.dto.request;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountTopicRequest{
	private String accountNo;
	private String accountName;
	private String email;
	private String status;
	private Date statusDate;
	private Date lastUpd;
}
