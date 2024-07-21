package com.engineerpro.example.springdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.example.springdata.exception.UsernameExistException;
import com.engineerpro.example.springdata.model.User;
import com.engineerpro.example.springdata.repository.UserRepository;
import com.engineerpro.example.springdata.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringdataApplication {


	public static void main(String[] args) throws UsernameExistException, InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(SpringdataApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		UserRepository userRepository = context.getBean(UserRepository.class);

		// userService.register(new UserRegisterRequest("admin1", "123"));
		User admin = userRepository.findUserByUsername("admin");
		log.info("admin={}", admin);
	}

}
