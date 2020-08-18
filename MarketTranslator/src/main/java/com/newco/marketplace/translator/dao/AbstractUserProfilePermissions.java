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
 * AbstractUserProfilePermissions entity provides the base persistence
 * definition of the UserProfilePermissions entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractUserProfilePermissions implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7907299368502317097L;
	private UserProfilePermissionsId id;
	private UserProfile userProfile;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractUserProfilePermissions() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractUserProfilePermissions(UserProfilePermissionsId id,
			UserProfile userProfile) {
		this.id = id;
		this.userProfile = userProfile;
	}

	/** full constructor */
	public AbstractUserProfilePermissions(UserProfilePermissionsId id,
			UserProfile userProfile, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		this.id = id;
		this.userProfile = userProfile;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "userName", column = @Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 30)),
			@AttributeOverride(name = "roleActivityId", column = @Column(name = "role_activity_id", unique = false, nullable = false, insertable = true, updatable = true)) })
	public UserProfilePermissionsId getId() {
		return this.id;
	}

	public void setId(UserProfilePermissionsId id) {
		this.id = id;
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

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}