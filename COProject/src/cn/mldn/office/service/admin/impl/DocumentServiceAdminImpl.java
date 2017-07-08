package cn.mldn.office.service.admin.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IDoctypeDAO;
import cn.mldn.office.dao.IDocumentDAO;
import cn.mldn.office.pojo.Document;
import cn.mldn.office.service.admin.IDocumentServiceAdmin;
@Service
public class DocumentServiceAdminImpl implements IDocumentServiceAdmin {
	@Resource
	private IDocumentDAO documentDAO ;
	@Resource
	private IDoctypeDAO doctypeDAO ;
	@Override
	public Map<String, Object> insertPre() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allDoctypes", this.doctypeDAO.findAll()) ;
		return map;
	}

	@Override
	public boolean insert(Document vo) throws Exception {
		vo.setPubdate(new Date());
		return this.documentDAO.doCreate(vo);
	}

	@Override
	public Map<String, Object> updatePre(int did) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allDoctypes", this.doctypeDAO.findAll()) ;
		map.put("document", this.documentDAO.findById(did)) ;
		return map;
	}

	@Override
	public boolean update(Document vo) throws Exception {
		return this.documentDAO.doUpdate(vo);
	}

	@Override
	public boolean delete(Set<Integer> ids) throws Exception {
		if (ids.size() == 0) {
			return false ;
		}
		return this.documentDAO.doRemoveBatch(ids); 
	}

	@Override
	public Map<String, Object> list(int currentPage, int lineSize,
			String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allDoctypes", this.doctypeDAO.findAll()) ;
		map.put("allDocuments", this.documentDAO.findAllSplit(currentPage, lineSize, column, keyWord)) ;
		map.put("documentCount", this.documentDAO.getAllCount(column, keyWord)) ;
		return map;
	}

}
