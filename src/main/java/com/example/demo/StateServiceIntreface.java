package com.example.demo;

public interface StateServiceIntreface {
	public void storeWordInCache(String s);
	public void storeEventInCache(String s);
	
	public int getWordKeyFromCache();
	public int getEventKeyFromCache();
	
	public boolean deleteWordKeyFromCache();
	public boolean deleteEventKeyFromCache();
	
	public void cleanCache();
}
