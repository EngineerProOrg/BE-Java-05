package com.engineerpro.di2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("v8engine")
public class V8Engine implements Engine {

  @Override
  public void start() {
    System.out.println("start v8 engine");
  }

}
