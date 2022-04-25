package com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl;

import org.springframework.stereotype.Component;

import com.nttdata.SignCustAccountservice.FeignClient.CustomerFeignClient;
import com.nttdata.SignCustAccountservice.model.Customer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CustomerFeignClientFallBack implements CustomerFeignClient {

	public Customer customerfindById(Long id) {
		  Customer customer = new Customer();
		  customer.setIdCustomer(Long.valueOf(-1));
		  log.info("CustomerFeignClientFallBack -> "+customer);
		return customer;

	}

}
