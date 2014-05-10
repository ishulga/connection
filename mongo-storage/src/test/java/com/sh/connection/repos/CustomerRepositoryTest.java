package com.sh.connection.repos;

import com.sh.connection.persistence.model.mongo.Customer;
import com.sh.connection.persistence.model.mongo.Review;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

public class CustomerRepositoryTest extends AbstractTest {

    Logger logger = LoggerFactory.getLogger(CustomerRepositoryTest.class);
    @Autowired
    private MongoCustomerRepo customerRepo;
    @Autowired
    private ReviewRepository reviewRepo;

    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setName("cName");
        customer.setLogin("cLogin");
        customer.setEmail("cEmail");
        customer.setPassword("cPassword");

        Review currentReview = new Review();
        currentReview.setTitle("currentReview");
        Review savedReview = reviewRepo.save(currentReview);

        customer.setReviews(new HashSet(Arrays.asList(currentReview)));
        customer.setCurrentReview(savedReview);

        Customer save = customerRepo.save(customer);
        Assert.assertNotNull(save.getId());
        Assert.assertEquals("cName", save.getName());
    }

}
