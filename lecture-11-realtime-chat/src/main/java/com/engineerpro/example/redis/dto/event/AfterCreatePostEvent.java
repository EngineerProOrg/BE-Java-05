package com.engineerpro.example.redis.dto.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AfterCreatePostEvent {
  int postId;
}
