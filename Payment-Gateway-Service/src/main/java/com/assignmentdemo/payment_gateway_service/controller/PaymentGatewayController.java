package com.assignmentdemo.payment_gateway_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentdemo.payment_gateway_service.dto.Payment;
import com.assignmentdemo.payment_gateway_service.dto.PaymentResponse;
import com.assignmentdemo.payment_gateway_service.service.PaymentGatewayService;

@RestController
public class PaymentGatewayController {
	
	private PaymentGatewayService paymentGatewayService;
	
	@Autowired
    public PaymentGatewayController(PaymentGatewayService paymentGatewayService) {
        this.paymentGatewayService = paymentGatewayService;
    }
    /**
     * Processes the payment request and returns a response with the payment status.
     * This method accepts a payment request and processes it using the PaymentGatewayService.
     * 
     * @param payment the payment details to be processed
     * @return a ResponseEntity containing the payment response and HTTP status
     */
	@PostMapping("/api/payment")
	public ResponseEntity<PaymentResponse> processPayment(@RequestBody Payment payment) {
		// Process the payment through the payment gateway service
		PaymentResponse paymentResponse = paymentGatewayService.processPayment(payment);
		// Return the payment response along with an OK HTTP status
	    return new ResponseEntity<>(paymentResponse, HttpStatus.OK); 
	}

}
