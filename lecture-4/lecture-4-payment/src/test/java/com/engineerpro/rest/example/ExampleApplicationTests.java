package com.engineerpro.rest.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.engineerpro.rest.example.controller.TransactionController;
import com.engineerpro.rest.example.controller.UserController;

@SpringBootTest
class ExampleApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private TransactionController transactionController;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
		assertThat(transactionController).isNotNull();
	}

}
