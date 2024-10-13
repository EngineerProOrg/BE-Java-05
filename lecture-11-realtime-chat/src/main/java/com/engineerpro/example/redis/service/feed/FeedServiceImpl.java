package com.engineerpro.example.redis.service.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.GetFeedResponse;
import com.engineerpro.example.redis.model.Post;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.model.UserFollowing;
import com.engineerpro.example.redis.repository.FollowerRepository;
import com.engineerpro.example.redis.repository.PostRepository;
import com.engineerpro.example.redis.service.profile.ProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedServiceImpl implements FeedService {
  @Autowired
  private ProfileService profileService;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private FollowerRepository followerRepository;

  @Override
  public GetFeedResponse getFeed(UserPrincipal userPrincipal, int limit, int page) {
    Profile profile = profileService.getUserProfile(userPrincipal);

    List<UserFollowing> followings = followerRepository.findByFollowerUserId(profile.getId());
    List<Integer> followingProfileIdList = followings.stream().map(following -> following.getFollowingUserId())
        .toList();
    log.info("followingProfileIdList={}", followingProfileIdList);
    int totalPost = postRepository.countByCreatedByIn(followingProfileIdList);
    log.info("totalPost={}", totalPost);
    int totalPage = (int) Math.ceil((double) totalPost / limit);
    int offset = (page - 1) * limit;

    List<Post> posts = postRepository
        .findByCreatedBy(followingProfileIdList, limit, offset);

    return GetFeedResponse.builder()
        .posts(posts).totalPage(totalPage).build();
  }

}
