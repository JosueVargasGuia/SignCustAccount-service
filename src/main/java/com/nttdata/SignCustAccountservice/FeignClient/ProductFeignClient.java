package com.nttdata.SignCustAccountservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.SignCustAccountservice.FeignClient.FallBackImpl.ProductFeignClientFallBack;
import com.nttdata.SignCustAccountservice.model.Product;


@FeignClient(name ="${api.product-service.uri}"
//, fallback = ProductFeignClientFallBack.class
)
public interface ProductFeignClient {
 
	@GetMapping("/{idProduct}") 
	Product findById(@PathVariable(name="idProduct")Long id);
}
