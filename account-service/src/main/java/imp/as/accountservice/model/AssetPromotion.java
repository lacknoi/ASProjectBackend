package imp.as.accountservice.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AS_AC_ASSET_PROMOTION", schema = "USRDEBT")
@SequenceGenerator(name = "AS_AC_ASSET_PROMOTION_ID_SEQ", sequenceName = "AS_AC_ASSET_PROMOTION_ID_SEQ", allocationSize = 1)
public class AssetPromotion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_AC_ASSET_PROMOTION_ID_SEQ")
	private Integer assetPromotionId;
	@ManyToOne
	@JoinColumn(name = "mobile_id")
	private Mobile mobile;
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private String status;
	private Date statusDate;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
