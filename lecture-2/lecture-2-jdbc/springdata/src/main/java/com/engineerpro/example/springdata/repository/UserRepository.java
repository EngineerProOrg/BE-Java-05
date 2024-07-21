package com.engineerpro.example.springdata.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.engineerpro.example.springdata.model.User;
import com.engineerpro.example.springdata.repository.mapper.UserRowMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private UserRowMapper userRowMapper;

  public User findUserByUsername(String username) {
    List<User> matchedUsers = jdbcTemplate.query("SELECT * from user WHERE username = ?", userRowMapper, username);
    return matchedUsers.size() > 0 ? matchedUsers.get(0) : null;
  }

  public boolean insertUser(String username, String password) {
    int inserted = jdbcTemplate.update("INSERT INTO user (username, password) VALUES (?, ?)", username,
        password);
    return inserted > 0;
  }

  public List<User> listAllUser() {
    return jdbcTemplate.query("SELECT * from user", userRowMapper);
  }
}
