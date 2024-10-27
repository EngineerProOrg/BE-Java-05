package com.engineerpro.example.redis.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.UserPrincipal;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/auth")
public class AuthController {
  @GetMapping("/inspect")
  public ResponseEntity<UserPrincipal> inspect(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    log.info(String.format("authentication %s", userPrincipal.getId()));
    return ResponseEntity.ok().body((UserPrincipal) authentication.getPrincipal());
  }

  @GetMapping("/check-has-admin-role")
  @PreAuthorize("hasRole('ADMIN')")
  public Object sayHello(Authentication authentication) {
    return ResponseEntity.ok().body("has admin role");
  }

  @GetMapping("/check-has-user-role")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Object getPrincipal(Authentication authentication) {
    return ResponseEntity.ok().body("has user role");
  }
}
