package cn.mldn.office.service.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.office.dao.IDocumentDAO;
import cn.mldn.office.pojo.Document;
import cn.mldn.office.service.common.IDocumentServiceCommon;
@Service
public class DocumentServiceCommonImpl implements IDocumentServiceCommon {
	@Resource
	private IDocumentDAO documentDAO ;
	@Override
	public Document show(int did) throws Exception {
		Document pojo = this.documentDAO.findById(did) ;
		pojo.getDoctype().getTitle() ;	// 加载文档类型
		return pojo ;
	}

}
