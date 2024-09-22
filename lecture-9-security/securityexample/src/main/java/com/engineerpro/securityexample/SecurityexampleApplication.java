package com.engineerpro.securityexample;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.engineerpro.securityexample.dto.RegisterRequest;
import com.engineerpro.securityexample.entity.User;
import com.engineerpro.securityexample.repository.UserRepository;
import com.engineerpro.securityexample.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SecurityexampleApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SecurityexampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService authenticationService,
			UserRepository userRepository) {
		return args -> {
			String adminEmail = "admin@mail.com";
			Optional<User> optionalUser = userRepository.findByEmail(adminEmail);
			if (optionalUser.isEmpty()) {
				RegisterRequest admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email(adminEmail)
						.password("password")
						.build();
				log.info("Admin token: " + authenticationService.registerAdmin(admin).getAccessToken());
			}
		};
	}

}
