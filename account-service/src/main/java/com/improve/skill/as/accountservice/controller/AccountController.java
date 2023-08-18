package com.improve.skill.as.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.improve.skill.as.accountservice.dto.AccountResponse;
import com.improve.skill.as.accountservice.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/account")
	public ResponseEntity<AccountResponse> getAccount() {
		AccountResponse accountResponse = new AccountResponse();
		
		accountService.getAccount();

		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}
}
