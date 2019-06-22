package servlet;
import bl.UserService;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {
	private UserService userService = new UserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User user = new User();
		user.setName(request.getParameter("name"));
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		userService.addUser(user);
		response.sendRedirect(request.getContextPath() + "/");
	}
}
