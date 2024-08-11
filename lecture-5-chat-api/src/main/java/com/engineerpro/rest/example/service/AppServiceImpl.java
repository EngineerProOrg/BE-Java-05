package com.engineerpro.rest.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.repository.AppRepository;

@Service
public class AppServiceImpl implements AppService {
  @Autowired
  private AppRepository appRepository;

  @Override
  @Cacheable("getAppByClientKey")
  public App getAppByClientKey(String apiClientKey) {
    return appRepository.findByApiClientKey(apiClientKey);
  }
}
