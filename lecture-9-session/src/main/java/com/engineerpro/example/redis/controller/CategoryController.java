package com.engineerpro.example.redis.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.GetCategoryArticlesRequest;
import com.engineerpro.example.redis.dto.GetCategoryArticlesResponse;
import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.exception.CategoryNotFoundException;
import com.engineerpro.example.redis.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @GetMapping("{id}/articles")
  public ResponseEntity<GetCategoryArticlesResponse> getArticles(@PathVariable Integer id, Principal principal)
      throws IOException {
    OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;
    log.info("request id={}, auth={}", id, oAuth2AuthenticationToken.getPrincipal().getName());
    UserPrincipal userPrincipal = (UserPrincipal) oAuth2AuthenticationToken.getPrincipal();
    log.info("request id={}, auth={}", id, userPrincipal.getId());
    GetCategoryArticlesResponse response;
    try {
      response = categoryService.getArticles(GetCategoryArticlesRequest.builder().categoryId(id).build());
    } catch (CategoryNotFoundException e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(response);
  }

}
