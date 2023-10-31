package imp.as.accountservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.accountservice.model.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Integer> {
	@Query(value = "SELECT nvl(max(mobileId) + 1, 1) mobileId from Mobile")
	Integer getNextValMobileSequence();
	@Query(value = "SELECT mo from Mobile mo JOIN mo.account ac where mo.status = 'Active' and ac.accountNo = :accountNo")
	List<Mobile> getMobileActiveByAccountNo(String accountNo);
	@Query(value = "SELECT mo from Mobile mo JOIN mo.account ac where ac.accountNo = :accountNo and mo.mobileNo = :mobileNo")
	Optional<Mobile> getMobileByAccountAndMobileNo(String accountNo, String mobileNo);
	Optional<Mobile> findByMobileNo(String mobileNo);
}
