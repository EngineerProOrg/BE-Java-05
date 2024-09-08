package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;

@Service
public interface UserService {
  GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request);
}
