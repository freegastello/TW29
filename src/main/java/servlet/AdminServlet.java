package servlet;
import bl.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setAttribute("error",		request.getParameter("message"));
		request.setAttribute("name",		request.getParameter("name"));
		request.setAttribute("login",		request.getParameter("login"));
		request.setAttribute("password",	request.getParameter("password"));
		request.setAttribute("role",		request.getParameter("role"));
		request.setAttribute("users",		userService.getAll());
		request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
	}
}













//// Check there is a user in db
//		if (userService.userIsExist("1", "1")) {
//			System.out.println("-AdminServlet- This name is already there, name");
//		} else {
//			System.out.println("-AdminServlet- not found");
//		}

//// Checking user and get role
//		List<User> list = userService.checkUserAndGetRole("admin", "admin");
//		if (list.size() > 0) {
//			User.ROLE rol =  list.get(0).getRole();
//			System.out.println("-AdminServlet- This name is already there, role = " + rol);
//		} else {
//			System.out.println("-AdminServlet- not found");
//		}