package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserProfilePermissionsId entity provides the base persistence definition of
 * the ilePermissionsId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserProfilePermissionsId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private Integer roleActivityId;

	// Constructors

	/** default constructor */
	public UserProfilePermissionsId() {
		// intentionally blank
	}

	/** full constructor */
	public UserProfilePermissionsId(String userName, Integer roleActivityId) {
		this.userName = userName;
		this.roleActivityId = roleActivityId;
	}

	// Property accessors

	@Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "role_activity_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getRoleActivityId() {
		return this.roleActivityId;
	}

	public void setRoleActivityId(Integer roleActivityId) {
		this.roleActivityId = roleActivityId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserProfilePermissionsId))
			return false;
		UserProfilePermissionsId castOther = (UserProfilePermissionsId) other;

		return ((this.getUserName() == castOther.getUserName()) || (this
				.getUserName() != null
				&& castOther.getUserName() != null && this.getUserName()
				.equals(castOther.getUserName())))
				&& ((this.getRoleActivityId() == castOther.getRoleActivityId()) || (this
						.getRoleActivityId() != null
						&& castOther.getRoleActivityId() != null && this
						.getRoleActivityId().equals(
								castOther.getRoleActivityId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37
				* result
				+ (getRoleActivityId() == null ? 0 : this.getRoleActivityId()
						.hashCode());
		return result;
	}

}