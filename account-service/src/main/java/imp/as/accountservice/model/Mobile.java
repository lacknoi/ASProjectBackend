package imp.as.accountservice.model;

import java.util.Date;
import java.util.List;

import imp.as.accountservice.dto.request.MobileTopicRequest;
import imp.as.accountservice.dto.response.MobileResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String status;
	private Date statusDate;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	@OneToMany(mappedBy = "mobile", cascade = CascadeType.ALL)
	private List<AssetPromotion> assetPromotions;
	
	@PrePersist
    protected void onCreate() {
		created = new Date();
		lastUpd = new Date();
    }
	
	@PreUpdate
    protected void onUpdate() {
		lastUpd = new Date();
    }
	
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
						.accountNo(account.getAccountNo())
						.mobileNo(mobileNo)
						.status(status)
						.statusDate(statusDate)
						.lastUpd(lastUpd)
						.build();
	}
}
