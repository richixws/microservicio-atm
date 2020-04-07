package com.example.springboot.atm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	@Bean
	@Qualifier("bankingProduct")
	public WebClient getClient() {
		return WebClient.create("http://localhost:8104/bankingProduct");
	}
	
	@Bean
	@Qualifier("creditProduct")
	public WebClient getCredit() {
		return WebClient.create("http://localhost:8104/creditProduct");
	}
}
