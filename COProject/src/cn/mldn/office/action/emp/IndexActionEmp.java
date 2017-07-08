package cn.mldn.office.action.emp;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.User;
import cn.mldn.office.service.emp.IIndexServiceEmp;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("empStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Namespace("/")
@Action("IndexActionEmp")
public class IndexActionEmp extends AbstractAction {
	@Resource
	private IIndexServiceEmp indexServiceEmp ;
	public void allCount() {
		User emp = (User) super.getSession().getAttribute("emp") ;
		try {
			Map<String,Integer> map = this.indexServiceEmp.unreadCount(emp.getUserid()) ;
			JSONObject obj = new JSONObject() ;
			obj.put("noticeCount", map.get("noticeCount")) ;
			obj.put("status0Count", map.get("status0Count")) ;
			obj.put("status1Count", map.get("status1Count")) ;
			super.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
