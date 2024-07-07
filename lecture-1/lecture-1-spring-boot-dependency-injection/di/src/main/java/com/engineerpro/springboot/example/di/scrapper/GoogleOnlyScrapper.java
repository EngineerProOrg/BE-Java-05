package com.engineerpro.springboot.example.di.scrapper;

import org.springframework.stereotype.Component;

@Component
public class GoogleOnlyScrapper implements Scrapper {

  @Override
  public int crawlStatusCode(String url) {
    if (url.contains("google")) {
      return 200;
    }
    return 404;
  }

}
