package util;
import DAO.UserDAOImpl;
import DAO.UserDao;

class JdbcFactory extends Factory {
	@Override
	UserDao getUserDao() {
		return new UserDAOImpl();
	}
}
