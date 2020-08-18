package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserProfileRoleId entity provides the base persistence definition of the
 * ileRoleId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class UserProfileRoleId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private Integer roleId;

	// Constructors

	/** default constructor */
	public UserProfileRoleId() {
		// intentionally blank
	}

	/** full constructor */
	public UserProfileRoleId(String userName, Integer roleId) {
		this.userName = userName;
		this.roleId = roleId;
	}

	// Property accessors

	@Column(name = "user_name", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "role_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserProfileRoleId))
			return false;
		UserProfileRoleId castOther = (UserProfileRoleId) other;

		return ((this.getUserName() == castOther.getUserName()) || (this
				.getUserName() != null
				&& castOther.getUserName() != null && this.getUserName()
				.equals(castOther.getUserName())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this
						.getRoleId() != null
						&& castOther.getRoleId() != null && this.getRoleId()
						.equals(castOther.getRoleId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37 * result
				+ (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}

}