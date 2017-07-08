package cn.mldn.office.action.manager;

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
import cn.mldn.office.service.manager.INoticeServiceManager;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("managerStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Namespace("/pages/jsp/manager/notice")
@Results(value = { 
		@Result(name = "notice.list", location = "/pages/jsp/manager/notice/manager_notice_list.jsp") })
@Action("NoticeActionManager")
public class NoticeActionManager extends AbstractAction {
	@Resource
	private INoticeServiceManager noticeServiceManager ;
	private Notice notice = new Notice() ;
	public Notice getNotice() {
		return notice;
	} 
	public void uncount() {
		User manager = (User) super.getSession().getAttribute("manager") ;
		try {
			super.print(this.noticeServiceManager.unreadCount(manager.getUserid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void show() {
		User manager = (User) super.getSession().getAttribute("manager") ;
		try {
			Notice vo = this.noticeServiceManager.show(this.notice.getSnid(), manager.getUserid()) ;
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
		User manager = (User) super.getSession().getAttribute("manager") ;
		try {
			Map<String, Object> map = this.noticeServiceManager
					.list(manager.getUserid(),super.getCp(), super.getLs(), super.getCol(),
							super.getKw());
			super.handleSplit(map.get("noticeCount"),
					"manager.notice.split.url", null, null);
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
