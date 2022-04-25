package com.nttdata.SignCustAccountservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
 
	private Long idCustomer;
	private String firstname;
	private String lastname;
	private String documentNumber;
	private TypeDocument typeDocument;
	private TypeCustomer typeCustomer;
	private String emailAddress;
	private String phoneNumber;
	private String homeAddress;
	
}
