package cn.mldn.office.action.admin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Role;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.IUserServiceAdmin;
import cn.mldn.util.MD5Code;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "user.insert", location = "/pages/jsp/admin/user/admin_user_insert.jsp"),
		@Result(name = "user.show", location = "/pages/jsp/admin/user/admin_user_show.jsp"),
		@Result(name = "user.list.active", location = "/pages/jsp/admin/user/admin_user_active_list.jsp"),
		@Result(name = "user.list.lock", location = "/pages/jsp/admin/user/admin_user_lock_list.jsp"),
		@Result(name = "insertVF", location = "/pages/jsp/admin/user/admin_user_insert.jsp"),
		@Result(name = "updateVF", location = "/pages/jsp/admin/user/UserActionAdmin!list.action", type = "redirectAction") })
@Namespace("/pages/jsp/admin/user")
@Action("UserActionAdmin")
public class UserActionAdmin extends AbstractAction {
	private static String insertRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int|user.level:int";
	private static String updateRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int|user.level:int";
	private String ids;
	@Resource
	private IUserServiceAdmin userServiceAdmin;

	private User user = new User();
	private Role role = new Role();

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String insertPre() {
		return "user.insert" ;
	}
	public String insert() {
		this.user
				.setPassword(new MD5Code().getMD5ofStr(this.user.getPassword()));
		this.user.setRole(this.role);// 设置用户的角色信息
		User admin = (User) super.getSession().getAttribute("admin");
		try {
			if (this.userServiceAdmin.insert(user, admin.getUserid())) {
				super.setMsgAndUrl("insert.success.msg",
						"admin.user.insert.page");
			} else {
				super.setMsgAndUrl("insert.failure.msg",
						"admin.user.insert.page");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public void checkUserid() { // 查看用户的编号是否可以使用
		try {
			super.print(this.userServiceAdmin.checkUser(this.user.getUserid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String listActive() {
		try {
			Map<String, Object> map = this.userServiceAdmin
					.list(0,super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("userCount"), "admin.admin.split.url",
					null, null);
			super.getRequest().setAttribute("allUsers", map.get("allUsers"));
			super.getRequest().setAttribute("allRoles", map.get("allRoles"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user.list.active";
	}
	public String listLock() {
		try {
			Map<String, Object> map = this.userServiceAdmin
					.list(1,super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("userCount"), "admin.admin.split.url",
					null, null);
			super.getRequest().setAttribute("allUsers", map.get("allUsers"));
			super.getRequest().setAttribute("allRoles", map.get("allRoles"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user.list.lock";
	}
	public void updateRole() { // 更新管理员的角色
		try {
			this.user.setRole(this.role);
			super.print(this.userServiceAdmin.updateRole(this.user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePassword() {
		try {
			this.user.setPassword(new MD5Code().getMD5ofStr(this.user
					.getPassword()));
			super.print(this.userServiceAdmin.updatePassword(this.user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateInfo() { // 更新基础信息
		try {
			this.user.setRole(this.role);
			super.print(this.userServiceAdmin.update(this.user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePre() { // 数据修改前的查询操作
		try {
			Map<String, Object> map = this.userServiceAdmin
					.updatePre(this.user.getUserid());
			User u = (User) map.get("user");
			JSONObject obj = new JSONObject();
			obj.put("userid", u.getUserid());
			obj.put("name", u.getName());
			obj.put("phone", u.getPhone());
			obj.put("email", u.getEmail());
			obj.put("level", u.getLevel());
			obj.put("photo", u.getPhoto());
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String updateActive() {
		Set<String> set = new HashSet<String>();
		String result[] = this.ids.split("\\|");
		for (int x = 0; x < result.length; x++) {
			set.add(result[x]);
		}
		try {
			if (this.userServiceAdmin.updateLock(set,0)) {
				super.setMsgAndUrl("update.success.msg",
						"admin.user.list.lock.action");
			} else {
				super.setMsgAndUrl("update.failure.msg",
						"admin.user.list.lock.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}
	
	public String updateLock() {
		Set<String> set = new HashSet<String>();
		String result[] = this.ids.split("\\|");
		for (int x = 0; x < result.length; x++) {
			set.add(result[x]);
		}
		try {
			if (this.userServiceAdmin.updateLock(set,1)) {
				super.setMsgAndUrl("update.success.msg",
						"admin.user.list.active.action");
			} else {
				super.setMsgAndUrl("update.failure.msg",
						"admin.user.list.lock.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public String show() {
		try {
			super.getRequest().setAttribute("user",
					this.userServiceAdmin.show(this.user.getUserid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user.show";
	}

	public Role getRole() {
		return role;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getTypeName() {
		return "用户";
	}

	@Override
	public String getDefaultColumn() {
		return "userid";
	}

	@Override
	public String getColumnData() {
		return "用户ID:userid|真实姓名:name|电话号码:phone|EMAIL:email";
	}

}
