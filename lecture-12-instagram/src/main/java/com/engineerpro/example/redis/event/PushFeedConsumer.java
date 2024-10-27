package com.engineerpro.example.redis.event;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.engineerpro.example.redis.config.MessageQueueConfig;
import com.engineerpro.example.redis.model.Post;
import com.engineerpro.example.redis.model.UserFollowing;
import com.engineerpro.example.redis.repository.FeedRepository;
import com.engineerpro.example.redis.repository.FollowerRepository;
import com.engineerpro.example.redis.repository.NotificationRepository;
import com.engineerpro.example.redis.service.feed.PostService;
import com.engineerpro.example.redis.service.profile.FollowerService;
import com.engineerpro.example.redis.service.profile.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RabbitListener(queues = MessageQueueConfig.AFTER_CREATE_POST_QUEUE)
public class PushFeedConsumer {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProfileService profileService;

    @Autowired
    PostService postService;

    @Autowired
    FollowerRepository followerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    FeedRepository feedRepository;

    @RabbitHandler
    public void receive(Integer postId) throws JsonMappingException, JsonProcessingException {
        log.info(" [x] Received '" + postId + "'");

        Post post = postService.getPost(postId);

        List<UserFollowing> follwerList = followerRepository.findByFollowingUserId(post.getCreatedBy().getId());

        for (UserFollowing userFollowing : follwerList) {
            log.info("userFollowing={}", userFollowing);
            feedRepository.addPostToFeed(post.getId(), userFollowing.getFollowerUserId());
        }
    }
}
