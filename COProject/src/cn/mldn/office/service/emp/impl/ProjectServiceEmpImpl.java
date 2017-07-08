package cn.mldn.office.service.emp.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.pojo.Project;
import cn.mldn.office.service.emp.IProjectServiceEmp;

@Service
public class ProjectServiceEmpImpl implements IProjectServiceEmp {
	@Resource
	private IProjectDAO projectDAO;

	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allProjects", this.projectDAO.findAllSplit(currentPage,
				lineSize, column, keyWord));
		map.put("projectCount", this.projectDAO.getAllCount(column, keyWord));
		return map;
	}

	@Override
	public Project show(int id) throws Exception {
		return this.projectDAO.findById(id);
	}

}
