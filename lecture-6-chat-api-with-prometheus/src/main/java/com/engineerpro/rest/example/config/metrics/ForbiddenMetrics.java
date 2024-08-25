package com.engineerpro.rest.example.config.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class ForbiddenMetrics {
  private final Counter forbiddenCounter;

  public ForbiddenMetrics(MeterRegistry meterRegistry) {
    this.forbiddenCounter = meterRegistry.counter("forbidden.counter");
  }

  public void incrementCounter() {
    forbiddenCounter.increment();
  }
}
