package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IUserDAO;
import cn.mldn.office.pojo.User;
import cn.mldn.util.dao.AbstractDAOImpl;

@Component
public class UserDAOImpl extends AbstractDAOImpl implements IUserDAO {
	@Override
	public boolean doCreate(User vo) throws SQLException {
		return super.getSession().save(vo) != null ; 
	}

	@Override
	public boolean doUpdate(User vo) throws SQLException {
		String hql = "UPDATE User AS u SET u.name=?,u.phone=?,u.email=?,u.photo=? WHERE u.userid=?";
		Query query = super.getQuery(hql);
		query.setParameter(0, vo.getName());
		query.setParameter(1, vo.getPhone());
		query.setParameter(2, vo.getEmail());
		query.setParameter(3, vo.getPhoto());
		query.setParameter(4, vo.getUserid());
		return query.executeUpdate() > 0;
	}

	@Override
	public boolean doRemoveBatch(Set<String> ids) throws SQLException {
		StringBuffer buf = new StringBuffer() ;
		buf.append("DELETE FROM ").append("User").append(" WHERE ")
				.append("userid").append(" IN (");
		Iterator<?> iter = ids.iterator() ;
		while (iter.hasNext()) {
			buf.append("'").append(iter.next()).append("'").append(",") ;
		}
		buf.delete(buf.length() - 1, buf.length()).append(")") ;
		buf.append(" AND level>0") ;
		Query query = this.getQuery(buf.toString()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public User findById(String id) throws SQLException {
		return (User) super.getSession().get(User.class, id);
	}

	@Override
	public List<User> findAll() throws SQLException {
		return null;
	}

	@Override
	public List<User> findAllSplit(Integer currentPage, Integer lineSize,
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
	public User findLogin(String userid, String password) throws Exception {
		String hql = "FROM User AS u WHERE u.userid=? AND u.password=? AND lockflag=0";
		Query query = super.getQuery(hql);
		query.setParameter(0, userid);
		query.setParameter(1, password);
		User user = (User) query.uniqueResult();
		return user;
	}

	@Override
	public boolean doUpdatePassword(User vo) throws Exception {
		String hql = "UPDATE User AS u SET u.password=? WHERE u.userid=?";
		Query query = super.getQuery(hql);
		query.setParameter(0, vo.getPassword());
		query.setParameter(1, vo.getUserid());
		return query.executeUpdate() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUser(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws Exception {
		String hql = "FROM User AS u WHERE u." + column + " LIKE ? AND u.level IN(2,3)" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, "%" + keyWord + "%");
		query.setFirstResult((currentPage - 1) * lineSize) ;
		query.setMaxResults(lineSize) ;
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUserByLock(Integer lockflag,Integer currentPage, Integer lineSize,
			String column, String keyWord) throws Exception {
		String hql = "FROM User AS u WHERE u." + column + " LIKE ? AND u.level IN(2,3) AND u.lockflag=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, lockflag) ;
		query.setFirstResult((currentPage - 1) * lineSize) ;
		query.setMaxResults(lineSize) ;
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllAdmin(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws Exception {
		String hql = "FROM User AS u WHERE u." + column + " LIKE ? AND u.level IN(0,1)" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, "%" + keyWord + "%");
		query.setFirstResult((currentPage - 1) * lineSize) ;
		query.setMaxResults(lineSize) ;
		return query.list();
	}
	@Override
	public Integer getAllAdminCount(String column, String keyWord)
			throws Exception {
		String hql = "SELECT COUNT(*) FROM User AS u WHERE u." + column + " LIKE ? AND u.level IN(0,1)" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, "%" + keyWord + "%");
		return ((Long) query.uniqueResult()).intValue();
	}
	@Override
	public Integer getAllUserCount(String column, String keyWord)
			throws Exception {
		String hql = "SELECT COUNT(*) FROM User AS u WHERE u." + column + " LIKE ? AND u.level IN(2,3)" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, "%" + keyWord + "%");
		return ((Long) query.uniqueResult()).intValue();
	}
	@Override
	public Integer getAllUserCountByLock(Integer lockflag ,String column, String keyWord)
			throws Exception {
		String hql = "SELECT COUNT(*) FROM User AS u WHERE u." + column + " LIKE ? AND u.level IN(2,3) AND u.lockflag=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, lockflag) ;
		return ((Long) query.uniqueResult()).intValue();
	}
	@Override
	public boolean doUpdateInfo(User vo) throws Exception {
		String hql = "UPDATE User AS u SET u.name=?,u.phone=?,u.email=?,u.role.rid=?,u.level=? WHERE u.userid=?";
		Query query = super.getQuery(hql);
		query.setParameter(0, vo.getName());
		query.setParameter(1, vo.getPhone());
		query.setParameter(2, vo.getEmail());
		query.setParameter(3,vo.getRole().getRid());
		query.setParameter(4, vo.getLevel()) ;
		query.setParameter(5, vo.getUserid());
		return query.executeUpdate() > 0;
	}
	@Override
	public boolean doUpdateRole(User vo) throws Exception {
		String hql = "UPDATE User AS u SET u.role.rid=?,u.level=? WHERE u.userid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getRole().getRid()) ;
		query.setParameter(1, vo.getLevel()) ;
		query.setParameter(2, vo.getUserid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doUpdateLock(Set<String> ids, Integer lock) throws Exception {
		StringBuffer buf = new StringBuffer() ;
		buf.append("UPDATE ").append(" User SET lockflag=").append(lock).append(" WHERE ")
				.append("userid").append(" IN (");
		Iterator<?> iter = ids.iterator() ;
		while (iter.hasNext()) {
			buf.append("'").append(iter.next()).append("'").append(",") ;
		}
		buf.delete(buf.length() - 1, buf.length()).append(")") ;
		buf.append(" AND level>1") ;
		Query query = this.getQuery(buf.toString()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public List<User> findAllByLevel(Integer level) throws Exception {
		String hql = "FROM User AS u WHERE u.level=? AND u.lockflag=0" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, level) ;
		return query.list();
	}
}
