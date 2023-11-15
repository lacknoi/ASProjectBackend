package imp.as.debtservice.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DCC_TEMP_TRANSACTION", schema = "USRDEBT")
@IdClass(TempTransactionKey.class)
@Builder
public class TempTransaction {
	@Id
    private String modeId;
	@Id
    private String preassignId;
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_no", referencedColumnName = "accountNo")
    private Account account;
	private BigDecimal debtMny;
	private Integer debtAge;
	private String status;
	private Date statusDate;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	@PrePersist
    protected void onCreate() {
		created = new Date();
		lastUpd = new Date();
    }
	
	@PreUpdate
    protected void onUpdate() {
		lastUpd = new Date();
    }
}
