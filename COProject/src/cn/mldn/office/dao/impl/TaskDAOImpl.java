package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.ITaskDAO;
import cn.mldn.office.pojo.Task;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class TaskDAOImpl extends AbstractDAOImpl implements ITaskDAO {

	@Override
	public boolean doCreate(Task vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Task vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Task findById(Integer id) throws SQLException {
		return (Task) super.getSession().get(Task.class, id);
	}

	@Override
	public List<Task> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findAllByProject(Integer proid, Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception {
		Criteria criteria = this.getCriteria(Task.class);
		criteria.add(Restrictions.and(
				Restrictions.like(column, "%" + keyWord + "%") ,
				Restrictions.eq("project.proid", proid)));
		criteria.setFirstResult((currentPage - 1) * lineSize);
		criteria.setMaxResults(lineSize);
		return criteria.list() ; 
	}

	@Override
	public Integer getAllCountByProject(Integer proid, String column,
			String keyWord) throws Exception {
		String hql = "SELECT COUNT(*) FROM Task AS p WHERE p."
				+ column + " LIKE ? AND p.project.proid=?";
		Query query = this.getQuery(hql);
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, proid) ;
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public boolean doUpdateCancel(Task vo) throws Exception {
		String hql = "UPDATE Task AS t SET t.userByCanceler.userid=?,t.cnote=?,t.status=? WHERE t.tid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getUserByCanceler().getUserid()) ;
		query.setParameter(1, vo.getCnote()) ;
		query.setParameter(2, vo.getStatus()) ;
		query.setParameter(3, vo.getTid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doUpdateInfo(Task vo) throws Exception {
		String hql = "UPDATE Task AS t SET t.userByReceiver.userid=?,t.title=?,t.lastupdatedate=?,t.priority=?,t.estimate=?,t.note=?,t.tasktype.ttid=? WHERE t.tid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getUserByReceiver().getUserid()) ;
		query.setParameter(1, vo.getTitle()) ;
		query.setParameter(2, vo.getLastupdatedate()) ;
		query.setParameter(3, vo.getPriority()) ;
		query.setParameter(4, vo.getEstimate()) ;
		query.setParameter(5, vo.getNote()) ;
		query.setParameter(6, vo.getTasktype().getTtid()) ;
		query.setParameter(7, vo.getTid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public List<Task> findAllByManager(String userid, Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception {
		Criteria criteria = this.getCriteria(Task.class);
		criteria.add(Restrictions.and(
				Restrictions.like(column, "%" + keyWord + "%") ,
				Restrictions.eq("userByCreator.userid", userid)));
		criteria.setFirstResult((currentPage - 1) * lineSize);
		criteria.setMaxResults(lineSize);
		return criteria.list() ; 
	}

	@Override
	public Integer getAllCountByManager(String userid, String column,
			String keyWord) throws Exception {
		String hql = "SELECT COUNT(*) FROM Task AS p WHERE p."
				+ column + " LIKE ? AND p.userByCreator.userid=?";
		Query query = this.getQuery(hql);
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, userid) ;
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public boolean doUpdateFinish(Task vo) throws Exception {
		String hql = "UPDATE Task AS t SET t.expend=?,t.status=?,t.finishdate=?,t.rnote=? WHERE t.tid=?" ;
		Query query = super.getQuery(hql) ; 
		query.setParameter(0, vo.getExpend()) ;
		query.setParameter(1, vo.getStatus()) ;
		query.setParameter(2, vo.getFinishdate()) ;
		query.setParameter(3, vo.getRnote()) ;
		query.setParameter(4, vo.getTid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doUpdateStatus(Integer id, Integer status) throws Exception {
		String hql = "UPDATE Task AS t SET t.status=? WHERE t.tid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, status) ;
		query.setParameter(1, id) ;
		return query.executeUpdate() > 0 ;
	}
	@Override
	public List<Task> findAllByEmp(String userid, Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception {
		Criteria criteria = this.getCriteria(Task.class);
		criteria.add(Restrictions.and(
				Restrictions.like(column, "%" + keyWord + "%") ,
				Restrictions.eq("userByReceiver.userid", userid)));
		criteria.setFirstResult((currentPage - 1) * lineSize);
		criteria.setMaxResults(lineSize);
		return criteria.list() ; 
	}

	@Override
	public Integer getAllCountByEmp(String userid, String column,
			String keyWord) throws Exception {
		String hql = "SELECT COUNT(*) FROM Task AS p WHERE p."
				+ column + " LIKE ? AND p.userByReceiver.userid=?";
		Query query = this.getQuery(hql);
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, userid) ;
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public Integer getAllCountByStatus(String userid, Integer status)
			throws Exception {
		String hql = "SELECT COUNT(*) FROM Task AS p WHERE p.status=? AND p.userByReceiver.userid=?";
		Query query = this.getQuery(hql);
		query.setParameter(0, status);
		query.setParameter(1, userid) ;
		return ((Long) query.uniqueResult()).intValue();
	}
}
