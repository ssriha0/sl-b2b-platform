package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractUserProfile entity provides the base persistence definition of the
 * UserProfile entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractUserProfile implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3763392853165208905L;
	private String userName;
	private UserRoles userRoles;
	private Integer vendorId;
	private String password;
	private Integer questionId;
	private String answerTxt;
	private Integer contactId;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private Byte generatedPasswordInd;
	private Byte lockedInd;
	private Short loginAttemptCount;
	private Set<UserProfileRole> userProfileRoles = new HashSet<UserProfileRole>(
			0);
	private Set<UserProfilePermissions> userProfilePermissionses = new HashSet<UserProfilePermissions>(
			0);

	// Constructors

	/** default constructor */
	public AbstractUserProfile() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractUserProfile(String userName, String password,
			Integer contactId) {
		this.userName = userName;
		this.password = password;
		this.contactId = contactId;
	}

	/** full constructor */
	public AbstractUserProfile(String userName, UserRoles userRoles,
			Integer vendorId, String password, Integer questionId,
			String answerTxt, Integer contactId, Date createdDate,
			Date modifiedDate, String modifiedBy, Byte generatedPasswordInd,
			Byte lockedInd, Short loginAttemptCount,
			Set<UserProfileRole> userProfileRoles,
			Set<UserProfilePermissions> userProfilePermissionses) {
		this.userName = userName;
		this.userRoles = userRoles;
		this.vendorId = vendorId;
		this.password = password;
		this.questionId = questionId;
		this.answerTxt = answerTxt;
		this.contactId = contactId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.generatedPasswordInd = generatedPasswordInd;
		this.lockedInd = lockedInd;
		this.loginAttemptCount = loginAttemptCount;
		this.userProfileRoles = userProfileRoles;
		this.userProfilePermissionses = userProfilePermissionses;
	}

	// Property accessors
	@Id
	@Column(name = "user_name", unique = true, nullable = false, insertable = true, updatable = true, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", unique = false, nullable = true, insertable = true, updatable = true)
	public UserRoles getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(UserRoles userRoles) {
		this.userRoles = userRoles;
	}

	@Column(name = "vendor_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "question_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Column(name = "answer_txt", unique = false, nullable = true, insertable = true, updatable = true)
	public String getAnswerTxt() {
		return this.answerTxt;
	}

	public void setAnswerTxt(String answerTxt) {
		this.answerTxt = answerTxt;
	}

	@Column(name = "contact_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getContactId() {
		return this.contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "generated_password_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getGeneratedPasswordInd() {
		return this.generatedPasswordInd;
	}

	public void setGeneratedPasswordInd(Byte generatedPasswordInd) {
		this.generatedPasswordInd = generatedPasswordInd;
	}

	@Column(name = "locked_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getLockedInd() {
		return this.lockedInd;
	}

	public void setLockedInd(Byte lockedInd) {
		this.lockedInd = lockedInd;
	}

	@Column(name = "login_attempt_count", unique = false, nullable = true, insertable = true, updatable = true)
	public Short getLoginAttemptCount() {
		return this.loginAttemptCount;
	}

	public void setLoginAttemptCount(Short loginAttemptCount) {
		this.loginAttemptCount = loginAttemptCount;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userProfile")
	public Set<UserProfileRole> getUserProfileRoles() {
		return this.userProfileRoles;
	}

	public void setUserProfileRoles(Set<UserProfileRole> userProfileRoles) {
		this.userProfileRoles = userProfileRoles;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userProfile")
	public Set<UserProfilePermissions> getUserProfilePermissionses() {
		return this.userProfilePermissionses;
	}

	public void setUserProfilePermissionses(
			Set<UserProfilePermissions> userProfilePermissionses) {
		this.userProfilePermissionses = userProfilePermissionses;
	}

}