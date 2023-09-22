package imp.as.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import imp.as.accountservice.model.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Integer> {
	@Query(value = "SELECT nvl(max(mobileId) + 1, 1) mobileId from Mobile")
	Integer getNextValMobileSequence();
}
