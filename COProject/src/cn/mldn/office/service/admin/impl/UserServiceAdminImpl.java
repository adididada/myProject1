package cn.mldn.office.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IActionDAO;
import cn.mldn.office.dao.IGroupsDAO;
import cn.mldn.office.dao.IRoleDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.pojo.Action;
import cn.mldn.office.pojo.Groups;
import cn.mldn.office.pojo.Role;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.IUserServiceAdmin;

@Service
public class UserServiceAdminImpl implements IUserServiceAdmin {
	@Resource
	private IRoleDAO roleDAO;
	@Resource
	private IUserDAO userDAO;
	@Resource
	private IGroupsDAO groupsDAO;
	@Resource
	private IActionDAO actionDAO;

	@Override
	public boolean insert(User vo, String userid) throws Exception {
		User user = this.userDAO.findById(userid);
		if (user.getLevel() <= 1) { // 管理员级别
			if (this.userDAO.findById(vo.getUserid()) == null) { // 不存在
				// 根据级别来确定用户角色
				Role role = new Role() ;
				if (vo.getLevel() == 2) {
					role.setRid(4);
				} 
				if (vo.getLevel() == 3) {
					role.setRid(5);
				}
				vo.setRole(role);
				vo.setLockflag(0); // 新的管理员信息不锁定
				vo.setPhoto("nophoto.jpg");
				return this.userDAO.doCreate(vo);
			}
		}
		return false;
	}

	@Override
	public Map<String, Object> updatePre(String userid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.userDAO.findById(userid));
		return map;
	}

	@Override
	public boolean update(User vo) throws Exception {
		if (this.userDAO.findById(vo.getUserid()).getLevel() > 1) {
			Role role = new Role() ;
			if (vo.getLevel() == 2) {
				role.setRid(4);
			} 
			if (vo.getLevel() == 3) {
				role.setRid(5);
			}
			vo.setRole(role);
			return this.userDAO.doUpdateInfo(vo);
		}
		return false;
	}

	@Override
	public boolean updateLock(Set<String> ids, int lock) throws Exception {
		if (ids.size() == 0) {
			return false;
		}
		return this.userDAO.doUpdateLock(ids, lock);
	}

	@Override
	public boolean updateRole(User vo) throws Exception {
		if (this.userDAO.findById(vo.getUserid()).getLevel() > 1) {
			if (vo.getRole().getRid() == 4) {
				vo.setLevel(2);
			}
			if (vo.getRole().getRid() == 5) {
				vo.setLevel(3);
			}
			return this.userDAO.doUpdateRole(vo);
		}
		return false;
	}

	@Override
	public boolean updatePassword(User vo) throws Exception {
		if (this.userDAO.findById(vo.getUserid()).getLevel() > 1) {
			return this.userDAO.doUpdatePassword(vo);
		}
		return false;
	}

	@Override
	public boolean checkUser(String userid) throws Exception {
		return this.userDAO.findById(userid) == null;
	}

	@Override
	public Map<String, Object> list(int lockflag, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allUsers", this.userDAO.findAllUserByLock(lockflag,
				currentPage, lineSize, column, keyWord));
		map.put("userCount",
				this.userDAO.getAllUserCountByLock(lockflag, column, keyWord));
		return map;
	}

	@Override
	public User show(String id) throws Exception {
		User retObject = new User(); // 开辟一个新的
		User pojo = this.userDAO.findById(id) ;	// 根据编号查询出管理员的完整信息
		if (pojo != null) {	// 表示信息已经查询到了
			// 保存用户的基础信息
			retObject.setUserid(id);
			retObject.setLastlogin(pojo.getLastlogin());
			retObject.setLevel(pojo.getLevel());
			retObject.setName(pojo.getName());
			retObject.setPhoto(pojo.getPhoto());
			retObject.setPhone(pojo.getPhone());
			retObject.setEmail(pojo.getEmail()); 
			pojo.setLastlogin(new Date());
			// 保存一个用户的角色信息
			Role role = this.roleDAO.findById(pojo.getRole().getRid()) ;
			// 找到角色对应所有权限组
			List<Groups> allGroups = this.groupsDAO.findAllByRole(pojo
					.getRole().getRid());
			// 找到所有权限组对应的权限数据
			Iterator<Groups> iter = allGroups.iterator();
			while (iter.hasNext()) {
				Groups gup = iter.next(); // 取出每一个权限组对象
				Set<Action> set = new HashSet<Action>(); // 使用的是Set集合
				set.addAll(this.actionDAO.findAllByGroups(gup.getGid()));// 将List集合变为Set
				gup.setActions(set);
			}
			Set<Groups> gset = new TreeSet<Groups>();
			gset.addAll(allGroups);
			role.setGroupses(gset);
			retObject.setRole(role);
		}
		return retObject ;
	}

}
