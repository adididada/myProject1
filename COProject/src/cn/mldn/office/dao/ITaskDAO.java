package cn.mldn.office.dao;

import java.util.List;

import cn.mldn.office.pojo.Task;
import cn.mldn.util.dao.IDAO;

public interface ITaskDAO extends IDAO<Integer, Task> {
	/**
	 * 根据项目编号分页查询出所有的任务信息
	 * @param proid 项目编号
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回指定项目的所有任务信息
	 * @throws Exception
	 */
	public List<Task> findAllByProject(Integer proid, Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception;
	/**
	 * 根据项目的编号统计出该项目中的所有任务量
	 * @param proid 项目编号
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByProject(Integer proid,String column,String keyWord) throws Exception ;
	/**
	 * 表示任务的取消操作，取消操作需要更新的是取消时间、状态、取消原因
	 * @param vo 
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateCancel(Task vo) throws Exception ;
	/**
	 * 任务的更新操作，更新操作的时候更新的是任务的标题、执行者、内容、最后一次修改日期
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateInfo(Task vo) throws Exception ;
	/**
	 * 实现雇员完成任务的任务信息修改
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateFinish(Task vo) throws Exception ;
	/**
	 * 列出一个项目经理所发布的所有任务信息
	 * @param userid
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public List<Task> findAllByManager(String userid,Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception ;
	/**
	 * 统计出一个项目经理所发出任务信息的数量
	 * @param userid
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByManager(String userid,String column,String keyWord) throws Exception ;
	/**
	 * 查看所有任务执行者的任务列表
	 * @param userid
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public List<Task> findAllByEmp(String userid,Integer currentPage,
			Integer lineSize, String column, String keyWord) throws Exception ;
	/**
	 * 取得任务执行者的执行任务数量
	 * @param userid
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByEmp(String userid,String column,String keyWord) throws Exception ;
	
	/**
	 * 更新任务的操作状态
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdateStatus(Integer id,Integer status) throws Exception ;
	/**
	 * 根据任务的状态统计一个雇员的相关任务数量
	 * @param userid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Integer getAllCountByStatus(String userid,Integer status) throws Exception ;
}
