package cn.mldn.office.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.INoticeDAO;
import cn.mldn.office.pojo.Notice;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class NoticeDAOImpl extends AbstractDAOImpl implements INoticeDAO {

	@Override 
	public boolean doCreate(Notice vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Notice vo) throws SQLException {
		String hql = "UPDATE Notice SET title=?,content=?,level=? WHERE snid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getTitle()) ;
		query.setParameter(1, vo.getContent()) ;
		query.setParameter(2, vo.getLevel()) ;
		query.setParameter(3, vo.getSnid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		return super.handleRemoveBatch("Notice", "snid", ids);
	}

	@Override
	public Notice findById(Integer id) throws SQLException {
		return (Notice) super.getSession().get(Notice.class, id);
	}

	@Override
	public List<Notice> findAll() throws SQLException {
		return super.handleList(Notice.class);
	}

	@Override
	public List<Notice> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		return super.handleListSplit(Notice.class, currentPage, lineSize, column, keyWord);
	}

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		return super.handleCount("Notice", column, keyWord);
	}

	@Override
	public boolean doUpdateLevel(Integer snid, Integer level) throws Exception {
		String hql = "UPDATE Notice SET level=? WHERE snid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, level) ;
		query.setParameter(1, snid) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public Map<Integer, Boolean> findUnread(String userid,Integer level) throws Exception {
		Map<Integer,Boolean> map = new HashMap<Integer,Boolean>() ;
		String sql = "SELECT snid "
				+ " FROM notice "
				+ " WHERE snid NOT IN( "
				+ " SELECT snid FROM user_notice WHERE userid=?) AND level>=?" ;
		Query query = super.getSQLQuery(sql) ;
		query.setParameter(0, userid) ;
		query.setParameter(1, level) ;
		List<Object> result = query.list() ;
		Iterator<Object> iter = result.iterator() ;
		while(iter.hasNext()) {
			map.put((Integer) iter.next(), true) ;
		}
		return map;
	}

	@Override
	public Integer getAllCountUnread(String userid, Integer level)
			throws Exception {
		String sql = "SELECT COUNT(*) "
				+ " FROM notice "
				+ " WHERE snid NOT IN( "
				+ " SELECT snid FROM user_notice WHERE userid=?) AND level>=? " ;
		Query query = super.getSQLQuery(sql) ;
		query.setParameter(0, userid) ;
		query.setParameter(1, level) ;
		return ((BigInteger) query.uniqueResult()).intValue() ;
	}

	@Override
	public Notice findByIdAndLevel(Integer id, Integer level) throws Exception {
		String hql = "FROM Notice AS n WHERE n.snid=? AND n.level>=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, id) ;
		query.setParameter(1, level) ;
		return (Notice) query.uniqueResult();
	}

	@Override
	public List<Notice> findAllSplitByLevel(Integer level, Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception {
		Criteria criteria = this.getCriteria(Notice.class);
		criteria.add(Restrictions.and(
				Restrictions.like(column, "%" + keyWord + "%"),
				Restrictions.ge("level", level)));
		criteria.setFirstResult((currentPage - 1) * lineSize);
		criteria.setMaxResults(lineSize);
		return criteria.list() ; 
	}

	@Override
	public Integer getAllCountByLevel(Integer level, String column,
			String keyWord) throws Exception {
		String hql = "SELECT COUNT(*) FROM Notice AS p WHERE p."
				+ column + " LIKE ? AND level>=?";
		Query query = this.getQuery(hql);
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, level) ;
		return ((Long) query.uniqueResult()).intValue();
	}

}
 