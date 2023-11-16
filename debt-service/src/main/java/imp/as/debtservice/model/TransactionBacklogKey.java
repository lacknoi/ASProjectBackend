package imp.as.debtservice.model;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TransactionBacklogKey {
	private String modeId;
    private String preassignId;
    private String assignId;
    private Account account;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionBacklogKey)) return false;
        TransactionBacklogKey that = (TransactionBacklogKey) o;
        return Objects.equals(getModeId(), that.getModeId()) &&
               Objects.equals(getPreassignId(), that.getPreassignId()) &&
               Objects.equals(getAssignId(), that.getAssignId()) &&
               Objects.equals(getAccount().getAccountNo(), that.getAccount().getAccountNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModeId(), getPreassignId(), getAssignId(), getAccount().getAccountNo());
    }
}
