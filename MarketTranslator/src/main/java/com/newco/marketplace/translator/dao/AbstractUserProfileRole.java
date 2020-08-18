package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractUserProfileRole entity provides the base persistence definition of
 * the UserProfileRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractUserProfileRole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4036920249187430738L;
	private UserProfileRoleId id;
	private UserRoles userRoles;
	private UserProfile userProfile;
	private Date createdDate;
	private Date modifiedDate;

	// Constructors

	/** default constructor */
	public AbstractUserProfileRole() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractUserProfileRole(UserProfileRoleId id, UserRoles userRoles,
			UserProfile userProfile) {
		this.id = id;
		this.userRoles = userRoles;
		this.userProfile = userProfile;
	}

	/** full constructor */
	public AbstractUserProfileRole(UserProfileRoleId id, UserRoles userRoles,
			UserProfile userProfile, Date createdDate, Date modifiedDate) {
		this.id = id;
		this.userRoles = userRoles;
		this.userProfile = userProfile;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "userName", column = @Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 30)),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", unique = false, nullable = false, insertable = true, updatable = true)) })
	public UserProfileRoleId getId() {
		return this.id;
	}

	public void setId(UserProfileRoleId id) {
		this.id = id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", unique = false, nullable = false, insertable = false, updatable = false)
	public UserRoles getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(UserRoles userRoles) {
		this.userRoles = userRoles;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_name", unique = false, nullable = false, insertable = false, updatable = false)
	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

}