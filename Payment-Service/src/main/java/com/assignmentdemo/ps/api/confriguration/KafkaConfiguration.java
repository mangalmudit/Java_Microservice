package com.assignmentdemo.ps.api.confriguration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Kafka producer configuration class.
 * This class sets up the Kafka producer with properties externalized in the application.properties file.
 * It provides the KafkaTemplate that can be used to send messages to Kafka topics.
 */
@Configuration
public class KafkaConfiguration {
    
	// Externalized property for Kafka bootstrap servers
	@Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;
	   /**
     * Configures the Kafka producer with properties from the application.properties file.
     * The bootstrap server URL is injected into the producer configuration.
     *
     * @return KafkaTemplate configured with the producer properties
     */
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}
	/**
     * Creates and configures a KafkaTemplate bean to be used for sending messages to Kafka topics.
     * This method uses the configured ProducerFactory to set up the KafkaTemplate with the necessary properties.
     * 
     * @return KafkaTemplate configured with the producer factory
     */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
 
}
