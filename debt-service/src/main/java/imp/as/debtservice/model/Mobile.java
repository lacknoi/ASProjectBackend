package imp.as.debtservice.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DCC_MOBILES", schema = "USRDEBT")
public class Mobile {
	@Id
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
}
