package common;

import java.util.Date;

import lombok.Data;

@Data
public class CreateAccountTopicRequest{
	private Integer accountId;
	private String accountNo;
	private String accountName;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
