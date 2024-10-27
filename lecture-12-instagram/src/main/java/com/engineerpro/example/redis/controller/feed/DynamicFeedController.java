package com.engineerpro.example.redis.controller.feed;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.GetFeedResponse;
import com.engineerpro.example.redis.service.feed.FeedService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/dynamic-feeds")
public class DynamicFeedController {
  private FeedService feedService;

  public DynamicFeedController(@Qualifier("dynamicFeedService") FeedService feedService) {
    this.feedService = feedService;
  }

  @GetMapping()
  public ResponseEntity<GetFeedResponse> getFeed(@RequestParam("page") int page,
      @RequestParam("limit") int limit, Authentication authentication) {
    log.info("page={}, limit={}", page, limit);
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    GetFeedResponse response = feedService.getFeed(userPrincipal, limit, page);
    return ResponseEntity.ok().body(response);
  }
}
