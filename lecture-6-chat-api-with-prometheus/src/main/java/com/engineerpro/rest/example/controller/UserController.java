package com.engineerpro.rest.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.aop.logexecutiontime.LogExecutionTime;
import com.engineerpro.rest.example.dto.user.CreateUserRequest;
import com.engineerpro.rest.example.dto.user.CreateUserResponse;
import com.engineerpro.rest.example.dto.user.GetUserResponse;
import com.engineerpro.rest.example.model.User;
import com.engineerpro.rest.example.service.UserService;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/api/users")
public class UserController extends BaseController {
	@Autowired
	private final UserService userService;

	@GetMapping("{id}/by-client-user-id")
	@LogExecutionTime
	public ResponseEntity<GetUserResponse> getUser(@PathVariable String id) {
		User user = userService.getUserByClientId(this.getAuthenticatedApp(), id);
		return ResponseEntity.status(HttpStatus.OK).body(GetUserResponse.builder().user(user).build());
	}

	@PostMapping("/")
	@LogExecutionTime
	public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
		User user = userService.createUser(getAuthenticatedApp(), request);
		return ResponseEntity.status(HttpStatus.OK).body(CreateUserResponse.builder().user(user).build());
	}
}
