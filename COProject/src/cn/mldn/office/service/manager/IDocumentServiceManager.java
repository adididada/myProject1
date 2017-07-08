package cn.mldn.office.service.manager;

import java.util.Map;
import java.util.Set;

import cn.mldn.office.pojo.Document;

public interface IDocumentServiceManager {
	/**
	 * 数据增加前的查询操作，要执行如下功能：<br>
	 * <li>调用IDoctypeDAO.findAll()查询全部类型</li>
	 * @return 数据以Map集合的方式返回，包含有如下信息：<br>
	 * <li>key = allDoctypes、value = IDoctypeDAO.findAll()</li>
	 * @throws Exception
	 */
	public Map<String,Object> insertPre() throws Exception ;
	/**
	 * 文档数据的增加操作，调用IDocument.doCreate()，增加前需要处理好发布日期
	 * @param vo
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean insert(Document vo) throws Exception ;
	/**
	 * 数据增加前的查询操作，要执行如下功能：<br>
	 * <li>调用IDoctypeDAO.findAll()查询全部类型</li>
	 * <li>调用IDocumentDAO.findById()根据编号查询出指定的文档内容</li>
	 * @return 数据以Map集合的方式返回，包含有如下信息：<br>
	 * <li>key = allDoctypes、value = IDoctypeDAO.findAll()</li>
	 * <li>key = document、value = IDocumentDAO.findById()</li>
	 * @throws Exception
	 */
	public Map<String,Object> updatePre(int did) throws Exception ;
	/**
	 * 文档的更新操作，调用IDocumentDAO.doUpdate()方法
	 * @param vo
	 * @return 更新成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(Document vo) throws Exception ;
	/**
	 * 文档数据的删除操作
	 * @param ids 要删除的ID集合
	 * @param userid 发出删除操作的用户编号
	 * @return 删除成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean delete(Set<Integer> ids,String userid) throws Exception ;
	
	/**
	 * 分页查询出所有的文档数据，要执行如下的操作：<br>
	 * <li>为了可以显示出文档类型名字，那么执行IDoctypeDAO.findAll()</li>
	 * <li>分页查询文档数据，执行IDocumentDAO.findAllSplit()</li>
	 * <li>统计查询个数，执行IDocumentDAO.getAllCount()</li>
	 * @param currentPage 
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回的结果包含有如下内容：<br>
	 * <li>key = allDocuments、value = IDocumentDAO.findAllSplit()</li>
	 * <li>key = documentCount、value = IDocumentDAO.getAllCount()</li>
	 * <li>key = allDoctypes、value = IDoctypesDAO.findAll()</li>
	 * @throws Exception
	 */ 
	public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception ;
	
	/**
	 * 根据用户分页查询出所有的文档数据，要执行如下的操作：<br>
	 * <li>为了可以显示出文档类型名字，那么执行IDoctypeDAO.findAll()</li>
	 * <li>分页查询文档数据，执行IDocumentDAO.findAllSplitByUser()</li>
	 * <li>统计查询个数，执行IDocumentDAO.getAllCountByUser()</li>
	 * @param userid 要进行管理的用户名称
	 * @param currentPage 
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回的结果包含有如下内容：<br>
	 * <li>key = allDocuments、value = IDocumentDAO.findAllSplitByUser()</li>
	 * <li>key = documentCount、value = IDocumentDAO.getAllCountByUser()</li>
	 * <li>key = allDoctypes、value = IDoctypesDAO.findAll()</li>
	 * @throws Exception
	 */ 
	public Map<String,Object> listByUser(String userid,int currentPage,int lineSize,String column,String keyWord) throws Exception ;
	/**
	 * 查询一个文档的完整信息
	 * @param did
	 * @return
	 * @throws Exception
	 */
	public Document show(int did) throws Exception ;
}
