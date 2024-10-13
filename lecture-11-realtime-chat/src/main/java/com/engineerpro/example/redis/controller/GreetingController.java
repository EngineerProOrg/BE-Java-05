package com.engineerpro.example.redis.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.engineerpro.example.redis.dto.Greeting;
import com.engineerpro.example.redis.dto.HelloMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		log.info("got message {}", message);
		Thread.sleep(500); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

}
