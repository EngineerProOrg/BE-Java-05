package com.engineerpro.example.springdata.model;

import lombok.Data;

@Data
public class User {
  int id;
  String username;
  String password;
  int balance;
}
