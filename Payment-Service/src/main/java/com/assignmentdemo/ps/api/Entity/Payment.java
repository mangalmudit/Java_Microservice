package com.assignmentdemo.ps.api.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

	@Id
	@GeneratedValue
	private Integer paymentId;
	private Integer amount;
	private Integer accountNumber;
	private String cardNumber;
	private String cvv;
	private String receiverAccountNumber;
	private String recepientName;

}
