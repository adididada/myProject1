package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IRoleDAO;
import cn.mldn.office.pojo.Role;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class RoleDAOImpl extends AbstractDAOImpl implements IRoleDAO {

	@Override
	public boolean doCreate(Role vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Role vo) throws SQLException {
		super.getSession().update(vo);// 因为牵扯到级联数据的维护
		return true ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		return false;
	}

	@Override
	public Role findById(Integer id) throws SQLException {
		return (Role) super.getSession().get(Role.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAll() throws SQLException {
		return super.handleList(Role.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		return super.handleListSplit(Role.class, currentPage, lineSize, column, keyWord);
	}

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		return super.handleCount("Role", column, keyWord);
	}

	@Override
	public Role findByTitle(String title) throws Exception {
		String hql = "FROM Role AS r WHERE r.title=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, title) ;
		return (Role) query.uniqueResult() ;
	}

	@Override
	public Role findByTitleAndNotId(String title, Integer rid) throws Exception {
		String hql = "FROM Role AS r WHERE r.title=? AND r.rid!=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, title) ;
		query.setParameter(1, rid) ;
		return (Role) query.uniqueResult() ;
	}

	@Override
	public Map<Integer, Boolean> findRoleGroups(Integer rid) throws Exception {
		Map<Integer,Boolean> map = new HashMap<Integer,Boolean>() ;
		String sql = "SELECT gid FROM role_groups WHERE rid=?" ;
		Query query = super.getSQLQuery(sql) ;
		query.setParameter(0, rid) ;
		List all = query.list() ;
		Iterator iter = all.iterator() ;
		while(iter.hasNext()) {
			Integer gid = (Integer) iter.next() ;
			map.put(gid, true) ;
		}
		return map ; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAllAdmin() throws Exception {
		String hql = "FROM Role AS r WHERE r.rid NOT IN(4,5)" ;
		Query query = super.getQuery(hql) ;
		return query.list();
	}

}
