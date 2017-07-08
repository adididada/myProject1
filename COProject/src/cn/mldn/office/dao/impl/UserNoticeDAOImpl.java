package cn.mldn.office.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IUserNoticeDAO;
import cn.mldn.office.pojo.UserNotice;
import cn.mldn.util.dao.AbstractDAOImpl;

@Component
public class UserNoticeDAOImpl extends AbstractDAOImpl implements
		IUserNoticeDAO {

	@Override
	public boolean doCreate(UserNotice vo) throws Exception {
		String sql = "INSERT INTO user_notice(userid,snid,rdate) VALUES (?,?,?)";
		Query query = super.getSQLQuery(sql);
		query.setParameter(0, vo.getUser().getUserid());
		query.setParameter(1, vo.getNotice().getSnid());
		query.setParameter(2, vo.getRdate());
		return query.executeUpdate() > 0;
	}

	@Override
	public boolean findByExists(String userid, Integer snid) throws Exception {
		String sql = "SELECT COUNT(*) FROM user_notice WHERE userid=? AND snid=?";
		Query query = super.getSQLQuery(sql);
		query.setParameter(0, userid);
		query.setParameter(1, snid);
		return ((BigInteger) query.uniqueResult()).intValue() > 0;
	}

}
