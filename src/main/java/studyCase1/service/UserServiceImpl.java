package studyCase1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import studyCase1.dao.UserDao;
import studyCase1.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Override
	public void save(User user) {
		this.dao.save(user);
	}

	@Override
	public boolean checkExisting(String username) {
		return this.dao.checkExisting(username);
	}

	@Override
	public boolean login(User user) {
		return this.dao.login(user);
	}

	@Override
	public List<String> getUsers() {
		return this.dao.getUsers();
	}

}
