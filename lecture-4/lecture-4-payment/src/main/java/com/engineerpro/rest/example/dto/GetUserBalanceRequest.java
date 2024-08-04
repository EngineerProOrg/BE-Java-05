package com.engineerpro.rest.example.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserBalanceRequest {
  @Positive
  private int userId;

}
