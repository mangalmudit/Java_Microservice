package com.assignmentdemo.ps.api.exceptionhandler;

public class PaymentDetailsNotFoundException extends RuntimeException{
	public PaymentDetailsNotFoundException(String message) {
        super(message);
    }
}
