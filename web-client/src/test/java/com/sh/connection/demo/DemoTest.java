package com.sh.connection.demo;

import static com.sh.connection.util.JettyContainer.URL;
import static com.sh.connection.util.Messages.VISIBILITY_SUCCESSFULLY_CHANGED;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertButtonPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertElementPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresentWithExactText;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertRadioOptionSelected;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextNotPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickButton;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLinkWithExactText;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickRadioOption;
import static net.sourceforge.jwebunit.junit.JWebUnit.getTestingEngine;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sh.connection.util.JettyContainer;

public class DemoTest {

	private static final String LOGOUT_BTN_ID = "logoutForm:logoutBtn";


	private static final String LOGIN_INPUT_ID = "loginForm:loginInput";
	private static final String PASSWORD_INPUT_ID = "loginForm:passwordInput";
	private static final String LOGIN_BTN_ID = "loginForm:loginBtn";
	private static final String REGISTRATION_BTN_ID = "loginForm:registrationBtn";

	private static final String REGISTRATION_LOGIN_INPUT_ID = "registrationForm:loginInput";
	private static final String REGISTRATION_NAME_INPUT_ID = "registrationForm:nameInput";
	private static final String REGISTRATION_PASSWORD_INPUT_ID = "registrationForm:passwordInput";
	private static final String REGISTRATION_CONFIRMATION_INPUT_ID = "registrationForm:confirmationInput";
	private static final String REGISTRATION_EMAIL_INPUT_ID = "registrationForm:emailInput";
	private static final String REGISTRATION_REGISTER_BTN_ID = "registrationForm:registerBtn";


	private static final String CHANGE_VISIBILITY_BTN_ID = "visibilityForm:changeVisibilityBtn";
	private static final String VISIBILITY_RADIO_SELECT_ID = "visibilityForm:visibilitySelect";


	private static String testLogin = "test_login";
	private static String testName = "test_name";
	private static String testPassword = "test_password";
	private static String testConfirmation = testPassword;
	private static String testEmail = "test_email@example.com";

	@BeforeClass
	public static void beforeClass() throws Exception {
		new JettyContainer();
		setBaseUrl(URL);
		registerUser(testLogin, testName, testPassword, testConfirmation,
				testEmail);
	}

	@Before
	public void prepare() {
		setBaseUrl(URL);
	}

	@After
	public void logout() {
		if (getTestingEngine().hasButton(LOGOUT_BTN_ID)) {
			clickButton(LOGOUT_BTN_ID);
		}
	}

	@Test
	public void testMainPageComponents() {
		beginAt("/");

		assertTextPresent("Registered users:");
		assertTextPresent("User");
		assertTextPresent("Actions");
		assertElementPresent(LOGIN_INPUT_ID);
		assertElementPresent(PASSWORD_INPUT_ID);
		assertButtonPresent(LOGIN_BTN_ID);
		assertButtonPresent(REGISTRATION_BTN_ID);
		login();
		assertLinkPresentWithExactText("Profile");
		assertLinkPresentWithExactText("Send message");
	}

	@Test
	public void testRegistrationOfExistingUser() {
		beginAt("/");
		clickButton(REGISTRATION_BTN_ID);

		String login = testLogin;
		String name = "valid_name";
		String email = "valid_email@example.com";

		registerUser(login, name, "valid_password", "valid_password", email);

		assertTextNotPresent("Welcome, " + name);
	}

	@Test
	public void testRegistrationWithEmptyData() {
		beginAt("/");
		clickButton(REGISTRATION_BTN_ID);

		String login = "";
		String name = "";
		String password = "";
		String email = "";

		registerUser(login, name, password, password, email);

		assertTextNotPresent("Welcome, " + name);
		assertTextPresent("Login is required.");
		assertTextPresent("Name is required.");
		assertTextPresent("Password is required.");
		assertTextPresent("Confirmation is required.");
		assertTextPresent("Email is required.");
	}

	@Test
	public void testLoginWithEmptyFields() {
		beginAt("/");
		setTextField(LOGIN_INPUT_ID, "");
		setTextField(PASSWORD_INPUT_ID, "");
		clickButton(LOGIN_BTN_ID);

		assertTextNotPresent("Welcome, ");
		assertTextPresent("Login is required.");
		assertTextPresent("Password is required.");
	}

	@Test
	public void testLogout() {
		login();

		clickButton(LOGOUT_BTN_ID);
		assertButtonPresent(LOGIN_BTN_ID);
		assertButtonPresent(REGISTRATION_BTN_ID);
	}

	@Test
	public void testLoginWithWrongCredentials() {
		beginAt("/");
		setTextField(LOGIN_INPUT_ID, "unexisting_user_login");
		setTextField(PASSWORD_INPUT_ID, "unexisting_user_password");
		clickButton(LOGIN_BTN_ID);
		assertTextNotPresent("Welcome, ");
	}

	@Test
	public void testChangeVisibility() {
		login();

		clickLinkWithExactText(testName);

		String visibility = "false";
		clickRadioOption(VISIBILITY_RADIO_SELECT_ID, visibility);
		clickButton(CHANGE_VISIBILITY_BTN_ID);

		assertTextPresent(VISIBILITY_SUCCESSFULLY_CHANGED + "Private");
		assertRadioOptionSelected(VISIBILITY_RADIO_SELECT_ID, visibility);
	}

	private static void registerUser(String login, String name,
			String password, String confirmation, String email) {
		beginAt("/");
		clickButton(REGISTRATION_BTN_ID);

		setTextField(REGISTRATION_LOGIN_INPUT_ID, login);
		setTextField(REGISTRATION_NAME_INPUT_ID, name);
		setTextField(REGISTRATION_PASSWORD_INPUT_ID, password);
		setTextField(REGISTRATION_CONFIRMATION_INPUT_ID, confirmation);
		setTextField(REGISTRATION_EMAIL_INPUT_ID, email);

		clickButton(REGISTRATION_REGISTER_BTN_ID);
	}

	private void login() {
		login(testLogin, testPassword);
	}

	private void login(String login, String password) {
		beginAt("/");
		setTextField(LOGIN_INPUT_ID, login);
		setTextField(PASSWORD_INPUT_ID, password);
		clickButton(LOGIN_BTN_ID);
		assertButtonPresent(LOGOUT_BTN_ID);

	}
}
