package com.engineerpro.example.redis.dto.profile;

import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateProfileRequest {
  @Length(min = 1, max = 100)
  private String displayName;
  @Length(min = 1, max = 100)
  private String username;
  @Length(min = 1, max = 100)
  private String bio;
}
