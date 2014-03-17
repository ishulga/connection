package com.sh.connection.repos;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.sh.connection.persistence.model.mongo.Customer;

public interface CustomerRepository extends CrudRepository<Customer, BigInteger> {

}
