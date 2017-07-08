package cn.mldn.office.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Doctype;
import cn.mldn.office.service.admin.IDoctypeServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "doctype.list", location = "/pages/jsp/admin/document/admin_document_type.jsp"),
		@Result(name = "insertVF", location = "/pages/jsp/admin/document/admin_document_type.jsp"),
		@Result(name = "updateVF", location = "/pages/jsp/admin/document/admin_document_type.jsp") })
@Namespace("/pages/jsp/admin/document")
@Action("DoctypeActionAdmin")
public class DoctypeActionAdmin extends AbstractAction {
	private static String insertRule = "doctype.title:string";
	private static String updateRule = "doctype.dtid:int|doctype.title|string";
	@Resource
	private IDoctypeServiceAdmin doctypeSeviceAdmin;
	private Doctype doctype = new Doctype();

	public Doctype getDoctype() {
		return doctype;
	}
	
	public void insert() {
		// 必须要考虑到文档类型编号的取得
		JSONObject obj = new JSONObject() ;
		try {
			obj.put("flag", this.doctypeSeviceAdmin.insert(this.doctype)) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.put("dtid", this.doctype.getDtid()) ;
		super.print(obj);
	}

	public String list() { // 数据分页显示
		try {
			super.getRequest().setAttribute("allDoctypes",
					this.doctypeSeviceAdmin.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doctype.list";
	}

	public void update() {
		try {
			super.print(this.doctypeSeviceAdmin.update(this.doctype));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTypeName() {
		return "文档类型";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "类型标题:title";
	}

}
