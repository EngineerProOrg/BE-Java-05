package com.engineerpro.rest.example.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserTransactionsRequest {
  @Positive
  private int userId;

}
