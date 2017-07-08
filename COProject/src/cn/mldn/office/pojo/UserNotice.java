package cn.mldn.office.pojo;

import java.util.Date;
public class UserNotice implements java.io.Serializable {
	private User user;
	private Notice notice;
	private Date rdate ;
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getRdate() {
		return rdate;
	}
	public User getUser() {
		return user;
	}

}