package DAO;
import model.User;
import servlet.AddUserServlet;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDao {
	private static Connection connection;
	private String reg = "[0-9A-Za-zА-Яа-я_]+";
	private static String str = "0-9A-Za-zА-Яа-я_";
	public UserDAOImpl() {getConnect();}

	private void getConnect() {
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

	public List<User> selectOne(Long users_id) {
		List<User> arrayList = new ArrayList<>();
		String query = "SELECT * FROM users WHERE users_id = " + users_id + ";";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String name = resultSet.getString("name");
				String login = resultSet.getString("login");
				String password = resultSet.getString(4);
				arrayList.add(new User(users_id, name, login, password));
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
			while (resultSet.next()) {
				Long users_id = resultSet.getLong("users_id");
				String name = resultSet.getString("name");
				String login = resultSet.getString("login");
				String password = resultSet.getString(4);
				newUsers = new User(users_id, name, login, password);
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

	public boolean errorCheck(User user) {
		if (user.getName() == null || (!user.getName().matches(reg))) {
			AddUserServlet.mess = "Enter your name " + str;
			return false;
		}
		boolean bool = searchFromSqlNameExist(user.getName());
		if (bool) {
			AddUserServlet.mess = "User with the same name already exists";
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
		return true;
	}

	public boolean addUser(User user) {
		if (!errorCheck(user)) {return false;}
		String userName = user.getName();
		String userLogin = user.getLogin();
		String userPassword = user.getPassword();

		String queryInsert = "INSERT INTO users (name, login, password) VALUE (?, ?, ?);";
		try (final PreparedStatement addInsert = connection.prepareStatement(queryInsert)) {

//			addInsert.setBigDecimal(1, null);

			if (userName != null && userName.length() > 0) {
				addInsert.setString(1, userName);
			} else {
				System.out.println("ERROR!!! addUser -> INSERT name не введён. Вставка не произведена.");
				return false;
			}
			if (userLogin != null && userLogin.length() > 0) {
				addInsert.setString(2, userLogin);
			} else {
				System.out.println("ERROR!!! addUser -> INSERT login не введён. Вставка не произведена.");
				return false;
			}
			if (userPassword != null && userPassword.length() > 0) {
				addInsert.setString(3, userPassword);
			} else {
				System.out.println("ERROR!!! addUser -> INSERT password не введён. Вставка не произведена.");
				return false;
			}
			int num = addInsert.executeUpdate();
			System.out.println("-addUser- Произведено = " + num + " вставок");
		} catch (SQLException e) {
			System.out.println("ERROR!!! -addUser- SELECT + INSERT");
			e.printStackTrace();
		}
		return true;
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

	public void update(User user) {
		if (!user.getName().matches("[\\w]+")) return;
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
				return;
			}
		} else {
			System.out.println("ERROR!!! -update- is NULL");
			return;
		}

		String queryUp = "UPDATE users SET users_id = (?)," +
				" name = (?)," +
				" login = (?)," +
				" password = (?) WHERE users_id = " +
				userUsers_id + ";";
		try (final PreparedStatement upd = connection.prepareStatement(queryUp)) {
//			if(userUsers_id != null) {
				upd.setBigDecimal(1, BigDecimal.valueOf(userUsers_id));
//			} else {
//				System.out.println("ERROR!!! getUpdate -> UPDATE users_id = " + userUsers_id);
//				return;
//			}
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
			upd.executeUpdate();
			System.out.println("UPDATE выполнено по users_id = " + userUsers_id);
		} catch (SQLException e) {
			System.out.println("ERROR!!! - UPDATE Connection failed...");
			e.printStackTrace();
		}
	}
}
