package DAO;
import model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
	List<User> selectOne(Long users_id) throws SQLException;

	List<User> getAll () throws SQLException;

	boolean searchFromSqlNameExist(String userName) throws SQLException;

	boolean errorCheck(User user) throws SQLException;

	boolean addUser(User user) throws SQLException;

	void delete(Long users_id) throws SQLException;

	void update(User user) throws SQLException;
}
