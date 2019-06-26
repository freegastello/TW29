package util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyValue {
	public String getPropertyValue(String propertyName) {
		String propertyValue = "";
		Properties properties = new Properties();
		try (InputStream inputStream = this.getClass().getResourceAsStream("../application.properties")) {
			properties.load(inputStream);
			propertyValue = properties.getProperty(propertyName);
		} catch (IOException e) {
			e.getStackTrace();
		}
		return propertyValue;
	}
}





//	public static UserDao getUserDao() {
//		Factory factory = new FactoryMethod.CreatorFactory(new PropertyValue().getPropertyValue("dbtype"));
//		return factory.create().dbType();
//	}

//class FactoryMethod {
//	interface Dao {
//		UserDao dbType();
//	}
//	static class Hibernate implements Dao {
//		@Override
//		public UserDao dbType() {
//			return new UserHibernateDaoImpl();
//		}
//	}
//	static class Jdbc implements Dao {
//		@Override
//		public UserDao dbType() {
//			return new UserDAOImpl();
//		}
//	}
//	abstract static class Factory {
//		String flag;
//		abstract Dao create();
//		Factory(String flag) {
//			this.flag = flag;
//		}
//	}
//	static class CreatorFactory extends Factory {
//		CreatorFactory(String flag) {
//			super(flag);
//		}
//		@Override
//		Dao create() {
//			if (flag.equals("hibernate")) {
//				return new Hibernate();
//			} else if (flag.equals("jdbc")) {
//				return new Jdbc();
//			} else {
//				throw new IllegalArgumentException();
//			}
//		}
//	}
//}