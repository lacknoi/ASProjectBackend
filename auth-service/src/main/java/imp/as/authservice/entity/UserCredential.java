package imp.as.authservice.entity;

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
@Table(name = "AS_USERS", schema = "USRDEBT")
@SequenceGenerator(name = "AS_USERS_SEQ", sequenceName = "AS_USERS_SEQ", allocationSize = 1)
public class UserCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AS_USERS_SEQ")
    private Integer userId;
    private String name;
    private String email;
    private String password;
}
