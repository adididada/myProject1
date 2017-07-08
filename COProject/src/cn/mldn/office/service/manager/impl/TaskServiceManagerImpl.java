package cn.mldn.office.service.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.dao.ITaskDAO;
import cn.mldn.office.dao.ITasktypeDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.pojo.Project;
import cn.mldn.office.pojo.Task;
import cn.mldn.office.service.manager.ITaskServiceManager;
@Service
public class TaskServiceManagerImpl implements ITaskServiceManager {
	@Resource
	private IUserDAO userDAO ;
	@Resource
	private IProjectDAO projectDAO ;
	@Resource
	private ITaskDAO taskDAO ;
	@Resource
	private ITasktypeDAO tasktypeDAO ;
	@Override
	public Map<String, Object> insertPre() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allUsers", this.userDAO.findAllByLevel(3)) ;
		map.put("allTasktypes", this.tasktypeDAO.findAll()) ;
		return map;
	}

	@Override
	public boolean insert(Task vo) throws Exception {
		// 1、首先判断项目的负责人信息，那么要找到项目的原始信息
		Project project = this.projectDAO.findById(vo.getProject().getProid()) ;
		// 2、要判断项目的负责人信息
		if (vo.getUserByCreator().getUserid()
				.equals(project.getUserByMgr().getUserid())) {
			// 3、查询项目负责人的状态
			if (this.userDAO.findById(vo.getUserByCreator().getUserid())
					.getLockflag() == 0) {	// 未锁定状态
				// 4、判断任务的接收者用户状态
				if (this.userDAO.findById(vo.getUserByReceiver().getUserid())
						.getLockflag() == 0) {
					vo.setLastupdatedate(new Date());	// 创建日期为当前最后一次修改日期
					vo.setCreatedate(new Date());
					vo.setStatus(0);	// 刚刚开始的任务状态
					return this.taskDAO.doCreate(vo) ;
				}
			}
		}
		return false;
	}

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
	public boolean updateCancel(Task vo) throws Exception {
		Project pro = this.projectDAO.findById(vo.getProject().getProid()) ;
		// 取消者是项目的管理者
		if (vo.getUserByCanceler().getUserid().equals(pro.getUserByMgr().getUserid())) {
			vo.setStatus(2); 
			return this.taskDAO.doUpdateCancel(vo) ;
		}
		return false;
	}

	@Override
	public Map<String, Object> updatePre(int tid) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allUsers", this.userDAO.findAllByLevel(3)) ;
		map.put("allTasktypes", this.tasktypeDAO.findAll()) ;
		map.put("task", this.taskDAO.findById(tid)) ;
		return map;
	}

	@Override
	public boolean update(Task vo) throws Exception {
		// 1、首先判断项目的负责人信息，那么要找到项目的原始信息
		Project project = this.projectDAO.findById(vo.getProject().getProid()) ;
		// 2、要判断项目的负责人信息
		if (vo.getUserByCreator().getUserid()
				.equals(project.getUserByMgr().getUserid())) {
			// 3、查询项目负责人的状态
			if (this.userDAO.findById(vo.getUserByCreator().getUserid())
					.getLockflag() == 0) {	// 未锁定状态
				// 4、判断任务的接收者用户状态
				if (this.userDAO.findById(vo.getUserByReceiver().getUserid())
						.getLockflag() == 0) {
					vo.setLastupdatedate(new Date());	// 创建日期为当前最后一次修改日期
					return this.taskDAO.doUpdateInfo(vo) ; 
				}
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> listByCreator(String userid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allTasks", this.taskDAO.findAllByManager(userid, currentPage, lineSize, column, keyWord)) ;
		map.put("taskCount", this.taskDAO.getAllCountByManager(userid, column, keyWord)) ;
		return map ;
	}

 
  
}
