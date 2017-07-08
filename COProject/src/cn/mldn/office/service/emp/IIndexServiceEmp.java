package cn.mldn.office.service.emp;

import java.util.Map;

public interface IIndexServiceEmp {
	/**
	 * 返回统计信息，包括如下内容：<br>
	 * <li>未读的公告数量。</li>
	 * <li>未完成的任务数量。</li>
	 * <li>刚刚分配的任务数量。</li>
	 * @param userid 要进行统计的用户ID
	 * @return 返回的集合包含有如下的内容：<br>
	 * <li>key = noticeCount、value = INoticeService.getAllCountUnread()</li>
	 * @throws Exception
	 */
	public Map<String,Integer> unreadCount(String userid) throws Exception ;
}
