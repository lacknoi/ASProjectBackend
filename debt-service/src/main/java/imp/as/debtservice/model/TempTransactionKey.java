package imp.as.debtservice.model;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TempTransactionKey {
    private String modeId;
    private String preassignId;
    private String accountNo;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempTransactionKey)) return false;
        TempTransactionKey that = (TempTransactionKey) o;
        return Objects.equals(getModeId(), that.getModeId()) &&
               Objects.equals(getPreassignId(), that.getPreassignId()) &&
               Objects.equals(getAccountNo(), that.getAccountNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModeId(), getPreassignId(), getAccountNo());
    }
}
