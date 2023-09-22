package com.improve.skill.as.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.improve.skill.as.paymentservice.model.AccountBalance;

public interface PMAccountBalanceRepository extends JpaRepository<AccountBalance, String> {
}
