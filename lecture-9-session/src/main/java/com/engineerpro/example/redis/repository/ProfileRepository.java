package com.engineerpro.example.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.example.redis.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
