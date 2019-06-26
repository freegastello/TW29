package DAO;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBUtils;

import java.util.List;

public class UserHibernateDaoImpl implements UserDao {
	private static SessionFactory sessionFactory = DBUtils.getDBUtils().getConfiguration();
	private String str = "(0-9A-Za-zА-Яа-я_)";
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
		boolean bool = searchFromSqlNameExist(user.getName());
		if (bool) {
			return 2;
		}
		if (user.getLogin() == null || (!user.getLogin().matches(reg))) {
			return 3;
		}
		if (user.getPassword() == null || (!user.getPassword().matches(reg))) {
			return 4;
		}
		return 0;
	}

	public boolean addUser(User user) {
		if (errorCheck(user) != 0) {return false;}
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
}
