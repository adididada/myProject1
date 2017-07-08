package cn.mldn.office.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.INoticeDAO;
import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.pojo.Notice;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.INoticeServiceAdmin;

@Service
public class NoticeServiceAdminImpl implements INoticeServiceAdmin {
	@Resource
	private INoticeDAO noticeDAO;
	@Resource
	private IUserDAO userDAO;

	@Override
	public boolean insert(Notice vo) throws Exception {
		User user = this.userDAO.findById(vo.getUser().getUserid());
		if (user.getLevel() == 0 || user.getLevel() == 1) { // 只有管理员可以发布公告
			if (vo.getLevel() == 2 || vo.getLevel() == 3) {
				vo.setRnum(0);
				vo.setPubdate(new Date());
				return this.noticeDAO.doCreate(vo);
			}
		}
		return false;
	}

	@Override
	public Notice updatePre(Integer snid) throws Exception {
		return this.noticeDAO.findById(snid);
	}

	@Override
	public boolean update(Notice vo) throws Exception {
		User user = this.userDAO.findById(vo.getUser().getUserid());
		if (user.getLevel() == 0 || user.getLevel() == 1) { // 只有管理员可以发布公告
			if (vo.getLevel() == 2 || vo.getLevel() == 3) {
				return this.noticeDAO.doUpdate(vo);
			}
		}
		return false;
	}

	@Override
	public boolean delete(Set<Integer> ids) throws Exception {
		if (ids.size() > 0) {
			return this.noticeDAO.doRemoveBatch(ids);
		}
		return false;
	}

	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allNotices", this.noticeDAO.findAllSplit(currentPage,
				lineSize, column, keyWord));
		map.put("noticeCount", this.noticeDAO.getAllCount(column, keyWord));
		return map;
	}

	@Override
	public boolean updateLevel(int snid, int level) throws Exception {
		if (level == 2 || level == 3) {
			return this.noticeDAO.doUpdateLevel(snid, level);
		}
		return false;
	}

}
