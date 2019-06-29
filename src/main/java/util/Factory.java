package util;
import DAO.UserDao;

public abstract class Factory {
	abstract UserDao getUserDao();

	public abstract UserDao getDao();

	public static Factory create() {
		Factory factory;
		String val = new PropertyValue().getPropertyValue("dbtype");
		switch (val) {
			case "hibernate":
				factory = new HibernateFactory();
				break;
			case "jdbc":
				factory = new JdbcFactory();
				break;
			default:
				throw new IllegalArgumentException();
		}
		return factory;
	}
}







//package util;
//import DAO.UserDao;
//
//public abstract class Factory {
//	abstract UserDao getUserDao();
//
//	public abstract UserDao getDao();
//
//	public static Factory create() {
//		Factory factory;
//		String val = new PropertyValue().getPropertyValue("dbtype");
//		if (val.equals("hibernate")) {
//			factory = new HibernateFactory();
//		} else if (val.equals("jdbc")) {
//			factory = new JdbcFactory();
//		} else {
//			throw new IllegalArgumentException();
//		}
//		return factory;
//	}
//}