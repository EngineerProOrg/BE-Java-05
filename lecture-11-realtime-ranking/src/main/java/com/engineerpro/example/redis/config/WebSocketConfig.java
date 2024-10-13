package com.engineerpro.example.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Value("${spring.rabbitmq.stomp.relay.host}")
	private String relayHost;

	@Value("${spring.rabbitmq.stomp.relay.port}")
	private int relayPort;

	@Value("${spring.rabbitmq.stomp.relay.login}")
	private String relayLogin;

	@Value("${spring.rabbitmq.stomp.relay.passcode}")
	private String relayPasscode;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// config.enableSimpleBroker("/topic");
		config.enableStompBrokerRelay("/topic")
				.setRelayHost(relayHost)
				.setRelayPort(relayPort)
				.setClientLogin(relayLogin)
				.setClientPasscode(relayPasscode);
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws");
	}

}
