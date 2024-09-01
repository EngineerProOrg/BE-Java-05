package com.engineerpro.rest.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.dto.GetUserBalanceRequest;
import com.engineerpro.rest.example.dto.GetUserBalanceResponse;
import com.engineerpro.rest.example.service.UserService;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@GetMapping("{id}/balance")
	public ResponseEntity<GetUserBalanceResponse> getUserBalance(@PathVariable Integer id) {
		
		final GetUserBalanceResponse registrationResponse = userService
				.getUserBalance(GetUserBalanceRequest.builder().userId(id).build());

		return ResponseEntity.status(HttpStatus.OK).body(registrationResponse);
	}

}
