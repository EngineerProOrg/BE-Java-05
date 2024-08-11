package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.model.App;

@Service
public interface AppService {
  App getAppByClientKey(String apiClientKey);
}
