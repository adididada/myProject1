package cn.mldn.office.action.emp;

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
import cn.mldn.office.service.emp.ITaskServiceEmp;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("empStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "task.list", location = "/pages/jsp/emp/task/emp_task_list.jsp") ,
		@Result(name = "task.admin", location = "/pages/jsp/emp/task/emp_task_admin.jsp") })
@Namespace("/pages/jsp/emp/task")
@Action("TaskActionEmp")
public class TaskActionEmp extends AbstractAction {
	@Resource
	private ITaskServiceEmp taskServiceEmp ;
	private Task task = new Task() ;
	public Task getTask() {
		return task;
	}
	private Project project = new Project() ;	// 任务的所属项目
	public Project getProject() {
		return project;
	}
	public void show() {
		try {
			User emp = (User) super.getSession().getAttribute("emp") ;
			Task task = this.taskServiceEmp.show(this.task.getTid(),emp.getUserid()) ;
			JSONObject obj = new JSONObject() ;
			obj.put("tid", task.getTid()) ;
			obj.put("title", task.getTitle()) ;
			obj.put("creator", task.getUserByCreator().getUserid()) ;
			obj.put("receiver", task.getUserByReceiver().getUserid()) ;
			if (task.getUserByCanceler() == null){
				obj.put("cancel", "") ;
			} else {
				obj.put("cancel", task.getUserByCanceler().getUserid()) ;
			}
			obj.put("type", task.getTasktype().getTitle()) ;
			obj.put("project", task.getProject().getTitle()) ;
			obj.put("note", task.getNote()) ;
			obj.put("rnote", task.getRnote()) ;
			obj.put("cnote", task.getCnote()) ;
			obj.put("pri", task.getPriority()) ;
			obj.put("createdate", super.formatDate(task.getCreatedate())) ;
			obj.put("expiredate", super.formatDate(task.getExpiredate())) ;
			obj.put("lastupdatedate", super.formatDate(task.getLastupdatedate())) ;
			obj.put("es", task.getEstimate()) ;
			obj.put("ep", task.getExpend()) ;
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateFinish() {
		User emp = (User) super.getSession().getAttribute("emp") ;
		this.task.setUserByReceiver(emp);// 当前的用户信息
		try {
			super.print(this.taskServiceEmp.updateFinish(this.task));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String admin() {
		try {
			User emp = (User) super.getSession().getAttribute("emp") ;
			Map<String, Object> map = this.taskServiceEmp
					.listByReceiver(emp.getUserid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("taskCount"),
					"emp.task.admin.split.url", "project.proid", String.valueOf(this.project.getProid()));
			super.getRequest()
					.setAttribute("allTasks", map.get("allTasks"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task.admin" ;
	}
	
	public String list() {
		try {
			Map<String, Object> map = this.taskServiceEmp
					.listByProject(this.project.getProid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.getRequest().setAttribute("project", map.get("project"));
			super.handleSplit(map.get("taskCount"),
					"emp.task.split.url", "project.proid", String.valueOf(this.project.getProid()));
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
