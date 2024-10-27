package com.engineerpro.example.redis.service.feed;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.config.MessageQueueConfig;
import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.CreatePostRequest;
import com.engineerpro.example.redis.exception.NoPermissionException;
import com.engineerpro.example.redis.exception.PostNotFoundException;
import com.engineerpro.example.redis.model.Post;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.repository.PostRepository;
import com.engineerpro.example.redis.service.UploadService;
import com.engineerpro.example.redis.service.profile.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
  @Autowired
  private ProfileService profileService;

  @Autowired
  private UploadService uploadService;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  RabbitTemplate rabbitTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public Post createPost(UserPrincipal userPrincipal, CreatePostRequest request) {
    Profile profile = profileService.getUserProfile(userPrincipal);
    String url = uploadService.uploadImage(request.getBase64ImageString());
    Post post = new Post();
    post.setCaption(request.getCaption());
    post.setCreatedAt(new Date());
    post.setCreatedBy(profile);
    post.setImageUrl(url);
    postRepository.save(post);

    rabbitTemplate.convertAndSend(MessageQueueConfig.AFTER_CREATE_POST_QUEUE, post.getId());

    return post;
  }

  @Override
  public Post getPost(int postId) {
    return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
  }

  @Override
  public void deletePost(UserPrincipal userPrincipal, int postId) {
    Profile profile = profileService.getUserProfile(userPrincipal);
    Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
    if (post.getCreatedBy().getId() != profile.getId()) {
      throw new NoPermissionException();
    }
    postRepository.delete(post);
  }

  @Override
  public Post likePost(UserPrincipal userPrincipal, int postId) {
    Profile profile = profileService.getUserProfile(userPrincipal);
    Post post = getPost(postId);
    post.getUserLikes().add(profile);
    postRepository.save(post);

    return post;
  }

  @Override
  public Post unlikePost(UserPrincipal userPrincipal, int postId) {
    Profile profile = profileService.getUserProfile(userPrincipal);
    Post post = getPost(postId);
    post.getUserLikes().remove(profile);
    postRepository.save(post);
    return post;
  }

  @Override
  public List<Post> getUserPosts(int userId) {
    Profile profile = profileService.getUserProfile(userId);
    return postRepository.findByCreatedBy(profile);
  }

}
