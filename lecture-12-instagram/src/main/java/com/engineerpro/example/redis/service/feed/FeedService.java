package com.engineerpro.example.redis.service.feed;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.GetFeedResponse;

public interface FeedService {
  GetFeedResponse getFeed(UserPrincipal userPrincipal, int limit, int page);
}
