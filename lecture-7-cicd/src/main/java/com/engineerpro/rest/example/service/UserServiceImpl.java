package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.exception.UserNotFoundException;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public GetUserBalanceResponse getUserBalance(GetUserBalanceRequest request) {
    final User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
    return GetUserBalanceResponse.builder().balance(user.getBalance()).build();
  }

}
