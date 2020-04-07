package com.example.springboot.atm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.springboot.atm.model.Atm;

public interface IAtmRepository extends ReactiveMongoRepository<Atm, String>{

	
	
}
