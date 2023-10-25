package imp.as.debtservice.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String statusCd;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
