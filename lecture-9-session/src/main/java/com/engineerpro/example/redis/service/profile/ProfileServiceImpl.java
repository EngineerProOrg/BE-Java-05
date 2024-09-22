package com.engineerpro.example.redis.service.profile;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
  @Autowired
  ProfileRepository profileRepository;

  @Override
  public Profile getProfile(int userId) {
    Optional<Profile> optionalProfile = profileRepository.findById(userId);
    // implement business logic 
    
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProfile'");
  }

}
