package cn.mldn.office.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Doctype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "doctype", catalog = "office")
public class Doctype implements java.io.Serializable {

	// Fields

	private Integer dtid;
	private String title;
	private Set<Document> documents = new HashSet<Document>(0);

	// Constructors

	/** default constructor */
	public Doctype() {
	}

	/** minimal constructor */
	public Doctype(String title) {
		this.title = title;
	}

	/** full constructor */
	public Doctype(String title, Set<Document> documents) {
		this.title = title;
		this.documents = documents;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "dtid", unique = true, nullable = false)
	public Integer getDtid() {
		return this.dtid;
	}

	public void setDtid(Integer dtid) {
		this.dtid = dtid;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctype")
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

}