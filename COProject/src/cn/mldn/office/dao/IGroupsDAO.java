package cn.mldn.office.dao;

import java.util.List;

import cn.mldn.office.pojo.Groups;
import cn.mldn.util.dao.IDAO;

public interface IGroupsDAO extends IDAO<Integer,Groups> {
	/**
	 * 利用子查询查询一个角色对应的所有权限组的信息，这样的做法：避免掉Hibernate中自动级联时多余的查询以及性能低下多表查询
	 * @param rid 角色编号
	 * @return 一个角色具备的所有权限组信息
	 * @throws Exception
	 */
	public List<Groups> findAllByRole(Integer rid) throws Exception ;
}
