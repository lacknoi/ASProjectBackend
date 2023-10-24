package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.Mobile;

public interface MobileRepository extends JpaRepository<Mobile, Integer> {
}
