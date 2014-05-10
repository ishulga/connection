package com.sh.connection.service;

import com.sh.connection.ApplicationConfig;
import com.sh.connection.persistence.model.Message;
import com.sh.connection.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private MessageService messageService;

    @Test
    public void testsaveValidUser() throws ServiceException {
        User user = createUser("valid_user_login", "valid_user_name",
                "valid_user_password", "valid_user_email@example.com");
        User savedUser = userService.save(user);
        assertEqualUsers(user, savedUser);
    }

    @Test(expected = ServiceException.class)
    public void testSaveUserWithExistingLogin() throws ServiceException {
        String existing_user_login = "existing_user_login";
        User userOne = createUser(existing_user_login, "existing_user_name",
                "existing_user_password", "existing_user_email@example.com");
        User userTwo = createUser(existing_user_login, "existing_user_name",
                "existing_user_password", "another_user_email@example.com");

        userService.save(userOne);
        userService.save(userTwo);
    }

    @Test(expected = ServiceException.class)
    public void testSaveUserWithExistingEmail() throws ServiceException {
        String user_with_existing_email = "user_with_existing_email_name";
        User userOne = createUser("user_with_existing_email_login",
                user_with_existing_email,
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
        Message message = new Message();
        message.setText(postContent);
        message.setTitle(postTitle);

        message = messageService.save(message);
        userService.addMessage(saveedUser.getId(), message.getId());

        assertEquals(1, user.getMessages().size());
        message = user.getMessages().iterator().next();
        assertEquals(postContent, message.getText());
        assertEquals(postTitle, message.getTitle());
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

    @Test
    public void testConversations() throws ServiceException {
        User userJack = createUser("login", "Jack", "pass", "jack@googole.com");
        User userBen = createUser("login2", "Ben", "pass", "ben@googole.com");
        Message messageJack = new Message("JackTitle", "JackText");
        Message messageBen = new Message("BenTitle", "BenText");

        userJack.setMessages(new HashSet<Message>(Arrays.asList(messageJack)));
        userJack = userService.save(userJack);

        userBen.setMessages(new HashSet<Message>(Arrays.asList(messageBen)));
        userBen = userService.save(userBen);

        userJack.setConversations(new HashSet<User>(Arrays.asList(userBen)));
        User saved = userService.update(userJack);
    }

    @Test
    public void testSendMessage() throws ServiceException {
        User userJack = createUser("login0", "Jack", "pass", "jack@googole1.com");
        User userBen = createUser("login2", "Ben", "pass", "ben@googole1.com");
        Message messageJack = new Message("JackTitle", "JackText");
        Message messageBen = new Message("BenTitle", "BenText");
        userJack = userService.save(userJack);
        userBen = userService.save(userBen);

        userService.sendMessage(userJack, userBen.getId(), messageJack);

        userJack = userService.get(userJack.getId());
        assertEquals(1, userJack.getConversations().size());
        assertEquals(1, userJack.getMessages().size());
        assertEquals("Ben", userJack.getConversations().iterator().next().getName());
        assertEquals("JackTitle", userJack.getConversations().iterator().next().getMessages().iterator().next().getTitle());
        assertEquals("JackTitle", userJack.getMessages().iterator().next().getTitle());

        //Send second message
        userService.sendMessage(userBen, userJack.getId(), messageBen);
        userJack = userService.get(userJack.getId());
        userJack = userService.get(userJack.getId());

        assertEquals(1, userJack.getConversations().size());
        assertEquals(1, userBen.getConversations().size());

        assertEquals(2, userJack.getMessages().size());
        assertEquals(2, userBen.getMessages().size());

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
