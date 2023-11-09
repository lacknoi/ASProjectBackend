package imp.as.debtservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.debtservice.model.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Integer> {
	@Query(value = "SELECT mo from Mobile mo JOIN mo.account ac where mo.status = 'Active' and ac.accountNo = :accountNo")
	List<Mobile> getMobileActiveByAccountNo(String accountNo);
}
