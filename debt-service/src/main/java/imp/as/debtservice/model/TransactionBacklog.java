package imp.as.debtservice.model;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DCC_TRANSACTION_BACKLOG", schema = "USRDEBT")
@IdClass(TransactionBacklogKey.class)
public class TransactionBacklog {
	@Id
    private String modeId;
	@Id
    private String preassignId;
	@Id
    private String assignId;
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_no", referencedColumnName = "accountNo")
    private Account account;
	
	private Date preassignDate;
	private Date assignDate;
	private Integer reasonId;
	private Date created;
	private String createdBy;
	
	@PrePersist
    protected void onCreate() {
		created = new Date();
    }
	
	@PreUpdate
    protected void onUpdate() {
		created = new Date();
    }
}
