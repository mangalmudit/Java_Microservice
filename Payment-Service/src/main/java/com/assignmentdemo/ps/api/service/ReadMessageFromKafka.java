package com.assignmentdemo.ps.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.assignmentdemo.ps.api.Entity.PaymentResponse;
import com.assignmentdemo.ps.api.repository.PaymentRepository;

@Service
public class ReadMessageFromKafka {
	
	@KafkaListener(topics = "Payment-Response", groupId = "payment-group")
	public void readkafkaMessage(String paymentResponse)

	{
		 System.out.println(paymentResponse);
	}
}
