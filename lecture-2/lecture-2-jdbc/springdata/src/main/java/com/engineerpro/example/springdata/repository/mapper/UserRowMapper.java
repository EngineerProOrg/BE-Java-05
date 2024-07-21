package com.engineerpro.example.springdata.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.engineerpro.example.springdata.model.User;

@Component
public class UserRowMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    User user = new User();

    user.setId(rs.getInt("id"));
    user.setUsername(rs.getString("username"));
    user.setPassword(rs.getString("password"));
    user.setBalance(rs.getInt("balance"));
    return user;
  }
}
