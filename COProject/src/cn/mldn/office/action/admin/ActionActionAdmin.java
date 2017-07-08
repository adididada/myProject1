package cn.mldn.office.action.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.service.admin.IActionServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = { @Result(name = "action.list", location = "/pages/jsp/admin/role/admin_action_list.jsp") })
@Namespace("/pages/jsp/admin/role")
@Action("ActionActionAdmin")
public class ActionActionAdmin extends AbstractAction {
	private static String updateRule = "action.title:string|action.url:string";
	private cn.mldn.office.pojo.Action action = new cn.mldn.office.pojo.Action();
	@Resource
	private IActionServiceAdmin actionServiceAdmin;

	public cn.mldn.office.pojo.Action getAction() {
		return this.action;
	}

	public String list() { // 数据分页显示
		try {
			Map<String, Object> map = this.actionServiceAdmin
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("actionCount"), "admin.action.split.url",
					null, null);
			super.getRequest()
					.setAttribute("allActions", map.get("allActions"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "action.list";
	}

	public void update() {
		try {
			super.print(this.actionServiceAdmin.update(this.action));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTypeName() {
		return "权限";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "权限标题:title|权限路径:url";
	}

}
