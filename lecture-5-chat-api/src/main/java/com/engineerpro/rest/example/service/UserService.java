package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.user.CreateUserRequest;
import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.model.User;

@Service
public interface UserService {
  User createUser(App app, CreateUserRequest request);

  User getUserByClientId(App app, String clientUserId);
}
