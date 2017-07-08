package cn.mldn.office.action.admin;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Project;
import cn.mldn.office.pojo.Task;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.ITaskServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "task.list", location = "/pages/jsp/admin/task/admin_task_list.jsp") })
@Namespace("/pages/jsp/admin/task")
@Action("TaskActionAdmin")
public class TaskActionAdmin extends AbstractAction {
	@Resource
	private ITaskServiceAdmin taskServiceAdmin ;
	private Project project = new Project() ;	// 任务的所属项目
	public Project getProject() {
		return project;
	}
	public String list() {
		try {
			Map<String, Object> map = this.taskServiceAdmin
					.listByProject(this.project.getProid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("taskCount"),
					"admin.task.split.url", "project.proid", String.valueOf(this.project.getProid()));
			super.getRequest()
					.setAttribute("allTasks", map.get("allTasks"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task.list" ;
	}

	@Override
	public String getTypeName() {
		return "项目任务";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "任务标题:title";
	}

}
