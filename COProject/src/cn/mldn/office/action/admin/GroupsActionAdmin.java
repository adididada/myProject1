package cn.mldn.office.action.admin;

import java.util.Iterator;
import java.util.Map;

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
import cn.mldn.office.service.admin.IGroupsServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = { @Result(name = "groups.list", location = "/pages/jsp/admin/role/admin_groups_list.jsp") })
@Namespace("/pages/jsp/admin/role")
@Action("GroupsActionAdmin")
public class GroupsActionAdmin extends AbstractAction {
	private static String updateRule = "groups.gid:int|groups.title:string";
	private static String showRule = "groups.gid:int";
	@Resource
	private IGroupsServiceAdmin groupsServiceAdmin ;
	private Groups groups = new Groups() ;
	public Groups getGroups() {
		return groups;
	}
	public String list() {
		try {
			Map<String, Object> map = this.groupsServiceAdmin
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("groupsCount"), "admin.groups.split.url",
					null, null);
			super.getRequest()
					.setAttribute("allGroups", map.get("allGroups"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "groups.list" ;
	}
	public void update() {
		try {
			super.print(this.groupsServiceAdmin.update(this.groups));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void show() {
		try {
			Groups gup = this.groupsServiceAdmin.show(this.groups.getGid()) ;
			JSONObject obj = new JSONObject() ;
			obj.put("gid", gup.getGid()) ;
			obj.put("title", gup.getTitle()) ;
			obj.put("note", gup.getNote()) ;
			JSONArray array = new JSONArray() ; 
			Iterator<cn.mldn.office.pojo.Action> iter = gup.getActions().iterator() ;
			while (iter.hasNext()) {
				cn.mldn.office.pojo.Action act = iter.next() ;
				JSONObject temp = new JSONObject() ;
				temp.put("actid", act.getActid()) ;
				temp.put("title", act.getTitle()) ;
				temp.put("url", act.getUrl()) ;
				array.add(temp) ;
			}
			obj.put("actions", array) ;
			super.print(obj); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String getTypeName() {
		return "权限组";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "权限组标题:title";
	}

}
