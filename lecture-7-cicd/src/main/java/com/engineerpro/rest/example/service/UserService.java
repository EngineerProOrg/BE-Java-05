package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.exception.UserNotFoundException;

@Service
public interface UserService {
  GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request);
}
