package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Validatator implements ValidateDataInterface {
	
	@Autowired
	private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
	
	@Override
	public boolean isValidJson(String nodeString) {
		boolean toReturn = false;
		
		ObjectMapper mapper = springMvcJacksonConverter.getObjectMapper();
		try {
			JsonNode actualObj = mapper.readTree(nodeString);
				toReturn = true;
		}catch(Exception e) {
			toReturn = false;
		}
		return toReturn;
	}

	@Override
	public boolean isValidString(String data) {
		if(data != null && data.length() > 0)
			return true;
		return false;
	}
	@PostConstruct
	public void init() {
		System.out.println("springMvcJacksonConverter" + springMvcJacksonConverter);
		System.out.println(springMvcJacksonConverter == null);
	}
}
