package bl;
import DAO.UserDao;
import model.User;
import util.Factory;
import java.util.List;

public class UserService {
	private UserDao userDAO = Factory.create().getDao();


	public int addUser(User user) {	return userDAO.addUser(user);}

	public void delete(Long users_id) {userDAO.delete(users_id);}

	public int update(User user) {
		return userDAO.update(user);
	}

	public List<User> selectOne(Long users_id) {return userDAO.selectOne(users_id);}

	public List<User> getAll() {return userDAO.getAll();}

	public int errorCheck(User user) {return userDAO.errorCheck(user);}

	public boolean userIsExist(String login, String password) {
		return userDAO.userIsExist(login, password);
	}

	public List<User> checkUserAndGetRole(String login, String password) {
		return userDAO.checkUserAndGetRole(login, password);
	}



}
