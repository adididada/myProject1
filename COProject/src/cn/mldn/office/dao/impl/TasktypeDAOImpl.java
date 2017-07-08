package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.ITasktypeDAO;
import cn.mldn.office.pojo.Tasktype;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class TasktypeDAOImpl extends AbstractDAOImpl implements ITasktypeDAO {

	@Override
	public boolean doCreate(Tasktype vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Tasktype vo) throws SQLException {
		String hql = "UPDATE Tasktype SET title=? WHERE ttid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getTitle()) ;
		query.setParameter(1, vo.getTtid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tasktype findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tasktype> findAll() throws SQLException {
		return super.handleList(Tasktype.class);
	}

	@Override
	public List<Tasktype> findAllSplit(Integer currentPage, Integer lineSize,
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


}
