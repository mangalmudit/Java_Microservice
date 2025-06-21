package com.assignmentdemo.ps.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentdemo.ps.api.Entity.Payment;
import com.assignmentdemo.ps.api.Entity.PaymentResponse;
import com.assignmentdemo.ps.api.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	private PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	/**
	 * Initiates a payment process and records the payment details.
	 * Accepts a Payment object in the request body and returns a PaymentResponse with the result of the payment operation.
	 */
	@PostMapping("/initiate")
	public ResponseEntity<PaymentResponse> handlePayment(@RequestBody Payment payment) {
		PaymentResponse response = paymentService.makeAndRecordPayment(payment);
		return new ResponseEntity<PaymentResponse>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Verifies the status of a payment using the provided payment ID.
	 * Accepts a payment ID as a path variable and returns a PaymentResponse with the verification result.
	 */
	@GetMapping("/{paymentId}")
	public PaymentResponse verifyPayment(@PathVariable int paymentId) {
		logger.info("Payment verification successful for ID: {}", paymentId);
		return paymentService.verifyPayment(paymentId);
	}
}