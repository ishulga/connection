package com.sh.connection.service;

import static com.sh.connection.service.Messages.ALREADY_SUBSCRIBED;
import static com.sh.connection.service.Messages.DID_NOT_SUBSCRIBED;
import static com.sh.connection.service.Messages.EMAIL_EXISTS;
import static com.sh.connection.service.Messages.EMPTY_EMAIL;
import static com.sh.connection.service.Messages.EMPTY_LOGIN;
import static com.sh.connection.service.Messages.EMPTY_NAME;
import static com.sh.connection.service.Messages.EMPTY_PASSWORD;
import static com.sh.connection.service.Messages.INVALID_EMAIL;
import static com.sh.connection.service.Messages.LOGIN_EXISTS;
import static com.sh.connection.service.Messages.SELF_SUBSCRIPTION;
import static com.sh.connection.service.Messages.WRONG_CREDENTIALS;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.jpa.repository.UserRepository;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;
import com.sh.connection.util.InterfaceUtils;

public class UserService {

	@Autowired
	private UserRepository userPL;
	@Autowired
	private PostService postService;

	public User save(User user) throws ServiceException {
		validate(user);
		return getEager(userPL.save(user).getId());
	}

	public User get(Long id) {
		return userPL.findOne(id);
	}

	public List<User> getAll() {
		return toList(userPL.findAll());
	}
	
	public List<User> toList(Iterable<User> iterable) {
		List<User> list = new ArrayList<User>();
		for (User user : iterable) {
			list.add(user);
		}
		return list;
	}

	public List<User> getListEager() {
		return toList(userPL.findAll());
	}

	public User getEager(Long id) {
		return userPL.findOne(id);
	}

	public void addPost(Long userId, Long postId) {
		User user = userPL.findOne(userId);
		Post post = postService.findOne(postId);
		user.getPosts().add(post);
		userPL.save(user);
	}

	public User getUserWithPosts(Long id) {
		return userPL.findOne(id);
	}

	public User getUserWithSubscriptions(Long id) {
		return userPL.findOne(id);
	}

	public void subscribeTo(Long subscriberUserId, Long subscriptionUserId)
			throws ServiceException {
		if (subscriberUserId == subscriptionUserId) {
			throw new ServiceException(SELF_SUBSCRIPTION);
		}
		User subscriberUser = getUserWithSubscriptions(subscriberUserId);
		if (InterfaceUtils.getById(subscriberUser.getSubscriptions(),
				subscriptionUserId) != null) {
			throw new ServiceException(ALREADY_SUBSCRIBED);
		}
		User subscriptionUser = get(subscriptionUserId);
		subscriberUser.getSubscriptions().add(subscriptionUser);
		userPL.save(subscriberUser);
	}

	public void unsubscribeFrom(Long subscriberUserId, Long toUnsubscribeUserId)
			throws ServiceException {
		User subscriberUser = getUserWithSubscriptions(subscriberUserId);
		if (InterfaceUtils.getById(subscriberUser.getSubscriptions(),
				toUnsubscribeUserId) == null) {
			throw new ServiceException(DID_NOT_SUBSCRIBED);
		}
		User subscriptionUser = get(toUnsubscribeUserId);
		subscriberUser.getSubscriptions().remove(subscriptionUser);
		userPL.save(subscriberUser);

	}

	public User login(String login, String password) throws ServiceException {
		User user = userPL.findByLogin(login);
		if (user != null && user.getPassword().equals(password.trim())) {
			return getEager(user.getId());
		} else {
			throw new ServiceException(WRONG_CREDENTIALS);
		}
	}

	public void validate(User user) throws ServiceException {
		if (StringUtils.isEmpty(user.getLogin())) {
			throw new ServiceException(EMPTY_LOGIN);
		}
		if (userPL.findByLogin(user.getLogin()) != null) {
			throw new ServiceException(LOGIN_EXISTS);
		}
		if (StringUtils.isEmpty(user.getEmail())) {
			throw new ServiceException(EMPTY_EMAIL);
		}
		if (!EmailValidator.getInstance().isValid(user.getEmail())) {
			throw new ServiceException(INVALID_EMAIL);
		}
		if (userPL.findByEmail(user.getEmail()) != null) {
			throw new ServiceException(EMAIL_EXISTS);
		}
		if (StringUtils.isEmpty(user.getName())) {
			throw new ServiceException(EMPTY_NAME);
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new ServiceException(EMPTY_PASSWORD);
		}
	}

	public boolean isPostAuthor(Long userId, Long postId) {
		User user = getUserWithPosts(userId);
		return InterfaceUtils.getById(user.getPosts(), postId) != null;
	}

	public void changeVisibility(User user) {
		userPL.save(user);
	}
}
