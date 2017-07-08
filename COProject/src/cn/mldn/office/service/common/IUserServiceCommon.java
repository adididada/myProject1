package cn.mldn.office.service.common;

import cn.mldn.office.pojo.User;

public interface IUserServiceCommon {
	/**
	 * 用户登录功能，在本操作之中要进行如下的操作：<br>
	 * <li>需要使用IUserDAO.findLogin()方法进行用户名和密码的查询，如果正确则返回POJO对象，否则返回null，利用是否为null判断是否登录成功</li>
	 * <li>用户并不需要角色的具体信息，只需要角色的编号，根据角色的编号查询出所有的权限组信息，调用IGroupsDAO.findAllByRole()方法</li>
	 * <li>根据每一个权限组取出所有的对应权限操作，这一点可以利用Hibernate的数据级联操作完成</li>
	 * <li>利用对象的持久态，更新最后一次登录日期！</li>
	 * @param userid 用户名
	 * @param password 密码
	 * @return 如果登录成功返回的是POJO类对象，如果登录失败返回的是null
	 * @throws Exception
	 */
	public User login(String userid, String password)
			throws Exception;
	/**
	 * 进行密码的修改操作，本操作需要按照如下步骤进行：<br>
	 * <li>首先利用IUserDAO.findLogin()方法判断密码是否正确。</li>
	 * <li>如果正确，则利用IUserDAO.doUpdatePassword()方法更新密码。<li>
	 * @param userid 用户ID
	 * @param oldpass 原始密码
	 * @param newpass 新的密码
	 * @return 密码修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean updatePassword(String userid, String oldpass, String newpass)
			throws Exception;
	/**
	 * 进行数据的ID查询操作，使用IUserDAO.findById()
	 * @param userid 用户编号
	 * @return 数据以POJO类对象的形式返回
	 * @throws Exception
	 */
	public User updatePre(String userid) throws Exception ;
	/**
	 * 执行用户的更新操作，使用IUserDAO.doUpdate()
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean update(User vo) throws Exception ; 
	
	/**
	 * 查询一个用户的完整信息
	 * @param id 要查询的管理员数据
	 * @return 数据以POJO类的形式返回，否则返回null
	 * @throws Exception
	 */
	public User show(String id) throws Exception ;
}
