package com.engineerpro.rest.example.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ListMessageRequest {
  private String channelId;
  private long pivotId;
  private int prevLimit;
  private int nextLimit;
}
