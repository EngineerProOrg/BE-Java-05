package com.engineerpro.example.springdata.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.springdata.dto.UserRegisterRequest;
import com.engineerpro.example.springdata.exception.UsernameExistException;
import com.engineerpro.example.springdata.model.User;
import com.engineerpro.example.springdata.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public void register(UserRegisterRequest userRegisterRequest) throws UsernameExistException {
    User existedUser = userRepository.findUserByUsername(userRegisterRequest.getUsername());
    log.info("existed user {}", existedUser);
    if (!Objects.isNull(existedUser)) {
      throw new UsernameExistException();
    }
    boolean inserted = userRepository.insertUser(userRegisterRequest.getUsername(), userRegisterRequest.getPassword());
    if (!inserted) {
      throw new IllegalStateException(
          String.format("Cannot insert new user with username {0}", userRegisterRequest.getUsername()));
    }
  }
}
