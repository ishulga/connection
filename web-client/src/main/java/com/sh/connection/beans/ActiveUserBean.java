package com.sh.connection.beans;

import static com.sh.connection.util.Messages.LOGIN_TO_SUBSCRIBE;
import static com.sh.connection.util.Messages.LOGIN_TO_UNSUBSCRIBE;
import static com.sh.connection.util.Messages.LOGIN_TO_VIEW_PROFILE;
import static com.sh.connection.util.Messages.SUCCESSFULLY_SUBSCRIBED;
import static com.sh.connection.util.Messages.SUCCESSFULLY_UNSUBSCRIBED;
import static com.sh.connection.util.Messages.VISIBILITY_SUCCESSFULLY_CHANGED;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sh.connection.persistence.model.User;
import com.sh.connection.service.ServiceException;
import com.sh.connection.service.UserService;
import com.sh.connection.util.InterfaceUtils;
import com.sh.connection.util.WebApplication;

public class ActiveUserBean implements Serializable {
	private static final long serialVersionUID = 402936075617772893L;
	private UserService userService = WebApplication
			.getService(UserService.class);
	private User newUser = new User();
	private String login;
	private String password;
	private String confirmation;
	private User user;
	private Message message = new Message();
	private Long subscriptionId;

	public String sendMessage() {
		return "MESSAGE";
	}

	public String register() {
		try {
			user = userService.register(newUser);
			return "PROFILE";
		} catch (ServiceException e) {
			WebApplication.error(e.getMessage());
			return null;
		}
	}

	public String login() {
		try {
			user = userService.login(login, password);
			return "MAIN";
		} catch (ServiceException e) {
			WebApplication.error(e.getMessage());
			return (login = password = null);
		}
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		user = null;
		return "MAIN";
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public void subscribe() {
		if (isLoggedIn()) {
			try {
				userService.subscribeTo(user.getId(), subscriptionId);

				WebApplication.info(SUCCESSFULLY_SUBSCRIBED
						+ userService.get(subscriptionId).getName());

				user = userService.getEager(user.getId());
			} catch (ServiceException e) {
				WebApplication.error(e.getLocalizedMessage());
			}
		} else {
			WebApplication.error(LOGIN_TO_SUBSCRIBE);
		}
	}

	public void unsubscribe() {
		if (!isLoggedIn()) {
			WebApplication.error(LOGIN_TO_UNSUBSCRIBE);			
			return;
		} 
		try {
			userService.unsubscribeFrom(user.getId(), subscriptionId);
			WebApplication.info(SUCCESSFULLY_UNSUBSCRIBED
					+ userService.get(subscriptionId).getName());
			user = userService.getEager(user.getId());
		} catch (ServiceException e) {
			WebApplication.error(e.getMessage());
		}
	}

	public Collection<Long> getUserSubscriptions() {
		return InterfaceUtils.getIdSet(user.getSubscriptions());
	}

	public void changeVisibility() {
		userService.changeVisibility(user);
		String visibility = user.isVisible() ? "Public" : "Private";
		WebApplication.info(VISIBILITY_SUCCESSFULLY_CHANGED + visibility + ".");
	}

	public void checkAccess() throws IOException {
		if (!isLoggedIn()) {
			WebApplication.error(LOGIN_TO_VIEW_PROFILE);
		}
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
