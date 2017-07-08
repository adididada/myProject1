package cn.mldn.office.action.common;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Document;
import cn.mldn.office.service.common.IDocumentServiceCommon;
import cn.mldn.util.action.AbstractAction;

@Repository
@ParentPackage("root")
@Namespace("/pages/jsp/common/document")
@Action("DocumentActionCommon")
@InterceptorRef("commonStack")
@SuppressWarnings("serial")
public class DocumentActionCommon extends AbstractAction {
	@Resource
	private IDocumentServiceCommon documentServiceCommon ;
	private Document document = new Document();
	public Document getDocument() {
		return document;
	}
	public void show() {
		try {
			Document document = this.documentServiceCommon.show(this.document
					.getDid());
			JSONObject obj = new JSONObject() ;
			obj.put("did", document.getDid()) ;
			obj.put("title", document.getTitle()) ;
			obj.put("pubdate", super.formatDate(document.getPubdate()));
			obj.put("content", document.getContent()) ;
			obj.put("file", document.getFile()) ;
			obj.put("userid", document.getUser().getUserid()) ;
			obj.put("doctype", document.getDoctype().getTitle()) ;
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getTypeName() {
		return null;
	}

	@Override
	public String getDefaultColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnData() {
		// TODO Auto-generated method stub
		return null;
	}
}
