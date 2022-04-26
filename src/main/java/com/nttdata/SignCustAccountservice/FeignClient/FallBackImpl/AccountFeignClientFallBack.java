package com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nttdata.SignCustAccountservice.FeignClient.AccountFeignClient;
import com.nttdata.SignCustAccountservice.model.Account;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AccountFeignClientFallBack implements AccountFeignClient{
	
	@Value("${api.account-service.uri}")
	private String accountService;
	
	@Override
	public Account accountFindById(Long id) {
		log.info("AccountFeignClientFallBack -> " + accountService);
		return null;
	}

	
}
