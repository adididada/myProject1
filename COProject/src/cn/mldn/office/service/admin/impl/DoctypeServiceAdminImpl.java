package cn.mldn.office.service.admin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IDoctypeDAO;
import cn.mldn.office.pojo.Doctype;
import cn.mldn.office.service.admin.IDoctypeServiceAdmin;
@Service
public class DoctypeServiceAdminImpl implements IDoctypeServiceAdmin {
	@Resource
	private IDoctypeDAO doctypeDAO ;
	@Override
	public boolean insert(Doctype vo) throws Exception {
		return this.doctypeDAO.doCreate(vo);
	}

	@Override
	public boolean update(Doctype vo) throws Exception {
		return this.doctypeDAO.doUpdate(vo);
	}

	@Override
	public List<Doctype> list() throws Exception {
		return this.doctypeDAO.findAll();
	}

}
