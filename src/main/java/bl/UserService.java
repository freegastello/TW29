package bl;
import DAO.UserDao;
import model.User;
import util.Application;
import java.sql.SQLException;
import java.util.List;

public class UserService {
	private UserDao userDAO = new Application().start();


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
}
