package com.engineerpro.example.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ranking {
  private String name;
  private double score;
}
