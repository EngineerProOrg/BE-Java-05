package com.engineerpro.example.redis.service.profile;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.profile.UpdateProfileImageRequest;
import com.engineerpro.example.redis.dto.profile.UpdateProfileRequest;
import com.engineerpro.example.redis.exception.UserNotFoundException;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.repository.ProfileRepository;
import com.engineerpro.example.redis.service.UploadService;

@Service
public class ProfileServiceImpl implements ProfileService {
  @Autowired
  private UploadService uploadService;
  @Autowired
  private ProfileRepository profileRepository;

  @Override
  public Profile getUserProfile(UserPrincipal userPrincipal) {
    Profile profile = profileRepository.findOneByUserId(userPrincipal.getId().toString());
    if (Objects.isNull(profile)) {
      profile = new Profile();
      profile.setUserId(userPrincipal.getId().toString());
      profile.setDisplayName(userPrincipal.getName());
      profileRepository.save(profile);
    }
    return profile;
  }

  @Override
  public Profile getUserProfile(int id) {
    return profileRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public Profile updateProfile(UserPrincipal userPrincipal, UpdateProfileRequest request) {
    Profile profile = this.getUserProfile(userPrincipal);
    profile.setBio(request.getBio());
    profile.setDisplayName(request.getDisplayName());
    profile.setUsername(request.getUsername());
    profileRepository.save(profile);
    return profile;
  }

  @Override
  public Profile updateProfileImage(UserPrincipal userPrincipal, UpdateProfileImageRequest request) {
    String url = uploadService.uploadImage(request.getBase64ImageString());
    Profile profile = this.getUserProfile(userPrincipal);
    profile.setProfileImageUrl(url);
    profileRepository.save(profile);
    return profile;
  }
}