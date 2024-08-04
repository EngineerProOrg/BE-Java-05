package com.engineerpro.rest.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.dto.CreateTransactionRequest;
import com.engineerpro.rest.example.dto.CreateTransactionResponse;
import com.engineerpro.rest.example.dto.GetUserTransactionsRequest;
import com.engineerpro.rest.example.dto.GetUserTransactionsResponse;
import com.engineerpro.rest.example.service.TransactionService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;

@RestController
@Timed(histogram = true)
@RequestMapping("/transactions")
public class TransactionController {
	private final Counter myCounter;
	private final TransactionService transactionService;

	public TransactionController(MeterRegistry registry, TransactionService transactionService) {
		this.myCounter = Counter.builder("my_custom_counter")
				.description("A custom counter metric")
				.register(registry);
		this.transactionService = transactionService;

	}

	@GetMapping("/{userId}")
	public ResponseEntity<GetUserTransactionsResponse> getUserTransactions(@PathVariable Integer userId) {

		myCounter.increment();

		final GetUserTransactionsResponse response = transactionService
				.getUserTransactions(GetUserTransactionsRequest.builder().userId(userId).build());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	public ResponseEntity<CreateTransactionResponse> createTransaction(
			@Valid @RequestBody CreateTransactionRequest registrationRequest) {
		final CreateTransactionResponse response = transactionService.createTransaction(registrationRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
