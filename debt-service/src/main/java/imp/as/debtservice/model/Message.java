package imp.as.debtservice.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_MESSAGE", schema = "USRDEBT")
@Builder
@SequenceGenerator(name = "AS_MESSAGE_SEQ", sequenceName = "AS_MESSAGE_SEQ", allocationSize = 1)
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_MESSAGE_SEQ")
	private Integer messageId;
	private String message;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
