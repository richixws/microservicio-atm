package com.example.springboot.atm.service;

import com.example.springboot.atm.dto.BankingDto;
import com.example.springboot.atm.dto.CreditDto;
import com.example.springboot.atm.model.Atm;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAtmService {

	// listar todo 
	public Flux<Atm> findAll();

	// buscar operacion por id 
	public Mono<Atm> findById(String id);

	// guardar una operacion 
	public Mono<Atm> save(Atm atm);
	
	//actualizar operacion
	public Mono<Atm> update(Atm atm , String id);
	
	//eliminar operacion
	public Mono<Void> delete(Atm atm);
	
	
	/*
	 * ------- metodos propios asignados
	 */
	
	public Mono<Atm> depositAccountBToAccountC(Double amount,String accountO, String accountD);
	
	public Mono<Atm> retiroAccountCToAccountB(Double amount,String accountO, String accountD);
	
	/*
	 * ------- metodos de banking Customer
	 */
	
	public Flux<BankingDto> findAllBankingProducts();
	
	public Mono<BankingDto> findBankingById(String id);
	
	public Mono<BankingDto> findByNumAccountB(String numAccount);
	
	public Mono<BankingDto> save(BankingDto bankDto);
	
	public Mono<BankingDto> depositB(Double amount,String numAccount);
	
	public Mono<BankingDto> retiroB(Double amount, String numAccount);
	
	/*
	 * ----- metodos de credit client --------------
	 */
	
	public Flux<CreditDto> findAllCreditProducts();
	
	public Mono<CreditDto> findCreditById(String id);
	
	public Mono<CreditDto> findByNumAccountC(String numberaAccount);
	
    public Mono<CreditDto> save(CreditDto credit);
	
	public Mono<CreditDto> depositC(Double amount, String numberAccount);
	
	public Mono<CreditDto> retiroC(Double amount, String numberAccount);
	
	
	

}
