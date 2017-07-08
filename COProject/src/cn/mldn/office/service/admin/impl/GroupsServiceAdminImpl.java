package cn.mldn.office.service.admin.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IGroupsDAO;
import cn.mldn.office.pojo.Groups;
import cn.mldn.office.service.admin.IGroupsServiceAdmin;

@Service
public class GroupsServiceAdminImpl implements IGroupsServiceAdmin {
	@Resource
	private IGroupsDAO groupsDAO;

	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allGroups", this.groupsDAO.findAllSplit(currentPage, lineSize,
				column, keyWord));
		map.put("groupsCount", this.groupsDAO.getAllCount(column, keyWord));
		return map;
	}

	@Override
	public boolean update(Groups vo) throws Exception {
		return this.groupsDAO.doUpdate(vo);
	}

	@Override
	public Groups show(int gid) throws Exception {
		Groups vo = this.groupsDAO.findById(gid) ;
		if (vo != null) {	// 默认延迟加载是打开的
			vo.getActions().size() ;	// 执行此操作就表示要加载“多”方数据
		}
		return vo ; 
	}

}
