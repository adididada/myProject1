package cn.mldn.office.service.emp.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.INoticeDAO;
import cn.mldn.office.dao.ITaskDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.dao.IUserNoticeDAO;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.emp.IIndexServiceEmp;
@Service
public class IndexServiceEmpImpl implements IIndexServiceEmp {
	@Resource
	private IUserDAO userDAO;
	@Resource
	private INoticeDAO noticeDAO;
	@Resource
	private IUserNoticeDAO userNoticeDAO;
	@Resource
	private ITaskDAO taskDAO ; 

	@Override
	public Map<String,Integer> unreadCount(String userid) throws Exception {
		Map<String,Integer> map = new HashMap<String,Integer>() ;
		// 根据用户的编号取出用户对象，目的是取得用户的级别
		User user = this.userDAO.findById(userid);
		map.put("noticeCount",
				this.noticeDAO.getAllCountUnread(userid, user.getLevel()));
		map.put("status0Count", this.taskDAO.getAllCountByStatus(userid, 0)) ;
		map.put("status1Count", this.taskDAO.getAllCountByStatus(userid, 1)) ;
		return map ;
	}

}
