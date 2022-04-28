package com.nttdata.SignCustAccountservice.service;

import java.util.Map;

import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
import com.nttdata.SignCustAccountservice.model.Account;
import com.nttdata.SignCustAccountservice.model.BankAccounts;
import com.nttdata.SignCustAccountservice.model.Customer;
import com.nttdata.SignCustAccountservice.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SignatoriesCustomerAccountsService {

	Flux<SignatoriesCustomerAccounts> findAll();

	Mono<SignatoriesCustomerAccounts> findById(Long idSignatoriesCustomerAccounts);

	Mono<SignatoriesCustomerAccounts> save(SignatoriesCustomerAccounts signatoriesCustomerAccounts);

	Mono<SignatoriesCustomerAccounts> update(SignatoriesCustomerAccounts signatoriesCustomerAccounts);

	Mono<Void> delete(Long idSignatoriesCustomerAccounts);

	Product findIdProducto(Long idProducto);

	Customer findIdCustomer(Long id);

	BankAccounts findIdAccount(Long idBankAccount);

	Mono<Map<String, Object>> registerSignature(SignatoriesCustomerAccounts signatoriesCustomerAccounts);

	Long generateKey(String nameTable);
}
