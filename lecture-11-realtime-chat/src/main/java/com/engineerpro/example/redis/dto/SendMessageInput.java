package com.engineerpro.example.redis.dto;

import lombok.Data;

@Data
public class SendMessageInput {
	private String receiver;
	private String content;
}
