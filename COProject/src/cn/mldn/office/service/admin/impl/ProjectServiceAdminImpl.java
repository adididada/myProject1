package cn.mldn.office.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.pojo.Project;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.IProjectServiceAdmin;
import cn.mldn.util.dao.AbstractDAOImpl;

@Service
public class ProjectServiceAdminImpl extends AbstractDAOImpl implements
		IProjectServiceAdmin {
	@Resource
	private IProjectDAO projectDAO;
	@Resource
	private IUserDAO userDAO;

	@Override
	public Map<String, Object> insertPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allManagers", this.userDAO.findAllByLevel(2));
		return map;
	}

	@Override
	public boolean insert(Project vo) throws Exception {
		User admin = this.userDAO.findById(vo.getUserByCreid().getUserid());
		if (admin.getLevel() < 2) { // 是管理员发布的
			User manager = this.userDAO.findById(vo.getUserByMgr().getUserid());
			if (manager.getLevel() == 2) { // 是项目经理
				vo.setPubdate(new Date());
				vo.setName(manager.getName());// 手工设置项目经理的名字
				return this.projectDAO.doCreate(vo);
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> updatePre(int id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allManagers", this.userDAO.findAllByLevel(2));
		map.put("project", this.projectDAO.findById(id));
		return map;
	}

	@Override
	public boolean update(Project vo) throws Exception {
		User admin = this.userDAO.findById(vo.getUserByCreid().getUserid());
		if (admin.getLevel() < 2) { // 是管理员发布的
			User manager = this.userDAO.findById(vo.getUserByMgr().getUserid());
			if (manager.getLevel() == 2) { // 是项目经理
				vo.setName(manager.getName());// 手工设置项目经理的名字
				return this.projectDAO.doUpdate(vo);
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allProjects", this.projectDAO.findAllSplit(currentPage,
				lineSize, column, keyWord));
		map.put("projectCount", this.projectDAO.getAllCount(column, keyWord));
		return map;
	}

}
