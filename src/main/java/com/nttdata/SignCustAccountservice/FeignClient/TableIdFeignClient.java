package com.nttdata.SignCustAccountservice.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl.TableIdFeignClientFallBack;



@FeignClient(name="${api.tableId-service.uri}", fallback = TableIdFeignClientFallBack.class)
public interface TableIdFeignClient {

	@GetMapping("/generateKey/{nameTable}")
	public Long generateKey(@PathVariable("nameTable") String nameTable);
}
