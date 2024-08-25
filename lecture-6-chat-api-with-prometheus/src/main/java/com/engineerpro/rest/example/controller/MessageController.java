package com.engineerpro.rest.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.aop.logexecutiontime.LogExecutionTime;
import com.engineerpro.rest.example.dto.message.ListMessageRequest;
import com.engineerpro.rest.example.dto.message.ListMessageResponse;
import com.engineerpro.rest.example.dto.message.SendMessageRequest;
import com.engineerpro.rest.example.dto.message.SendMessageResponse;
import com.engineerpro.rest.example.model.Message;
import com.engineerpro.rest.example.service.MessageService;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/api/messages")
public class MessageController extends BaseController {
	@Autowired
	private final MessageService messageService;

	@PostMapping("/{channelId}")
	@LogExecutionTime
	public ResponseEntity<SendMessageResponse> sendMessage(@PathVariable String channelId,
			@Valid @RequestBody SendMessageRequest request) {
		Message message = messageService.sendMessage(getAuthenticatedApp(), channelId, request);
		return ResponseEntity.status(HttpStatus.OK).body(SendMessageResponse.builder().message(message).build());
	}

	@GetMapping("{channelId}")
	@LogExecutionTime
	public ResponseEntity<ListMessageResponse> getUser(@PathVariable String channelId,
			@RequestParam("pivotId") long pivotId,
			@RequestParam("prevLimit") int prevLimit, @RequestParam("nextLimit") int nextLimit) {
		List<Message> messages = messageService.listMessages(getAuthenticatedApp(), ListMessageRequest.builder()
				.channelId(channelId)
				.pivotId(pivotId)
				.prevLimit(prevLimit)
				.nextLimit(nextLimit)
				.build());
		return ResponseEntity.status(HttpStatus.OK).body(ListMessageResponse.builder().messages(messages).build());
	}

}
