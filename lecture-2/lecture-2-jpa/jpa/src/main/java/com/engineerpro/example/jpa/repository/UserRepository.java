package com.engineerpro.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.example.jpa.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findUserByUsername(String username);
}
