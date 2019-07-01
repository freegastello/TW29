package DAO;
import model.User;
import java.util.List;

public interface UserDao {
	List<User> selectOne(Long users_id);

	List<User> getAll ();

	boolean searchFromSqlNameExist(String userName);

	boolean searchFromSqlLoginExist(String userLogin);

	int errorCheck(User user);

	int addUser(User user);

	void delete(Long users_id);

	int update(User user);

	boolean userIsExist(String login, String password);

	List<User> checkUserAndGetRole(String login, String password);

}
