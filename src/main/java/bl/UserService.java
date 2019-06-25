package bl;

import DAO.UserHibernateDaoImpl;
import model.User;

import java.util.List;

public class UserService {
//	private UserDAOImpl userDAO = new UserDAOImpl();
	private UserHibernateDaoImpl userDAO = new UserHibernateDaoImpl();


	public boolean addUser(User user) {
		if (userDAO.addUser(user)) {
			return true;
		}
		return false;
	}

	public void delete(Long users_id) {userDAO.delete(users_id);}

	public void update(User user) {userDAO.update(user);}

	public List<User> selectOne(Long users_id) {return userDAO.selectOne(users_id);}

	public List<User> getAll() {return userDAO.getAll();}
}
