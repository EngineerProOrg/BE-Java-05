package com.engineerpro.rest.example.dto.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChannelRequest {
  @NotEmpty
  private String name;
  @NotEmpty
  private String clientReferenceId;
}
