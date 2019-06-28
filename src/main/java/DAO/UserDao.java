package DAO;
import model.User;
import java.util.List;

public interface UserDao {
	List<User> selectOne(Long users_id);

	List<User> getAll ();

	boolean searchFromSqlNameExist(String userName);

	int errorCheck(User user);

	boolean addUser(User user);

	void delete(Long users_id);

	void update(User user);
}
