package com.engineerpro.di2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean(name = "brand")
  String brand() {
    return "Toyota";
  }

  @Bean(name = "model")
  String model() {
    return "Vios";
  }

  // if not use @Qualifier("v6engine") in Car class
  // @Bean
  // Engine engine() {
  // return new V8Engine();
  // // return new V6Engine();
  // }
}
