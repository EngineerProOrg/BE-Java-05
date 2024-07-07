package com.engineerpro.springboot.example.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.springboot.example.di.exception.InvalidStatusCodeException;
import com.engineerpro.springboot.example.di.scrapper.Scrapper;

@Service
public class CrawlerService {
  @Autowired
  private Scrapper scrapper;

  public int showStatusCodeOrRaiseException(String url) throws InvalidStatusCodeException {
    int statusCode = scrapper.crawlStatusCode(url);
    if (statusCode == 200) {
      System.out.println("Happy status code");
      return statusCode;
    }
    throw new InvalidStatusCodeException();
  }
}
