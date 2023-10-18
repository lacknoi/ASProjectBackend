package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.SMSMessage;

public interface SMSMessageRepository extends JpaRepository<SMSMessage, Integer>{

}
