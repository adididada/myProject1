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
import cn.mldn.office.service.admin.IAdminServiceAdmin;
import cn.mldn.util.MD5Code;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "admin.show", location = "/pages/jsp/admin/user/admin_user_show.jsp"),
		@Result(name = "admin.insert", location = "/pages/jsp/admin/admin/admin_admin_insert.jsp"),
		@Result(name = "admin.list", location = "/pages/jsp/admin/admin/admin_admin_list.jsp"),
		@Result(name = "insertVF", location = "/pages/jsp/admin/admin/AdminActionAdmin!insertPre.action", type = "redirectAction"),
		@Result(name = "updateVF", location = "/pages/jsp/admin/admin/AdminActionAdmin!list.action", type = "redirectAction") })
@Namespace("/pages/jsp/admin/admin")
@Action("AdminActionAdmin")
public class AdminActionAdmin extends AbstractAction {
	private static String insertRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int";
	private static String updateRule = "user.userid:string|user.name:string|user.phone:string|user.email:string|role.rid:int";
	private String ids;
	@Resource
	private IAdminServiceAdmin adminServiceAdmin;

	private User user = new User();
	private Role role = new Role();

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String insertPre() {
		try {
			Map<String, Object> map = this.adminServiceAdmin.insertPre();
			super.getRequest().setAttribute("allRoles", map.get("allRoles"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin.insert";
	}

	public String insert() {
		this.user
				.setPassword(new MD5Code().getMD5ofStr(this.user.getPassword()));
		this.user.setRole(this.role);// 设置用户的角色信息
		User admin = (User) super.getSession().getAttribute("admin");
		try {
			if (this.adminServiceAdmin.insert(user, admin.getUserid())) {
				super.setMsgAndUrl("insert.success.msg",
						"admin.admin.insert.action");
			} else {
				super.setMsgAndUrl("insert.failure.msg",
						"admin.admin.insert.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public void checkUserid() { // 查看用户的编号是否可以使用
		try {
			super.print(this.adminServiceAdmin.checkUser(this.user.getUserid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String list() {
		try {
			Map<String, Object> map = this.adminServiceAdmin
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("userCount"), "admin.admin.split.url",
					null, null);
			super.getRequest().setAttribute("allUsers", map.get("allUsers"));
			super.getRequest().setAttribute("allRoles", map.get("allRoles"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin.list";
	}

	public void updateRole() { // 更新管理员的角色
		try {
			this.user.setRole(this.role);
			super.print(this.adminServiceAdmin.updateRole(this.user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePassword() {
		try {
			this.user.setPassword(new MD5Code().getMD5ofStr(this.user
					.getPassword()));
			super.print(this.adminServiceAdmin.updatePassword(this.user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateInfo() { // 更新基础信息
		try {
			this.user.setRole(this.role);
			super.print(this.adminServiceAdmin.update(this.user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePre() { // 数据修改前的查询操作
		try {
			Map<String, Object> map = this.adminServiceAdmin
					.updatePre(this.user.getUserid());
			User u = (User) map.get("user");
			List<Role> roles = (List<Role>) map.get("allRoles");
			JSONObject obj = new JSONObject();
			obj.put("userid", u.getUserid());
			obj.put("name", u.getName());
			obj.put("phone", u.getPhone());
			obj.put("email", u.getEmail());
			obj.put("rid", u.getRole().getRid());
			obj.put("photo", u.getPhoto());
			JSONArray array = new JSONArray();
			Iterator<Role> iter = roles.iterator();
			while (iter.hasNext()) {
				Role r = iter.next();
				JSONObject temp = new JSONObject();
				temp.put("rid", r.getRid());
				temp.put("title", r.getTitle());
				array.add(temp);
			}
			obj.put("roles", array);
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String delete() {
		Set<String> set = new HashSet<String>();
		String result[] = this.ids.split("\\|");
		for (int x = 0; x < result.length; x++) {
			set.add(result[x]);
		}
		try {
			if (this.adminServiceAdmin.delete(set)) {
				super.setMsgAndUrl("delete.success.msg",
						"admin.admin.list.action");
			} else {
				super.setMsgAndUrl("delete.failure.msg",
						"admin.admin.list.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public String show() {
		try {
			super.getRequest().setAttribute("user",
					this.adminServiceAdmin.show(this.user.getUserid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin.show";
	}

	public Role getRole() {
		return role;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getTypeName() {
		return "管理员";
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
