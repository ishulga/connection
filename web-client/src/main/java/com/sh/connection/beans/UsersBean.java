package com.sh.connection.beans;

import java.io.Serializable;
import java.util.List;

import com.sh.connection.persistence.model.User;
import com.sh.connection.service.UserService;
import com.sh.connection.util.WebApplication;

public class UsersBean implements Serializable {
	private static final long serialVersionUID = 6616834549063004766L;

	private UserService userService = WebApplication
	        .getService(UserService.class);

	private List<User> users;

	public UsersBean() {
	}

	public List<User> getUsers() {
		users = userService.getListEager();
		return users;
	}
}
