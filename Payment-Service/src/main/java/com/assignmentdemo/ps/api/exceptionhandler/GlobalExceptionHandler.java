package com.assignmentdemo.ps.api.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaymentDetailsNotFoundException.class)
	public ResponseEntity<String> handlePaymenDetailstNotFoundException(PaymentDetailsNotFoundException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleUsernameNotFoundException(AuthenticationException ex) {

		return new ResponseEntity<>("Authentication failed: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
		return new ResponseEntity<>("Invalid credentials: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex){
		return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(),
				HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
