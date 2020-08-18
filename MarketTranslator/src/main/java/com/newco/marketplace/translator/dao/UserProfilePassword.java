package com.newco.marketplace.translator.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * UserProfilePassword entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_profile_password",  uniqueConstraints = {})
public class UserProfilePassword extends AbstractUserProfilePassword implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public UserProfilePassword() {
		// intentionally blank
	}

	/** full constructor */
	public UserProfilePassword(Integer userPasswordId, String userName,
			String password, Date createdDate, String modifiedBy) {
		super(userPasswordId, userName, password, createdDate, modifiedBy);
	}

}
