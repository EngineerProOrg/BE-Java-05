package com.engineerpro.rest.example.dto.message;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMessageRequest {
  @NotEmpty
  private String clientUserId;
  @NotEmpty
  private String message;
  @NotEmpty
  private String imgUrl;
}
