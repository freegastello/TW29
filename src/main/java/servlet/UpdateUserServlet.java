package servlet;
import bl.UserService;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class UpdateUserServlet extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		User user = createUser(request);
		userService.update(user);
		response.sendRedirect("/");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("userMap", userService.selectOne(Long.valueOf(request.getParameter("users_id"))));
		request.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(request, response);
	}

	private User createUser(HttpServletRequest request) {
		User user = new User();

		user.setUsers_id(Long.valueOf(request.getParameter("users_id")));

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
