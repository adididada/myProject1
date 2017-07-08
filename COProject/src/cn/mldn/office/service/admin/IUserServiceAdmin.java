package cn.mldn.office.service.admin;

import java.util.Map;
import java.util.Set;

import cn.mldn.office.pojo.User;

public interface IUserServiceAdmin {
	/**
	 * 用户的增加操作，但是在本操作之中必须要注意以下情况：<br>
	 * <li>调用IUser.findById()以确定操作的是否是管理员，保证业务的完善性</li>
	 * <li>保证用户名称不能够重复，所以需要使用IUserDAO.findById()判断用户ID是否已存在</li>
	 * <li>如果用户不存在，则进行用户信息保存，级别是根据你的角色来决定的</li>
	 * @param vo 包含有新管理员信息的对象
	 * @param userid 当前操作的管理员编号
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean insert(User vo,String userid) throws Exception ;
	/**
	 * 表示在用户修改前取出所有相关信息，执行如下操作：<br>
	 * <li>调用IUserDAO.findById()</li>
	 * @return 返回的结果以Map形式取得，包含的数据如下：<br>
	 * <li>key = user、value = IUserDAO.findById()</li>
	 * @throws Exception
	 */
	public Map<String,Object> updatePre(String userid) throws Exception ;
	/**
	 * 修改用户信息，本功能执行的操作步骤如下：<br>
	 * <li>如果要修改的数据是超级管理员，那么不允许修改，调用IUserDAO.findById()</li>
	 * <li>如果不是普通用户，则执行修改操作，调用IUserDAO.doUpdateInfo()</li>
	 * @param vo 要修改的用户数据
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(User vo) throws Exception ;
	/**
	 * 用户的批量信息锁定，调用IUserDAO.doUpdateLock()方法
	 * @param ids 包含了所有要删除的用户编号
	 * @return 删除成功返回true，否则返回false
	 * @throws Exception
	 */ 
	public boolean updateLock(Set<String> ids,int lock) throws Exception ;
	/**
	 * 修改用户的角色信息，调用IUserDAO.doUpdateRole()操作
	 * @param vo 包含有用户编号以及角色编号
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean updateRole(User vo) throws Exception ;
	/**
	 * 修改密码操作，调用IUSerDAO.doUpdatePassword()
	 * @param vo 包含有用户编号以及新密码的信息
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean updatePassword(User vo) throws Exception ;
	/**
	 * 检查用户编号是否已经存在，调用IUserDAO.findById()
	 * @param userid 要检查的用户编号
	 * @return 如果不存在表示可以使用返回true，否则返回false
	 * @throws Exception
	 */
	public boolean checkUser(String userid) throws Exception ;
	/**
	 * 进行数据的分页显示操作
	 * @param currentPage 当前所在页
	 * @param lineSize 每页显示的数据行
	 * @param column 模糊查询列
	 * @param keyWord 模糊查询字段
	 * @return 返回的内容以Map的形式保存，包含如下数据：<br>
	 * <li>key = allUsers、value = IUserDAO.findAllUserByLock()</li>
	 * <li>key = userCount、value = IUserDAO.getAllUserByLock()</li>
	 * @throws Exception
	 */
	public Map<String, Object> list(int lockflag,int currentPage, int lineSize,
			String column, String keyWord) throws Exception;
	/**
	 * 查询一个用户的完整信息，这个信息包含有管理员的权限、角色、权限组
	 * @param id 要查询的管理员数据
	 * @return 数据以POJO类的形式返回，否则返回null
	 * @throws Exception
	 */
	public User show(String id) throws Exception ;
	
}
