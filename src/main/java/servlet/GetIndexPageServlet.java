package servlet;
import bl.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class GetIndexPageServlet extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (AddUserServlet.mess != null) {
			request.setAttribute("error", AddUserServlet.mess);
			AddUserServlet.mess = null;
			request.setAttribute("name",		request.getParameter("name"));
			request.setAttribute("login",		request.getParameter("login"));
			request.setAttribute("password",	request.getParameter("password"));
		}
		request.setAttribute("users", userService.getAll());
		request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
	}
}
