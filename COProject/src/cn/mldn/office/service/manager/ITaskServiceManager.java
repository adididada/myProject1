package cn.mldn.office.service.manager;

import java.util.Map;

import cn.mldn.office.pojo.Task;

public interface ITaskServiceManager {
	/**
	 * 在进行项目经理发布任务时需要执行如下操作：<br>
	 * <li>需要找到所有的员工信息（level=3），调用IUserDAO.findAllByLevel(3)</li>
	 * <li>需要找到所有的任务类型信息，调用ITasktypeDAO.findAll()</li> 
	 * @return 返回的内容包含如下信息：<br>
	 * <li>key = allUsers、value = IUserDAO.findAllByLevel(3)</li>
	 * <li>key = allTasktypes、value = ITasktypeDAO.findAll()</li>
	 * @throws Exception
	 */
	public Map<String,Object> insertPre() throws Exception ;
	/**
	 * 项目任务的发布操作，本操作需要执行如下功能：<br>
	 * <li>要判断任务的发布者是否是项目的负责人，调用IProject.findById()取得项目完整信息后判断</li>
	 * <li>要判断当前的项目负责人状态是什么，是否是活跃状态</li>
	 * <li>要判断项目任务接收者的状态是什么，是否是活跃状态</li>
	 * <li>最后一次的更新日期应该为任务的发布日期</li>
	 * @param vo
	 * @return 增加成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean insert(Task vo) throws Exception ;
	/**
	 * 根据项目查询出项目对应的所有任务信息
	 * @param proid 要查询所有任务的项目编号
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回有如下的数据：<br>
	 * <li>key = allTasks、value = ITaskDAO.findAllByProject()</li>
	 * <li>key = taskCount、value = ITaskDAO.getAllCountByProject()</li>
	 * @throws Exception
	 */
	public Map<String, Object> listByProject(int proid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception;
	/**
	 * 任务的取消操作，但是必须完成如下的操作：<br>
	 * <li>取消者必须是项目的负责人</li>
	 * <li>取消后的任务状态必须是2</li>
	 * @param vo
	 * @return 取消成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean updateCancel(Task vo) throws Exception ;
	/**
	 * 在进行项目经理修改任务时需要执行如下操作：<br>
	 * <li>需要找到所有的员工信息（level=3），调用IUserDAO.findAllByLevel(3)</li>
	 * <li>需要找到所有的任务类型信息，调用ITasktypeDAO.findAll()</li>
	 * <li>找到具体的项目信息，调用ITaskDAO.findById()</li> 
	 * @return 返回的内容包含如下信息：<br>
	 * <li>key = allUsers、value = IUserDAO.findAllByLevel(3)</li>
	 * <li>key = allTasktypes、value = ITasktypeDAO.findAll()</li>
	 * @throws Exception
	 */
	public Map<String,Object> updatePre(int tid) throws Exception ;
	/**
	 * 任务信息的修改操作，本操作要执行如下的功能：<br>
	 * <li>要判断任务的发布者是否是项目的负责人，调用IProject.findById()取得项目完整信息后判断</li>
	 * <li>要判断当前的项目负责人状态是什么，是否是活跃状态</li>
	 * <li>要判断项目任务接收者的状态是什么，是否是活跃状态</li>
	 * <li>最后一次的更新日期应该为任务的修改日期</li>
	 * <li>任务数据的更新操作，调用ITaskDAO.doUpdateInfo()方法</li>
	 * @param vo
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(Task vo) throws Exception ;
	/**
	 * 根据任务创建者的ID，查找出他所创建的所有任务信息
	 * @param userid
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回有如下的数据：<br>
	 * <li>key = allTasks、value = ITaskDAO.findAllByProject()</li>
	 * <li>key = taskCount、value = ITaskDAO.getAllCountByProject()</li>
	 * @throws Exception
	 */
	public Map<String, Object> listByCreator(String userid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception;
}
