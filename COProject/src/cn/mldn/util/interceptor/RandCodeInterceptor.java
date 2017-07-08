package cn.mldn.util.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class RandCodeInterceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String rand = (String) invocation.getInvocationContext().getSession().get("rand");
		String code = ServletActionContext.getRequest().getParameter("code");
		if (code != null) { // 表示有验证码输入
			if (code.equalsIgnoreCase(rand)) { // 验证码通过验证
				return invocation.invoke(); // 继续向下执行
			} else {
				ServletActionContext.getRequest().setAttribute("msg",
						"验证码输入错误，请重新执行操作！");
				ServletActionContext.getRequest().setAttribute("url",
						"/login.jsp");
				return "forward.page"; // 返回的路径
			}
		} else {
			ServletActionContext.getRequest().setAttribute("msg",
					"验证码输入错误，请重新执行操作！");
			ServletActionContext.getRequest().setAttribute("url", "/login.jsp");
			return "forward.page"; // 返回的路径
		}
	}
}
