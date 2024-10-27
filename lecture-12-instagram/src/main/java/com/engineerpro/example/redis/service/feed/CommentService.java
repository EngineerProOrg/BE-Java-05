package com.engineerpro.example.redis.service.feed;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.CreateCommentRequest;
import com.engineerpro.example.redis.model.Post;

public interface CommentService {
  Post createComment(UserPrincipal userPrincipal, CreateCommentRequest request);

  Post deleteComment(UserPrincipal userPrincipal, int commentId);
}
