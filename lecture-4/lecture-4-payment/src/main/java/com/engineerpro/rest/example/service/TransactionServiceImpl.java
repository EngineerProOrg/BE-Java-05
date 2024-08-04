package com.engineerpro.rest.example.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engineerpro.rest.example.dto.CreateTransactionRequest;
import com.engineerpro.rest.example.dto.CreateTransactionResponse;
import com.engineerpro.rest.example.dto.GetUserTransactionsRequest;
import com.engineerpro.rest.example.dto.GetUserTransactionsResponse;
import com.engineerpro.rest.example.exception.DuplicatedIdempotentKeyException;
import com.engineerpro.rest.example.exception.NotEnoughBalanceException;
import com.engineerpro.rest.example.exception.TooManyRequestException;
import com.engineerpro.rest.example.exception.UserNotFoundException;
import com.engineerpro.rest.example.model.Transaction;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.TransactionRepository;
import com.engineerpro.rest.example.repository.UserRepository;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  private final DistributedLockService distributedLockService;
  private final TransactionRepository transactionRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
    CreateTransactionResponse createTransactionResponse;
    if (distributedLockService.accquireLock(request.getIdempotentKey())) {
      try {
        TimeUnit.SECONDS.sleep(30);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      log.info("accquired lock succesfully");
      try {
        Optional<Transaction> optionalExistedTransaction = transactionRepository
            .findOneByIdempotentKey(request.getIdempotentKey());
        log.info("optionalExistedTransaction={}", optionalExistedTransaction);
        if (optionalExistedTransaction.isPresent()) {
          Transaction existedTransaction = optionalExistedTransaction.get();
          if (existedTransaction.getUserId() == request.getUserId()
              && existedTransaction.getAmount() == request.getAmount()) {
            createTransactionResponse = CreateTransactionResponse.builder().transactionId(existedTransaction.getId())
                .remainBalance(existedTransaction.getBalanceAfterTransaction()).build();
          } else {
            throw new DuplicatedIdempotentKeyException();
          }
        } else {
          final User user = userRepository.findOneWithLockingById(request.getUserId())
              .orElseThrow(UserNotFoundException::new);
          if (user.getBalance() < request.getAmount()) {
            throw new NotEnoughBalanceException();
          }
          int balanceBeforeTransaction = user.getBalance();
          int balanceAfterTransaction = user.getBalance() - request.getAmount();
          user.setBalance(balanceAfterTransaction);
          userRepository.save(user);
          Transaction transaction = transactionRepository.save(Transaction.builder()
              .idempotentKey(request.getIdempotentKey())
              .userId(request.getUserId())
              .amount(request.getAmount())
              .balanceBeforeTransaction(balanceBeforeTransaction)
              .balanceAfterTransaction(balanceAfterTransaction)
              .build());
          createTransactionResponse = CreateTransactionResponse.builder().transactionId(transaction.getId())
              .remainBalance(balanceAfterTransaction)
              .build();
        }
      } finally {
        distributedLockService.releaseLock(request.getIdempotentKey());
        log.info("release lock succesfully");
      }
    } else {
      log.info("cannot accquired lock");
      throw new TooManyRequestException();
    }
    return createTransactionResponse;
  }

  @Override
  public GetUserTransactionsResponse getUserTransactions(GetUserTransactionsRequest request) {
    return GetUserTransactionsResponse.builder().transactions(transactionRepository.findByUserId(request.getUserId()))
        .build();
  }

}
