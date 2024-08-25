package com.engineerpro.rest.example.dto.message;

import java.util.List;

import com.engineerpro.rest.example.model.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ListMessageResponse {
  private List<Message> messages;
}
