package cn.mldn.office.service.admin.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IGroupsDAO;
import cn.mldn.office.dao.IRoleDAO;
import cn.mldn.office.pojo.Role;
import cn.mldn.office.service.admin.IRoleServiceAdmin;
@Service
public class RoleServiceAdminImpl implements IRoleServiceAdmin {
	@Resource 
	private IGroupsDAO groupsDAO ;
	@Resource
	private IRoleDAO roleDAO ;
	@Override
	public Map<String, Object> insertPre() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allGroups", this.groupsDAO.findAll()) ;
		return map;
	}

	@Override
	public Map<String, Object> updatePre(int rid) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		// 此处之所以分开查询，是避免了角色的持久态问题
		map.put("allGroups", this.groupsDAO.findAll()) ;
		map.put("role", this.roleDAO.findById(rid)) ;
		map.put("gids", this.roleDAO.findRoleGroups(rid)) ; 
		return map;
	}

	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allRoles", this.roleDAO.findAllSplit(currentPage, lineSize, column, keyWord)) ;
		map.put("roleCount", this.roleDAO.getAllCount(column, keyWord)) ;
		return map;
	}

	@Override
	public boolean update(Role vo) throws Exception {
		if (this.roleDAO.findByTitleAndNotId(vo.getTitle(), vo.getRid()) == null) {	// 表示名称不重复
			return this.roleDAO.doUpdate(vo) ;
		}
		return false;
	}

	@Override
	public boolean insert(Role vo) throws Exception {
		if (this.roleDAO.findByTitle(vo.getTitle()) == null) {
			return this.roleDAO.doCreate(vo) ;
		}
		return false;
	}

	@Override
	public boolean checkTitle(String title) throws Exception {
		return this.roleDAO.findByTitle(title) == null ;
	}

	@Override
	public boolean checkTitle(String title, int rid) throws Exception {
		return this.roleDAO.findByTitleAndNotId(title, rid) == null ;
	}

}
