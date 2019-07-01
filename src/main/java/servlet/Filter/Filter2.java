//package servlet.Filter;
//import bl.UserService;
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static model.User.ROLE.ADMIN;
//
//@WebFilter(filterName = "Filter2", urlPatterns = "/admin")
//public class Filter2 extends AbstractFilter implements javax.servlet.Filter {
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
//		if (req.getSession().getAttribute("role") != ADMIN) {
//			res.sendRedirect("/admin");
//		}
//		filterChain.doFilter(request, response);
//	}
//
//	@Override
//	public void destroy() {}
//}








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
