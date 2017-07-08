package cn.mldn.office.action.manager;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Project;
import cn.mldn.office.pojo.Task;
import cn.mldn.office.pojo.Tasktype;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.manager.ITaskServiceManager;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("managerStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "task.insert", location = "/pages/jsp/manager/task/manager_task_insert.jsp") ,
		@Result(name = "task.update", location = "/pages/jsp/manager/task/manager_task_update.jsp") ,
		@Result(name = "task.list", location = "/pages/jsp/manager/task/manager_task_list.jsp") ,
		@Result(name = "task.admin", location = "/pages/jsp/manager/task/manager_task_admin.jsp") ,
		@Result(name = "updateVF", location = "/pages/jsp/manager/task/TaskActionManager!list.action",type="redirectAction") ,
		@Result(name = "insertVF", location = "/pages/jsp/manager/task/TaskActionManager!insertPre.action",type="redirectAction")})
@Namespace("/pages/jsp/manager/task")
@Action("TaskActionManager")
public class TaskActionManager extends AbstractAction {
	private static String insertRule = "task.title:string|task.estimate:int|task.expiredate:date|task.note:string" ; 
	private static String updateRule = "task.tid:int|task.title:string|task.estimate:int|task.expiredate:date|task.note:string" ; 
	@Resource
	private ITaskServiceManager taskServiceManager ;
	private Task task = new Task() ;
	private User receiver = new User() ;	// 任务的实施者
	private Tasktype tasktype = new Tasktype() ;	// 项目类型
	private Project project = new Project() ;	// 任务的所属项目
	public Project getProject() {
		return project;
	}
	public Task getTask() {
		return task;
	}
	public User getReceiver() {
		return receiver;
	}
	public Tasktype getTasktype() {
		return tasktype;
	}
	
	public String insertPre() {
		try {
			Map<String,Object> map = this.taskServiceManager.insertPre() ;
			super.getRequest().setAttribute("allUsers", map.get("allUsers"));
			super.getRequest().setAttribute("allTasktypes", map.get("allTasktypes"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task.insert" ;
	}
	
	public String updatePre() {
		try {
			Map<String,Object> map = this.taskServiceManager.updatePre(this.task.getTid()) ;
			super.getRequest().setAttribute("allUsers", map.get("allUsers"));
			super.getRequest().setAttribute("allTasktypes", map.get("allTasktypes"));
			super.getRequest().setAttribute("task", map.get("task"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task.update" ;
	}
	
	public String update() {
		User manager = (User) super.getSession().getAttribute("manager") ;
		this.task.setUserByCreator(manager);	// 任务的发布者
		this.task.setTasktype(this.tasktype); 	// 任务的类型
		this.task.setUserByReceiver(this.receiver);// 任务的承接者
		this.task.setProject(this.project);
		try {
			if (this.taskServiceManager.update(this.task)) {
				super.setMsgAndUrl("update.success.msg", "manager.task.list.action");
			} else {
				super.setMsgAndUrl("update.failure.msg", "manager.task.list.action");
			}
			super.getRequest().setAttribute("url", super.getUrl("manager.task.list.action")+"?project.proid=" + this.project.getProid());	// 自己手工控制url路径
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page" ;
	}
	
	public String list() {
		try {
			Map<String, Object> map = this.taskServiceManager
					.listByProject(this.project.getProid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.getRequest().setAttribute("project", map.get("project"));
			super.handleSplit(map.get("taskCount"),
					"manager.task.split.url", "project.proid", String.valueOf(this.project.getProid()));
			super.getRequest()
					.setAttribute("allTasks", map.get("allTasks"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task.list" ;
	}
	
	public String admin() {
		try {
			User manager = (User) super.getSession().getAttribute("manager") ;
			Map<String, Object> map = this.taskServiceManager
					.listByCreator(manager.getUserid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.getRequest().setAttribute("project", map.get("project"));
			super.handleSplit(map.get("taskCount"),
					"manager.task.admin.split.url", "project.proid", String.valueOf(this.project.getProid()));
			super.getRequest()
					.setAttribute("allTasks", map.get("allTasks"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "task.admin" ;
	}
	
	public void cancel() {
		User manager = (User) super.getSession().getAttribute("manager") ;
		this.task.setUserByCanceler(manager);	// 设置取消者
		this.task.setProject(this.project);// 任务所属的项目
		try {
			super.print(this.taskServiceManager.updateCancel(this.task));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String insert() {
		User manager = (User) super.getSession().getAttribute("manager") ;
		this.task.setUserByCreator(manager);	// 任务的发布者
		this.task.setTasktype(this.tasktype); 	// 任务的类型
		this.task.setUserByReceiver(this.receiver);// 任务的承接者
		this.task.setProject(this.project);
		try {
			if (this.taskServiceManager.insert(this.task)) {
				super.setMsgAndUrl("insert.success.msg", "manager.task.insert.action");
			} else {
				super.setMsgAndUrl("insert.failure.msg", "manager.task.insert.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page" ;
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
