package com.engineerpro.di2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("v6engine")
public class V6Engine implements Engine {

  @Override
  public void start() {
    System.out.println("start v6 engine");
  }

}
