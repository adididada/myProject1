package cn.mldn.office.service.common.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IActionDAO;
import cn.mldn.office.dao.IGroupsDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.pojo.Action;
import cn.mldn.office.pojo.Groups;
import cn.mldn.office.pojo.Role;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.common.IUserServiceCommon;

@Service
public class UserServiceCommonImpl implements IUserServiceCommon {
	@Resource
	private IUserDAO userDAO;
	@Resource
	private IGroupsDAO groupsDAO;
	@Resource
	private IActionDAO actionDAO;

	@Override
	public User login(String userid, String password) throws Exception {
		User retObject = new User(); // 开辟一个新的
		User pojo = this.userDAO.findLogin(userid, password); // 1、验证用户名或密码
		if (pojo != null) { // 用户已经可以成功登录，那么应该取出所有的权限组
			// 2、根据用户的角色编号查询出用户对应的所有权限组的信息
			List<Groups> allGroups = this.groupsDAO.findAllByRole(pojo
					.getRole().getRid());
			// 3、根据权限组的编号取出每一个权限组对应的所有权限数据
			Iterator<Groups> iter = allGroups.iterator();
			while (iter.hasNext()) {
				Groups gup = iter.next(); // 取出每一个权限组对象
				Set<Action> set = new HashSet<Action>(); // 使用的是Set集合
				set.addAll(this.actionDAO.findAllByGroups(gup.getGid()));// 将List集合变为Set
				gup.setActions(set);
			}
			// 4、设置用户角色与权限组的关系
			Set<Groups> gset = new TreeSet<Groups>();
			gset.addAll(allGroups);
			// ****** 此时对数据进行的修改是非持久态的对象 ******
			Role role = new Role();
			role.setGroupses(gset);
			retObject.setRole(role);
			retObject.setUserid(userid);
			retObject.setLastlogin(pojo.getLastlogin());
			retObject.setLevel(pojo.getLevel());
			retObject.setName(pojo.getName());
			retObject.setPhoto(pojo.getPhoto());
			// ******************************
			// 5、更改最后一次登录日期时间
			pojo.setLastlogin(new Date());
		}
		return retObject;
	}

	@Override
	public boolean updatePassword(String userid, String oldpass, String newpass)
			throws Exception {
		if (this.userDAO.findLogin(userid, oldpass) != null) { // 原始密码验证通过
			User vo = new User() ;
			vo.setUserid(userid);
			vo.setPassword(newpass); 
			return this.userDAO.doUpdatePassword(vo) ;
		}
		return false;
	}

	@Override
	public User updatePre(String userid) throws Exception {
		return this.userDAO.findById(userid) ;
	}

	@Override
	public boolean update(User vo) throws Exception {
		return this.userDAO.doUpdate(vo); 
	}

	@Override
	public User show(String id) throws Exception {
		User pojo = this.userDAO.findById(id) ;	// 根据编号查询出管理员的完整信息
		return pojo ;
	}
}
