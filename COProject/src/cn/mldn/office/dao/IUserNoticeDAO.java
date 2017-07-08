package cn.mldn.office.dao;

import cn.mldn.office.pojo.UserNotice;

public interface IUserNoticeDAO {
	/**
	 * 增加用于的阅读记录信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean doCreate(UserNotice vo) throws Exception ;
	/**
	 * 判断用户的阅读记录是否已经存在
	 * @param userid
	 * @param snid
	 * @return 如果存在返回true，否则返回false 
	 * @throws Exception
	 */
	public boolean findByExists(String userid,Integer snid) throws Exception ;
}
