package util;
import DAO.UserDao;

public abstract class Factory {
	abstract UserDao getUserDao();
}





/** working version */
//new UserHibernateDaoImpl();
//new IllegalArgumentException();
//	public UserDao createFactory() {
//		FactoryCreate fc = new CreatorFactory(new PropertyValue().getPropertyValue("dbtype"));
//	return fc.create();
//	}
//abstract class FactoryCreate {
//	String flag;
//	abstract UserDao create();
//	FactoryCreate(String flag) {
//		this.flag = flag;
//	}
//}
//hibfac
//jdbcfac
//class CreatorFactory extends FactoryCreate {//??
//	CreatorFactory(String flag) {
//		super(flag);
//	}
//	@Override
//	UserDao create() {
//		if (flag.equals("hibernate")) {
//			return new UserHibernateDaoImpl();//factory
//		} else if (flag.equals("jdbc")) {
//			return new UserDAOImpl();//factory
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}
//}



/** 190627= */
//package util;
//import DAO.UserDAOImpl;
//import DAO.UserDao;
//import DAO.UserHibernateDaoImpl;
//
//public class Factory {
//	public UserDao getUserDao() {//abstract
//		FactoryCreate fc = new CreatorFactory(new PropertyValue().getPropertyValue("dbtype"));
//		return fc.create();
//	}
//
//	abstract class FactoryCreate {
//		String flag;
//		abstract UserDao create();
//		FactoryCreate(String flag) {
//			this.flag = flag;
//		}
//	}
////hibfac
////jdbcfac
//	private class CreatorFactory extends FactoryCreate {//??
//		CreatorFactory(String flag) {
//			super(flag);
//		}
//		@Override
//		UserDao create() {
//			if (flag.equals("hibernate")) {
//				return new UserHibernateDaoImpl();//factory
//			} else if (flag.equals("jdbc")) {
//				return new UserDAOImpl();//factory
//			} else {
//				throw new IllegalArgumentException();
//			}
//		}
//	}
//}



/** working old */
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
