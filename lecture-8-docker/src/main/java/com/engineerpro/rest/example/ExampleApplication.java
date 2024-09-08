package com.engineerpro.rest.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.model.User;
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

			User user1 = userRepository.save(User.builder().balance(100).build());

			GetUserBalanceResponse getUserBalanceResponse = userService
					.getUserBalance(GetUserBalanceRequest.builder().userId(user1.getId()).build());
			log.info("GetUserBalanceResponse={}", getUserBalanceResponse);
	}

}
