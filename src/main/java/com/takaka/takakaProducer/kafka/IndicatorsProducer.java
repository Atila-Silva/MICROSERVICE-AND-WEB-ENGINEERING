package com.takaka.takakaProducer.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class IndicatorsProducer {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public IndicatorsProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void publish(String id, String indicator) {
		kafkaTemplate.send("indicator", id, indicator);
	}

}
