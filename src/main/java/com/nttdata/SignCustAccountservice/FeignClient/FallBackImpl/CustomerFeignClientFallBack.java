package com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.nttdata.SignCustAccountservice.FeignClient.CustomerFeignClient;
import com.nttdata.SignCustAccountservice.model.Customer;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CustomerFeignClientFallBack implements CustomerFeignClient {

@Value("${api.customer-service.uri}")
String customerService;
	public Customer customerfindById(Long id) {
		//Customer customer = new Customer();
		//customer.setIdCustomer(Long.valueOf(-1));
		log.info("CustomerFeignClientFallBack:"+customerService+"/"+id);
		return null;

	}

}
