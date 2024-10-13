package com.engineerpro.example.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.engineerpro.example.redis.model.Ranking;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RankingService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  private static final String RANKING_KEY = "ranking";

  public void updateRanking(String user, double score) {
    ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
    // update score to sorted set
    zSetOps.add(RANKING_KEY, user, score);
    // get top 10 highest score
    Set<TypedTuple<Object>> data = zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9);
    log.info("data = {}, {}", data);
    List<Ranking> topRankings = zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9)
        .stream()
        .map(tuple -> {
          return new Ranking(tuple.getValue().toString(), tuple.getScore());
        })
        .collect(Collectors.toList());
    // send to message broker
    messagingTemplate.convertAndSend("/topic/rankings", topRankings);
  }

  public List<Ranking> getTopRankings(int count) {
    return redisTemplate.opsForZSet()
        .reverseRangeWithScores(RANKING_KEY, 0, count)
        .stream()
        .map(tuple -> {
          Ranking ranking = new Ranking(tuple.getValue().toString(), tuple.getScore());
          return ranking;
        })
        .collect(Collectors.toList());
  }

}
