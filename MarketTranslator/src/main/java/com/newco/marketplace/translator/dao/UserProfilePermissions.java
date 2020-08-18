package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserProfilePermissions entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_profile_permissions",  uniqueConstraints = {})
public class UserProfilePermissions extends AbstractUserProfilePermissions
		implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public UserProfilePermissions() {
		// intentionally blank
	}

	/** minimal constructor */
	public UserProfilePermissions(UserProfilePermissionsId id,
			UserProfile userProfile) {
		super(id, userProfile);
	}

	/** full constructor */
	public UserProfilePermissions(UserProfilePermissionsId id,
			UserProfile userProfile, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		super(id, userProfile, createdDate, modifiedDate, modifiedBy);
	}

}
