package com.sh.connection.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sh.connection.ApplicationConfig;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	private PostService postService;

	@Test
	public void testsaveValidUser() throws ServiceException {
		User user = createUser("valid_user_login", "valid_user_name",
		        "valid_user_password", "valid_user_email@example.com");

		User saveedUser = userService.save(user);

		assertEqualUsers(user, saveedUser);
	}

	@Test
	public void testGetUnexistingUserById() throws ServiceException {
		Long userId = 666666666L;
		assertNull(userService.get(userId));
	}

	@Test(expected = ServiceException.class)
	public void testsaveUserWithExistingLogin() throws ServiceException {
		User userOne = createUser("existing_user_login", "existing_user_name",
		        "existing_user_password", "existing_user_email@example.com");
		User userTwo = createUser("existing_user_login", "existing_user_name",
		        "existing_user_password", "another_user_email@example.com");

		userService.save(userOne);
		userService.save(userTwo);
	}

	@Test(expected = ServiceException.class)
	public void testsaveUserWithExistingEmail() throws ServiceException {
		User userOne = createUser("user_with_existing_email_login",
		        "user_with_existing_email_name",
		        "user_with_existing_email_password",
		        "user_with_existing_email_email@example.com");
		User userTwo = createUser("second_user_with_existing_email_login",
		        "second_user_with_existing_email_name",
		        "second_user_with_existing_email_password",
		        "user_with_existing_email_email@example.com");

		userService.save(userOne);
		userService.save(userTwo);
	}

	@Test(expected = ServiceException.class)
	public void testsaveUserWithEmptyLogin() throws ServiceException {
		String login = "";
		String name = "user_with_empty_login_name";
		String password = "user_with_empty_login_password";
		String email = "user_with_empty_login_email@example.com";
		User user = createUser(login, name, password, email);

		userService.save(user);
	}

	@Test(expected = ServiceException.class)
	public void testsaveUserWithEmptyPassword() throws ServiceException {
		String login = "user_with_empty_password_login";
		String name = "user_with_empty_password_name";
		String password = "";
		String email = "user_with_empty_password_email@example.com";
		User user = createUser(login, name, password, email);

		userService.save(user);
	}

	@Test(expected = ServiceException.class)
	public void testsaveUserWithEmptyName() throws ServiceException {
		String name = "";
		User user = createUser("user_with_empty_name_login", name,
		        "user_with_empty_name_password",
		        "user_with_empty_name_email@example.com");

		userService.save(user);
	}

	@Test(expected = ServiceException.class)
	public void testsaveUserWithInvalidEmail() throws ServiceException {
		User user = createUser("user_with_invalid_email_login",
		        "user_with_invalid_email_name",
		        "user_with_invalid_email_password",
		        "user_with_@_invalid_email@example.com");

		userService.save(user);
	}

	@Test
	public void testUsersListing() throws ServiceException {
		User userOne = createUser("one_login", "one_name", "one_password",
		        "one@example.com");
		User userTwo = createUser("two_login", "two_name", "two_password",
		        "two@example.com");

		userService.save(userOne);
		userService.save(userTwo);

		List<User> userList = userService.getAll();
		assertTrue(userList.size() >= 2);
	}

	@Test
	public void testAddValidPost() throws ServiceException {
		User user = createUser("user_with_post_login", "user_with_post_name",
		        "user_with_post_password", "user_with_post_email@example.com");

		User saveedUser = userService.save(user);

		String postContent = "Post content.";
		String postTitle = "Post title.";
		Post post = new Post();
		post.setPost(postContent);
		post.setTitle(postTitle);

		post = postService.save(post);
		userService.addPost(saveedUser.getId(), post.getId());

		user = userService.getUserWithPosts(saveedUser.getId());

		assertEquals(1, user.getPosts().size());
		post = user.getPosts().iterator().next();
		assertEquals(postContent, post.getPost());
		assertEquals(postTitle, post.getTitle());
	}

	@Test
	public void testSubscribe() throws ServiceException {
		User subscriberUser = createUser("subscriber_login", "subscriber_name",
		        "subscriber_password", "subscriber_email@example.com");
		subscriberUser = userService.save(subscriberUser);

		User subscriptionUser = createUser("subscription_login",
		        "subscription_name", "subscription_password",
		        "subscription_email@example.com");
		subscriptionUser = userService.save(subscriptionUser);

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
		unsubscriberUser = userService.save(unsubscriberUser);

		User unsubscriptionUser = createUser("unsubscription_login",
		        "unsubscription_name", "unsubscription_password",
		        "unsubscription_email@example.com");
		unsubscriptionUser = userService.save(unsubscriptionUser);

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
		userService.save(user);

		User loggedInUser = userService.login(login, password);

		assertEqualUsers(user, loggedInUser);
	}

	@Test(expected = ServiceException.class)
	public void testLoginWithWrongCredentials() throws ServiceException {
		String password = "test_wrong_password";
		String login = "test_wrong_login";
		User user = createUser(login, "test_wrong_name", password,
		        "test_wrong_login@example.com");
		userService.save(user);

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
