package com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl;

import org.springframework.stereotype.Component;

import com.nttdata.SignCustAccountservice.FeignClient.TableIdFeignClient;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class TableIdFeignClientFallBack implements TableIdFeignClient {

	public Long generateKey(String nameTable) {
		log.error("[TableId -> FallBack]");
		return Long.valueOf(-1);
	}

}
