package cn.mldn.office.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/pages/*")
public class PagesFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
		if (session.getAttribute("admin") == null) {
			if (session.getAttribute("manager") == null) {
				if (session.getAttribute("emp") == null) { // 未登录用户
					request.setAttribute("msg", "您还未登录，请先登录！");
					request.setAttribute("url", "/login.jsp");
					request.getRequestDispatcher("/forward.jsp").forward(arg0,
							arg1);
				} else {
					arg2.doFilter(arg0, arg1);
				}
			} else {
				arg2.doFilter(arg0, arg1);
			}
		} else {
			arg2.doFilter(arg0, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
