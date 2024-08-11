package com.engineerpro.rest.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.rest.example.model.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
  Optional<Channel> findByAppIdAndClientReferenceId(String appId, String clientReferenceId);
  // Channel findByAppIdAndClientReferenceId(String appId, String clientReferenceId);
}
