package com.engineerpro.example.redis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
  @Id
  int id;
  String profileImageUrl;
  String displayName;
  String username;
  String bio;
}
