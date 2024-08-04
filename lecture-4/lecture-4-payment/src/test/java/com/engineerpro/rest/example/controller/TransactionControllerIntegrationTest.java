package com.engineerpro.rest.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.engineerpro.rest.example.dto.CreateTransactionRequest;
import com.engineerpro.rest.example.dto.CreateTransactionResponse;
import com.engineerpro.rest.example.dto.GetUserTransactionsResponse;
import com.engineerpro.rest.example.model.Transaction;
import com.engineerpro.rest.example.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest()
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransactionService transactionService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetTransactionHistory() throws Exception {
    // given
    when(transactionService.getUserTransactions(any())).thenReturn(new GetUserTransactionsResponse(Arrays.asList(Transaction.builder().id(123456).idempotentKey("abc").amount(1).balanceAfterTransaction(99).balanceBeforeTransaction(100).userId(1).build())));
    // when - then
    this.mockMvc.perform(get("/transactions/{id}", "1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.transactions[0].id").value(123456))
    .andExpect(jsonPath("$.transactions[0].idempotentKey").value("abc"))
    .andExpect(jsonPath("$.transactions[0].userId").value(1))
    .andExpect(jsonPath("$.transactions[0].amount").value(1))
    .andExpect(jsonPath("$.transactions[0].balanceBeforeTransaction").value(100))
    .andExpect(jsonPath("$.transactions[0].balanceAfterTransaction").value(99));
  }

  @Test
  public void testCreateTransaction() throws Exception {
  // given
  when(transactionService.createTransaction(any())).thenReturn(new CreateTransactionResponse(99, 123456));
  // when - then
  String payload = objectMapper.writeValueAsString(CreateTransactionRequest.builder().amount(1).idempotentKey("an-idempotent-key").userId(1).build());
  this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(payload))
  .andExpect(status().isOk())
  .andExpect(jsonPath("$.remainBalance").value(99))
  .andExpect(jsonPath("$.transactionId").value(123456));
  }
}
