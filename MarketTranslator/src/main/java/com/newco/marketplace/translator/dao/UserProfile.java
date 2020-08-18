package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserProfile entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_profile",  uniqueConstraints = {})
public class UserProfile extends AbstractUserProfile implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public UserProfile() {
		// intentionally blank
	}

	/** minimal constructor */
	public UserProfile(String userName, String password, Integer contactId) {
		super(userName, password, contactId);
	}

	/** full constructor */
	public UserProfile(String userName, UserRoles userRoles, Integer vendorId,
			String password, Integer questionId, String answerTxt,
			Integer contactId, Date createdDate, Date modifiedDate,
			String modifiedBy, Byte generatedPasswordInd, Byte lockedInd,
			Short loginAttemptCount, Set<UserProfileRole> userProfileRoles,
			Set<UserProfilePermissions> userProfilePermissionses) {
		super(userName, userRoles, vendorId, password, questionId, answerTxt,
				contactId, createdDate, modifiedDate, modifiedBy,
				generatedPasswordInd, lockedInd, loginAttemptCount,
				userProfileRoles, userProfilePermissionses);
	}

}
