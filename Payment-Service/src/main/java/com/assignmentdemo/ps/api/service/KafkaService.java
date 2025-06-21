package com.assignmentdemo.ps.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.assignmentdemo.ps.api.Entity.PaymentResponse;

@Service
public class KafkaService {

	private KafkaTemplate<String, String> kafkaTemplate;
	
	/**
     * Constructs a KafkaService with the provided KafkaTemplate.
     *
     * @param kafkaTemplate the KafkaTemplate used to send messages to Kafka
     */
	@Autowired
	public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	 /**
     * Sends the provided payment response to the Kafka topic "Payment-Response".
     * The payment response will be converted to a string and published to the topic.
     *
     * @param paymentResponse the payment response to be sent to Kafka
     */
	public void sendMessagetoTopic(PaymentResponse paymentResponse) {
		kafkaTemplate.send("Payment-Response", paymentResponse.toString());
	}

}
