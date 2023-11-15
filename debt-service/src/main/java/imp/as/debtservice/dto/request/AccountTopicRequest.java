package imp.as.debtservice.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class AccountTopicRequest{
	private String accountNo;
	private String accountName;
	private String email;
	private String status;
	private Date statusDate;
	private Date lastUpd;
}
