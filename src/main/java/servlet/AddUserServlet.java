package servlet;
import bl.UserService;
import model.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {
	private UserService userService = new UserService();
	private String str = "(0-9A-Za-zА-Яа-я_)";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String text = null;
		User user = createUser(request);

//		System.out.println("AddUserServlet request = " + request.getParameter("name"));

		try {
			int x = userService.errorCheck(user);
			if (x == 1) {
				text = "Enter your name " + str;
			} else if (x == 2) {
				text = "User with the same name already exists " + str;
			} else if (x == 3) {
				text = "Enter your login " + str;
			} else if (x == 4) {
				text = "Enter your password " + str;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!userService.addUser(user)) {
				response.sendRedirect(
						"/?name=" + request.getParameter("name")
						+ "&login="		 + request.getParameter("login")
						+ "&password="	 + request.getParameter("password")
						+ "&message="	 + text);
			} else {
				response.sendRedirect("/");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private User createUser(HttpServletRequest request) {
		User user = new User();

		if (request.getParameter("name") != null && request.getParameter("name").length() > 0) {
			user.setName(request.getParameter("name"));
		} else {
			user.setName(null);
		}
		if (request.getParameter("login") != null && request.getParameter("login").length() > 0) {
			user.setLogin(request.getParameter("login"));
		} else {
			user.setLogin(null);
		}
		if (request.getParameter("password") != null && request.getParameter("password").length() > 0) {
			user.setPassword(request.getParameter("password"));
		} else {
			user.setPassword(null);
		}
		return user;
	}

}
