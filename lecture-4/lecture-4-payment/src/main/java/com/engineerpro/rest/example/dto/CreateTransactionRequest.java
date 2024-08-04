package com.engineerpro.rest.example.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionRequest {
  @Positive
  @NotNull
  private int userId;

  @Positive
  @NotNull
  private int amount;

  @Length(max = 100, min = 10)
  @NotNull
  private String idempotentKey;

}
