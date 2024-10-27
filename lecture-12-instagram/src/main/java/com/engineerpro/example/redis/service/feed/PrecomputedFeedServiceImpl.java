package com.engineerpro.example.redis.service.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.GetFeedResponse;
import com.engineerpro.example.redis.model.Post;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.repository.FeedRepository;
import com.engineerpro.example.redis.repository.PostRepository;
import com.engineerpro.example.redis.service.profile.ProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("precomputedFeedService")
public class PrecomputedFeedServiceImpl implements FeedService {
  @Autowired
  private ProfileService profileService;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private FeedRepository feedRepository;

  @Override
  public GetFeedResponse getFeed(UserPrincipal userPrincipal, int limit, int page) {
    Profile profile = profileService.getUserProfile(userPrincipal);

    List<Long> postIds = feedRepository.getFeed(profile.getId(), limit, page);
    log.info("postIds={}", postIds);

    List<Post> posts = postRepository.findAllById(postIds.stream().map(Long::intValue).toList());

    Long totalPost = feedRepository.getFeedSize(profile.getId());
    log.info("totalPost={}", totalPost);
    int totalPage = (int) Math.ceil((double) totalPost / limit);

    return GetFeedResponse.builder()
        .posts(posts).totalPage(totalPage).build();
  }

}
