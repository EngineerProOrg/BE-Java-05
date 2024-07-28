package com.engineerpro.example.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
class AppConfig {

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
