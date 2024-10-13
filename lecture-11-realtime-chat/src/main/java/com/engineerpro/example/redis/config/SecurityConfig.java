package com.engineerpro.example.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.engineerpro.example.redis.service.OAuth2UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2UserService oAuth2UserService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.warn("Configuring http filterChain");
    http
        .authorizeHttpRequests(authorize -> authorize.requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll())
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest()
            .authenticated())
        .oauth2Login(oauth2 -> oauth2
            .userInfoEndpoint(infoEndpoint -> infoEndpoint.userService(oAuth2UserService)))
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
