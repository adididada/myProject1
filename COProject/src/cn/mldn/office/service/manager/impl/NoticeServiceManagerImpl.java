package cn.mldn.office.service.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.INoticeDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.dao.IUserNoticeDAO;
import cn.mldn.office.pojo.Notice;
import cn.mldn.office.pojo.User;
import cn.mldn.office.pojo.UserNotice;
import cn.mldn.office.service.manager.INoticeServiceManager;
@Service
public class NoticeServiceManagerImpl implements INoticeServiceManager {
	@Resource
	private IUserDAO userDAO ;
	@Resource
	private INoticeDAO noticeDAO ;
	@Resource
	private IUserNoticeDAO userNoticeDAO ;
	@Override
	public int unreadCount(String userid) throws Exception {
		// 根据用户的编号取出用户对象，目的是取得用户的级别
		User user = this.userDAO.findById(userid) ;	
		return this.noticeDAO.getAllCountUnread(userid, user.getLevel());
	}

	@Override
	public Map<String, Object> list(String userid,int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allNotices", this.noticeDAO.findAllSplit(currentPage,
				lineSize, column, keyWord));
		map.put("noticeCount", this.noticeDAO.getAllCount(column, keyWord));
		User user = this.userDAO.findById(userid) ;	
		map.put("unread", this.noticeDAO.findUnread(userid,user.getLevel())) ;
		return map;
	}

	@Override
	public Notice show(int snid, String userid) throws Exception {
		// 1、读读取用户的级别信息
		User user = this.userDAO.findById(userid) ;
		// 2、要读取公告的完整信息
		Notice notice = this.noticeDAO.findByIdAndLevel(snid,user.getLevel()) ;
		// 3、判断此公告信息是否已经读取过了
		if (!this.userNoticeDAO.findByExists(userid, snid)) {
			notice.setRnum(notice.getRnum() + 1);	// 修改已有的阅读量
			UserNotice unvo = new UserNotice() ;
			unvo.setUser(user);
			unvo.setNotice(notice);
			unvo.setRdate(new Date());
			this.userNoticeDAO.doCreate(unvo) ;
		}
		return notice ;
	}

}
