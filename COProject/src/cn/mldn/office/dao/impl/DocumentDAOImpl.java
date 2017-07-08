package cn.mldn.office.dao.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.mldn.office.dao.IDocumentDAO;
import cn.mldn.office.pojo.Document;
import cn.mldn.util.dao.AbstractDAOImpl;
@Component
public class DocumentDAOImpl extends AbstractDAOImpl implements IDocumentDAO {
	@Override
	public boolean doCreate(Document vo) throws SQLException {
		return super.getSession().save(vo) != null ;
	}

	@Override
	public boolean doUpdate(Document vo) throws SQLException {
		String hql = "UPDATE Document SET dtid=?,title=?,content=?,file=? WHERE did=?" ;
		Query query = super.getQuery(hql) ;
		query.setParameter(0, vo.getDoctype().getDtid()) ;
		query.setParameter(1, vo.getTitle()) ;
		query.setParameter(2, vo.getContent()) ;
		query.setParameter(3, vo.getFile()) ;
		query.setParameter(4, vo.getDid()) ;
		return query.executeUpdate() > 0 ;
	}

	@Override
	public boolean doRemoveBatch(Set<Integer> ids) throws SQLException {
		return super.handleRemoveBatch("Document", "did", ids);
	}

	@Override
	public Document findById(Integer id) throws SQLException {
		return (Document) super.getSession().get(Document.class, id);
	}

	@Override
	public List<Document> findAll() throws SQLException {
		return super.handleList(Document.class);
	}

	@Override
	public List<Document> findAllSplit(Integer currentPage, Integer lineSize,
			String column, String keyWord) throws SQLException {
		return super.handleListSplit(Document.class, currentPage, lineSize, column, keyWord);
	}

	@Override
	public Integer getAllCount(String column, String keyWord)
			throws SQLException {
		return super.handleCount("Document", column, keyWord) ;
	}

	@Override
	public List<Document> findAllSplitByUser(String userid,
			Integer currentPage, Integer lineSize, String column, String keyWord)
			throws Exception {
		Criteria criteria = this.getCriteria(Document.class);
		criteria.add(Restrictions.and(
				Restrictions.like(column, "%" + keyWord + "%"),
				Restrictions.eq("user.userid", userid)));
		criteria.setFirstResult((currentPage - 1) * lineSize);
		criteria.setMaxResults(lineSize);
		return criteria.list() ; 
	}

	@Override
	public Integer getAllCountByUser(String userid, String column,
			String keyWord) throws Exception {
		String hql = "SELECT COUNT(*) FROM Document AS p WHERE p."
				+ column + " LIKE ? AND p.user.userid=?";
		Query query = this.getQuery(hql);
		query.setParameter(0, "%" + keyWord + "%");
		query.setParameter(1, userid) ;
		return ((Long) query.uniqueResult()).intValue();
	}

	@Override
	public boolean doRemoveBatchByUser(String userid, Set<Integer> ids)
			throws Exception {
		StringBuffer buf = new StringBuffer() ;
		buf.append("DELETE FROM Document").append(" WHERE did").append(" IN (");
		Iterator<?> iter = ids.iterator() ;
		while (iter.hasNext()) {
			buf.append(iter.next()).append(",") ;
		}
		buf.delete(buf.length() - 1, buf.length()).append(")") ;
		buf.append(" AND userid='").append(userid).append("'") ;
		Query query = this.getQuery(buf.toString()) ;
		return query.executeUpdate() > 0 ; 
	}

}
