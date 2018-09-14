package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;

public interface ValidateDataInterface {
	public boolean isValidJson(String node);
	public boolean isValidString(String data);
}
