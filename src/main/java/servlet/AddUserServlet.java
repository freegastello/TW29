package servlet;
import bl.UserService;
import model.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {
	private UserService userService = new UserService();
	private String str = "(0-9A-Za-zА-Яа-я_)";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		User user = createUser(request);

		String textError = null;
		int errCode = userService.addUser(user);
		if (errCode != 0) {
			switch (errCode) {
				case 1: textError = "Enter your name" + str; break;
				case 2: textError = "User with the same name already exists" + str; break;
				case 3: textError = "Enter your login" + str; break;
				case 4: textError = "User with the same login already exists" + str; break;
				case 5: textError = "Enter your password" + str; break;
				default: break;
			}
			response.sendRedirect(
					"/admin?name=" + URLEncoder.encode(request.getParameter("name"), "UTF-8")
					+ "&login="		 + URLEncoder.encode(request.getParameter("login"), "UTF-8")
					+ "&password="	 + URLEncoder.encode(request.getParameter("password"), "UTF-8")
					+ "&message="	 + URLEncoder.encode(textError, "UTF-8"));
		} else {
			response.sendRedirect("/admin");
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
		user.setRole(User.ROLE.USER);
		return user;
	}
}
