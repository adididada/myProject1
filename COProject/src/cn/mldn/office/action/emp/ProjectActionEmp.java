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
import cn.mldn.office.service.emp.IProjectServiceEmp;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("empStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "project.list", location = "/pages/jsp/emp/project/emp_project_list.jsp") })
@Namespace("/pages/jsp/emp/project")
@Action("ProjectActionEmp")
public class ProjectActionEmp extends AbstractAction {
	private Project project = new Project() ;
	public Project getProject() {
		return project;
	}
	
	@Resource
	private IProjectServiceEmp projectServiceEmp;
	
	public String list() { // 数据分页显示
		try {
			Map<String, Object> map = this.projectServiceEmp
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("projectCount"), "emp.project.split.url",
					null, null);
			super.getRequest()
					.setAttribute("allProjects", map.get("allProjects"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "project.list";
	}
	public void show() {
		try {
			Project project = this.projectServiceEmp.show(this.project.getProid()) ;
			JSONObject obj = new JSONObject() ;
			obj.put("proid", project.getProid()) ;
			obj.put("title", project.getTitle()) ;
			obj.put("creid", project.getUserByCreid().getUserid()) ;
			obj.put("mgr", project.getUserByMgr().getUserid()) ;
			obj.put("name", project.getName()) ;
			obj.put("note", project.getNote()) ;
			obj.put("pubdate", super.formatDate(project.getPubdate())) ;
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
