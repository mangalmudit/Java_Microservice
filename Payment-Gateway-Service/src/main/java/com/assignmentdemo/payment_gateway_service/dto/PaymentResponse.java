package com.assignmentdemo.payment_gateway_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
	
	private Integer paymentId;;
	private String transactionId;
	private String paymentStatus;
	private String message;
	
}
