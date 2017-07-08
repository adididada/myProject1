package cn.mldn.office.service.admin;

import java.util.List;

import cn.mldn.office.pojo.Tasktype;

public interface ITasktypeServiceAdmin {
	/**
	 * 定义任务类型的增加操作
	 * @param vo
	 * @return 增加成功返回true，否则返回false，但是这个时候的VO类的ID是有内容的
	 * @throws Exception
	 */
	public boolean insert(Tasktype vo) throws Exception ;
	/**
	 * 任务类型修改操作
	 * @param vo 
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(Tasktype vo) throws Exception ;
	/**
	 * 任务类型的列表显示 
	 * @return
	 * @throws Exception
	 */
	public List<Tasktype> list() throws Exception ;
}
