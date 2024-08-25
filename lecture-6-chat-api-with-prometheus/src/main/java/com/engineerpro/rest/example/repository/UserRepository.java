package com.engineerpro.rest.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.rest.example.model.User;

import io.micrometer.core.annotation.Timed;

@Repository
@Timed(histogram = true)
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByAppIdAndClientUserId(String appId, String clientUserId);
}
