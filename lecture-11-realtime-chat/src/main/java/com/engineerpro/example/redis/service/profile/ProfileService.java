package com.engineerpro.example.redis.service.profile;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.profile.UpdateProfileImageRequest;
import com.engineerpro.example.redis.dto.profile.UpdateProfileRequest;
import com.engineerpro.example.redis.model.Profile;

public interface ProfileService {
  Profile getUserProfile(UserPrincipal userPrincipal);

  Profile getUserProfile(int id);

  Profile updateProfile(UserPrincipal userPrincipal, UpdateProfileRequest request);

  Profile updateProfileImage(UserPrincipal userPrincipal, UpdateProfileImageRequest request);
}