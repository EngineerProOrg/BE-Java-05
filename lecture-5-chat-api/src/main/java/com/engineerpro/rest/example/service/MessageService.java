package com.engineerpro.rest.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.message.ListMessageRequest;
import com.engineerpro.rest.example.dto.message.SendMessageRequest;
import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.model.Message;

@Service
public interface MessageService {
  Message sendMessage(App app, String channelId, SendMessageRequest request);

  List<Message> listMessages(App app, ListMessageRequest request);
}
