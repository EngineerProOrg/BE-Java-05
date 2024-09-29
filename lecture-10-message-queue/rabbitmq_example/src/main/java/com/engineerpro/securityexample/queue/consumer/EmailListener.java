package com.engineerpro.securityexample.queue.consumer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.engineerpro.securityexample.entity.User;
import com.engineerpro.securityexample.repository.UserRepository;
import com.engineerpro.securityexample.service.UserEmailService;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Component
@ConditionalOnProperty(name = "myapp.runDLQ.enabled", havingValue = "false", matchIfMissing = true)
@Slf4j
public class EmailListener {

  private final UserRepository userRepository;
  private final UserEmailService userEmailService;

  public EmailListener(UserEmailService userEmailService, UserRepository userRepository) {
    this.userEmailService = userEmailService;
    this.userRepository = userRepository;
    log.info("Init EmailListener");
  }

  @RabbitListener(queues = "emailQueue", ackMode = "MANUAL")
  public void sendActivationEmail(String userIdStr, Channel channel, Message message) throws IOException {
    // Retrieve the current retry count (default to 0 if not present)
    log.info("Start handle send activation email to userIdStr=" + userIdStr + " headers="
        + message.getMessageProperties().getHeaders());
    try {
      User user = userRepository.findById(Integer.parseInt(userIdStr))
          .orElseThrow(() -> new RuntimeException("Cannot find userId " + userIdStr));
      if (user.getIsSentActivationCode()) {
        // handle deduplication
        log.info("User " + userIdStr + " already activated");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        return;
      }
      userEmailService.sendUserActivationEmail(user);
      user.setIsSentActivationCode(true);
      userRepository.save(user);
      log.info("Saved isSentActivationCode");
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      log.info("ack message to queue");
    } catch (Exception ex) {
      channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
  }
}
