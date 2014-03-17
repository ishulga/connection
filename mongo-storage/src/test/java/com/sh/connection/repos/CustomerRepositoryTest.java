package com.sh.connection.repos;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.model.mongo.Customer;

public class CustomerRepositoryTest extends AbstractTest {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Test
	public void testSave(){
		Customer customer = new Customer();
		customer.setName("cName");
		customer.setLogin("cLogin");
		customer.setEmail("cEmail");
		customer.setPassword("cPassword");
		Customer save = customerRepo.save(customer);
		Assert.assertNotNull(save.getId());
		Assert.assertEquals("cName",save.getName());
	}

}
