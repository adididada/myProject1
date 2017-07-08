package cn.mldn.util.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class EncodingInterceptor extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		return invocation.invoke(); // 继续向下执行
	}
}
