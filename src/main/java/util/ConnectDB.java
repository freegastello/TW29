package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection connection;

	public ConnectDB() {
		getConnect();
	}

	private void getConnect() {
		if (driverValidate()) {
			try {
				String url = "jdbc:mysql://localhost:3306/crud?useSSL=false";
				String user = "admin";
				String pass = "admin";
				connection = DriverManager.getConnection(url, user, pass);
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
}
