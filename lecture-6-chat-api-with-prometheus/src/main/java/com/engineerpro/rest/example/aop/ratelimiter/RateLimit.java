package com.engineerpro.rest.example.aop.ratelimiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
  int limit() default 5; // Default limit is 5 requests

  int timeWindow() default 60; // Default time window is 60 seconds (1 minute)
}