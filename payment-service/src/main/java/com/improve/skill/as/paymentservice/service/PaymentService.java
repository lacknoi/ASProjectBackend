package com.improve.skill.as.paymentservice.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.improve.skill.as.paymentservice.dto.AccountBalanceRequest;
import com.improve.skill.as.paymentservice.dto.AccountBalanceResponce;
import com.improve.skill.as.paymentservice.model.AccountBalance;
import com.improve.skill.as.paymentservice.repository.PMAccountBalanceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
	@Autowired
	private final PMAccountBalanceRepository pmAccountBalanceRepository;
	
	public AccountBalanceResponce initAccountBalance(AccountBalanceRequest accountBalanceRequest) {
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setAccountNo(accountBalanceRequest.getAccountNo());
		accountBalance.setTotalBalance(BigDecimal.ZERO);
		accountBalance.setCreated(new Date());
		accountBalance.setCreatedBy(accountBalanceRequest.getUserName());
		accountBalance.setLastUpd(new Date());
		accountBalance.setLastUpdBy(accountBalanceRequest.getUserName());
		
		pmAccountBalanceRepository.save(accountBalance);
		
		return AccountBalanceResponce.builder()
					.accountNo(accountBalanceRequest.getAccountNo())
					.build();
	}
}
