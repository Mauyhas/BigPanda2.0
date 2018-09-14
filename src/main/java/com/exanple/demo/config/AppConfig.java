package com.exanple.demo.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.demo.CacheService;
import com.example.demo.Validatator;

@Configuration
public class AppConfig {

	@Bean
	public Validatator getValidator() {
		System.out.println("repo from bean");
		return new Validatator();
	}
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CacheService getCache() {
		System.out.println("repo from bean");
		return new CacheService();
	}

	
}