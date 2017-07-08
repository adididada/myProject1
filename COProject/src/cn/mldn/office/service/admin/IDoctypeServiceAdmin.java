package cn.mldn.office.service.admin;

import java.util.List;

import cn.mldn.office.pojo.Doctype;

public interface IDoctypeServiceAdmin {
	/**
	 * 定义文档类型的增加操作
	 * @param vo
	 * @return 增加成功返回true，否则返回false，但是这个时候的VO类的ID是有内容的
	 * @throws Exception
	 */
	public boolean insert(Doctype vo) throws Exception ;
	/**
	 * 文档类型修改操作
	 * @param vo 
	 * @return 修改成功返回true，否则返回false
	 * @throws Exception
	 */
	public boolean update(Doctype vo) throws Exception ;
	/**
	 * 文档类型的列表显示
	 * @return
	 * @throws Exception
	 */
	public List<Doctype> list() throws Exception ;
}
