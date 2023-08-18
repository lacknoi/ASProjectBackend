package com.improve.skill.as.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.improve.skill.as.accountservice.constant.EndpointConstant;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	@Autowired
	private final WebClient.Builder webClientBuilder;
	
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
