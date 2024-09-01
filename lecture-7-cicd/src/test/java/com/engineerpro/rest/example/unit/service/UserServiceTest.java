package com.engineerpro.rest.example.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.UserRepository;
import com.engineerpro.rest.example.service.UserService;
import com.engineerpro.rest.example.service.UserServiceImpl;

// https://www.baeldung.com/mockito-annotations
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  private UserService userService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    userService = new UserServiceImpl(userRepository);
  }

  @Test
  public void testWithCaptor() {
    // given
    ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
    final int userId = 1;
    when(this.userRepository.findById(anyInt()))
        .thenReturn(Optional.of(User.builder().balance(100).id(userId).build()));
    // when
    GetUserBalanceResponse response = userService.getUserBalance(GetUserBalanceRequest.builder().userId(userId).build());
    // then
    assertThat(response.getBalance()).isLessThanOrEqualTo(100);
    verify(userRepository).findById(argumentCaptor.capture());
    verify(userRepository, times(1)).findById(1);
    // verify captured params
    assertThat(argumentCaptor.getValue()).isEqualTo(1);
  }
}
