package studyCase1.dao;

import java.util.List;

import studyCase1.model.User;

public interface UserDao {
	public void save(User user);

	public boolean checkExisting(String username);

	public boolean login(User user);

	public List<String> getUsers();
}
