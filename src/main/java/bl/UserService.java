package bl;
import DAO.UserDao;
import model.User;
import util.Factory;
import java.sql.SQLException;
import java.util.List;

public class UserService {
	private UserDao userDAO = new Factory().getUserDao();


	public boolean addUser(User user) throws SQLException {
		if (userDAO.addUser(user)) {
			return true;
		}
		return false;
	}

	public void delete(Long users_id) throws SQLException {userDAO.delete(users_id);}

	public void update(User user) throws SQLException {userDAO.update(user);}

	public List<User> selectOne(Long users_id) throws SQLException {return userDAO.selectOne(users_id);}

	public List<User> getAll() throws SQLException {return userDAO.getAll();}

	public int errorCheck(User user) throws SQLException {
		return userDAO.errorCheck(user);
	}
}
