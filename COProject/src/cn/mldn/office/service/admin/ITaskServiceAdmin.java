package cn.mldn.office.service.admin;

import java.util.Map;

public interface ITaskServiceAdmin {
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
}
