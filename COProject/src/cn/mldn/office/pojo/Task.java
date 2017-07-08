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
 * Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "task", catalog = "office")
public class Task implements java.io.Serializable {

	// Fields

	private Integer tid;
	private User userByCanceler;
	private Tasktype tasktype;
	private User userByReceiver;
	private User userByCreator;
	private Project project;
	private String title;
	private Date expiredate;
	private Date createdate;
	private Date finishdate;
	private Integer status;
	private Date lastupdatedate;
	private Integer priority;
	private Integer expend;
	private Integer estimate;
	private String note;
	private String rnote;
	private String cnote;

	// Constructors

	/** default constructor */
	public Task() {
	}

	/** full constructor */
	public Task(User userByCanceler, Tasktype tasktype, User userByReceiver,
			User userByCreator, Project project, String title,
			Timestamp expiredate, Timestamp createdate, Timestamp finishdate,
			Integer status, Timestamp lastupdatedate, Integer priority,
			Integer expend, Integer estimate, String note, String rnote,
			String cnote) {
		this.userByCanceler = userByCanceler;
		this.tasktype = tasktype;
		this.userByReceiver = userByReceiver;
		this.userByCreator = userByCreator;
		this.project = project;
		this.title = title;
		this.expiredate = expiredate;
		this.createdate = createdate;
		this.finishdate = finishdate;
		this.status = status;
		this.lastupdatedate = lastupdatedate;
		this.priority = priority;
		this.expend = expend;
		this.estimate = estimate;
		this.note = note;
		this.rnote = rnote;
		this.cnote = cnote;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "tid", unique = true, nullable = false)
	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "canceler")
	public User getUserByCanceler() {
		return this.userByCanceler;
	}

	public void setUserByCanceler(User userByCanceler) {
		this.userByCanceler = userByCanceler;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ttid")
	public Tasktype getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(Tasktype tasktype) {
		this.tasktype = tasktype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver")
	public User getUserByReceiver() {
		return this.userByReceiver;
	}

	public void setUserByReceiver(User userByReceiver) {
		this.userByReceiver = userByReceiver;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator")
	public User getUserByCreator() {
		return this.userByCreator;
	}

	public void setUserByCreator(User userByCreator) {
		this.userByCreator = userByCreator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proid")
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "expiredate", length = 19)
	public Date getExpiredate() {
		return this.expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	@Column(name = "createdate", length = 19)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "finishdate", length = 19)
	public Date getFinishdate() {
		return this.finishdate;
	}

	public void setFinishdate(Date finishdate) {
		this.finishdate = finishdate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "lastupdatedate", length = 19)
	public Date getLastupdatedate() {
		return this.lastupdatedate;
	}

	public void setLastupdatedate(Date lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}

	@Column(name = "priority")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "expend")
	public Integer getExpend() {
		return this.expend;
	}

	public void setExpend(Integer expend) {
		this.expend = expend;
	}

	@Column(name = "estimate")
	public Integer getEstimate() {
		return this.estimate;
	}

	public void setEstimate(Integer estimate) {
		this.estimate = estimate;
	}

	@Column(name = "note", length = 65535)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "rnote", length = 65535)
	public String getRnote() {
		return this.rnote;
	}

	public void setRnote(String rnote) {
		this.rnote = rnote;
	}

	@Column(name = "cnote", length = 65535)
	public String getCnote() {
		return this.cnote;
	}

	public void setCnote(String cnote) {
		this.cnote = cnote;
	}

}