package imp.as.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imp.as.authservice.entity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,String> {
    Optional<UserCredential> findByName(String username);
}