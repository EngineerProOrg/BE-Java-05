package com.engineerpro.example.redis.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCategoryArticlesRequest {
  @Positive
  private int categoryId;

}
