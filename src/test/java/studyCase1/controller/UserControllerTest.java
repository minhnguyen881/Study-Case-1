package studyCase1.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import studyCase1.configuration.Message;
import studyCase1.model.User;
import studyCase1.service.UserService;
import studyCase1.utils.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	private static final String REGISTRATION_PAGE = "registration";
	private static final String HOME_PAGE = "dashboard";
	private static final String LOGIN_PAGE = "login";
	private static final String USERS_PAGE = "users";
	@Mock
	UserService userService;
	@Mock
	Message message;
	@InjectMocks
	UserController userController;
	@Spy
	ModelMap model;

	@Test
	public void givenNotExistedUser_whenSave_thenOkAndGoToLoginPage() {
		User user = new User("admin", "123456");
		when(this.userService.checkExisting(user.getUsername())).thenReturn(false);
		doNothing().when(this.userService).save(user);

		String actual = this.userController.save(user, model);
		assertEquals(LOGIN_PAGE, actual);
	}

	@Test
	public void givenExistedUser_whenSave_thenFalse() {
		User user = new User("admin", "123456");
		when(this.userService.checkExisting(user.getUsername())).thenReturn(true);

		String actual = this.userController.save(user, model);
		assertEquals(REGISTRATION_PAGE, actual);
	}

	@Test
	public void testOpenHomePage() {
		String actual = this.userController.openHomePage();
		assertEquals(HOME_PAGE, actual);
	}

	@Test
	public void testOpenRegistrationPage() {
		String actual = this.userController.openRegistrationPage();
		assertEquals(REGISTRATION_PAGE, actual);
	}

	@Test
	public void testOpenLoginPage() {
		String actual = this.userController.openLoginPage(model);
		assertEquals(LOGIN_PAGE, actual);
	}

	@Test
	public void givenExistedUser_thenLogin_thenGoToHomepage() {
		User user = new User("admin", "123456");
		when(this.userService.login(user)).thenReturn(true);

		String actual = this.userController.login(user, model);
		assertEquals(user.getUsername(), model.get("username"));
		assertEquals(HOME_PAGE, actual);
	}

	@Test
	public void givenNotExistedUser_thenLogin_GetFalse() {
		User user = new User("user2", "123456");
		when(this.userService.login(user)).thenReturn(false);

		String actual = this.userController.login(user, model);
		assertEquals(true, model.containsAttribute("message"));
		assertEquals(LOGIN_PAGE, actual);
	}

	@Test
	public void givenRequestGetUsers_whenFetchUsers_thenReturnUsers() {
		List<String> usernames = TestUtils.getUsernames();
		when(this.userService.getUsers()).thenReturn(usernames);

		String actualPage = this.userController.getUsers(model);
		assertEquals(USERS_PAGE, actualPage);
		List<User> userList = (List<User>) model.get("users");
		assertEquals(2, userList.size());
		assertEquals("user1", userList.get(0));
		assertEquals("user2", userList.get(1));
	}

}
