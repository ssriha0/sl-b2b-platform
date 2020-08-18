package com.servicelive.spn.services.email;

import com.servicelive.domain.spn.detached.Email;

/**
 * 
 * @author svanloon
 *
 */
public interface EmailService {

	/**
	 * 
	 * @param email
	 * @return boolean successful or not
	 */
	public boolean sendEmail(Email email);
}
