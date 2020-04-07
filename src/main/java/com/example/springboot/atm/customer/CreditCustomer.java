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

import com.example.springboot.atm.dto.CreditDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCustomer {

	@Autowired
	@Qualifier("creditProduct")
	private WebClient client;
	

		
		public Flux<CreditDto> findAllCreditProducts(){
			return client.get()
					.uri("/findAll")
					.retrieve()
					.bodyToFlux(CreditDto.class);
		}
		
		public Mono<CreditDto> findCreditById(String id){
			return client.get()
					.uri("/findById/{id}",Collections.singletonMap("id", id))
					.retrieve()
					.bodyToMono(CreditDto.class);
		}
		
		public Mono<CreditDto> findByNumAccountC(String numberAccount){
			return client.get()
					.uri("/findByNumAccount/{numberAccount}",Collections.singletonMap("numberAccount", numberAccount))
					.retrieve()
					.bodyToMono(CreditDto.class);
		}
		
		public Mono<CreditDto> save(CreditDto credit){
			return client.post()
					.uri("/create")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(credit))
					.retrieve()
					.bodyToMono(CreditDto.class);
		}
		
		public Mono<CreditDto> depositC(Double amount, String numberAccount){
			Map<String, String> path = new HashMap<String, String>();

			path.put("amount", Double.toString(amount));
			path.put("numberAccount", numberAccount);
			
			return client.put()
					.uri("/depositC/{amount}/{numberAccount}", path)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(CreditDto.class);
		}
		
		public Mono<CreditDto> retiroC(Double amount, String numberAccount){
			Map<String, String> path = new HashMap<String, String>();

			path.put("amount", Double.toString(amount));
			path.put("numberAccount", numberAccount);
			
			return client.put()
					.uri("/retiroC/{amount}/{numberAccount}", path)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(CreditDto.class);
		}
	
	
}
