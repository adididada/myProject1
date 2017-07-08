package cn.mldn.office.action.admin;

import java.util.HashSet;
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

import cn.mldn.office.pojo.Notice;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.admin.INoticeServiceAdmin;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = {
		@Result(name = "notice.insert", location = "/pages/jsp/admin/notice/admin_notice_insert.jsp"),
		@Result(name = "notice.update", location = "/pages/jsp/admin/notice/admin_notice_update.jsp"),
		@Result(name = "notice.list", location = "/pages/jsp/admin/notice/admin_notice_list.jsp") ,
		@Result(name = "insertVF", location = "/pages/jsp/admin/notice/admin_notice_insert.jsp") ,
		@Result(name = "updateVF", location = "/pages/jsp/admin/notice/NoticeActionAdmin!list.action",type="redirectAction") })
@Namespace("/pages/jsp/admin/notice")
@Action("NoticeActionAdmin") 
public class NoticeActionAdmin extends AbstractAction {
	private static final String insertRule = "notice.title:string|notice.content:string" ;
	private static final String updateRule = "notice.snid:int|notice.title:string|notice.content:string" ;
	private String ids ;
	private Notice notice = new Notice();
	@Resource
	private INoticeServiceAdmin noticeServiceAdmin;
	public void setIds(String ids) {
		this.ids = ids;
	}
	public Notice getNotice() {
		return notice;
	}

	public String list() { // 数据分页显示
		try {
			Map<String, Object> map = this.noticeServiceAdmin
					.list(super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("noticeCount"),
					"admin.notice.split.url", null, null);
			super.getRequest()
					.setAttribute("allNotices", map.get("allNotices"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notice.list";
	}

	public String insertPre() {
		return "notice.insert";
	}

	public String updatePre() {
		try {
			super.getRequest().setAttribute("notice",
					this.noticeServiceAdmin.updatePre(this.notice.getSnid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notice.update";
	}

	public String insert() {
		User admin = (User) super.getSession().getAttribute("admin");
		this.notice.setUser(admin);
		try {
			if (this.noticeServiceAdmin.insert(this.notice)) {
				super.setMsgAndUrl("insert.success.msg",
						"admin.notice.insert.action");
			} else {
				super.setMsgAndUrl("insert.failure.msg",
						"admin.notice.insert.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// admin.notice.insert.action
		return "forward.page";
	}
	

	public String update() {
		User admin = (User) super.getSession().getAttribute("admin");
		this.notice.setUser(admin);
		try {
			if (this.noticeServiceAdmin.update(this.notice)) {
				super.setMsgAndUrl("update.success.msg",
						"admin.notice.list.action");
			} else {
				super.setMsgAndUrl("update.failure.msg",
						"admin.notice.list.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// admin.notice.insert.action
		return "forward.page";
	}
	
	public String delete() {
		Set<Integer> set = new HashSet<Integer>() ;
		String result [] = this.ids.split("\\|") ;
		for (int x = 0 ; x < result.length ; x ++) {
			set.add(Integer.parseInt(result[x])) ;
		}
		try {
			if (this.noticeServiceAdmin.delete(set)) {
				super.setMsgAndUrl("delete.success.msg",
						"admin.notice.list.action");
			} else {
				super.setMsgAndUrl("delete.failure.msg",
						"admin.notice.list.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page" ;
	}

	public void updateLevel() {
		try {
			super.print(this.noticeServiceAdmin.updateLevel(
					this.notice.getSnid(), this.notice.getLevel()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTypeName() {
		return "系统公告";
	}

	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "公告标题:title|公告内容:content|发布管理员:user.userid";
	}

}
