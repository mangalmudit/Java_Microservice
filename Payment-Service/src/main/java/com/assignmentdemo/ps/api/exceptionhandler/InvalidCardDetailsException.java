package com.assignmentdemo.ps.api.exceptionhandler;

public class InvalidCardDetailsException extends RuntimeException {

	
	public InvalidCardDetailsException(String message) {
		super(message);
	}
}
