package com.engineerpro.rest.example.aop.ratelimiter;

public class RateLimitExceededException extends RuntimeException {
  public RateLimitExceededException(String message) {
    super(message);
  }
}