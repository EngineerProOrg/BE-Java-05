package com.engineerpro.example.redis.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig {
  public static final String QUEUE_LIKE_POST = "like-post-queue";
  public static final String QUEUE_LIKE_POST_DLQ = "like-post-queue-dlq";

  @Bean
  Queue queueLikePost() {
    return QueueBuilder.durable(QUEUE_LIKE_POST).build();
  }

}
