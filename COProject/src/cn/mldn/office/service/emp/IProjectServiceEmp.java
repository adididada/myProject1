package cn.mldn.office.service.emp;

import java.util.Map;

import cn.mldn.office.pojo.Project;

public interface IProjectServiceEmp {
	/**
	 * 分页显示出所有的项目信息，包含如下操作：<br>
	 * <li>调用IProjectDAO.findAll()方法查询出全部的项目内容</li>
	 * <li>调用IProjectDAO.getAllCount()方法统计项目的个数</li>
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回的集合包含如下数据：<br>
	 * <li>key = allProjects、value = IProjectDAO.findAll()</li>
	 * <li>key = projectCount、value = IProjectDAO.getAllCount()</li>
	 * @throws Exception
	 */
	public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception ;
	/**
	 * 项目信息的显示操作，调用IProjectDAO.findById()方法
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Project show(int id) throws Exception ; 
}
