package cn.mldn.office.dao;

import java.util.List;

import cn.mldn.office.pojo.Action;
import cn.mldn.util.dao.IDAO;

public interface IActionDAO extends IDAO<Integer,Action> {
	/**
	 * 根据权限组的编号查询出所有对应的权限信息
	 * @param gid 权限组编号
	 * @return 返回权限信息 
	 * @throws Exception
	 */
	public List<Action> findAllByGroups(Integer gid) throws Exception ;
}
