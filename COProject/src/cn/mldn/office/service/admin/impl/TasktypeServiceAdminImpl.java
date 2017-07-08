package cn.mldn.office.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.ITasktypeDAO;
import cn.mldn.office.pojo.Tasktype;
import cn.mldn.office.service.admin.ITasktypeServiceAdmin;
@Service
public class TasktypeServiceAdminImpl implements ITasktypeServiceAdmin {
	@Resource
	private ITasktypeDAO tasktypeDAO ;
	@Override
	public boolean insert(Tasktype vo) throws Exception {
		return this.tasktypeDAO.doCreate(vo);
	}

	@Override
	public boolean update(Tasktype vo) throws Exception {
		return this.tasktypeDAO.doUpdate(vo);
	}

	@Override
	public List<Tasktype> list() throws Exception {
		return this.tasktypeDAO.findAll();
	}

}
