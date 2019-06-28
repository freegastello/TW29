package util;
import DAO.UserDao;
import DAO.UserHibernateDaoImpl;

class HibernateFactory extends Factory {
	@Override
	UserDao getUserDao() {
		return new UserHibernateDaoImpl();
	}
}
