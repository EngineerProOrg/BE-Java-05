package com.engineerpro.rest.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.user.CreateUserRequest;
import com.engineerpro.rest.example.exception.ClientUserIdExistedException;
import com.engineerpro.rest.example.exception.UserNotFoundException;
import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUser(App app, CreateUserRequest request) {
    log.info("Create user app={} request={}", app, request);
    if (userRepository.findByAppIdAndClientUserId(app.getId(), request.getClientUserId()).isPresent()) {
      throw new ClientUserIdExistedException();
    }
    log.info("not exist user, create now {}", app);

    User user = User.builder()
        .app(app)
        .clientUserId(request.getClientUserId())
        .name(request.getName())
        .profileImgUrl(request.getProfileImgUrl())
        .build();
    return userRepository.save(user);
  }

  @Override
  public User getUserByClientId(App app, String clientUserId) {
    return userRepository.findByAppIdAndClientUserId(app.getId(), clientUserId)
        .orElseThrow(() -> new UserNotFoundException());
  }

}
