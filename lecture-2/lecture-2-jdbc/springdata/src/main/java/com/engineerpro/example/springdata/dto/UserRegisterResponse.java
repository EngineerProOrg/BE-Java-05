package com.engineerpro.example.springdata.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRegisterResponse {
  boolean success;
  String errorCode;
}
