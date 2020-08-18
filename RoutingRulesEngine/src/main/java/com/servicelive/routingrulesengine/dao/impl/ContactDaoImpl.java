package com.servicelive.routingrulesengine.dao.impl;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.common.Contact;
import com.servicelive.routingrulesengine.dao.ContactDao;

/**
 * 
 * @author svanloon
 *
 */
public class ContactDaoImpl extends AbstractBaseDao implements ContactDao {

	@Transactional
	public Contact save(Contact contact) throws Exception {
		this.getEntityManager().persist(contact);
		return contact;
	}

}
