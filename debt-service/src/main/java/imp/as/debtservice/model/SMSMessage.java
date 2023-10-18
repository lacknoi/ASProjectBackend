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
@Table(name = "AS_SMS_MESSAGE", schema = "USRDEBT")
public class SMSMessage {
	@Id
	private Integer messageId;
	private String message;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
