package com.engineerpro.example.redis.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public Object sayHello(Authentication authentication) {
        return authentication.getPrincipal();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object getPrincipal(Authentication authentication) {
        return authentication.getPrincipal();
    }
}
