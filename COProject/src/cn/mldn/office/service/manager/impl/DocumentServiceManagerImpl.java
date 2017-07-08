package cn.mldn.office.service.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IDoctypeDAO;
import cn.mldn.office.dao.IDocumentDAO;
import cn.mldn.office.pojo.Document;
import cn.mldn.office.service.manager.IDocumentServiceManager;
@Service
public class DocumentServiceManagerImpl implements IDocumentServiceManager {
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
		// 要判断当前修改这个文档的用户是否是此发布用户，修改的用户保存在了Document对象中
		Document pojo = this.documentDAO.findById(vo.getDid()) ;
		if (vo.getUser().getUserid().equals(pojo.getUser().getUserid())) {
			return this.documentDAO.doUpdate(vo);
		}
		return false ;
	}

	@Override
	public boolean delete(Set<Integer> ids, String userid) throws Exception {
		if (ids.size() == 0) {
			return false ;
		}
		return this.documentDAO.doRemoveBatchByUser(userid,ids); 
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

	@Override
	public Map<String, Object> listByUser(String userid, int currentPage,
			int lineSize, String column, String keyWord) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>() ;
		map.put("allDoctypes", this.doctypeDAO.findAll()) ;
		map.put("allDocuments", this.documentDAO.findAllSplitByUser(userid,currentPage, lineSize, column, keyWord)) ;
		map.put("documentCount", this.documentDAO.getAllCountByUser(userid,column, keyWord)) ;
		return map;
	}

	@Override
	public Document show(int did) throws Exception {
		Document pojo = this.documentDAO.findById(did) ;
		pojo.getDoctype().getTitle() ;	// 加载文档类型
		return pojo ;
	}

}
