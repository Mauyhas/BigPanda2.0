package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.ProducerCreator;


public class CacheService {
	
	private final String EVENT_KEY_NAME = "event_type";
	private final String DATA_KEY_NAME = "data";
	
	private  HashMap<String, Integer> m_dataMap;
	@Autowired
	private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
	ObjectMapper mapper;
	Producer<Long, String> producer; 
	
	public CacheService() {
		m_dataMap = new HashMap<String, Integer>();
		mapper = springMvcJacksonConverter.getObjectMapper();
		producer = ProducerCreator.createProducer();
	}
	
	public void storeDataInCacheQueue(String inputString) throws IOException {
		JsonNode actualObj = mapper.readTree(inputString);
		if(actualObj != null) {
			if(actualObj.has(EVENT_KEY_NAME)) {
				storeEventInCache(actualObj);
			}
			if(actualObj.has(EVENT_KEY_NAME)) {
				storeDataInCache(actualObj);
			}
			publishStatesToQueue();
		}else {
			System.out.println("Fail to convert to json:" + inputString);
		}
	}

	private void publishStatesToQueue() {
		
		JsonNode jsonToQueue = mapper.convertValue(m_dataMap, JsonNode.class);
		/**
		 * So API will be decoupled use queues
		 */
		putMsgInQueue(jsonToQueue.asText(), producer);
		
	}
	private static void putMsgInQueue(String s, Producer<Long, String> producer) {
		System.out.println("msg to producer= " + s);
		if (producer == null)
			producer = ProducerCreator.createProducer();

		final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME, s);
		try {
			RecordMetadata metadata = producer.send(record).get();
			System.out.println("to partition " + metadata.partition() + " with offset " + metadata.offset());
		} catch (ExecutionException e) {
			System.out.println("Error in sending record");
			System.out.println(e);
		} catch (InterruptedException e) {
			System.out.println("Error in sending record");
			System.out.println(e);
		}
	}
	private void storeDataInCache(JsonNode actualObj) {
		String dataKey = actualObj.get(DATA_KEY_NAME).asText();
		if(m_dataMap.containsKey(dataKey)) {
			m_dataMap.put(dataKey, m_dataMap.get(dataKey));
		}else {
			m_dataMap.put(dataKey, 1);
		}
	}

	private void storeEventInCache(JsonNode actualObj) {
		String eventKey = actualObj.get(EVENT_KEY_NAME).asText();
		if(m_dataMap.containsKey(eventKey)) {
			m_dataMap.put(eventKey, m_dataMap.get(eventKey));
		}else {
			m_dataMap.put(eventKey, 1);
		}
	}

}
