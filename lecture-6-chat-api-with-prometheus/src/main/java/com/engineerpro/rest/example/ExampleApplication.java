package com.engineerpro.rest.example;

import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.rest.example.model.App;
import com.engineerpro.rest.example.model.Channel;
import com.engineerpro.rest.example.model.Message;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.repository.AppRepository;
import com.engineerpro.rest.example.repository.ChannelRepository;
import com.engineerpro.rest.example.repository.MessageRepository;
import com.engineerpro.rest.example.repository.UserRepository;
import com.engineerpro.rest.example.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);
		UserService userService = context.getBean(UserService.class);
		AppRepository appRepository = context.getBean(AppRepository.class);
		ChannelRepository channelRepository = context.getBean(ChannelRepository.class);
		MessageRepository messageRepository = context.getBean(MessageRepository.class);

		String key = "engineer-pro-key";

		if (appRepository.findByApiClientKey(key) == null) {
			App app = appRepository.save(App.builder().isActive(true).apiClientKey(key).build());
			Channel channel = Channel.builder()
					.clientReferenceId("ref1")
					.name("channel1")
					.createdAt(Instant.now())
					.app(app)
					.build();
			Channel savedChannel = channelRepository.save(channel);
			log.info("appId = {} find by ref id = {}", app.getId(),
					channelRepository.findByAppIdAndClientReferenceId(app.getId(), "ref1"));
		}
		if (messageRepository.count() == 0) {
			Channel channel = channelRepository.findAll().get(0);
			User user = userRepository.findAll().get(0);
			for (int i = 0; i < 100; i++) {
				messageRepository.save(Message.builder()
						.channel(channel)
						.user(user)
						.message(String.format("Message %d", i))
						.imgUrl("imgurl")
						.isDeleted(false)
						.build());
			}
		}
	}

}
