package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IGroupsDAO;
import cn.mldn.office.pojo.Groups;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class GroupsDAOImpl extends AbstractDAOImpl implements IGroupsDAO {

	@Override
	public boolean doCreate(Groups vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Groups vo) throws SQLException {
		String hql = "UPDATE Groups AS g SET g.title=?,g.note=? WHERE g.gid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getTitle()) ;
		query.setParameter(1, vo.getNote()) ;
		query.setParameter(2, vo.getGid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Groups findById(Integer id) throws SQLException {
		return (Groups) super.getSession().get(Groups.class, id);
	} 

	@SuppressWarnings("unchecked")
	@Override
	public List<Groups> findAll() throws SQLException {
		return super.handleList(Groups.class) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Groups> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		return super.handleListSplit(Groups.class, currentPage, lineSize, column, keyWord);
	} 

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		return super.handleCount("Groups", column, keyWord);
	}

	@Override
	public List<Groups> findAllByRole(Integer rid) throws Exception {
		String sql = "SELECT gid,title,note FROM groups WHERE gid IN ("
				+ " SELECT gid FROM role_groups WHERE rid=? )";
		Query query = super.getSQLQuery(sql);
		query.setResultTransformer(new AliasToBeanResultTransformer(
				Groups.class));
		query.setParameter(0, rid);
		return query.list(); 
	}

}
