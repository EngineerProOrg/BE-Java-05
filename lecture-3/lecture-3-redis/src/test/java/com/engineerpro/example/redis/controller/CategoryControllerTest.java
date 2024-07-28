package com.engineerpro.example.redis.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
  @Autowired
  private MockMvc mvc;

  @Test
  public void testGetArticlesEndpoint() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/category/1/articles").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testGetArticlesEndpoint1() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/category/1/articles").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
