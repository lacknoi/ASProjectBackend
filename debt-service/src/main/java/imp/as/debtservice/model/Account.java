package imp.as.debtservice.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DCC_ACCOUNTS", schema = "USRDEBT")
public class Account {
	@Id
    private Integer accountId;
	private String accountNo;
	private String accountName;
	private String status;
	private Date statusDate;
	private Date lastUpd;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Mobile> mobiles;
	@OneToOne(mappedBy = "accountNo", cascade = CascadeType.ALL)
	private AccountBalance accountBalance;
}
