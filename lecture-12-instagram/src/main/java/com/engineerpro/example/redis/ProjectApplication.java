package com.engineerpro.example.redis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.example.redis.model.Comment;
import com.engineerpro.example.redis.model.Post;
import com.engineerpro.example.redis.model.Profile;
import com.engineerpro.example.redis.model.UserFollowing;
import com.engineerpro.example.redis.repository.CommentRepository;
import com.engineerpro.example.redis.repository.FollowerRepository;
import com.engineerpro.example.redis.repository.PostRepository;
import com.engineerpro.example.redis.repository.ProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProjectApplication {

	static ProfileRepository profileRepository;
	static PostRepository postRepository;
	static CommentRepository commentRepository;
	static FollowerRepository followerRepository;

	static List<Profile> randomProfiles(List<Profile> allProfiles, int ignoreProfileId, int quantity) {
		Collections.shuffle(allProfiles);
		List<Profile> res = new ArrayList<>();
		for (Profile profile : allProfiles.subList(0, quantity)) {
			if (profile.getId() != ignoreProfileId) {
				res.add(profile);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class, args);

		// profileRepository = context.getBean(ProfileRepository.class);
		// postRepository = context.getBean(PostRepository.class);
		// commentRepository = context.getBean(CommentRepository.class);
		// followerRepository = context.getBean(FollowerRepository.class);

		// List<Profile> allProfiles = new ArrayList<>();
		// try {
		// List<Integer> allIds = new ArrayList<>();
		// for (int i = 1; i <= 20; i++) {
		// allIds.add(i);
		// Profile profile = profileRepository.save(Profile.builder()
		// .id(i)
		// .username(String.format("username_%d", i))
		// .bio(String.format("about me %d", i))
		// .userId(UUID.randomUUID().toString())
		// .build());
		// allProfiles.add(profile);
		// }
		// // random follow
		// for (int i = 1; i <= 10; i++) {
		// Collections.shuffle(allIds);
		// for (int id : allIds.subList(0, 7)) {
		// if (id != i) {
		// UserFollowing userFollowing = new UserFollowing();
		// userFollowing.setCreatedAt(new Date());
		// userFollowing.setFollowerUserId(i);
		// userFollowing.setFollowingUserId(id);
		// followerRepository.save(userFollowing);
		// }
		// }
		// }
		// for (int i = 1; i <= 10; i++) {
		// Set<Profile> likes = new HashSet<>();
		// likes.add(allProfiles.get(1));
		// likes.add(allProfiles.get(2));
		// Post post = postRepository.save(Post.builder()
		// .caption(String.format("this is a new post, created by %d", i))
		// .createdBy(allProfiles.get(i - 1))
		// .userLikes(likes)
		// .createdAt(new Date())
		// .build());
		// commentRepository.save(Comment.builder()
		// .post(post)
		// .createdBy(allProfiles.get(0))
		// .comment("this is a comment by user 0")
		// .createdAt(new Date())
		// .build());
		// commentRepository.save(Comment.builder()
		// .post(post)
		// .createdBy(allProfiles.get(1))
		// .comment("this is a comment by user 1")
		// .createdAt(new Date())
		// .build());
		// }
		// } catch (Exception e) {
		// log.error("Cannot create seed data");
		// }
	}

}
