package cn.mldn.office.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Notice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "notice", catalog = "office")
public class Notice implements java.io.Serializable {

	// Fields

	private Integer snid;
	private User user;
	private String title;
	private Date pubdate;
	private String content;
	private Integer level;
	private Integer rnum;

	// Constructors

	/** default constructor */
	public Notice() {
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "snid", unique = true, nullable = false)
	public Integer getSnid() {
		return this.snid;
	}

	public void setSnid(Integer snid) {
		this.snid = snid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "pubdate", length = 19)
	public Date getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "rnum")
	public Integer getRnum() {
		return this.rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}

}