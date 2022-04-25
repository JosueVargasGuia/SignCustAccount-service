package com.nttdata.SignCustAccountservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
import com.nttdata.SignCustAccountservice.service.SignatoriesCustomerAccountsService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/signCustAccount")
public class SignatoriesCustomerAccountsController {
	
	@Autowired
	SignatoriesCustomerAccountsService accountsService;

	@GetMapping
	public Flux<SignatoriesCustomerAccounts> findAll() throws InterruptedException, ExecutionException {
	
		return accountsService.findAll();
	}
	//accountsService.findAll().toStream().map(obj->{return obj;}).collect(Collectors.toList()).forEach(c->log.info(c.toString()));

	/*Flux<SignatoriesCustomerAccounts> flux= accountsService.findAll();
	flux.flatMap(obj->{
		return Mono.just(obj);
	});
	
	List<SignatoriesCustomerAccounts> list2=accountsService.findAll().
	collectList().toFuture().get();
	list2.forEach(e->log.info("list2:"+e.toString()));
	
	flux.subscribe(e->log.info("e:"+e.toString()));
	
	Mono<List<SignatoriesCustomerAccounts>> mono=flux.collectList();
	mono.subscribe(e->log.info("e1:"+e.toString()));
	
	List<SignatoriesCustomerAccounts> list=mono.toFuture().get();
	list.forEach(e->log.info("message:"+e.toString()));*/

	@PostMapping
	public Mono<ResponseEntity<SignatoriesCustomerAccounts>> save(
			@RequestBody SignatoriesCustomerAccounts customerAccounts) {
		return accountsService.save(customerAccounts)
				.map(_customerAccounts -> ResponseEntity.ok().body(_customerAccounts)).onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				});
	}

	@GetMapping("/{idSignCustAccount}")
	public Mono<ResponseEntity<SignatoriesCustomerAccounts>> findById(
			@PathVariable(name = "idSignCustAccount") long idSignCustAccount) {
		return accountsService.findById(idSignCustAccount).map(product -> ResponseEntity.ok().body(product))
				.onErrorResume(e -> {
					log.info(e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PutMapping
	public Mono<ResponseEntity<SignatoriesCustomerAccounts>> update(
			@RequestBody SignatoriesCustomerAccounts customerAccounts) {
		Mono<SignatoriesCustomerAccounts> mono = accountsService.findById(customerAccounts.getIdSignCustAccount())
				.flatMap(objCustomerAccounts -> {
					log.info("Update:[new]" + customerAccounts + " [Old]:" + objCustomerAccounts);
					return accountsService.update(customerAccounts);
				});
		return mono.map(_product -> {
			log.info("Status:" + HttpStatus.OK);
			return ResponseEntity.ok().body(_product);
		}).onErrorResume(e -> {
			log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());

	};

	@DeleteMapping("/{idSignCustAccount}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "idSignCustAccount") long idSignCustAccount) {
		return accountsService.findById(idSignCustAccount).flatMap(producto -> {
			return accountsService.delete(producto.getIdSignCustAccount()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}

	@PostMapping("/registerSignature")
	public Mono<ResponseEntity<Map<String, Object>>> registerSignature(
			@RequestBody SignatoriesCustomerAccounts customerAccounts) {
		return accountsService.registerSignature(customerAccounts).map(_val -> ResponseEntity.ok().body(_val))
				.onErrorResume(e -> {
					log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
					Map<String, Object> hashMap = new HashMap<>();
					hashMap.put("Error", e.getMessage());
					return Mono.just(ResponseEntity.badRequest().body(hashMap));
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
}
