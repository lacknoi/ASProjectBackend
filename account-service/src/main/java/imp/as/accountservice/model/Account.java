package imp.as.accountservice.model;

import java.util.Date;

import common.CreateAccountTopicRequest;
import imp.as.accountservice.dto.response.AccountResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_AC_ACCOUNTS", schema = "USRDEBT")
@SequenceGenerator(name = "AS_AC_ACCOUNT_ID_SEQ", sequenceName = "AS_AC_ACCOUNT_ID_SEQ", allocationSize = 1)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_AC_ACCOUNT_ID_SEQ")
    private Integer accountId;
	private String accountNo;
	private String accountName;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	public AccountResponse getAccountResponse() {
		return AccountResponse.builder()
				.accountNo(accountNo)
				.build();
	}
	
	public CreateAccountTopicRequest getCreateAccountTopicRequest() {
		return CreateAccountTopicRequest.builder()
				.accountId(accountId)
				.accountNo(accountNo)
				.accountName(accountName)
				.created(created)
				.createdBy(createdBy)
				.lastUpd(lastUpd)
				.lastUpdBy(lastUpdBy)
				.build();
	}
}
