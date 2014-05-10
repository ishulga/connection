package com.sh.connection.service;

import com.sh.connection.ApplicationConfig;
import com.sh.connection.persistence.model.Message;
import com.sh.connection.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//@ActiveProfiles(value = "jpa-repo")
public class UserServiceJpaRepoTest extends UserServiceAbstract {


}
