package com.engineerpro.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.example.jpa.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class JpaApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JpaApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		// userRepository.save(User.builder().username("admin1").password("123").build());

		log.info("user={}", userRepository.findUserByUsername("admin"));
	}

}
