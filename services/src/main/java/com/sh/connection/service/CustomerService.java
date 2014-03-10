package com.sh.connection.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.jpa.CustomerRepository;
import com.sh.connection.persistence.model.Customer;

public class CustomerService {

	@Autowired
	private CustomerRepository customerPL;

	public Customer create(Customer customer) throws ServiceException {
		return customerPL.save(customer);
	}

	public Customer get(Long customerId) {
		return customerPL.findOne(customerId);
	}

	public void update(Customer customer) throws ServiceException {
		customerPL.save(customer);
	}

	public void delete(Long customerId) {
		customerPL.delete(customerId);
	}

}
