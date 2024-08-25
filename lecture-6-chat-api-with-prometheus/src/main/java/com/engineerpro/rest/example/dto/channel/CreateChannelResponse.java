package com.engineerpro.rest.example.dto.channel;

import com.engineerpro.rest.example.model.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateChannelResponse {
  private Channel channel;
}
