package cn.mldn.office.pojo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Document entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "document", catalog = "office")
public class Document implements java.io.Serializable {

	// Fields

	private Integer did;
	private User user;
	private Doctype doctype;
	private String title;
	private String content;
	private String file;
	private Date pubdate;

	// Constructors

	/** default constructor */
	public Document() {
	}

	/** minimal constructor */
	public Document(String title) {
		this.title = title;
	}

	/** full constructor */
	public Document(User user, Doctype doctype, String title, String content,
			String file, Timestamp pubdate) {
		this.user = user;
		this.doctype = doctype;
		this.title = title;
		this.content = content;
		this.file = file;
		this.pubdate = pubdate;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "did", unique = true, nullable = false)
	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dtid")
	public Doctype getDoctype() {
		return this.doctype;
	}

	public void setDoctype(Doctype doctype) {
		this.doctype = doctype;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "file", length = 200)
	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Column(name = "pubdate", length = 19)
	public Date getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

}