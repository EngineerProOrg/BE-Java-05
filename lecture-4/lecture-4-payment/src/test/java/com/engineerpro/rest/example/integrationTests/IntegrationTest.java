package com.engineerpro.rest.example.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.engineerpro.rest.example.dto.GetUserBalanceResponse;

public class IntegrationTest {
  WebTestClient client;

  @BeforeEach
  void setup() {
    client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
  }

  @Test
  void testUserWithBalance() {
    client.get()
        .uri("/users/1/balance")
        .exchange()
        .expectStatus().isOk()
        .expectBody(GetUserBalanceResponse.class).isEqualTo(new GetUserBalanceResponse(100));
  }

  @Test
  void testUserNoExist() {
    client.get()
        .uri("/users/1001/balance")
        .exchange()
        .expectStatus().isNotFound();
  }
}
