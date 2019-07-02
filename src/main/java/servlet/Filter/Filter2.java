package servlet.Filter;
import model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static java.util.Objects.nonNull;

@WebFilter(filterName = "Filter2", urlPatterns = {"/login"})
public class Filter2 implements javax.servlet.Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(final ServletRequest request,
						 final ServletResponse response,
						 final FilterChain filterChain) throws IOException, ServletException {
		System.out.println("Filter2 = doFilter");
		HttpServletRequest req  = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		if (nonNull(session.getAttribute("mySess"))) {
			User u = (User) session.getAttribute("mySess");
			if (nonNull(u.getRole())) {
				User.ROLE role = u.getRole();
				switch (role) {
					case ADMIN:
						res.sendRedirect("/admin");
						break;
					case USER:
						res.sendRedirect("/users");
						break;
					default:
						res.sendRedirect("/login");
						break;
				}
			}
		} else {
			filterChain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {}
}



















/** 190702= */
//package servlet.Filter;
//import bl.UserService;
//import model.User;
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//import static java.util.Objects.nonNull;
//
//@WebFilter(filterName = "Filter2", urlPatterns = "/")
//public class Filter2 implements javax.servlet.Filter {
//	private UserService userService = new UserService();
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {}
//
//	@Override
//	public void doFilter(final ServletRequest request,
//						 final ServletResponse response,
//						 final FilterChain filterChain) throws IOException, ServletException {
//		final HttpServletRequest req  = (HttpServletRequest) request;
//		final HttpServletResponse res = (HttpServletResponse) response;
//		final String login	  = req.getParameter("login");
//		final String password = req.getParameter("password");
//
//		HttpSession session = req.getSession();
//
//		List<User> list = userService.checkUserAndGetRole(login, password);
//
//		if (nonNull(session)
//				&& nonNull(session.getAttribute("login"))
//				&& nonNull(session.getAttribute("password"))) {
//			final User.ROLE role = (User.ROLE) session.getAttribute("role");
//			moveToMenu(req, res, role);
//		} else if (list != null && list.size() > 0) {
//			User user = new User();
//			user.setUsers_id(list.get(0).getUsers_id());
//			user.setName(list.get(0).getName());
//			user.setLogin(list.get(0).getLogin());
//			user.setPassword(list.get(0).getPassword());
//			User.ROLE role =  list.get(0).getRole();
//			user.setRole(role);
//
//			session.setAttribute("mySess", user);
//			moveToMenu(req, res, role);
//		} else {
//			moveToMenu(req, res, User.ROLE.UNKNOWN);
//		}
//		filterChain.doFilter(request, response);
//	}
//
//	private void moveToMenu(final HttpServletRequest req,
//							final HttpServletResponse res,
//							final User.ROLE role) throws ServletException, IOException {
//		switch (role) {
//			case ADMIN: res.sendRedirect("/admin"); break;
//			case USER:  res.sendRedirect("/users"); break;
//			default: req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res); break;
//		}
//	}
//
//	@Override
//	public void destroy() {}
//}



/** old */
//package servlet.Filter;
//import bl.UserService;
//import model.User;
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//import static java.util.Objects.nonNull;
//
//// (filterName = "Filter", urlPatterns = {"*.html", "/games/*", "/blog/*"}, servletNames = "ViewCode")
//
//@WebFilter(filterName = "Filter", urlPatterns = {"/"})
//public class Filter implements javax.servlet.Filter {
//	private UserService userService = new UserService();
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		ServletContext context = config.getServletContext();
//
//		FilterRegistration registration2 = context.getFilterRegistration("Filter2");
//		registration2.addMappingForUrlPatterns(null, true, "*.html", "/games/*", "/blog/*");
//		registration2.addMappingForServletNames(null, true, "ViewCode");
//
//		FilterRegistration registration22 = context.getFilterRegistration("Filter22");
//		registration22.addMappingForUrlPatterns(null, true, "*.html", "/games/*", "/blog/*");
//		registration22.addMappingForServletNames(null, true, "ViewCode");
//	}
//
//	@Override
//	public void doFilter(final ServletRequest request,
//						 final ServletResponse response,
//						 final FilterChain filterChain) throws IOException, ServletException {
//		final HttpServletRequest req  = (HttpServletRequest) request;
//		final HttpServletResponse res = (HttpServletResponse) response;
//		final String login	  = req.getParameter("login");
//		final String password = req.getParameter("password");
//
//		HttpSession session = req.getSession();
//
//		List<User> list = userService.checkUserAndGetRole(login, password);
//
//		if (nonNull(session)
//				&& nonNull(session.getAttribute("login"))
//				&& nonNull(session.getAttribute("password"))) {
//			final User.ROLE role = (User.ROLE) session.getAttribute("role");
//			moveToMenu(req, res, role);
//		} else if (list != null && list.size() > 0) {
//			User.ROLE role =  list.get(0).getRole();
//			req.getSession().setAttribute("password", password);
//			req.getSession().setAttribute("login", login);
//			req.getSession().setAttribute("role", role);
//			moveToMenu(req, res, role);
//		} else {
//			moveToMenu(req, res, User.ROLE.UNKNOWN);
//		}
//	}
//
//	private void moveToMenu(final HttpServletRequest req,
//							final HttpServletResponse res,
//							final User.ROLE role) throws ServletException, IOException {
//		switch (role) {
//			case ADMIN: res.sendRedirect("/admin"); break;
//			case USER:  res.sendRedirect("/users"); break;
//			default: req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res); break;
////			default: res.sendRedirect("/login"); break;
//		}
//	}
//
//	@Override
//	public void destroy() {}
//}
