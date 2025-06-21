package com.assignmentdemo.ps.api.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PaymentResponse_TB")
public class PaymentResponse {
	@Id 
	@GeneratedValue
	private Integer paymentId;
	private String transactionId;
	private String paymentStatus;
	private String message;
}