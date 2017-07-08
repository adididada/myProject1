package cn.mldn.office.service.emp.impl;

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
import cn.mldn.office.service.emp.INoticeServiceEmp;

@Service
public class NoticeServiceEmpImpl implements INoticeServiceEmp {
	@Resource
	private IUserDAO userDAO;
	@Resource
	private INoticeDAO noticeDAO;
	@Resource
	private IUserNoticeDAO userNoticeDAO;

	@Override
	public Map<String, Object> list(String userid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		User user = this.userDAO.findById(userid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allNotices", this.noticeDAO.findAllSplitByLevel(
				user.getLevel(), currentPage, lineSize, column, keyWord));
		map.put("noticeCount", this.noticeDAO.getAllCountByLevel(
				user.getLevel(), column, keyWord));
		map.put("unread", this.noticeDAO.findUnread(userid, user.getLevel()));
		return map;
	}

	@Override
	public Notice show(int snid, String userid) throws Exception {
		// 1、读读取用户的级别信息
		User user = this.userDAO.findById(userid);
		// 2、要读取公告的完整信息
		Notice notice = this.noticeDAO.findByIdAndLevel(snid, user.getLevel());
		// 3、判断此公告信息是否已经读取过了
		if (!this.userNoticeDAO.findByExists(userid, snid)) {
			notice.setRnum(notice.getRnum() + 1); // 修改已有的阅读量
			UserNotice unvo = new UserNotice();
			unvo.setUser(user);
			unvo.setNotice(notice);
			unvo.setRdate(new Date());
			this.userNoticeDAO.doCreate(unvo);
		}
		return notice;
	}

}
