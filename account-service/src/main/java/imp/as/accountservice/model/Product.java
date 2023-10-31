package imp.as.accountservice.model;

import java.math.BigDecimal;
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
@Table(name = "AS_AC_PRODUCT", schema = "USRDEBT")
public class Product {
	@Id
	private Integer productId;
	private String productCd;
	private String productName;
	private String productClass;
	private BigDecimal price;
	private String status;
	private Date statusDate;
	private Date created;
	private String createdBy;
	private Date lastUpd;
	private String lastUpdBy;
}
