package com.engineerpro.rest.example.dto.user;

import com.engineerpro.rest.example.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateUserResponse {
  private User user;

}
