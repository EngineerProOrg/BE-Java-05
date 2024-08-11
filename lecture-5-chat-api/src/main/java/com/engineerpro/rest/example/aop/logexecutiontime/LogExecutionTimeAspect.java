package com.engineerpro.rest.example.aop.logexecutiontime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {

  @Around("@annotation(com.engineerpro.rest.example.aop.logexecutiontime.LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    final long start = System.currentTimeMillis();

    final Object proceed = joinPoint.proceed();

    final long executionTime = System.currentTimeMillis() - start;

    log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");

    return proceed;
  }

}