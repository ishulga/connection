package com.sh.connection.service;

public interface Messages {
	String EMPTY_COMMENT_CONTENT = "Comment content is empty.";

	String EMPTY_POST_TITLE = "Post has an empty title.";
	String EMPTY_POST_CONTENT = "Post has an empty content.";

	String EMPTY_PASSWORD = "User password is empty.";
	String EMPTY_NAME = "User name is empty.";
	String EMAIL_EXISTS = "User with such email already exists.";
	String INVALID_EMAIL = "User has an invalid email.";
	String EMPTY_EMAIL = "User email is empty.";
	String LOGIN_EXISTS = "User with such login already exists.";
	String EMPTY_LOGIN = "User login is empty.";
	String WRONG_CREDENTIALS = "Wrong credentials.";
	String DID_NOT_SUBSCRIBED = "You can't unsubscribe from this user - you haven't subscribed for it.";
	String ALREADY_SUBSCRIBED = "You already subscribed to this user.";
	String SELF_SUBSCRIPTION = "You can't subscribe to yourself.";
}
