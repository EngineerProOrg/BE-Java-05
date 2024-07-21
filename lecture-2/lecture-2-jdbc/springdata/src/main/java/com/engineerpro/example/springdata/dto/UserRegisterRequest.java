package com.engineerpro.example.springdata.dto;

import lombok.Value;

@Value
public class UserRegisterRequest {
  String username;
  String password;
}
