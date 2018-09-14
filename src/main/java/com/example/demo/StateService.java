package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class StateService implements StateServiceIntreface{
	
	private static Map<String, Integer> m_wordCounter = new HashMap();
	private static Map<String, Integer> m_eventCounter = new HashMap();
	@Override
	public void storeWordInCache(String s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void storeEventInCache(String s) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getWordKeyFromCache() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getEventKeyFromCache() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean deleteWordKeyFromCache() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean deleteEventKeyFromCache() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void cleanCache() {
		// TODO Auto-generated method stub
		
	}
	


}
