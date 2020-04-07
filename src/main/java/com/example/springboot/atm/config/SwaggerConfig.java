package com.example.springboot.atm.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Richard Andrew Wong Solorzano",
			"https://github.com/richixws/microservicio-atm.git", "rwongsol@everis.com");
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Atm microservicio",
			"CRUD de operaciones Atm", "1.0", "urn:tos", DEFAULT_CONTACT.getName(), "Apache 2.0",
			"http://www.google.com.pe");
	private static Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));
	

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	} 
	
}
