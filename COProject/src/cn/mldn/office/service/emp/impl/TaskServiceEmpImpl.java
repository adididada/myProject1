package cn.mldn.office.service.emp.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.dao.ITaskDAO;
import cn.mldn.office.pojo.Task;
import cn.mldn.office.service.emp.ITaskServiceEmp;
@Service
public class TaskServiceEmpImpl implements ITaskServiceEmp {
	@Resource
	private IProjectDAO projectDAO ;
	@Resource
	private ITaskDAO taskDAO ;
	@Override
	public Map<String, Object> listByProject(int proid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("project", this.projectDAO.findById(proid)) ;
		map.put("allTasks", this.taskDAO.findAllByProject(proid, currentPage, lineSize, column, keyWord)) ;
		map.put("taskCount", this.taskDAO.getAllCountByProject(proid, column, keyWord)) ;
		return map ;
	}
	@Override
	public boolean updateFinish(Task vo) throws Exception {
		// 1、找到原本的项目任务信息
		Task task = this.taskDAO.findById(vo.getTid()) ;
		// 2、判断当前执行者是否是任务完成者
		if (task.getUserByReceiver().getUserid().equals(vo.getUserByReceiver().getUserid())) {
			if (task.getStatus() < 2) {
				vo.setFinishdate(new Date());
				vo.setStatus(3);
				return this.taskDAO.doUpdateFinish(vo) ;
			}
		}
		return false;
	}
	@Override
	public Task show(int id, String userid) throws Exception {
		Task task = this.taskDAO.findById(id) ;
		if (task != null) {
			if (task.getUserByReceiver().getUserid().equals(userid)) {	// 为当前用户
				this.taskDAO.doUpdateStatus(id, 1) ;	// 正在进行
				// 此处也可以利用持久态更新，但是尽量对于持久态
			}
			task.getProject().getTitle() ;
			task.getTasktype().getTitle() ;
		}
		return task;
	}
	@Override
	public Map<String, Object> listByReceiver(String userid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allTasks", this.taskDAO.findAllByEmp(userid, currentPage, lineSize, column, keyWord)) ;
		map.put("taskCount", this.taskDAO.getAllCountByEmp(userid, column, keyWord)) ;
		return map ;
	}

}
