package cn.mldn.office.service.emp;

import java.util.Map;

import cn.mldn.office.pojo.Task;

public interface ITaskServiceEmp {
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
	 * 实现任务完成的回单填写，要执行如下功能：<br>
	 * <li>首先要判断当前的完成用户是否是任务规定的用户</li>
	 * <li>要判断当前的任务状态是什么，只有0或1的时候才可以更新数据</li>
	 * <li>执行更新回单的操作方法</li>
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateFinish(Task vo) throws Exception ; 
	
	/**
	 * 显示一个任务的详细信息，同时更新状态
	 * @param id 要读取的任务
	 * @param userid 要操作的用户
	 * @return
	 * @throws Exception
	 */
	public Task show(int id,String userid) throws Exception ;
	
	/**
	 * 根据任务执行者的ID，查找出他所要执行的所有任务信息
	 * @param userid
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回有如下的数据：<br>
	 * <li>key = allTasks、value = ITaskDAO.findAllByEmp()</li>
	 * <li>key = taskCount、value = ITaskDAO.getAllCountByEmp()</li>
	 * @throws Exception
	 */
	public Map<String, Object> listByReceiver(String userid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception;

}
