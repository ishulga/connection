package com.sh.connection.repos;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.sh.connection.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfig.class})
public class AbstractTest {
	
	@Autowired
	Mongo mongo;
	
	@Before
	public void setUp(){
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
