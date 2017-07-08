package cn.mldn.office.service.admin.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IActionDAO;
import cn.mldn.office.pojo.Action;
import cn.mldn.office.service.admin.IActionServiceAdmin;
@Service
public class ActionServiceAdminImpl implements IActionServiceAdmin {
	@Resource
	private IActionDAO actionDAO ;
	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allActions", this.actionDAO.findAllSplit(currentPage, lineSize, column, keyWord)) ;
		map.put("actionCount", this.actionDAO.getAllCount(column, keyWord)) ;
		return map; 
	}

	@Override
	public boolean update(Action vo) throws Exception {
		return this.actionDAO.doUpdate(vo); 
	}

}
