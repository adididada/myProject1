package cn.mldn.office.dao;

import java.util.List;
import java.util.Map;

import cn.mldn.office.pojo.Role;
import cn.mldn.util.dao.IDAO;

public interface IRoleDAO extends IDAO<Integer, Role> {
	/**
	 * 主要功能是根据名称取得角色信息，目的是进行Ajax异步验证使用
	 * @param title 角色名称
	 * @return 如果可以找到指定的角色信息以POJO对象返回，否则返回null
	 * @throws Exception
	 */
	public Role findByTitle(String title) throws Exception ;
	/**
	 * 根据角色名称取得角色信息，但是要排除掉指定的角色编号，给更新操作使用的
	 * @param title 角色名称
	 * @param rid 要排除的角色编号
	 * @return 如果可以找到指定的角色信息以POJO对象返回，否则返回null
	 * @throws Exception
	 */
	public Role findByTitleAndNotId(String title,Integer rid) throws Exception ;
	/**
	 * 使用原生的SQL查询出一个角色对应的所有的权限组编号
	 * @param rid 角色编号
	 * @return 如果使用原生SQL查询，那么返回的就是Object[]对象数组，将对象数组变为Map集合，
	 * 其中key保存的是权限组的编号，而Value保存的是是否有此权限，只要有就保存true
	 * @throws Exception
	 */
	public Map<Integer,Boolean> findRoleGroups(Integer rid) throws Exception ;
	/**
	 * 查询出所有的管理员角色，在本程序里面就相当于避免了4、5角色
	 * @return
	 * @throws Exception
	 */
	public List<Role> findAllAdmin() throws Exception ;
}
