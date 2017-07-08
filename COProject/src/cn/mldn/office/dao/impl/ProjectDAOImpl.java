package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IProjectDAO;
import cn.mldn.office.pojo.Project;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class ProjectDAOImpl extends AbstractDAOImpl implements IProjectDAO {

	@Override
	public boolean doCreate(Project vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Project vo) throws SQLException {
		String hql = "UPDATE Project AS p SET p.title=?,p.note=?,p.userByMgr.userid=?,p.name=? WHERE p.proid=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getTitle()) ;
		query.setParameter(1, vo.getNote()) ;
		query.setParameter(2, vo.getUserByMgr().getUserid()) ;
		query.setParameter(3, vo.getName()) ;
		query.setParameter(4, vo.getProid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Project findById(Integer id) throws SQLException {
		return (Project) super.getSession().get(Project.class, id);
	}

	@Override
	public List<Project> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		return super.handleListSplit(Project.class, currentPage, lineSize, column, keyWord);
	}

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		return super.handleCount("Project", column, keyWord);
	}

}
