package studyCase1.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import studyCase1.dao.UserDao;
import studyCase1.model.User;
import studyCase1.utils.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock
	UserDao dao;
	@InjectMocks
	UserServiceImpl service;

	@Test
	public void givenUser_whenSave_thenOk() {
		User user = new User("admin", "111");
		doNothing().when(this.dao).save(user);

		service.save(user);
		verify(this.dao, times(1)).save(user);
	}

	@Test
	public void givenExistedUser_whenCheckExisting_thenOk() {
		String username = "admin";
		when(this.dao.checkExisting(username)).thenReturn(true);
		boolean actual = service.checkExisting(username);
		assertTrue(actual);
	}

	@Test
	public void givenExistedUser_whenCheckExisting_thenFalse() {
		String username = "admin";
		when(this.dao.checkExisting(username)).thenReturn(false);
		boolean actual = service.checkExisting(username);
		assertFalse(actual);
	}

	@Test
	public void givenExistedUser_whenLogin_thenOk() {
		User user = new User("admin", "111");
		when(this.dao.login(user)).thenReturn(true);
		boolean actual = service.login(user);
		assertTrue(actual);
	}

	@Test
	public void givenExistedUser_whenLogin_thenFalse() {
		User user = new User("admin", "111");
		when(this.dao.login(user)).thenReturn(false);
		boolean actual = service.login(user);
		assertFalse(actual);
	}

	@Test
	public void givenRequestGetUsers_whenFetchUsers_thenReturnUsers() {
		List<String> usernames = TestUtils.getUsernames();
		when(this.dao.getUsers()).thenReturn(usernames);

		List<String> userList = this.service.getUsers();
		assertEquals(2, userList.size());
		assertEquals("user1", userList.get(0));
		assertEquals("user2", userList.get(1));
	}
}
