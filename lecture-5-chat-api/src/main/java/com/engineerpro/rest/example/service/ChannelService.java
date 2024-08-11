package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.channel.CreateChannelRequest;
import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.model.Channel;

@Service
public interface ChannelService {
  Channel createChannel(App app, CreateChannelRequest requets);

  Channel getChannelByReferenceId(App app, String clientReferenceId);

  Channel getChannelById(App app, String id);
}
