package cn.mldn.util.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * 这个类作为DAO子类的公共父类，目的是简化重复代码的开发
 * 
 * @author mldn
 */
public abstract class AbstractDAOImpl {
	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 设置SessionFactory类对象，是在子类构造方法注入的时候自动完成调用
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 返回SessionFactory，一般只有在操作二级缓存的时候才会执行此操作
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 负责提供当前可用的Session对象
	 * 
	 * @return
	 */
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * 利用此方法可以取得Query对象
	 * 
	 * @param hql
	 *            要执行的HQL语句
	 * @return
	 */
	public Query getQuery(String hql) {
		return this.getSession().createQuery(hql);
	}

	/**
	 * 创建SQL查询操作
	 * 
	 * @param sql
	 *            要查询的SQL语句
	 * @return
	 */
	public Query getSQLQuery(String sql) {
		return this.getSession().createSQLQuery(sql);
	}

	/**
	 * 利用此方法取得Criteria对象
	 * 
	 * @param cls
	 *            操作的类型
	 * @return
	 */
	public Criteria getCriteria(Class<?> cls) {
		Criteria criteria = this.getSession().createCriteria(cls);
		return criteria;
	}
	/**
	 * 实现数据量统计的查询操作
	 * @param pojoName 要查询的POJO类的名字
	 * @param column 进行模糊处理时的列名称
	 * @param keyWord 查询关键字
	 * @return 统计的数据行数
	 */
	public Integer handleCount(String pojoName, String column, String keyWord) {
		String hql = "SELECT COUNT(*) FROM " + pojoName + " AS p WHERE p."
				+ column + " LIKE ?";
		Query query = this.getQuery(hql);
		query.setParameter(0, "%" + keyWord + "%");
		return ((Long) query.uniqueResult()).intValue();
	}
	/**
	 * 处理数据的分页显示查询操作
	 * @param cls 要处理的POJO类的名字
	 * @param currentPage 当前所在页
	 * @param lineSize 每页显示的数据行数
	 * @param column 模糊查询列
	 * @param keyWord 模糊查询关键字
	 * @return 查询数据以List集合返回
	 */
	@SuppressWarnings("rawtypes")
	public List handleListSplit(Class<?> cls, Integer currentPage,
			Integer lineSize, String column, String keyWord) {
		Criteria criteria = this.getCriteria(cls);
		criteria.add(Restrictions.like(column, "%" + keyWord + "%"));
		criteria.setFirstResult((currentPage - 1) * lineSize);
		criteria.setMaxResults(lineSize);
		return criteria.list() ; 
	}
	/**
	 * 进行数据的全部列表操作
	 * @param cls 要操作的POJO类型
	 * @return 列表以List集合返回
	 */
	@SuppressWarnings("rawtypes")
	public List handleList(Class<?> cls) {
		Criteria criteria = this.getCriteria(cls);
		return criteria.list() ;
	} 
	/**
	 * 负责处理数据的删除操作
	 * @param ids 包含了所有要删除数据的id
	 * @param pojo 要删除数据的POJO名称
	 * @param idName 主键列的名称
	 * @return
	 */
	public boolean handleRemoveBatch(String pojo , String idName , Set<?> ids) {
		StringBuffer buf = new StringBuffer() ;
		buf.append("DELETE FROM ").append(pojo).append(" WHERE ")
				.append(idName).append(" IN (");
		Iterator<?> iter = ids.iterator() ;
		while (iter.hasNext()) {
			buf.append(iter.next()).append(",") ;
		}
		buf.delete(buf.length() - 1, buf.length()).append(")") ;
		Query query = this.getQuery(buf.toString()) ;
		return query.executeUpdate() > 0 ;
	}
}
