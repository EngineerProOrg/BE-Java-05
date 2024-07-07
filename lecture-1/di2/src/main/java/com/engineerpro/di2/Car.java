package com.engineerpro.di2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Car {
  @Autowired
  private CarKey carKey;

  @Autowired
  @Qualifier("v6engine")
  private Engine engine;

  @Autowired
  @Qualifier("brand")
  private String brand;

  @Autowired
  @Qualifier("model")
  private String model;

  @Value("${myapp.custom.country}")
  private String country;

  // @Autowired
  // public Car(CarKey carKey, @Qualifier("v6engine") Engine engine) {
  // this.carKey = carKey;
  // this.engine = engine;
  // }

  // @Autowired
  // public void setEngine(Engine engine) {
  //   this.engine = engine;
  // }

  public void open() {
    System.out.println(String.format("car: open the car: %s - %s - %s", country, brand, model));
    carKey.clickOpenButton();
  }

  public void start() {
    System.out.println("car: start the car");
    engine.start();
  }
}
