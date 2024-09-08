package com.engineerpro.rest.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.exception.UserNotFoundException;
import com.engineerpro.rest.example.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void testUserWithBalance() throws Exception {
    // given
    when(userService.getUserBalance(any())).thenReturn(new GetUserBalanceResponse(100));
    // when - then
    this.mockMvc.perform(get("/users/{id}/balance", "1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.balance").value("100"));
  }

  @Test
  public void testNonExistUser() throws Exception {
    // given
    when(userService.getUserBalance(any())).thenThrow(UserNotFoundException.class);
    // when - then
    this.mockMvc.perform(get("/users/{id}/balance", "1"))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.status").value("NOT_FOUND"))
    .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"));
  }

}
