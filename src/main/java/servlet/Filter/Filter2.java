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
	public void doFilter(final ServletRequest request,
						 final ServletResponse response,
						 final FilterChain filterChain) throws IOException, ServletException {
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
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
}
