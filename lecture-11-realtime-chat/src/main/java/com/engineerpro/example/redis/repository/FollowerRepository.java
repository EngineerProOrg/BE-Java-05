package com.engineerpro.example.redis.repository;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engineerpro.example.redis.model.UserFollowing;

@Repository
public interface FollowerRepository extends JpaRepository<UserFollowing, Integer> {
  @Query(value = "select * from user_following where follower_user_id = :followerUserId LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<UserFollowing> findByFollowerUserId(@Param(value = "followerUserId") int followerUserId,
      @Param(value = "limit") int limit,
      @Param(value = "offset") int offset);

  int countByFollowerUserId(int followerUserId);

  List<UserFollowing> findByFollowerUserId(int followerUserId);

  @Query(value = "select * from user_following where following_user_id = :followingUserId LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<UserFollowing> findByFollowingUserId(@Param(value = "followingUserId") int followingUserId,
      @Param(value = "limit") int limit,
      @Param(value = "offset") int offset);

  int countByFollowingUserId(int followingUserId);

  UserFollowing findByFollowerUserIdAndFollowingUserId(int followerUserId, int followingUserId);

  void deleteByFollowerUserIdAndFollowingUserId(int followerUserId, int followingUserId);
}
