package com.assignmentdemo.payment_gateway_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	private Integer paymentId;
	private Integer amount;
	private Integer accountNumber;
	private String cardNumber;
	private String cvv;
	private String receiverAccountNumber;
	private String recepientName;

}
