package cn.mldn.office.action.emp;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Doctype;
import cn.mldn.office.pojo.Document;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.manager.IDocumentServiceManager;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("empStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "document.list", location = "/pages/jsp/emp/document/emp_document_list.jsp"),
		@Result(name = "document.list.admin", location = "/pages/jsp/emp/document/emp_document_admin.jsp"),
		@Result(name = "document.insert", location = "/pages/jsp/emp/document/emp_document_insert.jsp"),
		@Result(name = "document.update", location = "/pages/jsp/emp/document/emp_document_update.jsp"),
		@Result(name = "insertVF", location = "/pages/jsp/emp/document/emp_document_insert.jsp"),
		@Result(name = "updateVF", location = "/pages/jsp/emp/document/emp_document_update.jsp") })
@Namespace("/pages/jsp/emp/document")
@Action("DocumentActionEmp")
public class DocumentActionEmp extends AbstractAction {
	private static String insertRule = "document.title:string|document.content:string";
	private static String updateRule = "document.did:int|document.title:string|document.content:string";
	@Resource
	private IDocumentServiceManager documentServiceEmp;
	private String ids;
	private File file; // 上传的附件
	private String fileContentType; // 上传的类型

	private String oldfilename;

	private Document document = new Document();
	private Doctype doctype = new Doctype();

	public void setOldfilename(String oldfilename) {
		this.oldfilename = oldfilename;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Doctype getDoctype() {
		return doctype;
	}

	public Document getDocument() {
		return document;
	}

	public String insertPre() {
		try {
			Map<String, Object> map = this.documentServiceEmp.insertPre();
			super.getRequest().setAttribute("allDoctypes",
					map.get("allDoctypes"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document.insert";
	}

	public String insert() {
		this.document.setDoctype(this.doctype);
		User emp = (User) super.getSession().getAttribute("emp");
		this.document.setUser(emp);
		if (this.file.length() > 0) { // 有文件上传
			this.document.setFile(super.createSingleFileName(fileContentType));
		}
		try {
			if (this.documentServiceEmp.insert(this.document)) {
				String filePath = super.getApplication().getRealPath(
						"/upload/document/")
						+ this.document.getFile();
				if (this.file.length() > 0) {
					super.saveSingleFile(filePath, this.file);
				}
				super.setMsgAndUrl("insert.success.msg",
						"emp.document.insert.action");
			} else {
				super.setMsgAndUrl("insert.failre.msg",
						"emp.document.insert.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public String update() {
		if (this.file.length() > 0) { // 有文件上传
			// 原本并没有上传的附件
			this.document.setFile(super.createSingleFileName(fileContentType));
			String filePath = super.getApplication().getRealPath(
					"/upload/document/")
					+ this.oldfilename;
			super.deleteFile(filePath); // 删除掉原有的文件内容
		} else {
			this.document.setFile(this.oldfilename);
		}
		User emp = (User) super.getSession().getAttribute("emp");
		this.document.setUser(emp); // 设置的当前用户的信息
		this.document.setDoctype(this.doctype);
		try {
			if (this.documentServiceEmp.update(this.document)) {
				String filePath = super.getApplication().getRealPath(
						"/upload/document/")
						+ this.document.getFile();
				if (this.file.length() > 0) {
					super.saveSingleFile(filePath, this.file);
				}
				super.setMsgAndUrl("update.success.msg",
						"emp.document.list.admin.action");
			} else {
				super.setMsgAndUrl("update.failure.msg",
						"emp.document.list.admin.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	public String updatePre() {
		try {
			Map<String, Object> map = this.documentServiceEmp
					.updatePre(this.document.getDid());
			super.getRequest().setAttribute("allDoctypes",
					map.get("allDoctypes"));
			super.getRequest().setAttribute("document", map.get("document"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document.update";
	}

	public String delete() {
		User emp = (User) super.getSession().getAttribute("emp");
		Set<Integer> did = new HashSet<Integer>();
		Set<String> files = new HashSet<String>();
		// 删除数据的时候会牵扯到原始的附件信息删除，信息：ID:附件名称|
		if (this.ids != null) {
			String result[] = this.ids.split("\\|");
			for (int x = 0; x < result.length; x++) {
				String temp[] = result[x].split(":");
				did.add(Integer.parseInt(temp[0]));
				if (temp[1].length() > 0) {
					files.add(temp[1]);
				}
			}
			try {
				if (this.documentServiceEmp
						.delete(did, emp.getUserid())) {
					if (files.size() > 0) { // 现在有要删除的附件
						String filePath = super.getApplication().getRealPath(
								"/upload/document/");
						super.deleteFileBatch(filePath, files);
					}
					super.setMsgAndUrl("delete.success.msg",
							"emp.document.list.admin.action");
				} else {
					super.setMsgAndUrl("delete.failure.msg",
							"emp.document.list.admin.action");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "forward.page";
	}

	public String list() { // 数据分页显示
		try {
			Map<String, Object> map = this.documentServiceEmp
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("documentCount"),
					"emp.document.split.url", null, null);
			super.getRequest().setAttribute("allDocuments",
					map.get("allDocuments"));
			List<Doctype> dtlist = (List<Doctype>) map.get("allDoctypes");
			Map<Integer, String> typeMap = new HashMap<Integer, String>();
			Iterator<Doctype> iter = dtlist.iterator();
			while (iter.hasNext()) {
				Doctype dt = iter.next();
				typeMap.put(dt.getDtid(), dt.getTitle());
			}
			super.getRequest().setAttribute("allDoctypes", typeMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document.list";
	}

	public String admin() { // 数据分页显示
		try {
			User emp = (User) super.getSession().getAttribute("emp");
			Map<String, Object> map = this.documentServiceEmp.listByUser(
					emp.getUserid(), super.getCp(), super.getLs(),
					super.getCol(), super.getKw());
			super.handleSplit(map.get("documentCount"),
					"emp.document.split.url", null, null);
			super.getRequest().setAttribute("allDocuments",
					map.get("allDocuments"));
			List<Doctype> dtlist = (List<Doctype>) map.get("allDoctypes");
			Map<Integer, String> typeMap = new HashMap<Integer, String>();
			Iterator<Doctype> iter = dtlist.iterator();
			while (iter.hasNext()) {
				Doctype dt = iter.next();
				typeMap.put(dt.getDtid(), dt.getTitle());
			}
			super.getRequest().setAttribute("allDoctypes", typeMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "document.list.admin";
	}


	@Override
	public String getTypeName() {
		return "文档";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "文档标题:title|文档内容:content|发布者:user.userid";
	}

}
