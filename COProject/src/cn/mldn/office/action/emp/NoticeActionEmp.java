package cn.mldn.office.action.emp;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.Notice;
import cn.mldn.office.pojo.User;
import cn.mldn.office.service.emp.INoticeServiceEmp;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("empStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Namespace("/pages/jsp/emp/notice")
@Results(value = { 
		@Result(name = "notice.list", location = "/pages/jsp/emp/notice/emp_notice_list.jsp") })
@Action("NoticeActionEmp")
public class NoticeActionEmp extends AbstractAction {
	@Resource
	private INoticeServiceEmp noticeServiceEmp ;
	private Notice notice = new Notice() ;
	public Notice getNotice() {
		return notice;
	} 
//	public void uncount() {
//		User emp = (User) super.getSession().getAttribute("emp") ;
//		try {
//			super.print(this.noticeServiceEmp.unreadCount(emp.getUserid()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	public void show() {
		User emp = (User) super.getSession().getAttribute("emp") ;
		try {
			Notice vo = this.noticeServiceEmp.show(this.notice.getSnid(), emp.getUserid()) ;
			JSONObject obj = new JSONObject() ;
			obj.put("title", vo.getTitle()) ;
			obj.put("userid", vo.getUser().getUserid()) ;
			obj.put("pubdate", super.formatDate(vo.getPubdate())) ;
			obj.put("level", vo.getLevel()) ;
			obj.put("rnum", vo.getRnum()) ;
			obj.put("content", vo.getContent()) ;
			super.print(obj); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public String list() { // 数据分页显示
		User emp = (User) super.getSession().getAttribute("emp") ;
		try {
			Map<String, Object> map = this.noticeServiceEmp
					.list(emp.getUserid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("noticeCount"),
					"emp.notice.split.url", null, null);
			super.getRequest()
					.setAttribute("allNotices", map.get("allNotices"));
			super.getRequest().setAttribute("unread", map.get("unread"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notice.list";
	}
	@Override
	public String getTypeName() {
		return "公告信息";
	}
	@Override
	public String getDefaultColumn() {
		return "title";
	}

	@Override
	public String getColumnData() {
		return "公告标题:title|公告内容:content";
	}

}
