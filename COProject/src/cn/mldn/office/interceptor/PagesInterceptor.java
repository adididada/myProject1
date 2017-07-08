package cn.mldn.office.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class PagesInterceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 进入到pages目录下的可能有三种保存的信息：admin、manager、emp
		Map<String, Object> map = invocation.getInvocationContext()
				.getSession();
		ServletActionContext.getRequest().setAttribute("msg", "您还未登录，请先登录！");
		ServletActionContext.getRequest().setAttribute("url", "/login.jsp");
		if (map.get("admin") == null) { // 现在没有admin的属性
			if (map.get("manager") == null) { // 现在没有manager属性
				if (map.get("emp") == null) { // 没有emp属性
					return "forward.page"; // 重新登录
				} else {
					return invocation.invoke();
				}
			} else {
				return invocation.invoke();
			}
		} else {
			return invocation.invoke();
		}
	}

}
