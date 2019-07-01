package servlet;
import bl.UserService;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static model.User.ROLE.ADMIN;

@WebServlet("/admin/update")
public class UpdateUserServlet extends HttpServlet {
	private UserService userService = new UserService();
	private String str = "(0-9A-Za-zА-Яа-я_)";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		User user = createUser(request);

		String textError = null;
		int errCode = userService.update(user);
		if (errCode == 2 || errCode == 4) {
			switch (errCode) {
				case 2: textError = "User with the same name  already exists " + str; break;
				case 4: textError = "User with the same login already exists " + str; break;
				default: break;
			}
			request.setAttribute("error", textError);
			request.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(request, response);
		} else {
			response.sendRedirect("/admin");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
//		if (session.getAttribute("role") != ADMIN) {
		User user = (User) request.getSession().getAttribute("mySess");
		if (user.getRole() != ADMIN) {
			response.sendRedirect("/");
		} else {
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("userMap",
					userService.selectOne(Long.valueOf(request.getParameter("users_id"))));
			request.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(request, response);
		}
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
