package DAO;
import model.User;
import util.DBUtils;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDao {
	private static Connection connection = DBUtils.getDBUtils().getConnection();
	private String reg = "[0-9A-Za-zА-Яа-я_]+";

	public List<User> selectOne(Long users_id) {
		List<User> arrayList = new ArrayList<>();
		String query = "SELECT * FROM users WHERE users_id = " + users_id + ";";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			User.ROLE roleIns = User.ROLE.USER;
			if (resultSet.next()) {
				String name = resultSet.getString("name");
				String login = resultSet.getString("login");
				String password = resultSet.getString(4);
				if (resultSet.getString(5).equals("ADMIN")) {
					roleIns = User.ROLE.ADMIN;
				}
				arrayList.add(new User(users_id, name, login, password, roleIns));
			}
			System.out.println("-selectOne- worked correctly");
		} catch (SQLException e) {
			System.out.println("ERROR!!! -selectOne-");
			e.printStackTrace();
		}
		return arrayList;
	}

	public List<User> getAll () {
		User newUsers;
		List<User> arrayList = new ArrayList<>();
		String query = "SELECT * FROM users ORDER BY users_id ASC;";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			User.ROLE roleIns;
			while (resultSet.next()) {
				Long users_id = resultSet.getLong("users_id");
				String name = resultSet.getString("name");
				String login = resultSet.getString("login");
				String password = resultSet.getString(4);
				int role = resultSet.getInt(5);
				if (role == 1) {
					roleIns = User.ROLE.ADMIN;
				} else {
					roleIns = User.ROLE.USER;
				}
				newUsers = new User(users_id, name, login, password, roleIns);
				arrayList.add(newUsers);
			}
			System.out.println("getAll отработал правильно");
		} catch (SQLException e) {
			System.out.println("ОШИБКА!!! -> getAll");
			e.printStackTrace();
		}
		return arrayList;
	}

	public boolean searchFromSqlNameExist(String userName) {
		String query = "SELECT * FROM users WHERE name = '" + userName + "';";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("Такое имя уже есть, name = " + userName);
				return true;
			}
		} catch (SQLException e) {
			System.out.println("ERROR!!! searchFromSqlNameExist");
			e.getStackTrace();
		}
		System.out.println("-searchFromSqlNameExist- отработал правильно false");
		return false;
	}

	public boolean searchFromSqlLoginExist(String userLogin) {
		String query = "SELECT * FROM users WHERE login = '" + userLogin + "';";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("ERROR This login is already there, login = " + userLogin);
				return true;
			}
		} catch (SQLException e) {
			System.out.println("ERROR!!! searchFromSqlNameExist");
			e.getStackTrace();
		}
		System.out.println("-searchFromSqlNameExist- отработал правильно false");
		return false;
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
		String userName = user.getName();
		String userLogin = user.getLogin();
		String userPassword = user.getPassword();

		String queryInsert = "INSERT INTO users (name, login, password, role) VALUE (?, ?, ?, ?);";
		try (final PreparedStatement addInsert = connection.prepareStatement(queryInsert)) {
//			addInsert.setBigDecimal(1, null);
			addInsert.setString(1, userName);
			addInsert.setString(2, userLogin);
			addInsert.setString(3, userPassword);
			addInsert.setString(4, "0");
			int num = addInsert.executeUpdate();
				System.out.println("-addUser- Произведено = " + num + " вставок");
		} catch (SQLException e) {
			System.out.println("ERROR!!! -addUser- SELECT + INSERT");
			e.printStackTrace();
		}
		return 0;
	}

	public void delete(Long users_id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			int rows = statement.executeUpdate("DELETE FROM users WHERE users_id = " + users_id + ";");
			if (rows > 0) {
				System.out.println("Удалил в БД по users_id = " + users_id);
				System.out.printf("%d row(s) deleted ", rows);//1 row(s) deleted
			} else {
				System.out.println("ERROR!!! -delete- не удалось удалить по users_id = " + users_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int update(User user) {
		Long   userUsers_id	= user.getUsers_id();
		String userName		= user.getName();
		String userLogin	= user.getLogin();
		String userPassword = user.getPassword();

		List<User> u = selectOne(userUsers_id);
		if (u != null) {
			if (u.get(0).getUsers_id().equals(userUsers_id) &&
				u.get(0).getName().equals(userName) &&
				u.get(0).getLogin().equals(userLogin) &&
				u.get(0).getPassword().equals(userPassword)) {
				System.out.println("-getUpdate- Поля не изменились, запись в БД отменена");
				return 7;
			}
		} else {
			System.out.println("ERROR!!! -update- is NULL");
			return 7;
		}

		if (searchFromSqlNameExist(user.getName()) == true
				&& (!u.get(0).getName().equals(user.getName()))) return  2;
		if (searchFromSqlLoginExist(user.getLogin()) == true
				&& (!u.get(0).getLogin().equals(user.getLogin()))) return 4;

		String queryUp = "UPDATE users SET users_id = ?, name = ?, login = ?, password = ?, role = ? WHERE users_id = " +
				userUsers_id + ";";
		try (final PreparedStatement upd = connection.prepareStatement(queryUp)) {

			upd.setBigDecimal(1, BigDecimal.valueOf(userUsers_id));

			if (userName != null && userName.length() > 0) {
				upd.setString(2, userName);
			} else {
				upd.setString(2, u.get(0).getName());
			}
			if (userLogin != null && userLogin.length() > 0) {
				upd.setString(3, userLogin);
			} else {
				upd.setString(3, u.get(0).getLogin());
			}
			if (userPassword != null && userPassword.length() > 0) {
				upd.setString(4, userPassword);
			} else {
				upd.setString(4, u.get(0).getPassword());
			}
			upd.setString(5, "0");
			upd.executeUpdate();
			System.out.println("UPDATE выполнено по users_id = " + userUsers_id);
		} catch (SQLException e) {
			System.out.println("ERROR!!! - UPDATE Connection failed...");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean userIsExist(String login, String password) {
		return false;
	}

	@Override
	public List<User> checkUserAndGetRole(String login, String password) {
		return null;
	}
}
