package util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBUtils {
	//userDAO
	//fabrika
	//singletone

	private static DBUtils dbUtils;
	private DBUtils() {}
	public static synchronized DBUtils getDBUtils() {
		if (dbUtils == null) {
			dbUtils = new DBUtils();
		}
		return dbUtils;
	}

	public SessionFactory configureSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration()
				.setProperty( "hibernate.connection.driver_class",		"com.mysql.jdbc.Driver")
				.setProperty( "hibernate.connection.url",				"jdbc:mysql://localhost:3306/crud?useSSL=false")
				.setProperty( "hibernate.connection.username",			"admin")
				.setProperty( "hibernate.connection.password",			"admin")
				.setProperty( "hibernate.connection.pool_size",			"2")
				.setProperty( "hibernate.connection.autocommit",		"false")
				.setProperty( "hibernate.cache.provider_class",			"org.hibernate.cache.NoCacheProvider")
				.setProperty( "hibernate.cache.use_second_level_cache", "false")
				.setProperty( "hibernate.cache.use_query_cache",		"false")
				.setProperty( "hibernate.dialect",						"org.hibernate.dialect.MySQL5Dialect")
				.setProperty( "hibernate.show_sql",						"true")
				.setProperty( "hibernate.current_session_context_class","thread")
				.addAnnotatedClass(model.User.class)
				;
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}
