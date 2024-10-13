package com.engineerpro.example.redis.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class MessageQueueConfig {
  public static final String PRIVATE_CHAT_QUEUE = "privateChatQueue";
  public static final String CHAT_EXCHANGE = "chatExchange";

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();

    // Define your retry policies and backoff strategies here
    // For example:
    retryTemplate.setRetryPolicy(new SimpleRetryPolicy(2));
    // retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());

    return retryTemplate;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
      RetryTemplate retryTemplate) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setRetryTemplate(retryTemplate);
    return factory;
  }

  @Bean
  @Qualifier(PRIVATE_CHAT_QUEUE)
  Queue privateChatQueue() {
    return new Queue(PRIVATE_CHAT_QUEUE, false);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(CHAT_EXCHANGE);
  }

  @Bean
  Binding binding(@Qualifier(PRIVATE_CHAT_QUEUE) Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("chat.private.#");
  }

}
