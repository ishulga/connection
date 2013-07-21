package com.sh.connection.util;

import org.junit.Test;

import com.sh.connection.persistence.model.User;
import com.sh.connection.service.ServiceException;
import com.sh.connection.service.UserService;

public class WebContainer {
	@Test
	public void startWebContainer() throws Exception{
		JettyContainer c = new JettyContainer();
		c.join();
	}
	public void processPayload(int userCount) throws ServiceException{
		for (int i = 0; i < userCount; i++) {
			User user = new User();
			user.setLogin("login"+i);
			user.setPassword("name"+i);
			user.setPassword("password"+i);
			user.setEmail(i+"name@gmail.com");
			user.setVisible(true);
//			userService.register(user);
		}
	}
}
