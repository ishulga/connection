package com.sh.connection.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//@ContextConfiguration(classes = ApplicationConfig.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ActiveProfiles(value = "jpa")
public class UserServiceJpaTest extends UserServiceAbstract {


}
