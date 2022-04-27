package com.nttdata.SignCustAccountservice.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SignatoriesCustomerAccounts")
public class SignatoriesCustomerAccounts {
	
	@Id
	private Long idSignCustAccount;
	private Long idCustomer;
	private Long idAccount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dateModified;

	@Override
	public String toString() {
		return "SignatoriesCustomerAccounts [idSignCustAccount=" + idSignCustAccount + ", idCustomer=" + idCustomer
				+ ", idAccount=" + idAccount + "]";
	}

}
