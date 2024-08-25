package com.engineerpro.rest.example.exception.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.engineerpro.rest.example.aop.ratelimiter.RateLimitExceededException;
import com.engineerpro.rest.example.exception.ChannelExistedException;
import com.engineerpro.rest.example.exception.ChannelNotFoundException;
import com.engineerpro.rest.example.exception.ClientUserIdExistedException;
import com.engineerpro.rest.example.exception.DuplicatedIdempotentKeyException;
import com.engineerpro.rest.example.exception.NotEnoughBalanceException;
import com.engineerpro.rest.example.exception.TooManyRequestException;
import com.engineerpro.rest.example.exception.UserNotFoundException;

@RestControllerAdvice()
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String ERROR_CODE_INTERNAL = "INTERNAL_ERROR";
	private static final Map<Class<? extends RuntimeException>, HttpStatus> EXCEPTION_TO_HTTP_STATUS_CODE = Map.of(
			UserNotFoundException.class, HttpStatus.NOT_FOUND,
			DuplicatedIdempotentKeyException.class, HttpStatus.CONFLICT,
			ChannelExistedException.class, HttpStatus.CONFLICT,
			NotEnoughBalanceException.class, HttpStatus.BAD_REQUEST,
			TooManyRequestException.class, HttpStatus.TOO_MANY_REQUESTS,
			ChannelNotFoundException.class, HttpStatus.NOT_FOUND,
			ClientUserIdExistedException.class, HttpStatus.CONFLICT,
			RateLimitExceededException.class, HttpStatus.TOO_MANY_REQUESTS);

	private static final Map<Class<? extends RuntimeException>, String> EXCEPTION_TO_ERROR_CODE = Map.of(
			DuplicatedIdempotentKeyException.class, "DUPLICATED_IDEMPOTENT_KEY",
			NotEnoughBalanceException.class, "NOT_ENOUGH_BALANCE",
			ChannelNotFoundException.class, "CHANNEL_NOT_FOUND",
			UserNotFoundException.class, "USER_NOT_FOUND",
			ClientUserIdExistedException.class, "EXISTED_CLIENT_USER_ID",
			ChannelExistedException.class, "EXISTED_CHANNEL_REFERENCE_ID",
			TooManyRequestException.class, "TOO_MANY_REQUEST",
			RateLimitExceededException.class, "RATE_LIMIT_EXCEEDED");

	@ExceptionHandler()
	ResponseEntity<ApiExceptionResponse> handleUserNotFoundException(RuntimeException exception) {
		HttpStatus httpStatus = EXCEPTION_TO_HTTP_STATUS_CODE.getOrDefault(exception.getClass(),
				HttpStatus.INTERNAL_SERVER_ERROR);
		String errorCode = EXCEPTION_TO_ERROR_CODE.getOrDefault(exception.getClass(), ERROR_CODE_INTERNAL);

		final ApiExceptionResponse response = ApiExceptionResponse.builder().status(httpStatus).errorCode(errorCode)
				.build();

		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
