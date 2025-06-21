package com.assignmentdemo.payment_gateway_service.service;

import org.springframework.http.ResponseEntity;

import com.assignmentdemo.payment_gateway_service.dto.Payment;
import com.assignmentdemo.payment_gateway_service.dto.PaymentResponse;

public interface PaymentGatewayService {

	PaymentResponse processPayment(Payment payment);

}
