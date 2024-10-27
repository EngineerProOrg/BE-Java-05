package com.engineerpro.example.redis.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_following", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "followerUserId", "followingUserId" }) })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  private int followerUserId;
  private int followingUserId;
  private Date createdAt;
}
