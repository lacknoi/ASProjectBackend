package com.improve.skill.as.accountservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.improve.skill.as.accountservice.constant.AppConstant;
import com.improve.skill.as.accountservice.constant.EndpointConstant;
import com.improve.skill.as.accountservice.dto.AccountBalanceRequest;
import com.improve.skill.as.accountservice.dto.AccountBalanceResponce;
import com.improve.skill.as.accountservice.dto.AccountRequest;
import com.improve.skill.as.accountservice.dto.AccountResponse;
import com.improve.skill.as.accountservice.model.Account;
import com.improve.skill.as.accountservice.repository.AccountRepository;
import com.improve.skill.as.accountservice.utils.DateTimeUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
	@Autowired
	private final WebClient.Builder webClientBuilder;
	@Autowired
	private final AccountRepository accountRepository;
	
	private Account generateAccount(AccountRequest accountRequest) {
		String dateFor = DateTimeUtils.formatDate(AppConstant.ACCOUNT_NO_FORMAT, DateTimeUtils.currentDate());
		Integer accountNoSeq = accountRepository.getNextValAccountNoSequence();
		
		Account account = new Account();
		account.setAccountId(accountNoSeq);
		account.setAccountNo(dateFor + String.format("%04d", accountNoSeq));
		account.setAccountName(accountRequest.getAccountName());
		account.setCreated(new Date());
		account.setCreatedBy(accountRequest.getUserName());
		account.setLastUpd(new Date());
		account.setLastUpdBy(accountRequest.getUserName());
		
		return account;
	}
	
	public AccountResponse createAccount(AccountRequest accountRequest) {
		Account account = generateAccount(accountRequest);
		
		accountRepository.save(account);
		
		ResponseEntity<AccountBalanceResponce> response = webClientBuilder.build().post()
				.uri(EndpointConstant.ENDPOINT_PAYMENT + EndpointConstant.METHOD_INIT_ACCOUNT_BALANCE)
				.body(Mono.just(AccountBalanceRequest.builder().accountNo(account.getAccountNo()).build())
							, AccountBalanceRequest.class)
				.retrieve()
				.toEntity(AccountBalanceResponce.class)
				.block();
		
		System.out.println("StatusCode " + response.getStatusCode());		
		System.out.println("Body " + response.getBody());
		
		return AccountResponse.builder()
					.accountNo(account.getAccountNo())
					.build();
	}
	
	public void getAccount() {
		ResponseEntity<String> response = webClientBuilder.build().get()
											.uri(EndpointConstant.ENDPOINT_PAYMENT + EndpointConstant.METHOD_PAYMENT)
											.retrieve()
											.toEntity(String.class)
											.block();
		
		System.out.println("StatusCode " + response.getStatusCode());		
		System.out.println("Body " + response.getBody());
	}
}
