package cn.mldn.office.service.admin;

import java.util.Map;

import cn.mldn.office.pojo.Groups;

public interface IGroupsServiceAdmin {
	/**
	 * 进行权限组信息的列表操作，要执行如下功能：
	 * <li>调用IGroupsDAO.findAllSplit()取出全部的分页数据</li>
	 * <li>调用IGroupsDAO.getAllCount()取出全部的数据量</li>
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 以Map集合返回，返回的数据包含如下内容：<br>
	 * <li>key = allGroups、value = IGroupsDAO.findAllSplit()</li>
	 * <li>key = groupsCount、value = IGroupsDAO.getAllCount()</li>
	 * @throws Exception 
	 */
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception;
	/**
	 * 实现权限组的修改操作，调用IGroupsDAO.doUpdate()
	 * @param vo 要更新的VO数据
	 * @return 更新成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(Groups vo) throws Exception ;
	/**
	 * 显示一个权限组的完整内容，要求同时查询出此权限组对应的所有权限信息
	 * 在查询权限信息的时候可以利用对象的持久态完成
	 * @param gid 权限组的编号
	 * @return 如果查询到内容则以POJO对象返回，否则返回null
	 * @throws Exception
	 */
	public Groups show(int gid) throws Exception ;
}
