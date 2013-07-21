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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.UserPL;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;
import com.sh.connection.util.InterfaceUtils;
import com.sh.connection.util.ServiceFactory;

public class UserService {

	@Autowired
	private UserPL userPL;
	@Autowired
	private PostService postService;

	/**
	 * Save user with specified user data into DB.
	 * 
	 * @param name
	 *            user name
	 * @param login
	 *            user login
	 * @param password
	 *            user password
	 * @param email
	 *            user email
	 * @throws ServiceException
	 *             if encountered validation error
	 * @return user id
	 */
	public User register(User user) throws ServiceException {
		validate(user);
		return getEager(userPL.create(user));
	}

	/**
	 * Get user instance with specified id
	 * 
	 * @param id
	 *            user id to search for
	 * @return true if user with such id exists or null otherwise
	 */
	public User get(Long id) {
		return userPL.getById(id);
	}

	public List<User> getAll() {
		return userPL.getAll();
	}

	public List<User> getListEager() {
		return userPL.getListEager();
	}

	public User getEager(Long id) {
		return userPL.getEager(id);
	}

	public void addPost(Long userId, Long postId) {
		User user = userPL.getUserWithPosts(userId);
		Post post = postService.get(postId);
		user.getPosts().add(post);
		userPL.merge(user);
	}

	public User getUserWithPosts(Long id) {
		return userPL.getUserWithPosts(id);
	}

	public User getUserWithSubscriptions(Long id) {
		return userPL.getUserWithSubscriptions(id);
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
		userPL.merge(subscriberUser);
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
		userPL.merge(subscriberUser);

	}

	public User login(String login, String password) throws ServiceException {
		User user = userPL.getByLogin(login);
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
		if (userPL.getByLogin(user.getLogin()) != null) {
			throw new ServiceException(LOGIN_EXISTS);
		}
		if (StringUtils.isEmpty(user.getEmail())) {
			throw new ServiceException(EMPTY_EMAIL);
		}
		if (!EmailValidator.getInstance().isValid(user.getEmail())) {
			throw new ServiceException(INVALID_EMAIL);
		}
		if (userPL.getByEmail(user.getEmail()) != null) {
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
		userPL.merge(user);
	}
}
