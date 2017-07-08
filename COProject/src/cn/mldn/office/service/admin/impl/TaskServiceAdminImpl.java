package cn.mldn.office.service.admin.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.dao.ITaskDAO;
import cn.mldn.office.service.admin.ITaskServiceAdmin;
@Service
public class TaskServiceAdminImpl implements ITaskServiceAdmin {
	@Resource
	private ITaskDAO taskDAO ;
	@Override
	public Map<String, Object> listByProject(int proid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allTasks", this.taskDAO.findAllByProject(proid, currentPage, lineSize, column, keyWord)) ;
		map.put("taskCount", this.taskDAO.getAllCountByProject(proid, column, keyWord)) ;
		return map ;
	}

}
