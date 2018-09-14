package com.example.demo.api;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import kafka.ConsumerCreator;

@RestController
public class JsonAPIController {
	//TODO - inject mapper to this controller
	
    @GetMapping(path = "/hello", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sayHello()
    {
    	Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
        JsonNode toReturn = getMsgFromQueue(consumer);        
        return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
    }


	private  JsonNode getMsgFromQueue(Consumer<Long, String> consumer) {
		System.out.println("getMsgFromQueue");
		//TODO populate toReturn object 
		JsonNode toReturn = null;
		//TODO try-cacth block
		if(consumer == null)
			consumer = ConsumerCreator.createConsumer();

		final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
		consumerRecords.forEach(record -> {
			System.out.println("Record Key " + record.key());
			System.out.println("Record value " + record.value());
			System.out.println("Record partition " + record.partition());
			System.out.println("Record offset " + record.offset());
		});
		consumer.commitAsync();

		consumer.close();
		return toReturn;
		
	}
}
