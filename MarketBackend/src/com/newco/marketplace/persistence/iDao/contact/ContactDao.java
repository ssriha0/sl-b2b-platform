package com.newco.marketplace.persistence.iDao.contact;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * $Revision: 1.10 $ $Author: glacy $ $Date: 2008/04/26 00:40:39 $
 */
public interface ContactDao {
	/**
	 * @param contactId
	 * @return
	 * @throws DataServiceException
	 */
	public Contact query(Integer contactId) throws DataServiceException;
	/**
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	public Contact queryByName(String userName) throws DataServiceException;
	
	public Contact queryBySOID(String userName) throws DataServiceException;
	
	public Contact insert(Contact contact) throws DataServiceException;
	
	public void updateSimpleBuyer(Contact contact) throws DataServiceException;
}
