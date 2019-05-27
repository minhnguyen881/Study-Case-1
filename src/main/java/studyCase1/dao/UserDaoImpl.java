package studyCase1.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import studyCase1.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	private static final String USERNAME_COLUMN = "username";
	private static final String PASSWORD_COLUMN = "password";
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(User user) {
		getSession().persist(user);
	}

	@Override
	public boolean checkExisting(String username) {
		Criteria cr = this.getCriteria(username);
		List users = cr.list();
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	private Criteria getCriteria(String username) {
		Criteria cr = getSession().createCriteria(User.class);
		cr.add(Restrictions.eq(USERNAME_COLUMN, username));
		return cr;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public boolean login(User user) {
		Criteria cr = this.getCriteria(user);
		List users = cr.list();
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	private Criteria getCriteria(User user) {
		Criteria cr = getSession().createCriteria(User.class);
		cr.add(Restrictions.eq(USERNAME_COLUMN, user.getUsername()));
		cr.add(Restrictions.eq(PASSWORD_COLUMN, user.getPassword()));
		return cr;
	}

	@Override
	public List<String> getUsers() {
		String hql = "SELECT U.username FROM User U";
		Query query = getSession().createQuery(hql);
		return query.getResultList();
	}

}
