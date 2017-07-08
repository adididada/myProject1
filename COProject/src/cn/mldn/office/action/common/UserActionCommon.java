package cn.mldn.office.action.common;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.User;
import cn.mldn.office.service.common.IUserServiceCommon;
import cn.mldn.util.action.AbstractAction;

@Repository
@ParentPackage("root")
@Namespace("/pages/jsp/common/user")
@Action("UserActionCommon")
@InterceptorRef("commonStack")
@SuppressWarnings("serial")
public class UserActionCommon extends AbstractAction {
	@Resource
	private IUserServiceCommon userServiceCommon;
	private User user = new User();

	public User getUser() {
		return user;
	}
	
	public void show() {
		try {
			User user = this.userServiceCommon.show(this.user.getUserid()) ;
			JSONObject obj = new JSONObject() ;
			obj.put("userid", user.getUserid()) ;
			obj.put("photo", user.getPhoto()) ;
			obj.put("name", user.getName()) ;
			obj.put("phone",user.getPhone()) ;
			obj.put("email", user.getEmail()) ;
			obj.put("lastlogin", super.formatDate(user.getLastlogin())) ;
			obj.put("lockflag", user.getLockflag()) ;
			obj.put("level", user.getLevel()) ;
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
