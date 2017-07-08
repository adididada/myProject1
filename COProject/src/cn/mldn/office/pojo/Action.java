package cn.mldn.office.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Action entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "action", catalog = "office")
public class Action implements java.io.Serializable {

	// Fields

	private Integer actid;
	private Groups groups;
	private String title;
	private String url;

	// Constructors

	/** default constructor */
	public Action() {
	}

	/** minimal constructor */
	public Action(String title, String url) {
		this.title = title;
		this.url = url;
	}

	/** full constructor */
	public Action(Groups groups, String title, String url) {
		this.groups = groups;
		this.title = title;
		this.url = url;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "actid", unique = true, nullable = false)
	public Integer getActid() {
		return this.actid;
	}

	public void setActid(Integer actid) {
		this.actid = actid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gid")
	public Groups getGroups() {
		return this.groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "url", nullable = false, length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}