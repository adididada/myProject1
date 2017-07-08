package cn.mldn.office.pojo;

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
 * Project entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project", catalog = "office")
public class Project implements java.io.Serializable {

	// Fields

	private Integer proid;
	private User userByCreid;
	private User userByMgr;
	private String title;
	private String name;
	private String note;
	private Date pubdate;
	private Set<Task> tasks = new HashSet<Task>(0);

	// Constructors

	/** default constructor */
	public Project() {
	}


	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "proid", unique = true, nullable = false)
	public Integer getProid() {
		return this.proid;
	}

	public void setProid(Integer proid) {
		this.proid = proid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creid")
	public User getUserByCreid() {
		return this.userByCreid;
	}

	public void setUserByCreid(User userByCreid) {
		this.userByCreid = userByCreid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mgr")
	public User getUserByMgr() {
		return this.userByMgr;
	}

	public void setUserByMgr(User userByMgr) {
		this.userByMgr = userByMgr;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "note", length = 65535)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}
	public Date getPubdate() {
		return pubdate;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

}