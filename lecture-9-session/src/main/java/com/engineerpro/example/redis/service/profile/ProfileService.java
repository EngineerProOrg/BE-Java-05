package com.engineerpro.example.redis.service.profile;

import com.engineerpro.example.redis.model.Profile;

public interface ProfileService {
  Profile getProfile(int userId);
}