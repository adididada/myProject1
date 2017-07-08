package cn.mldn.office.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class ManagerInterceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> map = invocation.getInvocationContext()
				.getSession();
		ServletActionContext.getRequest().setAttribute("msg", "您不具备操作资格，请重新登录！");
		ServletActionContext.getRequest().setAttribute("url", "/login.jsp");
		if (map.get("manager") == null) { // 现在没有admin的属性
			return "forward.page" ;
		} else {
			return invocation.invoke();
		}
	}

}
