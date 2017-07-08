package cn.mldn.office.action.common;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.User;
import cn.mldn.office.service.common.IUserServiceCommon;
import cn.mldn.util.MD5Code;
import cn.mldn.util.action.AbstractAction;

@Repository
@ParentPackage("root")
@Namespace("/")
@Action("UserLogin")
@InterceptorRef("loginStack")
@Results(value = { @Result(name = "loginVF", location = "/login.jsp") })
@SuppressWarnings("serial")
public class UserLoginActionCommon extends AbstractAction {
	private String loginRule = "user.userid:string|user.password:string";

	@Resource
	private IUserServiceCommon userServiceCommon;
	private User user = new User();

	public User getUser() {
		return user;
	}

	public String login() {
		try {
			super.getSession().removeAttribute("admin");
			super.getSession().removeAttribute("manager");
			super.getSession().removeAttribute("emp");
			User resultUser = this.userServiceCommon.login(
					this.user.getUserid(),
					new MD5Code().getMD5ofStr(this.user.getPassword()));
			if (resultUser != null) { // 用户登录成功，信息可以正常返回
				// 在session中进行保存，在保存的时候要分好形式，根据级别保存
				// 如果级别是0或者是1，那么就是管理员
				if (resultUser.getLevel() == 0 || resultUser.getLevel() == 1) {
					super.getSession().setAttribute("admin", resultUser);
					super.setMsgAndUrl("user.login.success", "admin.index.page");
				} else if (resultUser.getLevel() == 2) {
					super.getSession().setAttribute("manager", resultUser);
					super.setMsgAndUrl("user.login.success",
							"manager.index.page");
				} else if (resultUser.getLevel() == 3) {
					super.getSession().setAttribute("emp", resultUser);
					super.setMsgAndUrl("user.login.success", "emp.index.page");
				}
			} else { // 登录失败
				super.setMsgAndUrl("user.login.failure", "login.page");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	@Override
	public String getTypeName() {
		return null;
	}

	@Override
	public String getDefaultColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnData() {
		// TODO Auto-generated method stub
		return null;
	}
}
