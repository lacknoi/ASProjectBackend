package imp.as.accountservice.model;

import java.util.Date;

import common.MobileTopicRequest;
import imp.as.accountservice.dto.response.MobileResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_AC_MOBILES", schema = "USRDEBT")
@SequenceGenerator(name = "AS_AC_MOBILE_ID_SEQ", sequenceName = "AS_AC_MOBILE_ID_SEQ", allocationSize = 1)
public class Mobile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_AC_MOBILE_ID_SEQ")
    private Integer mobileId;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	private String mobileNo;
	private String mobileStatus;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	public MobileResponse getMobileResponse() {
		return MobileResponse.builder()
						.mobileId(mobileId)
						.accountNo(account.getAccountNo())
						.mobileNo(mobileNo)
						.build();
	}
	
	public MobileTopicRequest getMobileTopicRequest() {
		return MobileTopicRequest.builder()
						.mobileId(mobileId)
						.accountId(account.getAccountId())
						.mobileNo(mobileNo)
						.mobileStatus(mobileStatus)
						.created(created)
						.createdBy(createdBy)
						.lastUpd(lastUpd)
						.lastUpdBy(lastUpdBy)
						.build();
	}
}
