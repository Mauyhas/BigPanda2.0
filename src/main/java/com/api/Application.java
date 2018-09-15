package com.api;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.IKafkaConstants;

import kafka.ProducerCreator;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}







	protected static void runProducer(String msg) {
		Producer<Long, String> producer = ProducerCreator.createProducer();

		final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
				msg);
		try {
			RecordMetadata metadata = producer.send(record).get();
			System.out.println("sending: " + metadata);
		} catch (ExecutionException e) {
			System.out.println("Error in sending record");
			System.out.println(e);
		} catch (InterruptedException e) {
			System.out.println("Error in sending record");
			System.out.println(e);
		}
	}
}
