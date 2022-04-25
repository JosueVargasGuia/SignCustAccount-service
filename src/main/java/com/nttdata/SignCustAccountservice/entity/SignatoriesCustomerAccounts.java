package com.nttdata.SignCustAccountservice.entity;

 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection =  "SignatoriesCustomerAccounts")
public class SignatoriesCustomerAccounts {
	@Id
	Long idSignCustAccount;
	Long idCustomer;
	Long idAccount;
	@Override
	public String toString() {
		return "SignatoriesCustomerAccounts [idSignCustAccount=" + idSignCustAccount + ", idCustomer=" + idCustomer
				+ ", idAccount=" + idAccount + "]";
	}
	
}
