package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserRoles entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_roles",  uniqueConstraints = {})
public class UserRoles extends AbstractUserRoles implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public UserRoles() {
		// intentionally blank
	}

	/** minimal constructor */
	public UserRoles(Integer roleId) {
		super(roleId);
	}

	/** full constructor */
	public UserRoles(Integer roleId, String roleName, String roleDescr,
			Byte internalRole, Date createdDate, Date modifiedDate,
			Set<UserProfileRole> userProfileRoles, Set<UserProfile> userProfiles) {
		super(roleId, roleName, roleDescr, internalRole, createdDate,
				modifiedDate, userProfileRoles, userProfiles);
	}

}
