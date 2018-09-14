package com.example.demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kafka.ConsumerCreator;

@RestController
public class JsonController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	protected static ConsumerRecords<Long, String> runConsumer() {
		Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
		ConsumerRecords<Long, String> consumerRecords = consumer.poll(1);
		consumer.commitAsync();
		consumer.close();
		return consumerRecords;
	}
}
