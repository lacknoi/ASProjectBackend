package imp.as.authservice.entity;

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
@Table(name = "AS_USERS", schema = "USRDEBT")
public class UserCredential {
	@Id
    private Integer userId;
    private String name;
    private String email;
    private String password;
}
