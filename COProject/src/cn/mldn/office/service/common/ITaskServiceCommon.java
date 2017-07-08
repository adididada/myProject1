package cn.mldn.office.service.common;

import cn.mldn.office.pojo.Task;

public interface ITaskServiceCommon {
	/**
	 * 显示一个任务的详细信息，包括项目的名称、任务的类型名称
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Task show(int id) throws Exception ;
}
