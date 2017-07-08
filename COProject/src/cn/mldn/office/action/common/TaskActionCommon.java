package cn.mldn.office.action.common;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Task;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.common.ITaskServiceCommon;
import cn.mldn.util.action.AbstractAction;

@Repository
@ParentPackage("root")
@Namespace("/pages/jsp/common/task")
@Action("TaskActionCommon")
@InterceptorRef("commonStack")
@SuppressWarnings("serial")
public class TaskActionCommon extends AbstractAction {
	@Resource
	private ITaskServiceCommon taskServiceCommon;
	private Task task = new Task();

	public Task getTask() {
		return task;
	}
	
	public void show() {
		try {
			Task task = this.taskServiceCommon.show(this.task.getTid()) ;
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

	@Override
	public String getTypeName() {
		return null;
	}

	@Override
	public String getDefaultColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnData() {
		// TODO Auto-generated method stub
		return null;
	}
}
