/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.sh.connection.persistence.jpa.CustomerRepository;
import com.sh.connection.persistence.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository customerRepo;

	@Test
	public void savesCustomerCorrectly() {
		Customer c = new Customer();
		c.setName("myname");
		Customer persisted = customerRepo.save(c);
		assertNotNull(persisted.getId());
		assertEquals("myname", persisted.getName());
		Customer findOne = customerRepo.findOne(persisted.getId());
		assertNotNull(findOne.getId());
		assertEquals("myname", findOne.getName());
	}

}
