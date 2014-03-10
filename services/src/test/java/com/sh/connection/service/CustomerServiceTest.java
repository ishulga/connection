package com.sh.connection.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sh.connection.ApplicationConfig;
import com.sh.connection.persistence.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	
	
	@Test
	public void testCreateValidComment() throws ServiceException {
		Customer customer = createCustomer("myLogin", "myName","myPass", "myEmail");
		Customer persisted = customerService.create(customer);

		Customer persistedComment = customerService.get(persisted.getId());
		assertEquals("myLogin", persistedComment.getLogin());
		assertNotNull(persisted.getId());
	}

	private Customer createCustomer(String login, String name, String password,
	        String email) {
		Customer user = new Customer();
		user.setName(name);
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
}
