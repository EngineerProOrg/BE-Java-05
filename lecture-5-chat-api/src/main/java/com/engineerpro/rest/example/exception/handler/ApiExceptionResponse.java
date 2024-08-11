package com.engineerpro.rest.example.exception.handler;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiExceptionResponse {
	private HttpStatus status;
	private String errorCode;
}
