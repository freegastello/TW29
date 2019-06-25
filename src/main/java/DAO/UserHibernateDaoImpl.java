package DAO;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import servlet.AddUserServlet;
import util.DBUtils;
import java.util.List;

public class UserHibernateDaoImpl implements UserDao {
//	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//	private static SessionFactory sessionFactory = configureSessionFactory();
	private static SessionFactory sessionFactory = DBUtils.getDBUtils().configureSessionFactory();
	private static String str = "(0-9A-Za-zА-Яа-я_)";
	private String reg = "[0-9A-Za-zА-Яа-я_]+";

	public List getAll () {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		List users = session.createQuery("FROM User").list();
		transaction.commit();
		session.close();
		return users;
	}

	public boolean errorCheck(User user) {
		if (user.getName() == null || (!user.getName().matches(reg))) {
			AddUserServlet.mess = "Enter your name " + str;
			return false;
		}
		if (user.getLogin() == null || (!user.getLogin().matches(reg))) {
			AddUserServlet.mess = "Enter your login " + str;
			return false;
		}
		if (user.getPassword() == null || (!user.getPassword().matches(reg))) {
			AddUserServlet.mess = "Enter your password " + str;
			return false;
		}
		boolean bool = searchFromSqlNameExist(user.getName());
		if (bool) {
			AddUserServlet.mess = "User with the same name already exists";
			return false;
		}
		return true;
	}

	public boolean addUser(User user) {
		if (!errorCheck(user)) {return false;}
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
		session.close();
		return true;
	}

	public void delete(Long users_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		User user = session.get(User.class, users_id);
		try {
			session.delete(user);
			System.out.println("Successfuly deleted by id = " + users_id);
		} catch (IllegalArgumentException e) {
			System.out.println("Error -delete- string with id = " + users_id + " not found");
			e.printStackTrace();
		}
		transaction.commit();
		session.close();
	}

	public List selectOne(Long users_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		List users = session.createQuery("FROM User as u WHERE u.users_id = " + users_id).list();
		transaction.commit();
		session.close();
		return users;
	}

	public void update(User user) {
		if (!user.getName().matches("[\\w]+")) return;
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		User userNow = new User();
		List<User> us = selectOne(user.getUsers_id());
		if (us == null) {
			System.out.println("ERROR!!! -update- is NULL");
			return;
		}
		if (us.get(0).getUsers_id().equals(user.getUsers_id()) &&
			us.get(0).getName().equals(user.getName()) &&
			us.get(0).getLogin().equals(user.getLogin()) &&
			us.get(0).getPassword().equals(user.getPassword())) {
			System.out.println("-update- Field values have not changed, database entry canceled");
			return;
		}

		userNow.setUsers_id(user.getUsers_id());

		if (user.getName() != null && user.getName().length() > 0) {
			userNow.setName(user.getName());
		} else {
			userNow.setName(us.get(0).getName());
		}

		if (user.getLogin() != null && user.getLogin().length() > 0) {
			userNow.setLogin(user.getLogin());
		} else {
			userNow.setLogin(us.get(0).getLogin());
		}

		if (user.getPassword() != null && user.getPassword().length() > 0) {
			userNow.setPassword(user.getPassword());
		} else {
			userNow.setPassword(us.get(0).getPassword());
		}

		try {
			session.update(userNow);
			System.out.println("Successfuly update by id = " + user.getUsers_id());
		} catch (IllegalArgumentException e) {
			System.out.println("Error -update- string with id = " + user.getUsers_id() + " not found");
			e.printStackTrace();
		}
		transaction.commit();
		session.close();
	}

	public boolean searchFromSqlNameExist(String userName) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		List name = session.createQuery("FROM User as u WHERE u.name = '" + userName + "'").list();
		transaction.commit();
		if (!name.isEmpty()) {
			System.out.println("ERROR This name is already there, name = " + userName);
			session.close();
			return true;
		}
		session.close();
		return false;
	}





















//	public class LiveHibernateConnector implements IHibernateConnector {
//		private String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
//		private String DB_URL		  = "jdbc:mysql://localhost:3306/crud";
//		private String DB_USERNAME	  = "admin";
//		private String DB_PASSWORD	  = "admin";
//		private String DIALECT		  = "org.hibernate.dialect.MySQL5Dialect";
//		private String HBM2DLL		  = "create";
//		private String SHOW_SQL		  = "true";
//
//		private static Configuration config;
//		private static SessionFactory sessionFactory;
//		private Session session;
//
//		private boolean CLOSE_AFTER_TRANSACTION = false;



//		public LiveHibernateConnector() {
//			config = new Configuration();
//			config.setProperty("hibernate.connector.driver_class",	"com.mysql.jdbc.Driver");
//			config.setProperty("hibernate.connection.url",			"jdbc:mysql://localhost:3306/crud");
//			config.setProperty("hibernate.connection.username",		"admin");
//			config.setProperty("hibernate.connection.password",		"admin");
//			config.setProperty("hibernate.dialect",					"org.hibernate.dialect.MySQL5Dialect");
//			config.setProperty("hibernate.hbm2dll.auto",			"update");
//			config.setProperty("hibernate.show_sql",				"true");
//			config.setProperty("connection.provider_class",			"org.hibernate.cache.NoCacheProvider");
//
//
//			/** Resource mapping */
////        config.addAnnotatedClass(User.class);
//			sessionFactory = config.buildSessionFactory();
//		}
//
//		public HibWrapper openSession() throws HibernateException {
//			return new HibWrapper(getOrCreateSession(), CLOSE_AFTER_TRANSACTION);
//		}
//
//		public Session getOrCreateSession() throws HibernateException {
//			if (session == null) {
//				session = sessionFactory.openSession();
//			}
//			return session;
//		}
//
//		public void reconnect() throws HibernateException {
//			this.sessionFactory = config.buildSessionFactory();
//		}


//	}



//	public class LiveHibernateConnector implements IHibernateConnector {
//		private String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
//		private String DB_URL		  = "jdbc:mysql://localhost:3306/crud";
//		private String DB_USERNAME	  = "admin";
//		private String DB_PASSWORD	  = "admin";
//		private String DIALECT		  = "org.hibernate.dialect.MySQL5Dialect";
//		private String HBM2DLL		  = "create";
//		private String SHOW_SQL		  = "true";
//
//		private static Configuration config;
//		private static SessionFactory sessionFactory;
//		private Session session;
//
//		private boolean CLOSE_AFTER_TRANSACTION = false;
//
//		public LiveHibernateConnector() {
//
//			config = new Configuration();
//
//			config.setProperty("hibernate.connector.driver_class", DB_DRIVER_NAME);
//			config.setProperty("hibernate.connection.url",         DB_URL);
//			config.setProperty("hibernate.connection.username",    DB_USERNAME);
//			config.setProperty("hibernate.connection.password",    DB_PASSWORD);
//			config.setProperty("hibernate.dialect",                DIALECT);
//			config.setProperty("hibernate.hbm2dll.auto",           HBM2DLL);
//			config.setProperty("hibernate.show_sql",               SHOW_SQL);
//
//			/* Config connection pools */
//
//			config.setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
//			config.setProperty("hibernate.c3p0.min_size", "5");
//			config.setProperty("hibernate.c3p0.max_size", "20");
//			config.setProperty("hibernate.c3p0.timeout", "300");
//			config.setProperty("hibernate.c3p0.max_statements", "50");
//			config.setProperty("hibernate.c3p0.idle_test_period", "3000");
//
//			/** Resource mapping */
////        config.addAnnotatedClass(User.class);
//			sessionFactory = config.buildSessionFactory();
//		}
//
//		public HibWrapper openSession() throws HibernateException {
//			return new HibWrapper(getOrCreateSession(), CLOSE_AFTER_TRANSACTION);
//		}
//
//		public Session getOrCreateSession() throws HibernateException {
//			if (session == null) {
//				session = sessionFactory.openSession();
//			}
//			return session;
//		}
//
//		public void reconnect() throws HibernateException {
//			this.sessionFactory = config.buildSessionFactory();
//		}
//	}













/** Do not delete */
//	public static void main(String[] args) {
//		sessionFactory = new Configuration().configure().buildSessionFactory();
//		UserHibernateDaoImpl userHibernateDao = new UserHibernateDaoImpl();
//		System.out.println("Adding user records to the DB");
//		User user = new User(null,"Hibernate3","hiLog3","hiPass3");
//		userHibernateDao.addUser(user);
//		System.out.println("Delete user to the DB");
//		userHibernateDao.delete((long) 12);
//		System.out.println("List of all Users");
//		List users = userHibernateDao.getAll();
//		for (Object user : users) {
//			System.out.println(user);
//		}
//		System.out.println("List of ONE User");
//		List users = userHibernateDao.selectOne((long) 13);
//		for (Object user : users) {
//			System.out.println("List of ONE User = " + user);
//		}
//	}

}
