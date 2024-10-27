package com.engineerpro.example.redis.dto.feed;

import java.util.List;

import com.engineerpro.example.redis.model.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetUserPostResponse {
  private List<Post> posts;
}
