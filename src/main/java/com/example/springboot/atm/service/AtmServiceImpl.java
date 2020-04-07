package com.example.springboot.atm.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.atm.customer.BankingCustomer;
import com.example.springboot.atm.customer.CreditCustomer;
import com.example.springboot.atm.dto.BankingDto;
import com.example.springboot.atm.dto.CreditDto;
import com.example.springboot.atm.model.Atm;
import com.example.springboot.atm.repository.IAtmRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AtmServiceImpl implements IAtmService {

	private static final Logger log = LoggerFactory.getLogger(AtmServiceImpl.class);

	@Autowired
	private IAtmRepository repository;

	@Autowired
	private BankingCustomer bankingcustomer;

	@Autowired
	private CreditCustomer creditcustomer;

	@Override
	public Flux<Atm> findAll() {

		return repository.findAll();
	}

	@Override
	public Mono<Atm> findById(String id) {

		return repository.findById(id);
	}

	@Override
	public Mono<Atm> save(Atm atm) {

		if (atm.getOperationDate() == null) {
			atm.setOperationDate(new Date());
		} else {

			atm.setOperationDate(atm.getOperationDate());
		}

		return repository.save(atm);
	}

	@Override
	public Mono<Atm> update(Atm atm, String id) {

		return repository.findById(id).flatMap(a -> {
			// cuenta origen
			a.setNumAccountO(atm.getNameAccountO());
			// cuenta destino
			a.setNumAccountD(atm.getNumAccountD());
			// monto a enviar
			a.setAmount(atm.getAmount());
			// fecha
			if (atm.getOperationDate() == null) {
				a.setOperationDate(a.getOperationDate());
			} else {
				a.setOperationDate(atm.getOperationDate());
			}
			return repository.save(a);

		});
	}

	@Override
	public Mono<Void> delete(Atm atm) {

		return repository.delete(atm);
	}

	@Override
	public Mono<Atm> depositAccountBToAccountC(Double amount, String accountO, String accountD) {

		Atm a = new Atm();

		creditcustomer.depositC(amount, accountD).subscribe();
		bankingcustomer.retiroB(amount, accountO).subscribe();

		a.setOperationType("DEPÓSITO - Cuenta Bancaria a Cuenta de Crédito");
		a.setNumAccountO(accountO);
		a.setNumAccountD(accountD);
		a.setAmount(amount);
		a.setOperationDate(new Date());

		bankingcustomer.findByNumAccountB(accountO).map(b -> {
			a.setNameAccountO(b.getNameOwner());
			repository.save(a).subscribe();
			return b;
		}).subscribe();

		creditcustomer.findByNumAccountC(accountD).map(c -> {
			a.setNameAccountD(c.getNameOwner());
			repository.save(a).subscribe();
			return c;
		}).subscribe();

		return repository.save(a);
	
	}

	@Override
	public Mono<Atm> retiroAccountCToAccountB(Double amount, String accountO, String accountD) {
		
		bankingcustomer.depositB(amount, accountD).subscribe();
		creditcustomer.retiroC(amount, accountO).subscribe();
		
		Atm a = new Atm();
		
		a.setNumAccountO(accountO);
		a.setNumAccountD(accountD);
		a.setAmount(amount);
		
		return repository.save(a);
		
	}

	// ----------------- Banking Client ------------------------->
	//--------------
	@Override
	public Flux<BankingDto> findAllBankingProducts() {
		return bankingcustomer.findAllBankingProducts();
	}

	@Override
	public Mono<BankingDto> findBankingById(String id) {
		return bankingcustomer.findBankingById(id);
	}

	@Override
	public Mono<BankingDto> findByNumAccountB(String numAccount) {
		return bankingcustomer.findByNumAccountB(numAccount);
	}
	
	@Override
	public Mono<BankingDto> save(BankingDto banking) {
		return bankingcustomer.save(banking);
	}

	@Override
	public Mono<BankingDto> depositB(Double amount, String numAccount) {
		
		return bankingcustomer.depositB(amount, numAccount);
	}

	@Override
	public Mono<BankingDto> retiroB(Double amount, String numAccount) {
		return bankingcustomer.retiroB(amount, numAccount);
	}

	//------------------------ Credit Client ---------------------->
	//-----------------------------
	@Override
	public Flux<CreditDto> findAllCreditProducts() {
		return creditcustomer.findAllCreditProducts();
	}

	@Override
	public Mono<CreditDto> findCreditById(String id) {
		return creditcustomer.findCreditById(id);
	}

	@Override
	public Mono<CreditDto> findByNumAccountC(String numberAccount) {
		return creditcustomer.findByNumAccountC(numberAccount);
	}
	
	@Override
	public Mono<CreditDto> save(CreditDto credit) {
		return creditcustomer.save(credit);
	}

	@Override
	public Mono<CreditDto> depositC(Double amount, String numberAccount) {
		return creditcustomer.depositC(amount, numberAccount);
	}

	@Override
	public Mono<CreditDto> retiroC(Double amount, String numberAccount) {
		return creditcustomer.retiroC(amount, numberAccount);
	}

}
