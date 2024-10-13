package com.engineerpro.example.redis.event;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorHandler implements RabbitListenerErrorHandler {
  @Override
  public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
      ListenerExecutionFailedException exception) throws Exception {
    // Custom error handling logic
    System.err.println("Error occurred: " + exception.getMessage());
    throw exception; // Rethrow the exception to trigger retry logic
  }
}
