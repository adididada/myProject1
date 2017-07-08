package cn.mldn.office.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Tasktype;
import cn.mldn.office.service.admin.ITasktypeServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "tasktype.list", location = "/pages/jsp/admin/task/admin_task_type.jsp"),
		@Result(name = "insertVF", location = "/pages/jsp/admin/task/TasktypeActionAdmin!list.action", type = "redirectAction"),
		@Result(name = "updateVF", location = "/pages/jsp/admin/task/TasktypeActionAdmin!list.action", type = "redirectAction") })
@Namespace("/pages/jsp/admin/task")
@Action("TasktypeActionAdmin")
public class TasktypeActionAdmin extends AbstractAction {
	private static String insertRule = "tasktype.title:string";
	private static String updateRule = "tasktype.ttid:int|tasktype.title|string";
	@Resource
	private ITasktypeServiceAdmin tasktypeSeviceAdmin;
	private Tasktype tasktype = new Tasktype();

	public Tasktype getTasktype() {
		return tasktype;
	}

	public void insert() {
		// 必须要考虑到文档类型编号的取得
		JSONObject obj = new JSONObject();
		try {
			obj.put("flag", this.tasktypeSeviceAdmin.insert(this.tasktype));
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.put("ttid", this.tasktype.getTtid());
		super.print(obj);
	}

	public String list() { // 数据分页显示
		try {
			super.getRequest().setAttribute("allTasktypes",
					this.tasktypeSeviceAdmin.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tasktype.list";
	}

	public void update() {
		try {
			super.print(this.tasktypeSeviceAdmin.update(this.tasktype));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTypeName() {
		return "任务类型";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "类型标题:title";
	}

}
