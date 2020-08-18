package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractUserProfilePassword entity provides the base persistence definition
 * of the UserProfilePassword entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractUserProfilePassword implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7400170684117124678L;
	private Integer userPasswordId;
	private String userName;
	private String password;
	private Date createdDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractUserProfilePassword() {
		// intentionally blank
	}

	/** full constructor */
	public AbstractUserProfilePassword(Integer userPasswordId, String userName,
			String password, Date createdDate, String modifiedBy) {
		this.userPasswordId = userPasswordId;
		this.userName = userName;
		this.password = password;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@Column(name = "user_password_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getUserPasswordId() {
		return this.userPasswordId;
	}

	public void setUserPasswordId(Integer userPasswordId) {
		this.userPasswordId = userPasswordId;
	}

	@Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_by", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}