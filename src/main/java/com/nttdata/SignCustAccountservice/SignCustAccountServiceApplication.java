package com.nttdata.SignCustAccountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SignCustAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SignCustAccountServiceApplication.class, args);
	}

}
