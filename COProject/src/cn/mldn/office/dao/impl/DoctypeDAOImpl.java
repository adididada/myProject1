package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IDoctypeDAO;
import cn.mldn.office.pojo.Doctype;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class DoctypeDAOImpl extends AbstractDAOImpl implements IDoctypeDAO {

	@Override
	public boolean doCreate(Doctype vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Doctype vo) throws SQLException {
		String hql = "UPDATE Doctype SET title=? WHERE dtid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getTitle()) ;
		query.setParameter(1, vo.getDtid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Doctype findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public List<Doctype> findAll() throws SQLException {
		return super.handleList(Doctype.class);
	}

	@Override
	public List<Doctype> findAllSplit(Integer currentPage, Integer lineSize,
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
