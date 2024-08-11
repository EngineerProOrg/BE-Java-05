package com.engineerpro.rest.example.dto.message;

import com.engineerpro.rest.example.model.Message;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMessageResponse {
  @NotNull
  private Message message;
}
