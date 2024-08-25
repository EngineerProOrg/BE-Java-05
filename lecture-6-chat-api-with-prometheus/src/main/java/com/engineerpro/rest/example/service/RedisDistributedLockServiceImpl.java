package com.engineerpro.rest.example.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisDistributedLockServiceImpl implements DistributedLockService {
  private static final int LOCK_TIME_IN_MINUTE = 3;
  private static final String LOCK_VALUE = "1";
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  private String generateLockKey(String idempotentKey) {
    // lockKey = lock:receipt_1000
    return String.format("lock:%s", idempotentKey);
  }

  @Override
  public boolean accquireLock(String idempotentKey) {
    // lockKey = lock:receipt_1000
    String lockKey = generateLockKey(idempotentKey);
    return redisTemplate.opsForValue().setIfAbsent(lockKey, LOCK_VALUE, Duration.ofMinutes(LOCK_TIME_IN_MINUTE));
  }

  @Override
  public void releaseLock(String idempotentKey) {
    // lockKey = lock:receipt_1000
    String lockKey = generateLockKey(idempotentKey);
    redisTemplate.delete(lockKey);
  }

}
