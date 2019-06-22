package bl;

import DAO.UserHibernateDao;
import model.User;
import java.util.List;

public class UserService {
//	private UserDAO userDAO = new UserDAO();
	private UserHibernateDao userDAO = new UserHibernateDao();


	public void addUser(User user) {userDAO.addUser(user);}

	public void delete(Long users_id) {
		userDAO.delete(users_id);
	}

	public void update(User user) {
		userDAO.update(user);
	}

	public List<User> selectOne(Long users_id) {return userDAO.selectOne(users_id);}

	public List<User> getAll() {return userDAO.getAll();}



}
