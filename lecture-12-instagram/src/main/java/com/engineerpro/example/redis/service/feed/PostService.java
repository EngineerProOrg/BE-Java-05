package com.engineerpro.example.redis.service.feed;

import java.util.List;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.CreatePostRequest;
import com.engineerpro.example.redis.model.Post;

public interface PostService {
  Post createPost(UserPrincipal userPrincipal, CreatePostRequest request);

  Post getPost(int postId);

  void deletePost(UserPrincipal userPrincipal, int postId);

  Post likePost(UserPrincipal userPrincipal, int postId);

  Post unlikePost(UserPrincipal userPrincipal, int postId);

  List<Post> getUserPosts(int userId);
}
