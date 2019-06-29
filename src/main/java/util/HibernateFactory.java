package util;
import DAO.UserDao;
import DAO.UserHibernateDaoImpl;

class HibernateFactory extends Factory {
	@Override
	UserDao getUserDao() {
		return new UserHibernateDaoImpl();
	}

	@Override
	public UserDao getDao() {
		return Factory.create().getUserDao();
	}
}




//package util;
//import DAO.UserDao;
//import DAO.UserHibernateDaoImpl;
//
//class HibernateFactory extends Factory {
//	@Override
//	UserDao getUserDao() {
//		return new UserHibernateDaoImpl();
//	}
//}