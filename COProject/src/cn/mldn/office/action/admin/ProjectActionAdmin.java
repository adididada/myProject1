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

import cn.mldn.office.pojo.Project;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.IProjectServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "project.insert", location = "/pages/jsp/admin/project/admin_project_insert.jsp"),
		@Result(name = "project.update", location = "/pages/jsp/admin/project/admin_project_update.jsp"),
		@Result(name = "project.list", location = "/pages/jsp/admin/project/admin_project_list.jsp") ,
		@Result(name = "insertVF", location = "/pages/jsp/admin/project/ProjectActionAdmin!insertPre.action",type="redirectAction"),
		@Result(name = "updateVF", location = "/pages/jsp/admin/project/ProjectActionAdmin!list.action",type="redirectAction")})
@Namespace("/pages/jsp/admin/project")
@Action("ProjectActionAdmin")
public class ProjectActionAdmin extends AbstractAction {
	private static String insertRule = "project.title:string|project.note:string";
	private static String updateRule = "project.proid:int|project.title:string|project.note:string";
	
	private Project project = new Project() ;
	private User mgr = new User() ;	// 负责接收项目经理的信息
	public User getMgr() {
		return mgr;
	}
	
	public Project getProject() {
		return project;
	}
	
	@Resource
	private IProjectServiceAdmin projectServiceAdmin;
	
	public String list() { // 数据分页显示
		try {
			Map<String, Object> map = this.projectServiceAdmin
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("projectCount"), "admin.project.split.url",
					null, null);
			super.getRequest()
					.setAttribute("allProjects", map.get("allProjects"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "project.list";
	}

	public String insertPre() {
		try {
			Map<String,Object> map = this.projectServiceAdmin.insertPre() ;
			super.getRequest().setAttribute("allManagers", map.get("allManagers"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "project.insert" ;
	}
	
	public String updatePre() {
		try {
			Map<String,Object> map = this.projectServiceAdmin.updatePre(this.project.getProid()) ;
			super.getRequest().setAttribute("allManagers", map.get("allManagers"));
			super.getRequest().setAttribute("project", map.get("project"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "project.update" ;
	}
	
	public String insert() {
		try {
			User admin = (User) super.getSession().getAttribute("admin") ;
			this.project.setUserByMgr(this.mgr);	// 保存项目经理的信息
			this.project.setUserByCreid(admin);// 保存项目创建者的信息
			if (this.projectServiceAdmin.insert(this.project)) {
				super.setMsgAndUrl("insert.success.msg", "admin.project.insert.action");
			} else {
				super.setMsgAndUrl("insert.failure.msg", "admin.project.insert.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page" ;
	}
	
	public String update() {
		try {
			User admin = (User) super.getSession().getAttribute("admin") ;
			this.project.setUserByMgr(this.mgr);	// 保存项目经理的信息
			this.project.setUserByCreid(admin);// 保存项目创建者的信息
			if (this.projectServiceAdmin.update(this.project)) {
				super.setMsgAndUrl("update.success.msg", "admin.project.list.action");
			} else {
				super.setMsgAndUrl("update.failure.msg", "admin.project.list.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page" ;
	}

	@Override
	public String getTypeName() {
		return "项目";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "项目标题:title|项目经理ID:userByMgr.userid|负责人:name";
	}

}
