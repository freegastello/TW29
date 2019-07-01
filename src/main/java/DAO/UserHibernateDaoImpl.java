package DAO;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBUtils;
import java.util.List;

public class UserHibernateDaoImpl implements UserDao {
	private static SessionFactory sessionFactory = DBUtils.getDBUtils().getConfiguration();
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

	public int errorCheck(User user) {
		if (user.getName() == null || (!user.getName().matches(reg))) {
			return 1;
		}
		boolean boolName = searchFromSqlNameExist(user.getName());
		if (boolName) {
			return 2;
		}
		if (user.getLogin() == null || (!user.getLogin().matches(reg))) {
			return 3;
		}
		boolean boolLog = searchFromSqlLoginExist(user.getLogin());
		if (boolLog) {
			return 4;
		}
		if (user.getPassword() == null || (!user.getPassword().matches(reg))) {
			return 5;
		}
		return 0;
	}

	public int addUser(User user) {
		int x = errorCheck(user);
		if (x != 0) {return x;}
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
		session.close();
		return x;
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

	public List<User> selectOne(Long users_id) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		List<User> users = session.createQuery("FROM User as u WHERE u.users_id = " + users_id).list();
		transaction.commit();
		session.close();
		return users;
	}

	public int update(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		User userNow = new User();
		List<User> us = selectOne(user.getUsers_id());
		if (us == null) {
			System.out.println("ERROR!!! -update- is NULL");
			return 7;
		}
		if (us.get(0).getUsers_id().equals(user.getUsers_id()) &&
			us.get(0).getName().equals(user.getName()) &&
			us.get(0).getLogin().equals(user.getLogin()) &&
			us.get(0).getPassword().equals(user.getPassword())) {
			System.out.println("-update- Field values have not changed, database entry canceled");
			return 7;
		}

		if (searchFromSqlNameExist(user.getName()) == true
				&& (!us.get(0).getName().equals(user.getName()))) return  2;
		if (searchFromSqlLoginExist(user.getLogin()) == true
				&& (!us.get(0).getLogin().equals(user.getLogin()))) return 4;

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
		userNow.setRole(User.ROLE.USER);
		try {
			session.update(userNow);
			System.out.println("Successfuly update by id = " + user.getUsers_id());
		} catch (IllegalArgumentException e) {
			System.out.println("Error -update- string with id = " + user.getUsers_id() + " not found");
			e.printStackTrace();
		}
		transaction.commit();
		session.close();
		return 0;
	}

	@Override
	public boolean userIsExist(String login, String password) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		List name = session.createQuery("FROM User as u WHERE u.login = '" + login + "' and u.password = '" + password + "'").list();
		transaction.commit();
		session.close();
		if (name.size() > 0) return true;
		return false;
	}

	@Override
	public List<User> checkUserAndGetRole(String login, String password) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<User> role = session.createQuery("FROM User as u WHERE u.login = '" + login + "' and u.password = '" + password + "'").list();
//		List role = session.createQuery("FROM User as u WHERE u.login = '" + login + "' and u.password = '" + password + "'").getSingleResult();
//		session.createNativeQuery("SELECT Count(login) FROM User as u WHERE u.login = '" + login + "' and u.password = '" + password + "'");
		transaction.commit();
		session.close();
		return role;
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

	public boolean searchFromSqlLoginExist(String userLogin) {
		Session session = sessionFactory.openSession();
		Transaction transaction;
		transaction = session.beginTransaction();
		List login = session.createQuery("FROM User as u WHERE u.login = '" + userLogin + "'").list();
		transaction.commit();
		if (!login.isEmpty()) {
			System.out.println("ERROR This login is already there, login = " + userLogin);
			session.close();
			return true;
		}
		session.close();
		return false;
	}
}
