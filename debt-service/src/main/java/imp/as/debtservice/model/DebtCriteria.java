package imp.as.debtservice.model;

import java.math.BigDecimal;
import java.util.Date;

import imp.as.debtservice.dto.response.DebtCriteriaResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_DEBT_CRITERIA", schema = "USRDEBT")
@SequenceGenerator(name = "AS_CRITERIA_ID_SEQ", sequenceName = "AS_CRITERIA_ID_SEQ", allocationSize = 1)
public class DebtCriteria {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_CRITERIA_ID_SEQ")
    private Integer criteriaId;
	private String modeId;
	private String criteriaType;
	private String criteriaDescription;
	private String assignStatus;
	private String preassignId;
	private Date preassignDate;
	private String assignId;
	private Date assignDate;
	private Date unassignDate;
	private BigDecimal debtAmtFrom;
	private BigDecimal debtAmtTo;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
	
	public DebtCriteriaResponse getDebtCriteriaResponse() {
		return DebtCriteriaResponse.builder()
								.modeId(modeId)
								.preassignId(preassignId)
								.criteriaId(criteriaId)
								.build();
	}
}
