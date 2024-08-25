package com.engineerpro.rest.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException.Forbidden;

import com.engineerpro.rest.example.common.AuthenticationFilter;
import com.engineerpro.rest.example.config.metrics.ForbiddenMetrics;
import com.engineerpro.rest.example.service.AppService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
  // @Bean
  // ObjectMapper objectMapper() {
  // ObjectMapper mapper = new ObjectMapper();
  // return mapper;
  // }

  @Bean
  public FilterRegistrationBean<AuthenticationFilter> loggingFilter(AppService appService,
      ForbiddenMetrics forbiddenMetrics) {
    FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new AuthenticationFilter(appService, forbiddenMetrics));
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(2);

    return registrationBean;
  }
}
