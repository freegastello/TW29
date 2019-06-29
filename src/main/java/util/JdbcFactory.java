package util;
import DAO.UserDAOImpl;
import DAO.UserDao;

class JdbcFactory extends Factory {
	@Override
	UserDao getUserDao() {
		return new UserDAOImpl();
	}

	@Override
	public UserDao getDao() {
		return Factory.create().getUserDao();
	}
}




//package util;
//import DAO.UserDAOImpl;
//import DAO.UserDao;
//
//class JdbcFactory extends Factory {
//	@Override
//	UserDao getUserDao() {
//		return new UserDAOImpl();
//	}
//}
