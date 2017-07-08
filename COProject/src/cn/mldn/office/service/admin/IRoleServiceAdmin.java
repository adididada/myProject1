package cn.mldn.office.service.admin;

import java.util.Map;

import cn.mldn.office.pojo.Role;

public interface IRoleServiceAdmin {
	/**
	 * 角色增加前的数据查询准备，主要执行如下操作：<br>
	 * <li>因为需要权限组，所以使用IGroupsDAO.findAll()查询</li>
	 * @return 返回的数据以Map集合保存，包含如下内容：<br>
	 * <li>key = allGroups、value = IGroupsDAO.findAll()</li>
	 * @throws Exception
	 */
	public Map<String,Object> insertPre() throws Exception ;
	/**
	 * 角色修改前的数据查询准备，主要执行如下操作：<br>
	 * <li>因为需要权限组，所以使用IGroupsDAO.findAll()查询</li>
	 * <li>根据角色编号查询出已有的角色信息，使用IRoleDAO.findById()查询</li>
	 * <li>为了可以进行数据的回填操作，需要使用IRoleDAO.findRoleGroups()查询每个角色对应的权限组编号<li>
	 * @return 返回的数据以Map集合保存，包含如下内容：<br>
	 * <li>key = allGroups、value = IGroupsDAO.findAll()</li>
	 * <li>key = role、value = IRoleDAO.findById()</li>
	 * <li>key = gids、value = IRoleDAO.findRoleGroups()</li>
	 * @throws Exception
	 */
	public Map<String,Object> updatePre(int rid) throws Exception ;
	/**
	 * 角色信息的列表显示，本功能要执行如下的操作：<br>
	 * <li>查询出所有的角色信息，调用IRoleDAO.findAllSplit()</li>
	 * <li>统计出所有的角色信息数量，调用IRoleDAO.getAllCount()</li>
	 * @param currentPage 当前所在页
	 * @param lineSize 每页显示的数据行
	 * @param column 要模糊查询的数据列
	 * @param keyWord 模糊查询关键字
	 * @return 返回的结果以Map集合形式返回，包含如下的内容：<br>
	 * <li>key = allRoles、value = IRoleDAO.findAllSplit()</li>
	 * <li>key = rolesCount、value = IRoleDAO.getAllCount()</li>
	 * @throws Exception
	 */
	public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception ;
	/**
	 * 角色信息的修改操作，修改的时候调用如下操作：<br>
	 * <li>要保证修改的角色名称与其它名称不重复，调用IRoleDAO.findByTitleAndNotId()</li>
	 * <li>IRoleDAO.doUpdate()方法自动维护关联数据</li>
	 * @param vo 包含了要修改数据的Role对象，同时保存有所有的权限组信息
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(Role vo) throws Exception ;
	/**
	 * 角色数据的增加操作，增加时自动维护权限组关系，包含如下操作：<br>
	 * <li>要保证修改的角色名称与其它名称不重复，调用IRoleDAO.findByTitle()</li>
	 * <li>IRoleDAO.doCreate()方法自动维护关联数据</li>
	 * @param vo 要增加的角色对象
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean insert(Role vo) throws Exception ;
	/**
	 * 检查指定的角色名称是否存在，调用IRoleDAO.findByTitle()方法
	 * @param title 要检查的角色名称
	 * @return 如果角色名称存在返回false，表示不可使用，角色名称不存在返回true，表示可以使用
	 * @throws Exception
	 */
	public boolean checkTitle(String title) throws Exception ;
	/**
	 * 检查排除指定的角色之外的其它角色名称是否存在，调用IRoleDAO.findByTitleAndNotId()方法
	 * @param title 要检查的角色名称
	 * @param rid 要排除的角色编号
	 * @return 如果角色名称存在返回false，表示不可使用，角色名称不存在返回true，表示可以使用
	 * @throws Exception
	 */ 
	public boolean checkTitle(String title,int rid) throws Exception ;
}
