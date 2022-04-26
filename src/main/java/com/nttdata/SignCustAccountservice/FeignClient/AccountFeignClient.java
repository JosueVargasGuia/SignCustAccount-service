package com.nttdata.SignCustAccountservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl.AccountFeignClientFallBack;
import com.nttdata.SignCustAccountservice.model.Account;

@FeignClient(name ="${api.account-service.uri}", fallback = AccountFeignClientFallBack.class)
public interface AccountFeignClient {

	@GetMapping("/{id}")
	Account accountFindById(@PathVariable("id") Long id);
}
