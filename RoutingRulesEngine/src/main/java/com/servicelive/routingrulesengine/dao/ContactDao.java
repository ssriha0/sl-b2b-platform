package com.servicelive.routingrulesengine.dao;

import com.servicelive.domain.common.Contact;

/**
 * 
 * @author svanloon
 *
 */
public interface ContactDao {

	/**
	 * 
	 * @param contact
	 * @return Contact
	 * @throws Exception
	 */
	public Contact save(Contact contact) throws Exception;
}
