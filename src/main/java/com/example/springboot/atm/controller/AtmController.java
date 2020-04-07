package com.example.springboot.atm.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.atm.dto.BankingDto;
import com.example.springboot.atm.dto.CreditDto;
import com.example.springboot.atm.model.Atm;
import com.example.springboot.atm.service.IAtmService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/atm")
public class AtmController {

	@Autowired
	private IAtmService service;

	// LISTAR TODAS LAS OPERACIONES
	@GetMapping("/findAll")
	public Flux<Atm> findAll() {
		return service.findAll();
	}

	// LISTAR UNA OPERACIÓN POR SU ID
	@GetMapping("/findById/{id}")
	public Mono<Atm> findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	// CREAR UNA OPERACIÓN
	@PostMapping("/create")
	public Mono<ResponseEntity<Atm>> create(@RequestBody Atm atm) {
		return service.save(atm).map(a -> ResponseEntity.created(URI.create("/atm/".concat(a.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(a));
	}

	// ACTUALIZAR UNA OPERACIÓN
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<Atm>> update(@PathVariable("id") String id, @RequestBody Atm atm) {
		return service.update(atm, id)
				.map(a -> ResponseEntity.created(URI.create("/atm/".concat(a.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(a))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	// ELIMINAR UNA OPERACIÓN
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		return service.findById(id).flatMap(a -> {
			return service.delete(a).then(Mono.	just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

	// REALIZAR UN DEPOSITO(PAGO) A UNA CUENTA CRÉDITO DESDE UNA CUENTA BANCARIA
	@PutMapping("/depositCreditFromBanking/{amount}/{accountO}/{accountD}")
	public Mono<Atm> depositCreditFromBanking(@PathVariable String amount, @PathVariable String accountO,
			@PathVariable String accountD) {
		return service.depositAccountBToAccountC(Double.parseDouble(amount), accountO, accountD);
	}

	@PutMapping("/depositBankingFromCredit/{amount}/{accountO}/{accountD}")
	public Mono<Atm> depositBankingFromCredit(@PathVariable String amount, @PathVariable String accountO,
			@PathVariable String accountD) {
		return service.retiroAccountCToAccountB(Double.parseDouble(amount), accountO, accountD);
	}

	// ------------------- Métodos Banking Client ------------------>
	// --------------------------------------------------------------
	@GetMapping("/findAllBankingClients")
	public Flux<BankingDto> findAllBankingProducts() {
		return service.findAllBankingProducts();
	}

	@GetMapping("/findByBankingId/{id}")
	public Mono<BankingDto> findByBankingId(@PathVariable("id") String id) {
		return service.findBankingById(id);
	}

	@GetMapping("/findByNumAccountB/{numAccount}")
	public Mono<BankingDto> findByNumAccountB(@PathVariable("numAccount") String numAccount) {
		return service.findByNumAccountB(numAccount);
	}

	@PutMapping("/depositB/{amount}/{numAccount}")
	public Mono<BankingDto> depositB(@PathVariable("amount") String amount,
			@PathVariable("numAccount") String numAccount) {
		return service.depositB(Double.parseDouble(amount), numAccount);
	}

	@PutMapping("/retiroB/{amount}/{numAccount}")
	public Mono<BankingDto> retiroB(@PathVariable("amount") String amount,
			@PathVariable("numAccount") String numAccount) {
		return service.retiroB(Double.parseDouble(amount), numAccount);
	}

	// ------------------- Métodos Credit Client ------------------>
	// --------------------------------------------------------------
	@GetMapping("/findAllCreditClients")
	public Flux<CreditDto> findAllCreditProducts() {
		return service.findAllCreditProducts();
	}

	@GetMapping("/findByCreditId/{id}")
	public Mono<CreditDto> findByCreditId(@PathVariable("id") String id) {
		return service.findCreditById(id);
	}

	@GetMapping("/findByNumAccountC/{numberAccount}")
	public Mono<CreditDto> findByNumAccountC(@PathVariable("numberAccount") String numberAccount) {
		return service.findByNumAccountC(numberAccount);
	}

	@PutMapping("/depositC/{amount}/{numberAccount}")
	public Mono<CreditDto> depositC(@PathVariable("amount") String amount,
			@PathVariable("numberAccount") String numberAccount) {
		return service.depositC(Double.parseDouble(amount), numberAccount);
	}

	@PutMapping("/retiroC/{amount}/{numberAccount}")
	public Mono<CreditDto> retiroC(@PathVariable("amount") String amount,
			@PathVariable("numberAccount") String numberAccount) {
		return service.retiroC(Double.parseDouble(amount), numberAccount);
	}

}
