package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.CreateTransactionRequest;
import com.engineerpro.rest.example.dto.CreateTransactionResponse;
import com.engineerpro.rest.example.dto.GetUserTransactionsRequest;
import com.engineerpro.rest.example.dto.GetUserTransactionsResponse;

@Service
public interface TransactionService {
  CreateTransactionResponse createTransaction(CreateTransactionRequest request);

  GetUserTransactionsResponse getUserTransactions(GetUserTransactionsRequest request);
}
