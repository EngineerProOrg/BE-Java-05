package com.engineerpro.rest.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.rest.example.model.Transaction;

import io.micrometer.core.annotation.Timed;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
  List<Transaction> findByUserId(int userId);

  Optional<Transaction> findOneByIdempotentKey(String idempotentKey);
}
