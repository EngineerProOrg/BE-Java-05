package com.engineerpro.example.redis.service.feed;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.dto.feed.CreateCommentRequest;
import com.engineerpro.example.redis.exception.CommentNotFoundException;
import com.engineerpro.example.redis.exception.NoPermissionException;
import com.engineerpro.example.redis.exception.PostNotFoundException;
import com.engineerpro.example.redis.model.Comment;
import com.engineerpro.example.redis.model.Post;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.repository.CommentRepository;
import com.engineerpro.example.redis.repository.PostRepository;
import com.engineerpro.example.redis.service.profile.ProfileService;

@Service
public class CommentServiceImpl implements CommentService {
  @Autowired
  private ProfileService profileService;


  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public Post createComment(UserPrincipal userPrincipal, CreateCommentRequest request) {
    Profile profile = profileService.getUserProfile(userPrincipal);
    Post post = postRepository.findById(request.getPostId()).orElseThrow(PostNotFoundException::new);
    Comment comment = new Comment();
    comment.setComment(request.getComment());
    comment.setCreatedAt(new Date());
    comment.setCreatedBy(profile);
    comment.setPost(post);
    commentRepository.save(comment);
    return post;
  }

  @Override
  public Post deleteComment(UserPrincipal userPrincipal, int commentId) {
    Profile profile = profileService.getUserProfile(userPrincipal);
    Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    if (comment.getCreatedBy().getId() != profile.getId()) {
      throw new NoPermissionException();
    }
    commentRepository.delete(comment);
    return comment.getPost();
  }

}
