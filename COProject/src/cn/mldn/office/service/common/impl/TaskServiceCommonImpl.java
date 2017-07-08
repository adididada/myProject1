package cn.mldn.office.service.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.dao.ITaskDAO;
import cn.mldn.office.dao.ITasktypeDAO;
import cn.mldn.office.pojo.Task;
import cn.mldn.office.service.common.ITaskServiceCommon;
@Service
public class TaskServiceCommonImpl implements ITaskServiceCommon {
	@Resource
	private ITaskDAO taskDAO ;
	@Override
	public Task show(int id) throws Exception {
		Task task = this.taskDAO.findById(id) ;
		if (task != null) {
			task.getProject().getTitle() ;
			task.getTasktype().getTitle() ;
		}
		return task ;
	}

}
