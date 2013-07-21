package com.sh.connection.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;
import com.sh.connection.service.PostService;
import com.sh.connection.service.ServiceException;
import com.sh.connection.service.UserService;
import com.sh.connection.util.ServiceFactory;

public class UserServiceTest {

	private UserService userService = ServiceFactory.INSTANCE
	        .getBean(UserService.class);
	private PostService postService = ServiceFactory.INSTANCE
	        .getBean(PostService.class);

	@Test
	public void testRegisterValidUser() throws ServiceException {
		User user = createUser("valid_user_login", "valid_user_name",
		        "valid_user_password", "valid_user_email@example.com");

		User registeredUser = userService.register(user);

		assertEqualUsers(user, registeredUser);
	}

	@Test
	public void testGetUnexistingUserById() throws ServiceException {
		Long userId = 666666666L;
		assertNull(userService.get(userId));
	}

	@Test(expected = ServiceException.class)
	public void testRegisterUserWithExistingLogin() throws ServiceException {
		User userOne = createUser("existing_user_login", "existing_user_name",
		        "existing_user_password", "existing_user_email@example.com");
		User userTwo = createUser("existing_user_login", "existing_user_name",
		        "existing_user_password", "another_user_email@example.com");

		userService.register(userOne);
		userService.register(userTwo);
	}

	@Test(expected = ServiceException.class)
	public void testRegisterUserWithExistingEmail() throws ServiceException {
		User userOne = createUser("user_with_existing_email_login",
		        "user_with_existing_email_name",
		        "user_with_existing_email_password",
		        "user_with_existing_email_email@example.com");
		User userTwo = createUser("second_user_with_existing_email_login",
		        "second_user_with_existing_email_name",
		        "second_user_with_existing_email_password",
		        "user_with_existing_email_email@example.com");

		userService.register(userOne);
		userService.register(userTwo);
	}

	@Test(expected = ServiceException.class)
	public void testRegisterUserWithEmptyLogin() throws ServiceException {
		String login = "";
		String name = "user_with_empty_login_name";
		String password = "user_with_empty_login_password";
		String email = "user_with_empty_login_email@example.com";
		User user = createUser(login, name, password, email);

		userService.register(user);
	}

	@Test(expected = ServiceException.class)
	public void testRegisterUserWithEmptyPassword() throws ServiceException {
		String login = "user_with_empty_password_login";
		String name = "user_with_empty_password_name";
		String password = "";
		String email = "user_with_empty_password_email@example.com";
		User user = createUser(login, name, password, email);

		userService.register(user);
	}

	@Test(expected = ServiceException.class)
	public void testRegisterUserWithEmptyName() throws ServiceException {
		String name = "";
		User user = createUser("user_with_empty_name_login", name,
		        "user_with_empty_name_password",
		        "user_with_empty_name_email@example.com");

		userService.register(user);
	}

	@Test(expected = ServiceException.class)
	public void testRegisterUserWithInvalidEmail() throws ServiceException {
		User user = createUser("user_with_invalid_email_login",
		        "user_with_invalid_email_name",
		        "user_with_invalid_email_password",
		        "user_with_@_invalid_email@example.com");

		userService.register(user);
	}

	@Test
	public void testUsersListing() throws ServiceException {
		User userOne = createUser("one_login", "one_name", "one_password",
		        "one@example.com");
		User userTwo = createUser("two_login", "two_name", "two_password",
		        "two@example.com");

		userService.register(userOne);
		userService.register(userTwo);

		List<User> userList = userService.getAll();
		assertTrue(userList.size() >= 2);
	}

	@Test
	public void testAddValidPost() throws ServiceException {
		User user = createUser("user_with_post_login", "user_with_post_name",
		        "user_with_post_password", "user_with_post_email@example.com");

		User registeredUser = userService.register(user);

		String postContent = "Post content.";
		String postTitle = "Post title.";
		Post post = new Post();
		post.setPost(postContent);
		post.setTitle(postTitle);

		post = postService.create(post);
		userService.addPost(registeredUser.getId(), post.getId());

		user = userService.getUserWithPosts(registeredUser.getId());

		assertEquals(1, user.getPosts().size());
		post = user.getPosts().iterator().next();
		assertEquals(postContent, post.getPost());
		assertEquals(postTitle, post.getTitle());
	}

	@Test
	public void testSubscribe() throws ServiceException {
		User subscriberUser = createUser("subscriber_login", "subscriber_name",
		        "subscriber_password", "subscriber_email@example.com");
		subscriberUser = userService.register(subscriberUser);

		User subscriptionUser = createUser("subscription_login",
		        "subscription_name", "subscription_password",
		        "subscription_email@example.com");
		subscriptionUser = userService.register(subscriptionUser);

		userService.subscribeTo(subscriberUser.getId(),
		        subscriptionUser.getId());

		subscriberUser = userService.getUserWithSubscriptions(subscriberUser
		        .getId());

		assertEquals(1, subscriberUser.getSubscriptions().size());
		User persistedSubscriptionUser = subscriberUser.getSubscriptions()
		        .iterator().next();
		assertEquals(subscriptionUser.getId(),
		        persistedSubscriptionUser.getId());
	}

	@Test
	public void testUnsubscribe() throws ServiceException {
		User unsubscriberUser = createUser("unsubscriber_login",
		        "unsubscriber_name", "unsubscriber_password",
		        "unsubscriber_email@example.com");
		unsubscriberUser = userService.register(unsubscriberUser);

		User unsubscriptionUser = createUser("unsubscription_login",
		        "unsubscription_name", "unsubscription_password",
		        "unsubscription_email@example.com");
		unsubscriptionUser = userService.register(unsubscriptionUser);

		userService.subscribeTo(unsubscriberUser.getId(),
		        unsubscriptionUser.getId());

		userService.unsubscribeFrom(unsubscriberUser.getId(),
		        unsubscriptionUser.getId());

		unsubscriberUser = userService
		        .getUserWithSubscriptions(unsubscriberUser.getId());

		assertEquals(0, unsubscriberUser.getSubscriptions().size());
	}

	@Test
	public void testGetAllWithPostsCount() {
		List<User> users = userService.getListEager();
		for (User user : users) {
			user.getPosts().size();
			user.getSubscriptions().size();
		}
	}

	@Test
	public void testLoginWithRightCredentials() throws ServiceException {
		String password = "test_password";
		String login = "test_login";
		User user = createUser(login, "test_name", password,
		        "test_login@example.com");
		userService.register(user);

		User loggedInUser = userService.login(login, password);

		assertEqualUsers(user, loggedInUser);
	}

	@Test(expected = ServiceException.class)
	public void testLoginWithWrongCredentials() throws ServiceException {
		String password = "test_wrong_password";
		String login = "test_wrong_login";
		User user = createUser(login, "test_wrong_name", password,
		        "test_wrong_login@example.com");
		userService.register(user);

		userService.login(login, "another password");
	}

	private void assertEqualUsers(User user, User persistedUser) {
		assertEquals(user.getLogin(), persistedUser.getLogin());
		assertEquals(user.getName(), persistedUser.getName());
		assertEquals(user.getEmail(), persistedUser.getEmail());
		assertEquals(user.getPassword(), persistedUser.getPassword());
	}

	private User createUser(String login, String name, String password,
	        String email) {
		User user = new User();
		user.setName(name);
		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
}
