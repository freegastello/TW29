package util;
import DAO.UserDAOImpl;
import DAO.UserDao;
import DAO.UserHibernateDaoImpl;

public class Factory {
	public UserDao getUserDao() {
		FactoryCreate fc = new CreatorFactory(new PropertyValue().getPropertyValue("dbtype"));
		return fc.create();
	}

	abstract class FactoryCreate {
		String flag;
		abstract UserDao create();
		FactoryCreate(String flag) {
			this.flag = flag;
		}
	}
	private class CreatorFactory extends FactoryCreate {
		CreatorFactory(String flag) {
			super(flag);
		}
		@Override
		UserDao create() {
			if (flag.equals("hibernate")) {
				return new UserHibernateDaoImpl();
			} else if (flag.equals("jdbc")) {
				return new UserDAOImpl();
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
}



/** working */
//package util;
//import DAO.UserDAOImpl;
//import DAO.UserDao;
//import DAO.UserHibernateDaoImpl;
//
//public class Factory {
//	public UserDao getUserDao() {
//		return create(new PropertyValue().getPropertyValue("dbtype"));
//	}
//
//	private UserDao create(String flag) {
//		if (flag.equals("hibernate")) {
//			return new UserHibernateDaoImpl();
//		} else if (flag.equals("jdbc")) {
//			return new UserDAOImpl();
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}
//}
