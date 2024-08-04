package com.engineerpro.rest.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.rest.example.dto.CreateTransactionRequest;
import com.engineerpro.rest.example.dto.CreateTransactionResponse;
import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.UserRepository;
import com.engineerpro.rest.example.service.TransactionService;
import com.engineerpro.rest.example.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);
		UserService userService = context.getBean(UserService.class);
		TransactionService transactionService = context.getBean(TransactionService.class);

		User user1 = userRepository.save(User.builder().balance(100).build());

		GetUserBalanceResponse getUserBalanceResponse = userService
				.getUserBalance(GetUserBalanceRequest.builder().userId(user1.getId()).build());
		log.info("GetUserBalanceResponse={}", getUserBalanceResponse);

		// CreateTransactionResponse createTransactionResponse1 = transactionService
		// .createTransaction(CreateTransactionRequest.builder().userId(user1.getId()).idempotentKey("abc").amount(1).build());
		// log.info("CreateTransactionResponse1={}", createTransactionResponse1);
		// CreateTransactionResponse createTransactionResponse2 = transactionService
		// .createTransaction(CreateTransactionRequest.builder().userId(user1.getId()).idempotentKey("abc1").amount(100).build());
		// log.info("CreateTransactionResponse2={}", createTransactionResponse2);
	}

}
