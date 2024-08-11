package com.engineerpro.rest.example.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.rest.example.dto.channel.CreateChannelRequest;
import com.engineerpro.rest.example.exception.ChannelExistedException;
import com.engineerpro.rest.example.exception.ChannelNotFoundException;
import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.model.Channel;
import com.engineerpro.rest.example.repository.ChannelRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {
  @Autowired
  private ChannelRepository channelRepository;

  @Override
  public Channel createChannel(App app, CreateChannelRequest request) {
    log.info("Create channel app={} request={}", app, request);
    if (channelRepository.findByAppIdAndClientReferenceId(app.getId(), request.getClientReferenceId()).isPresent()) {
      throw new ChannelExistedException();
    }
    Channel channel = Channel.builder()
        .clientReferenceId(request.getClientReferenceId())
        .name(request.getName())
        .createdAt(Instant.now())
        .app(app)
        .build();
    Channel savedChannel = channelRepository.save(channel);
    log.info("saved {}", channel.getId());
    return savedChannel;
  }

  @Override
  public Channel getChannelByReferenceId(App app, String clientReferenceId) {
    log.info("Find channel app={} clientReferenceId={}", app, clientReferenceId);
    Optional<Channel> optionalChannel = channelRepository.findByAppIdAndClientReferenceId(app.getId(),
        clientReferenceId);
    return optionalChannel.orElseThrow(() -> new ChannelNotFoundException());
  }

  @Override
  public Channel getChannelById(App app, String id) {
    return channelRepository.findById(id).orElseThrow(() -> new ChannelNotFoundException());
  }
}
