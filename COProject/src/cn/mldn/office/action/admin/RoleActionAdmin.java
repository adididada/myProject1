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

import cn.mldn.office.pojo.Groups;
import cn.mldn.office.pojo.Role;
import cn.mldn.office.service.admin.IRoleServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = { 
		@Result(name = "role.insert.page", location = "/pages/jsp/admin/role/admin_role_insert.jsp"),
		@Result(name = "role.list", location = "/pages/jsp/admin/role/admin_role_list.jsp") })
@Namespace("/pages/jsp/admin/role")
@Action("RoleActionAdmin")
public class RoleActionAdmin extends AbstractAction {
	private static String insertRule = "role.title:string";
	private static String updateRule = "role.rid:int|role.title:string";
	private Role role = new Role();
	private Integer[] gids; // 处理权限组编号，在增加的时候使用
	private String ugid ;	// 更新时使用的gid数据
	@Resource
	private IRoleServiceAdmin roleServiceAdmin;

	public void checkTitleInsert() {
		try {
			super.print(this.roleServiceAdmin.checkTitle(this.role.getTitle()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkTitleUpdate() {
		try {
			super.print(this.roleServiceAdmin.checkTitle(this.role.getTitle(),
					this.role.getRid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String insert() {
		Set<Groups> set = new HashSet<Groups>();
		for (int x = 0; x < this.gids.length; x++) {
			Groups gup = new Groups();
			gup.setGid(this.gids[x]);
			set.add(gup) ;
		}
		this.role.setGroupses(set);// 保存了角色与权限组的对应关系
		try {
			if (this.roleServiceAdmin.insert(this.role)) {
				super.setMsgAndUrl("insert.success.msg",
						"admin.role.insert.action");
			} else {
				super.setMsgAndUrl("insert.failure.msg",
						"admin.role.insert.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public String insertPre() {
		try {
			Map<String, Object> map = this.roleServiceAdmin.insertPre();
			super.getRequest().setAttribute("allGroups", map.get("allGroups"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "role.insert.page" ;
	}
	
	public void updatePre() {
		try {
			Map<String, Object> map = this.roleServiceAdmin.updatePre(this.role.getRid());
			Role role = (Role) map.get("role") ;
			Map<Integer,Boolean> rgids = (Map<Integer,Boolean>) map.get("gids") ;
			List<Groups> gups = (List<Groups>) map.get("allGroups") ;
			// 准备出JSON数据进行数据的返回
			JSONObject obj = new JSONObject() ;
			obj.put("rid", role.getRid()) ;
			obj.put("title", role.getTitle()) ;
			obj.put("note", role.getNote()) ;
			JSONArray array = new JSONArray() ;
			Iterator<Groups> iter = gups.iterator() ;
			while (iter.hasNext()) {
				JSONObject temp = new JSONObject() ;
				Groups g = iter.next() ;
				temp.put("gid", g.getGid()) ;
				temp.put("title", g.getTitle()) ;
				if (rgids.get(g.getGid()) != null) {
					temp.put("ckd", "checked") ;
				} else {
					temp.put("ckd", "") ;
				}
				array.add(temp) ;
			}
			obj.put("groups", array) ;
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		Set<Groups> set = new HashSet<Groups>() ;
		if (this.ugid != null) {
			String result [] = this.ugid.split("\\|") ;
			for (int x = 0 ; x < result.length ; x ++) {
				Groups g = new Groups() ;
				g.setGid(Integer.parseInt(result[x]));
				set.add(g) ;
			}
		}
		this.role.setGroupses(set);
		try {
			super.print(this.roleServiceAdmin.update(this.role));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	public String list() {
		try {
			Map<String, Object> map = this.roleServiceAdmin
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("roleCount"), "admin.role.split.url",
					null, null);
			super.getRequest()
					.setAttribute("allRoles", map.get("allRoles"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "role.list";
	}

	public void setGids(Integer[] gids) {
		this.gids = gids;
	}

	public Role getRole() {
		return role;
	}
	public void setUgid(String ugid) {
		this.ugid = ugid;
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "角色名称:title";
	}

	@Override
	public String getTypeName() {
		return "角色";
	}

}
