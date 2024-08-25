package com.engineerpro.rest.example.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
  @NotEmpty
  private String name;
  @NotEmpty
  private String clientUserId;
  @NotEmpty
  private String profileImgUrl;
}
