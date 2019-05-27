package studyCase1.service;

import java.util.List;

import studyCase1.model.User;

public interface UserService {
	public void save(User user);

	public boolean checkExisting(String username);

	public boolean login(User user);

	public List<String> getUsers();
}
