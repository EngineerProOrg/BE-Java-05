package com.engineerpro.rest.example.dto;

import java.util.List;

import com.engineerpro.rest.example.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetUserTransactionsResponse {
  private List<Transaction> transactions;
}
