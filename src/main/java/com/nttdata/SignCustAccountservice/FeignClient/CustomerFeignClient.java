package com.nttdata.SignCustAccountservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl.CustomerFeignClientFallBack;
import com.nttdata.SignCustAccountservice.model.Customer;

@FeignClient(name="customerFeignClient",url="${api.customer-service.uri}",
fallback = CustomerFeignClientFallBack.class)
public interface CustomerFeignClient {

	@GetMapping("/{id}")
	Customer customerfindById(@PathVariable(name = "id")Long id);
	
}