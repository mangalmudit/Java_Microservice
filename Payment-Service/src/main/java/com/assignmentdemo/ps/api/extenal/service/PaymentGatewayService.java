package com.assignmentdemo.ps.api.extenal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.assignmentdemo.ps.api.Entity.Payment;
import com.assignmentdemo.ps.api.Entity.PaymentResponse;

@FeignClient(value="Payment-Gateway-Service",url ="http://localhost:8081")
public interface PaymentGatewayService {

    @PostMapping("/api/payment")
    ResponseEntity<PaymentResponse> processPayment(@RequestBody Payment payment);
}
