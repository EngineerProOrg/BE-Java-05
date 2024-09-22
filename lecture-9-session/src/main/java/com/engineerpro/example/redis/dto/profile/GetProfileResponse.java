package com.engineerpro.example.redis.dto.profile;

import com.engineerpro.example.redis.model.Profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetProfileResponse {
  Profile profile;
}
