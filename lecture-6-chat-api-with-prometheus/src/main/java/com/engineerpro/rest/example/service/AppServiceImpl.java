package com.engineerpro.rest.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.aop.logexecutiontime.LogExecutionTime;
import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.repository.AppRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppServiceImpl implements AppService {
  @Autowired
  private AppRepository appRepository;

  @Override
  @LogExecutionTime
  @Cacheable("getAppByClientKey")
  public App getAppByClientKey(String apiClientKey) {
    // 2 cases
    // read from redis => cache hit
    // else, read from db => cache miss
    // create counter redis.getAppByClientId.cache.hit
    // create counter redis.getAppByClientId.cache.miss

    // case: use payment API
    // counter metric: request.payment.error
    return appRepository.findByApiClientKey(apiClientKey);
  }
}
