package com.engineerpro.securityexample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engineerpro.securityexample.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
