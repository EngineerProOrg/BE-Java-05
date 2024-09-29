package com.engineerpro.securityexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

@Configuration
public class RabbitMQConfig {
  // Define the email queue
  @Bean
  public Queue emailQueue() {
    return QueueBuilder.durable("emailQueue")
        .withArgument("x-dead-letter-exchange", "email-dlx-exchange") // Forward failed messages to DLX
        .withArgument("x-dead-letter-routing-key", "email-dlx-routing-key")
        .withArgument("x-message-ttl", 60000) // Time to live for the messages in the original queue (optional)
        .build();
  }

  // Define the exchange for email queue
  @Bean
  public DirectExchange exchange() {
    return new DirectExchange("email-exchange");
  }

  // Binding email queue to exchange with routing key
  @Bean
  public Binding emailQueueBinding(Queue emailQueue, DirectExchange exchange) {
    return BindingBuilder.bind(emailQueue).to(exchange).with("email-routing-key");
  }

  // Define the Dead Letter Queue (DLQ)
  @Bean
  public Queue emailDLQ() {
    return new Queue("emailDLQ");
  }

  // Define the Dead Letter Exchange (DLX)
  @Bean
  public DirectExchange deadLetterExchange() {
    return new DirectExchange("email-dlx-exchange");
  }

  // Binding DLQ to DLX with its routing key
  @Bean
  public Binding dlqBinding(Queue emailDLQ, DirectExchange deadLetterExchange) {
    return BindingBuilder.bind(emailDLQ).to(deadLetterExchange).with("email-dlx-routing-key");
  }
}
