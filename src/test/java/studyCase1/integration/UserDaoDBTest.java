package studyCase1.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import studyCase1.configuration.UserConfig;
import studyCase1.dao.UserDao;
import studyCase1.model.User;

@Category(IntegrationTest.class)
@ContextConfiguration(classes = { UserConfig.class })
public class UserDaoDBTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	UserDao userDao;

	@Before
	public void setup() {
		deleteFromTables("User");
	}

	@Test
	public void givenUser_whenSave_thenGetOk() {
		User user = new User("Admin", "123456");
		userDao.save(user);
		boolean isUserExist = userDao.checkExisting(user.getUsername());
		assertTrue(isUserExist);
	}

	@Test
	public void givenNotExistedUserUser_whenCheckExisting_thenGetFalse() {
		User user = new User("Admin", "123456");
		boolean isUserExist = userDao.checkExisting(user.getUsername());
		assertFalse(isUserExist);
	}

	@Test
	public void givenUser_whenLogin_thenGetOk() {
		User user = new User("Admin", "123456");
		userDao.save(user);
		boolean actual = userDao.login(user);
		assertTrue(actual);
	}

	@Test
	public void givenNotExistedUser_whenLogin_thenFalse() {
		User user = new User("Admin", "123456");
		boolean actual = userDao.login(user);
		assertFalse(actual);
	}

	@Test
	public void given_whenGetUsers_thenUsers() {
		User user1 = new User("Admin", "123456");
		userDao.save(user1);
		User user2 = new User("Admin", "123456");		
		userDao.save(user2);
		 
		List<String> actual = this.userDao.getUsers();
		assertEquals(2, actual.size());
	}
}
