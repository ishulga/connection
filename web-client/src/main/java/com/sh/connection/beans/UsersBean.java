package com.sh.connection.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.model.User;
import com.sh.connection.service.UserService;

public class UsersBean implements Serializable {
	private static final long serialVersionUID = 6616834549063004766L;
	@Autowired
	private UserService userService;

	private List<User> users;

	public UsersBean() {
	}

	public List<User> getUsers() {
		users = userService.getListEager();
		return users;
	}
}
