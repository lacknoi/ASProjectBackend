package imp.as.debtservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.debtservice.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
