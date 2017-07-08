package cn.mldn.office.dao;

import java.util.List;
import java.util.Set;

import cn.mldn.office.pojo.Document;
import cn.mldn.util.dao.IDAO;

public interface IDocumentDAO extends IDAO<Integer, Document> {
	/**
	 * 根据用户的名称查询自己所发布的所有文档信息
	 * @param userid 用户ID
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 所有用户发送的文档信息，以分页的形式返回
	 * @throws Exception
	 */
	public List<Document> findAllSplitByUser(String userid,
			Integer currentPage, Integer lineSize, String column, String keyWord)
			throws Exception;
	/**
	 * 根据用户名称查询出用户自己发布文档的数量
	 * @param userid
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByUser(String userid, String column,
			String keyWord) throws Exception;
	/**
	 * 删除指定用户的文档数据
	 * @param userid 要删除文档的用户信息
	 * @param ids 要删除的文档编号
	 * @return
	 * @throws Exception
	 */
	public boolean doRemoveBatchByUser(String userid,Set<Integer> ids) throws Exception ;
}
