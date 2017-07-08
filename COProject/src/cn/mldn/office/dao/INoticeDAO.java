package cn.mldn.office.dao;

import java.util.List;
import java.util.Map;

import cn.mldn.office.pojo.Notice;
import cn.mldn.util.dao.IDAO;

public interface INoticeDAO extends IDAO<Integer, Notice> {
	/**
	 * 公告级别修改
	 * @param snid 公告编号 
	 * @param level 修改的级别
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean doUpdateLevel(Integer snid,Integer level) throws Exception ;
	/**
	 * 读取没有阅读过的公告的编号数据，目的是在页面显示
	 * @param userid 要判断的用户ID
	 * @return key = 公告的编号、value = 是否阅读（true）
	 * @throws Exception
	 */
	public Map<Integer,Boolean> findUnread(String userid,Integer level) throws Exception ;
	/**
	 * 根据用户的编号以及用户的级别读取出未读的公告数据量
	 * @param userid 用户编号
	 * @param level 用户级别
	 * @return 返回统计的数据量信息
	 * @throws Exception
	 */
	public Integer getAllCountUnread(String userid,Integer level) throws Exception ;
	/**
	 * 根据公告编号以及用户的级别读取信息
	 * @param id
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public Notice findByIdAndLevel(Integer id,Integer level) throws Exception ;
	/**
	 * 根据级别查询所有的公告信息
	 * @param level
	 * @param currentPage
	 * @param lineSize
	 * @param column
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public List<Notice> findAllSplitByLevel(Integer level,Integer currentPage,Integer lineSize,String column,String keyWord) throws Exception ;
	public Integer getAllCountByLevel(Integer level,String column,String keyWord) throws Exception ;
}
