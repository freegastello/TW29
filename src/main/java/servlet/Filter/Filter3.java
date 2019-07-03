package servlet.Filter;
import model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static java.util.Objects.nonNull;
import static model.User.ROLE.ADMIN;

@WebFilter(filterName = "Filter3", urlPatterns = {"/admin"})
public class Filter3 implements javax.servlet.Filter {

	@Override
	public void doFilter(final ServletRequest request,
						 final ServletResponse response,
						 final FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req  = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if (nonNull(session.getAttribute("mySess"))) {
			User u = (User) session.getAttribute("mySess");
			User.ROLE role = u.getRole();
			if (!ADMIN.equals(role)) {
				res.sendRedirect("/logout");
			} else {
				filterChain.doFilter(req, res);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
}
