package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import kafka.ConsumerCreator;
import kafka.ProducerCreator;
@SpringBootApplication
public class ExeMain implements CommandLineRunner{
	
	public static final String M_EXE_FILE_PATH = "C:\\bigpanda\\generator-windows-amd64.exe";
	public static final String M_OPT = "-get t";
	

	//@Autowired - cannot autowire static
	
	
	public static void main(String[] args) {
		//Producer<Long, String> producer = ProducerCreator.createProducer();
		// 1-run exe
		ApplicationContext  applicationContext = SpringApplication.run(ExeMain.class, args);
		
	}

	@Autowired
	private Validatator validatatorService;
	
	@Autowired CacheService cacheService;
	
	@Override
	public void run(String... args) throws Exception {
		Runtime rt = Runtime.getRuntime();
		String[] commands = {M_EXE_FILE_PATH , M_OPT };
		
		try {
			
			
			Process proc = rt.exec(commands);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			// read the output from the command
			
			String inputString = null;
			while ((inputString = stdInput.readLine()) != null) {
				try {
					System.out.println("reading: " + inputString);
					if(validatatorService.isValidString(inputString) && validatatorService.isValidJson(inputString)){
						System.out.println("valid input: " + inputString);
						cacheService.storeDataInCacheQueue(inputString);
					}else {
						System.out.println("unvalid input: " + inputString);
					}
				}catch(IOException e) {
					System.out.println(e.toString());
				}catch (Exception e) {
					System.out.println(e.toString());
					
				}
				  //putMsgInQueue(inputString, producer);
				//testing //getMsgFromQueue(consumer);
			}

			// read error stream as well
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((inputString = stdError.readLine()) != null) {
				System.out.println(inputString);
				//log them for now....
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	


}
