package cn.mldn.office.action.admin;

import java.io.File;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Repository;

import cn.mldn.office.pojo.User;
import cn.mldn.office.service.common.IUserServiceCommon;
import cn.mldn.util.MD5Code;
import cn.mldn.util.action.AbstractAction;

@Repository
@InterceptorRef("adminStack")
@SuppressWarnings("serial")
@ParentPackage("root")
@Results(value = { 
		@Result(name = "updatePasswordVF", location = "/pages/jsp/admin/admin/admin_password_edit.jsp") ,
		@Result(name = "admin.updatepre", location = "/pages/jsp/admin/admin/admin_admin_update.jsp")})
@Namespace("/pages/jsp/admin/admin")
@Action("AdminUpdateActionAdmin")
public class AdminUpdateActionAdmin extends AbstractAction {
	private static String updatePasswordRule = "newpassword:string|oldpassword:string";
	private File photo ;	// 得到上传文件
	private String photoContentType ;	// 得到上传文件的类型
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	
	@Resource
	private IUserServiceCommon userServiceCommon;
	private String newpassword;
	private String oldpassword;
	
	private User user = new User() ;
	public User getUser() {
		return user;
	} 
	public String updatePre() {
		User user = (User) super.getSession().getAttribute("admin");
		try {
			super.getRequest().setAttribute("user", this.userServiceCommon.updatePre(user.getUserid()));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "admin.updatepre" ;
	}
	public String update() {
		User user = (User) super.getSession().getAttribute("admin");
		this.user.setUserid(user.getUserid());
		if (this.photo.length() > 0) {	// 现在有文件上传
			if ("nophoto.jpg".equals(this.user.getPhoto())) {	// 原本没有图片
				this.user.setPhoto(super.createSingleFileName(this.photoContentType));
			}
		}
		try {
			if (this.userServiceCommon.update(this.user)) {
				if (this.photo.length() > 0) {
					String filePath = super.getApplication().getRealPath("/upload/user/") + this.user.getPhoto() ;
					if(super.saveSingleFile(filePath, this.photo)) {
						// 更新session中的保存数据
						user.setPhoto(this.user.getPhoto());
						super.getSession().setAttribute("admin", user);
					}
				}
				super.setMsgAndUrl("update.success.msg", "admin.user.update.action");
			} else {
				super.setMsgAndUrl("update.success.failure", "admin.user.update.action");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page" ;
	} 

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String updatePassword() {
		User user = (User) super.getSession().getAttribute("admin");
		try {
			if (this.userServiceCommon.updatePassword(user.getUserid(),
					new MD5Code().getMD5ofStr(this.oldpassword),
					new MD5Code().getMD5ofStr(this.newpassword))) {
				super.setMsgAndUrl("user.password.update.success", "login.page");
			} else {
				super.setMsgAndUrl("user.password.update.failure", "login.page");
			}
			super.getSession().invalidate(); // 让Session失效
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward.page";
	}

	@Override
	public String getTypeName() {
		return "管理员";
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
