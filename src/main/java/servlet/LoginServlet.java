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
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserService();
		String login	= request.getParameter("login");
		String password	= request.getParameter("password");

		HttpSession session = request.getSession();

		List<User> list = userService.checkUserAndGetRole(login, password);

		if (list != null && list.size() > 0) {
			User user = new User();
			user.setUsers_id(list.get(0).getUsers_id());
			user.setName(list.get(0).getName());
			user.setLogin(list.get(0).getLogin());
			user.setPassword(list.get(0).getPassword());
			User.ROLE role =  list.get(0).getRole();
			user.setRole(role);
			session.setAttribute("mySess", user);
		}
		response.sendRedirect("/login");
	}
}


















/** 190702= */
//package servlet;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/login")
//public class LoginServlet extends HttpServlet {
//	@Override
//	protected void doGet(HttpServletRequest request,
//						 HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("LoginServlet = doGet");
//		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
//	}
//	@Override
//	protected void doPost(HttpServletRequest request,
//						  HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("LoginServlet = doPost");
//		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
//	}
//}