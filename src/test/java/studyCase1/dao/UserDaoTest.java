package studyCase1.dao;

import static org.mockito.Mockito.times;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import studyCase1.model.User;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
	@Mock
	SessionFactory sessionFactory;
	@InjectMocks
	UserDaoImpl userDao;

	@Test
	public void givenUser_whenSave_thenOk() {
		User user = new User("admin", "123456");
		Session session = Mockito.mock(Session.class);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doNothing().when(session).persist(user);
		
		userDao.save(user);
		Mockito.verify(session, times(1)).persist(user);
	}
}
