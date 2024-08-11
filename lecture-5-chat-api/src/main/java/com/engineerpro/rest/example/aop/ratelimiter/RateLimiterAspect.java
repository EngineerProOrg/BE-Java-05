package com.engineerpro.rest.example.aop.ratelimiter;

import java.lang.reflect.Method;
import java.time.Duration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.engineerpro.rest.example.model.App;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RateLimiterAspect {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private HttpServletRequest request;

  private static final String REDIS_RATE_LIMITER_KEY_PREFIX = "rate_limiter:";

  @Around("@annotation(RateLimit)")
  public Object rateLimiter(ProceedingJoinPoint joinPoint) throws Throwable {
    App app = (App) request.getAttribute("authenticatedApp");
    String apiKey = app.getApiClientKey();

    // Extract the method signature
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    RateLimit rateLimit = method.getAnnotation(RateLimit.class);

    // Extract the custom limit and time window
    int limit = rateLimit.limit();
    int timeWindow = rateLimit.timeWindow();

    String key = REDIS_RATE_LIMITER_KEY_PREFIX + apiKey + ":" + method.getName();
    ValueOperations<String, String> ops = redisTemplate.opsForValue();

    // Check current count
    String currentCountStr = ops.get(key);
    int currentCount = currentCountStr != null ? Integer.parseInt(currentCountStr) : 0;

    if (currentCount >= limit) {
      throw new RateLimitExceededException("Rate limit exceeded for API key: " + apiKey);
    }

    // Increment count
    ops.increment(key);

    // Set the expiration of the key if it's new
    if (currentCount == 0) {
      redisTemplate.expire(key, Duration.ofSeconds(timeWindow));
    }

    return joinPoint.proceed();
  }
}