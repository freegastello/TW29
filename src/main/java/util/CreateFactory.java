package util;
import DAO.UserDao;

public class CreateFactory {
	public static UserDao create() {
		String val = new PropertyValue().getPropertyValue("dbtype");
		if (val.equals("hibernate")) {
			return new HibernateFactory().getUserDao();
		} else if (val.equals("jdbc")) {
			return new JdbcFactory().getUserDao();
		} else {
			throw new IllegalArgumentException();
		}
	}
}
