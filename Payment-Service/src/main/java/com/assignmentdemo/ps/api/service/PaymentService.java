package com.assignmentdemo.ps.api.service;

import org.springframework.http.ResponseEntity;

import com.assignmentdemo.ps.api.Entity.Payment;
import com.assignmentdemo.ps.api.Entity.PaymentResponse;
import com.assignmentdemo.ps.api.Entity.UserInfo;

public interface PaymentService {

	PaymentResponse makeAndRecordPayment(Payment paymentRequest);

	PaymentResponse verifyPayment(int paymentId);

}
