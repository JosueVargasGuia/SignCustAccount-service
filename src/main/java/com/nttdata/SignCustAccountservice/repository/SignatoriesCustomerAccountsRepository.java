package com.nttdata.SignCustAccountservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
@Repository
public interface SignatoriesCustomerAccountsRepository extends ReactiveMongoRepository<SignatoriesCustomerAccounts, Long> {

}
