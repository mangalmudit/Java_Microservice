package com.assignmentdemo.ps.api.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignmentdemo.ps.api.Entity.Payment;
import com.assignmentdemo.ps.api.Entity.PaymentResponse;
import com.assignmentdemo.ps.api.Entity.UserInfo;
import com.assignmentdemo.ps.api.exceptionhandler.InvalidCardDetailsException;
import com.assignmentdemo.ps.api.exceptionhandler.PaymentDetailsNotFoundException;
import com.assignmentdemo.ps.api.extenal.service.PaymentGatewayService;
import com.assignmentdemo.ps.api.repository.PaymentRepository;
import com.assignmentdemo.ps.api.repository.UserInfoRepository;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class PaymentServiceImpl implements PaymentService {

	
	private PaymentRepository paymentRepository;
	private PaymentGatewayService paymentGatewayService;
	private KafkaService kafkaService;
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentGatewayService paymentGatewayService,
			KafkaService kafkaService) {
		this.paymentRepository = paymentRepository;
		this.paymentGatewayService = paymentGatewayService;
		this.kafkaService = kafkaService;
	}
	
	/**
	 * Processes a payment and records the payment details in the repository.
	 * Uses a Circuit Breaker to handle failures in payment processing with a fallback method.
	 * Saves the payment response to the repository and returns the payment response.
	 * 
	 * @param paymentRequest The payment details to be processed.
	 * @return The payment response after processing and saving the payment details.
	 */
	@Override
	@CircuitBreaker(name = "paymentServiceCircuitBreaker", fallbackMethod = "fallbackMakeAndRecordPayment")
	public PaymentResponse makeAndRecordPayment(Payment paymentRequest) {
		ResponseEntity<PaymentResponse> paymentResponse = paymentGatewayService.processPayment(paymentRequest);
		
		 logger.info("Payment successful for transaction ID: {}", paymentResponse.getBody().getTransactionId());
		/*
		 * if(paymentResponse.getBody().getPaymentStatus().equals("failed")) { throw new
		 * InvalidCardDetailsException(paymentResponse.getBody().getMessage()); }
		 */
		 
		paymentRepository.save(paymentResponse.getBody());
		logger.info("Sending paymentresponse to kafka topic");
		kafkaService.sendMessagetoTopic(paymentResponse.getBody());
		
		return paymentResponse.getBody();
	}
	/**
	 * Fallback method for processing payments when the primary payment service fails.
	 * Sets the payment status to "FAILED" and provides a custom error message. 
	 * Saves the fallback response to the repository and sends a message to the topic.
	 * 
	 * @param paymentRequest The payment details that failed to be processed.
	 * @param throwable The exception that caused the failure.
	 * @return The fallback payment response with a failure status and message.
	 */
	public PaymentResponse fallbackMakeAndRecordPayment(Payment paymentRequest, Exception throwable) {
		PaymentResponse fallbackResponse = new PaymentResponse();
	
		logger.info("Payment processing failed executing the fallback method");
		fallbackResponse.setPaymentStatus("FAILED");
		fallbackResponse.setMessage("Gateway service is unavailable. Please try again later.");
		fallbackResponse.setTransactionId("Transaction Id Not Generated");

		paymentRepository.save(fallbackResponse);
		kafkaService.sendMessagetoTopic(fallbackResponse);
		return fallbackResponse;
	}

	/**
	 * Verifies if a payment exists by its ID.
	 * Retrieves the payment details from the repository using the provided payment ID.
	 * Throws PaymentDetailsNotFoundException if no payment is found with the given ID.
	 */
	@Override
	public PaymentResponse verifyPayment(int paymentId) {

		  logger.info("Verifying payment with ID: {}", paymentId);
		Optional<PaymentResponse> optionalPayment = paymentRepository.findById(paymentId);

		if (optionalPayment.isPresent()) {
			 logger.info("Payment found for ID: {}", paymentId);
			return optionalPayment.get();

		} else {
			logger.error("No payment found with ID: {}", paymentId);
			throw new PaymentDetailsNotFoundException("No payment found with id: " + paymentId);
		}

	}

}
