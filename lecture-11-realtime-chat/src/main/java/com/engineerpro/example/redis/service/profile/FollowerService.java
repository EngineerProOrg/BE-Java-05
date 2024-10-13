package com.engineerpro.example.redis.service.profile;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.profile.GetFollowerResponse;
import com.engineerpro.example.redis.dto.profile.GetFollowingResponse;

public interface FollowerService {
  void folowUser(UserPrincipal userPrincipal, int profileId);

  void unfolowUser(UserPrincipal userPrincipal, int profileId);

  GetFollowerResponse getFollowers(int profileId, int page, int limit);

  GetFollowingResponse getFollowings(int profileId, int page, int limit);

}