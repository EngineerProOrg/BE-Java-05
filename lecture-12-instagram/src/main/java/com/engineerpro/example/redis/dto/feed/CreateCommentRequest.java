package com.engineerpro.example.redis.dto.feed;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateCommentRequest {
  @Positive
  int postId;
  @Length(min = 1, max = 2000)
  private String comment;
}
