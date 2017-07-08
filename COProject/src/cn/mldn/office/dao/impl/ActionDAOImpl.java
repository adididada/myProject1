package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IActionDAO;
import cn.mldn.office.pojo.Action;
import cn.mldn.util.dao.AbstractDAOImpl;

@Component
public class ActionDAOImpl extends AbstractDAOImpl implements IActionDAO {

	@Override
	public boolean doCreate(Action vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doUpdate(Action vo) throws SQLException {
		String hql = "UPDATE Action AS a SET a.title=?,a.url=? WHERE a.actid=?";
		Query query = super.getQuery(hql);
		query.setParameter(0, vo.getTitle());
		query.setParameter(1, vo.getUrl());
		query.setParameter(2, vo.getActid());
		return query.executeUpdate() > 0;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Action findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> findAll() throws SQLException {
		return super.handleList(Action.class);
	} 

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		return super.handleListSplit(Action.class, currentPage, lineSize,
				column, keyWord);
	} 

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		return super.handleCount("Action", column, keyWord);
	}

	@Override
	public List<Action> findAllByGroups(Integer gid) throws Exception {
		String hql = "FROM Action AS a WHERE a.groups.gid=?";
		Query query = super.getQuery(hql);
		query.setParameter(0, gid);
		return query.list();
	}

}
