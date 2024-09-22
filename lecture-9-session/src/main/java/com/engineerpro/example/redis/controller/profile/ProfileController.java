package com.engineerpro.example.redis.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.profile.GetProfileResponse;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.service.profile.ProfileService;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController {
  @Autowired
  ProfileService profileService;

  @GetMapping("/{id}")
  public ResponseEntity<GetProfileResponse> getProfile(@PathVariable int id) {
    Profile profile = profileService.getProfile(id);
    return ResponseEntity.ok(GetProfileResponse.builder().profile(profile).build());
    // throw new RuntimeException();
  }

}
