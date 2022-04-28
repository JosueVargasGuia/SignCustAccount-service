package com.nttdata.SignCustAccountservice.serviceImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.SignCustAccountservice.FeignClient.AccountFeignClient;
import com.nttdata.SignCustAccountservice.FeignClient.CustomerFeignClient;
import com.nttdata.SignCustAccountservice.FeignClient.ProductFeignClient;
import com.nttdata.SignCustAccountservice.FeignClient.TableIdFeignClient;
import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
import com.nttdata.SignCustAccountservice.model.Account;
import com.nttdata.SignCustAccountservice.model.BankAccounts;
import com.nttdata.SignCustAccountservice.model.Customer;
import com.nttdata.SignCustAccountservice.model.Product;
import com.nttdata.SignCustAccountservice.model.ProductId;
import com.nttdata.SignCustAccountservice.model.TypeCustomer;
import com.nttdata.SignCustAccountservice.repository.SignatoriesCustomerAccountsRepository;
import com.nttdata.SignCustAccountservice.service.SignatoriesCustomerAccountsService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class SignatoriesCustomerAccountsServiceImpl implements SignatoriesCustomerAccountsService {

	@Autowired
	AccountFeignClient accountFeignClient;

	@Autowired
	ProductFeignClient productFeignClient;

	@Autowired
	CustomerFeignClient customerFeignClient;

	@Autowired
	TableIdFeignClient tableIdFeignClient;

	/*
	 * @Value("${api.account-service.uri}") private String accountService;
	 * 
	 * @Value("${api.product-service.uri}") private String productService;
	 * 
	 * @Value("${api.customer-service.uri}") private String customerService;
	 * 
	 * @Value("${api.tableId-service.uri}") String tableIdService;
	 */

	@Autowired
	SignatoriesCustomerAccountsRepository accountsRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Flux<SignatoriesCustomerAccounts> findAll() {
		// TODO Auto-generated method stub
		return accountsRepository.findAll()
				.sort((objA, objB) -> objA.getIdSignCustAccount().compareTo(objB.getIdSignCustAccount()));
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> findById(Long idSignatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.findById(idSignatoriesCustomerAccounts);
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> save(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {

		Long key = generateKey(SignatoriesCustomerAccounts.class.getSimpleName());
		if (key >= 1) {
			signatoriesCustomerAccounts.setIdSignCustAccount(key);
			signatoriesCustomerAccounts.setCreationDate(Calendar.getInstance().getTime());
			log.info("SAVE [SignatoriesCustomerAccounts]: " + signatoriesCustomerAccounts.toString());
		} else {
			return Mono.error(new InterruptedException(
					"Servicio no disponible: " + SignatoriesCustomerAccounts.class.getSimpleName()));
		}

		return accountsRepository.insert(signatoriesCustomerAccounts);
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> update(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		signatoriesCustomerAccounts.setDateModified(Calendar.getInstance().getTime());
		return accountsRepository.save(signatoriesCustomerAccounts);
	}

	@Override
	public Mono<Void> delete(Long idSignatoriesCustomerAccounts) {
		// TODO Auto-generated method stub

		return accountsRepository.deleteById(idSignatoriesCustomerAccounts);
	}

	@Override
	public Mono<Map<String, Object>> registerSignature(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		BankAccounts account = this.findIdAccount(signatoriesCustomerAccounts.getIdAccount());
		Customer customer = this.findIdCustomer(signatoriesCustomerAccounts.getIdCustomer());
		Map<String, Object> hasMap = new HashMap<>();
		if (account != null) {
			if (customer != null) {
				Product product = this.findIdProducto(account.getIdProduct());
				if(product!=null) {
				if (customer.getTypeCustomer() == TypeCustomer.company) {
					// mas de un registro pero solo de tres cuentas
					//  Cuenta corriente:
					//  Empresarial
					//  Empresarial

					if (product.getProductId() == ProductId.CuentaCorriente
							|| product.getProductId() == ProductId.Empresarial
							|| product.getProductId() == ProductId.TarjetaCreditoEmpresarial) {
						hasMap.put("SignatoriesCustomerAccounts", "Firma autorizante registrado.");
						Mono<Map<String, Object>> mono = this.save(signatoriesCustomerAccounts).map(_obj -> {
							log.info("SignatoriesCustomerAccounts: Firma autorizante registrado.");

							return hasMap;
						});
						// mono.subscribe();
						return mono;
					} else {
						hasMap.put("SignatoriesCustomerAccounts","No se puede registrar la firma autorizante en la cuenta");
						return Mono.just(hasMap);
					}

				}
				if (customer.getTypeCustomer() == TypeCustomer.personal) {
					// Solo un registro
					Mono<Map<String, Object>> mono = this.findAll()
							.filter(_filter -> _filter.getIdAccount() == signatoriesCustomerAccounts.getIdAccount()
									&& _filter.getIdCustomer() == signatoriesCustomerAccounts.getIdCustomer())
							.collect(Collectors.counting()).map(_value -> {
								if (_value <= 0) {
									hasMap.put("SignatoriesCustomerAccounts", "Firma autorizante registrado.");
									log.info("SignatoriesCustomerAccounts: Firma autorizante registrado.");
									this.save(signatoriesCustomerAccounts).subscribe();
								} else {
									log.info("SignatoriesCustomerAccounts: Existe  una firma registrada para  el cliente "+ customer.getFirstname());
									hasMap.put("customer","Existe  una firma registrada para  el cliente " + customer.getFirstname());
								}
								return hasMap;
							});
					return mono;

				}
				return Mono.just(hasMap);
				}else {
					hasMap.put("product", "El producto no existe.");
					return Mono.just(hasMap);
				}
			} else {
				hasMap.put("customer", "El cliente no existe.");
				return Mono.just(hasMap);
			}
		} else {
			hasMap.put("account", "La cuenta no existe.");
			return Mono.just(hasMap);

		}

	}

	@Override
	public Product findIdProducto(Long idProducto) {
		/*log.info(productService + "/" + idProducto);
		ResponseEntity<Product> responseGet = restTemplate.exchange(productService + "/" + idProducto, HttpMethod.GET,
				null, new ParameterizedTypeReference<Product>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}*/
		
		Product product = productFeignClient.findById(idProducto);
		log.info("ProductFeignClient: " + product.toString());
		return product;
	}

	@Override
	public Customer findIdCustomer(Long idCustomer) {
		/*log.info(customerService + "/" + id);
		ResponseEntity<Customer> responseGet = restTemplate.exchange(customerService + "/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Customer>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}*/
		
		Customer customer = customerFeignClient.customerfindById(idCustomer);
		return customer;
	}

	@Override
	public BankAccounts findIdAccount(Long idAccount) {
		/*log.info(accountService + "/" + idCredit);
		ResponseEntity<Account> responseGet = restTemplate.exchange(accountService + "/" + idCredit, HttpMethod.GET,
				null, new ParameterizedTypeReference<Account>() {
				});

		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}*/
		
		BankAccounts account = accountFeignClient.accountFindById(idAccount);
		//log.info("AccountFeignClient: " + account.toString());
		return account;
	}

	@Override
	public Long generateKey(String nameTable) {
		/*log.info(tableIdService + "/generateKey/" + nameTable);
		ResponseEntity<Long> responseGet = restTemplate.exchange(tableIdService + "/generateKey/" + nameTable,
				HttpMethod.GET, null, new ParameterizedTypeReference<Long>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			log.info("Body:" + responseGet.getBody());

			return responseGet.getBody();
		} else {
			return Long.valueOf(0);
		}*/
		return tableIdFeignClient.generateKey(nameTable);
	}
}
