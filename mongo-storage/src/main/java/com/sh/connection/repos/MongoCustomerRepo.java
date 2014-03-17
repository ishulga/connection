package com.sh.connection.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import org.springframework.stereotype.Repository;

import com.sh.connection.persistence.model.mongo.Customer;

@Repository
public class MongoCustomerRepo implements CustomerRepository {

	@Autowired
	private MongoOperations operations;
	
	@Override
	public Customer findOne(Long id) {
		Query query = query(where("id").is(id));
		return operations.findOne(query, Customer.class);
	}

	@Override
	public Customer save(Customer customer) {
		operations.save(customer);
		return customer;
	}

	@Override
	public Customer findByEmail(String email) {
		Query query = query(where("email").is(email));
		return operations.findOne(query, Customer.class);
	}

}
