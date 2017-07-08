package cn.mldn.office.service.emp;

import java.util.Map;

import cn.mldn.office.pojo.Notice;

public interface INoticeServiceEmp {
	/**
	 * 公告的数据列表，但是在进行列表的时候要考虑到未读的数据情况：<br>
	 * <li>读取所有的公告数据，使用INoticeDAO.findAll()</li>
	 * <li>读取所有的公告数量，用于分页，使用INoticeDAO.getAllCount()</li>
	 * <li>读取未阅读公告的编号信息，使用INoticeDAO.findUnread()</li>
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回的内容包含有如下信息：<br>
	 * <li>key = allNotices、value = INoticeDAO.findAll()</li>
	 * <li>key = noticeCount、value = INoticeDAO.getAllCount()</li>
	 * <li>key = unread、value = INoticeDAO.findUnread()</li>
	 * @throws Exception
	 */
	public Map<String,Object> list(String userid,int currentPage,int lineSize,String column,String keyWord) throws Exception ;
	/**
	 * 进行数据的异步信息显示，此读取过程包含如下操作：<br>
	 * <li>首先读取公告信息内容。</li>
	 * <li>使用IUserNoticeDAO.findByExists()判断这个公告信息当前用户是否已经读取过了。</li>
	 * <li>如果这个用户没有读取过，则使用IUserNoticeDAO.doCreate()保存读取记录</li>
	 * <li>如果没有读取过，则进行阅读量的增加，直接利用对象的持久态就可以完成更新了</li>
	 * @param snid 要读取的公告编号
	 * @param userid 当前读取此公告的用户编号
	 * @return 公告信息
	 * @throws Exception
	 */
	public Notice show(int snid,String userid) throws Exception ;
	
}
