package com.engineerpro.example.redis.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.engineerpro.example.redis.config.MessageQueueConfig;
import com.engineerpro.example.redis.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageListener {
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @RabbitListener(queues = MessageQueueConfig.PRIVATE_CHAT_QUEUE)
  public void handlePrivateMessage(String message) throws JsonMappingException, JsonProcessingException {
    log.info("Listener got message {}", message);
    ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
    messagingTemplate.convertAndSendToUser(
        chatMessage.getReceiver(), "/queue/messages", chatMessage);
    log.info("Listener send message to Receiver = {} , user {}", chatMessage.getReceiver(), message);
  }

}
