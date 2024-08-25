package com.engineerpro.rest.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineerpro.rest.example.aop.logexecutiontime.LogExecutionTime;
import com.engineerpro.rest.example.aop.ratelimiter.RateLimit;
import com.engineerpro.rest.example.dto.app.InspectAppResponse;
import com.engineerpro.rest.example.model.App;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Timed(histogram = true)
@RequestMapping("/api/auth")
@Slf4j
public class AppController extends BaseController {

	@GetMapping("/inspect")
	@RateLimit(limit = 20, timeWindow = 20)
	@LogExecutionTime
	public ResponseEntity<InspectAppResponse> inspectApp(HttpServletRequest request) {
		App app = this.getAuthenticatedApp();
		log.info("class = {}, app = {}", app.getClass(), app);
		return ResponseEntity.ok(InspectAppResponse.builder().app(app).build());
	}
}
