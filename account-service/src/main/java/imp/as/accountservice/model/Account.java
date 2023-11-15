package imp.as.accountservice.model;

import java.util.Date;
import java.util.List;

import imp.as.accountservice.dto.request.AccountTopicRequest;
import imp.as.accountservice.dto.response.AccountResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
	private String email;
	private String status;
	private Date statusDate;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Mobile> mobiles;
	
	@PrePersist
    protected void onCreate() {
		created = new Date();
		lastUpd = new Date();
    }
	
	@PreUpdate
    protected void onUpdate() {
		lastUpd = new Date();
    }
	
	public AccountResponse getAccountResponse() {
		return AccountResponse.builder()
				.accountNo(accountNo)
				.build();
	}
	
	public AccountTopicRequest getCreateAccountTopicRequest() {
		return AccountTopicRequest.builder()
				.accountNo(accountNo)
				.accountName(accountName)
				.email(email)
				.status(status)
				.statusDate(statusDate)
				.lastUpd(lastUpd)
				.build();
	}
}
