package servlet;
import bl.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
	private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        request.setCharacterEncoding("UTF-8");
		userService.delete(Long.valueOf(request.getParameter("users_id")));
        resp.sendRedirect(request.getContextPath() + "/");
    }
}
