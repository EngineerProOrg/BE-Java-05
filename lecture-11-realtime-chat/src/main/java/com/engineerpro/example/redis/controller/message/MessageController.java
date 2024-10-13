package com.engineerpro.example.redis.controller.message;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.engineerpro.example.redis.config.MessageQueueConfig;
import com.engineerpro.example.redis.dto.SendMessageInput;
import com.engineerpro.example.redis.dto.UserPrincipal;
import com.engineerpro.example.redis.exception.UserNotFoundException;
import com.engineerpro.example.redis.model.ChatMessage;
import com.engineerpro.example.redis.model.User;
import com.engineerpro.example.redis.repository.ChatMessageRepository;
import com.engineerpro.example.redis.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MessageController {
  @Autowired
  UserRepository userRepository;

  @Autowired
  ChatMessageRepository chatMessageRepository;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat")
  @SendToUser("/queue/messages")
  public ChatMessage sendMessage(SendMessageInput input, Authentication authentication) throws JsonProcessingException {
    log.info("got input {}", input);
    // find the receiver user
    Optional<User> receiver = userRepository.findByUsername(input.getReceiver());
    if (!receiver.isPresent()) {
      throw new UserNotFoundException();
    }
    // create a chat message record
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    ChatMessage chatMessage = chatMessageRepository
        .save(ChatMessage.builder().content(input.getContent()).receiver(input.getReceiver())
            .sender(userPrincipal.getId().toString()).timestamp(LocalDateTime.now()).build());
    // send chat message to topic exchange
    String routingKey = "chat.private." + input.getReceiver();
    messagingTemplate.convertAndSendToUser(
        chatMessage.getReceiver(), "/queue/messages", chatMessage);
    // rabbitTemplate.convertAndSend(MessageQueueConfig.CHAT_EXCHANGE, routingKey,
    // objectMapper.writeValueAsString(chatMessage));
    log.info("sent message to chat exchange = {}, routing Key = {}, message = {}",
        MessageQueueConfig.CHAT_EXCHANGE,
        routingKey, chatMessage);
    return chatMessage;
  }
}
