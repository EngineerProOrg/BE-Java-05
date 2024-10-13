package com.engineerpro.example.redis.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.example.redis.dto.RankingUpdateRequest;
import com.engineerpro.example.redis.model.Ranking;
import com.engineerpro.example.redis.service.RankingService;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

  @Autowired
  private RankingService rankingService;

  @PostMapping("/update")
  public void updateRanking(@RequestBody RankingUpdateRequest request) {
    rankingService.updateRanking(request.getUser(), request.getScore());
  }

  @GetMapping("/top/{count}")
  public List<Ranking> getTopRankings(@PathVariable int count) {
    return rankingService.getTopRankings(count);
  }
}
