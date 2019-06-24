package servlet;

import bl.UserService;
import model.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {
	private UserService userService = new UserService();
	public static String mess;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		User user = createUser(request);
		if (!userService.addUser(user)) {
			response.sendRedirect("/?name="
					+ request.getParameter("name")
					+ "&login=" + request.getParameter("login")
					+ "&password=" + request.getParameter("password"));
		} else {
			response.sendRedirect("/");
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
