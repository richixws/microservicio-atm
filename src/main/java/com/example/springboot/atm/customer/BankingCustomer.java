package com.example.springboot.atm.customer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springboot.atm.dto.BankingDto;
import com.example.springboot.atm.dto.CreditDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankingCustomer {

	@Autowired
	@Qualifier("bankingProduct")
	private WebClient client;

	public Flux<BankingDto> findAllBankingProducts() {

		return client.get().uri("/findAll").retrieve().bodyToFlux(BankingDto.class);

	}

	public Mono<BankingDto> findBankingById(String id){
		return client.get()
				.uri("/findById/{id}",Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(BankingDto.class);
	}
	
	public Mono<BankingDto> findByNumAccountB(String numAccount){
		return client.get()
				.uri("/findByNumAccount/{numAccount}",Collections.singletonMap("numAccount", numAccount))
				.retrieve()
				.bodyToMono(BankingDto.class);
	}
	
	public Mono<BankingDto> save(BankingDto banking){
		return client.post()
				.uri("/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(banking))
				.retrieve()
				.bodyToMono(BankingDto.class);
	}
	
	public Mono<BankingDto> depositB(Double amount, String numAccount){
		Map<String, String> path = new HashMap<String, String>();
		
		path.put("amount", Double.toString(amount));
		path.put("numAccount", numAccount);
		
		return client.put()
				.uri("/depositB/{amount}/{numAccount}",path)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(BankingDto.class);
	}
	
	public Mono<BankingDto> retiroB(Double amount, String numAccount){
		Map<String, String> path = new HashMap<String, String>();
		
		path.put("amount", Double.toString(amount));
		path.put("numAccount", numAccount);
		
		return client.put()
				.uri("/retiroB/{amount}/{numAccount}",path)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(BankingDto.class);
	}
	
}
