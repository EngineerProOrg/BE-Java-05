package com.engineerpro.rest.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.aop.logexecutiontime.LogExecutionTime;
import com.engineerpro.rest.example.dto.channel.CreateChannelRequest;
import com.engineerpro.rest.example.dto.channel.CreateChannelResponse;
import com.engineerpro.rest.example.dto.channel.GetChannelResponse;
import com.engineerpro.rest.example.model.Channel;
import com.engineerpro.rest.example.service.ChannelService;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/api/channels")
public class ChannelController extends BaseController {

	private final ChannelService channelService;

	@GetMapping("{id}")
	@LogExecutionTime
	public ResponseEntity<GetChannelResponse> getChannelById(@PathVariable String id) {
		Channel channel = channelService.getChannelById(getAuthenticatedApp(), id);
		return ResponseEntity.status(HttpStatus.OK).body(GetChannelResponse.builder().channel(channel).build());
	}

	@GetMapping("{clientReferenceId}/by-reference-id")
	@LogExecutionTime
	public ResponseEntity<GetChannelResponse> getChannelByReferenceId(@PathVariable String clientReferenceId) {
		Channel channel = channelService.getChannelByReferenceId(getAuthenticatedApp(), clientReferenceId);
		return ResponseEntity.status(HttpStatus.OK).body(GetChannelResponse.builder().channel(channel).build());
	}

	@PostMapping("/")
	@LogExecutionTime
	public ResponseEntity<CreateChannelResponse> createChannel(@Valid @RequestBody CreateChannelRequest request) {
		Channel channel = channelService.createChannel(getAuthenticatedApp(), request);
		return ResponseEntity.ok(CreateChannelResponse.builder().channel(channel).build());
	}
}
