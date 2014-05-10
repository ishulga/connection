package com.sh.connection.repos;

import com.mongodb.*;
import com.sh.connection.ApplicationConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public abstract class AbstractTest {

    @Autowired
    private Mongo mongo;
    private Logger LOG = LoggerFactory.getLogger(AbstractTest.class);

    @Before
    public void setUp() {
        LOG.info("Populating mongodb");
        DB db = mongo.getDB("connection");
        DBCollection collection = db.getCollection("customers");
        DBObject cust1 = new BasicDBObject();
        cust1.put("login", "testlogin");
        cust1.put("password", "testpassword");
        cust1.put("name", "testname");
        cust1.put("email", "testemail");
        collection.insert(cust1);
    }

}
