package com.engineerpro.example.redis.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.profile.FollowUserRequest;
import com.engineerpro.example.redis.dto.profile.FollowUserResponse;
import com.engineerpro.example.redis.dto.profile.GetFollowerResponse;
import com.engineerpro.example.redis.dto.profile.GetFollowingResponse;
import com.engineerpro.example.redis.dto.profile.UnFollowUserResponse;
import com.engineerpro.example.redis.dto.profile.UnfollowUserRequest;
import com.engineerpro.example.redis.service.profile.FollowerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(path = "/follow")
@Validated
public class FollowerController {
  @Autowired
  FollowerService followerService;

  @GetMapping("/user/followers/{id}")
  public ResponseEntity<GetFollowerResponse> getFollowers(@PathVariable int id,
      @RequestParam("page") @Min(1) Integer page,
      @RequestParam("limit") @Min(1) int limit) {
    log.info("userId={}, page={}, limit={}", id, page, limit);
    return ResponseEntity.ok().body(followerService.getFollowers(id, page, limit));
  }

  @GetMapping("/user/followings/{id}")
  public ResponseEntity<GetFollowingResponse> getFollowing(@PathVariable int id, @RequestParam("page") @Min(1) int page,
      @RequestParam("limit") @Min(1) int limit) {
    log.info("userId={}, page={}, limit={}", id, page, limit);
    return ResponseEntity.ok().body(followerService.getFollowings(id, page, limit));
  }

  @PostMapping()
  public ResponseEntity<FollowUserResponse> folowUser(
      @Valid @RequestBody FollowUserRequest request, Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    followerService.folowUser(userPrincipal, request.getProfileId());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping()
  public ResponseEntity<UnFollowUserResponse> unfolowUser(
      @Valid @RequestBody UnfollowUserRequest request, Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    followerService.unfolowUser(userPrincipal, request.getProfileId());
    return ResponseEntity.ok().build();
  }
}
