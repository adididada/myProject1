package cn.mldn.office.service.admin;

import java.util.Map;
import java.util.Set;

import cn.mldn.office.pojo.Notice;

public interface INoticeServiceAdmin {
	/**
	 * 公告的发布操作，只有管理员可以进行公告的发布，该功能执行如下操作：<br>
	 * <li>利用IUserDAO.findById()操作判断用户的级别是否是管理员（0或者1）</li>
	 * <li>利用INoticeDAO.doCreate()保存公告信息</li>
	 * @param vo
	 * @return 增加成功返回true，如果增加的不是管理员或者增加失败返回false
	 * @throws Exception
	 */
	public boolean insert(Notice vo) throws Exception ;
	/**
	 * 公告修改前的数据查询操作，调用的INoticeDAO.findById()
	 * @param snid
	 * @return
	 * @throws Exception
	 */
	public Notice updatePre(Integer snid) throws Exception ;
	/**
	 * 公告的维护操作，只有管理员可以维护公告，该功能执行如下操作：<br>
	 * <li>利用IUserDAO.findById()操作判断用户的级别是否是管理员（0或者1）</li>
	 * <li>利用INoticeDAO.doUpdate()修改公告信息</li>
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean update(Notice vo) throws Exception ;
	/**
	 * 删除公告信息，调用INoticeDAO.doRemoveBatch()方法
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Set<Integer> ids) throws Exception ;
	/**
	 * 进行公告的分页列表显示
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return 返回的结果包含如下数据：<br>
	 * <li>key = allNotices、value = INoticeDAO.findAllSplit()</li>
	 * <li>key = noticeCount、value = INoticeDAO.getAllCount()</li>
	 * @throws Exception
	 */
	public Map<String,Object> list(int currentPage,int lineSize,String column,String keyWord) throws Exception ;
	/**
	 * 公告级别修改
	 * @param snid 公告的编号
	 * @param level 级别号，只有2和3两个取值
	 * @return 修改成功返回true、修改失败返回false
	 * @throws Exception
	 */
	public boolean updateLevel(int snid,int level) throws Exception ;
}
