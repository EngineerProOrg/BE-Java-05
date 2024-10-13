package com.engineerpro.example.redis.dto.feed;

import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreatePostRequest {
  @Length(min = 1)
  private String base64ImageString;
  @Length(min = 1, max = 2000)
  private String caption;
}
