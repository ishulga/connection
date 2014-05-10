package com.sh.connection.repos;

import com.sh.connection.persistence.model.mongo.Customer;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;

public interface CustomerRepository extends Repository<Customer, BigInteger> {

    /**
     * Returns the customer with the given identifier.
     *
     * @param id
     * @return
     */
    Customer findOne(Long id);

    /**
     * Saves the given {@link Customer}. #
     *
     * @param customer
     * @return
     */
    Customer save(Customer customer);

    /**
     * Returns the {@link Customer} with the given {@link EmailAddress}.
     *
     * @param string
     * @return
     */
    Customer findByEmail(String email);
}
