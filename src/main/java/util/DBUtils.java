package util;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	private static DBUtils dbUtils;

	private DBUtils() {}

	public static synchronized DBUtils getDBUtils() {
		if (dbUtils == null) {
			dbUtils = new DBUtils();
		}
		return dbUtils;
	}

	public SessionFactory getConfiguration() throws HibernateException {
		Configuration configuration = new Configuration()
				.setProperty( "hibernate.connection.driver_class",		"com.mysql.jdbc.Driver")
				.setProperty( "hibernate.connection.url",				"jdbc:mysql://localhost:3306/crud?useSSL=false")
				.setProperty( "hibernate.connection.username",			"admin")
				.setProperty( "hibernate.connection.password",			"admin")
				.setProperty( "hibernate.connection.pool_size",			"10")
				.setProperty( "hibernate.connection.autocommit",		"false")
				.setProperty( "hibernate.cache.provider_class",			"org.hibernate.cache.NoCacheProvider")
				.setProperty( "hibernate.cache.use_second_level_cache", "false")
				.setProperty( "hibernate.cache.use_query_cache",		"false")
				.setProperty( "hibernate.dialect",						"org.hibernate.dialect.MySQL5Dialect")
				.setProperty( "hibernate.show_sql",						"false")
				.setProperty( "hibernate.current_session_context_class","thread")
				.addAnnotatedClass(model.User.class)
				;
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}


	public Connection getConnection() {
		Connection connection = null;
		if (driverValidate()) {
			try {
				String url  = "jdbc:mysql://localhost:3306/crud?useSSL=false";
				String user = "admin";
				String pass = "admin";
				connection  = DriverManager.getConnection(url, user, pass);
				System.out.println("Подключение прошло успешно!");
			} catch (SQLException e) {
				System.out.println("ОШИБКА подключения getConnect");
				e.printStackTrace();
			}
		} else {
			System.out.println("Драйвер не подключился");
		}
		return connection;
	}

	private boolean driverValidate() {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			System.out.println("Драйвер подключён");
		} catch (ClassNotFoundException e) {
			System.out.println("Verefication Driver for All FAILED!!!!!!!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
