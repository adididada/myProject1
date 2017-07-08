package cn.mldn.office.action.common;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Repository;

import cn.mldn.util.action.AbstractAction;

@Repository
@ParentPackage("root")
@Namespace("/")
@Action("UserLogout")
@SuppressWarnings("serial")
public class UserLogoutActionCommon extends AbstractAction {
	public String logout() {
		super.getSession().invalidate();
		super.setMsgAndUrl("user.logout.msg", "login.page");
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
