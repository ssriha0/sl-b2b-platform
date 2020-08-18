package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserProfileRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_profile_role",  uniqueConstraints = {})
public class UserProfileRole extends AbstractUserProfileRole implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public UserProfileRole() {
		// intentionally blank
	}

	/** minimal constructor */
	public UserProfileRole(UserProfileRoleId id, UserRoles userRoles,
			UserProfile userProfile) {
		super(id, userRoles, userProfile);
	}

	/** full constructor */
	public UserProfileRole(UserProfileRoleId id, UserRoles userRoles,
			UserProfile userProfile, Date createdDate, Date modifiedDate) {
		super(id, userRoles, userProfile, createdDate, modifiedDate);
	}

}
