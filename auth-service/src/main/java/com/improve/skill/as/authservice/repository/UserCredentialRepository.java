package com.improve.skill.as.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.improve.skill.as.authservice.entity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,String> {
    Optional<UserCredential> findByName(String username);
}