package com.newco.marketplace.persistence.daoImpl.contact;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.contact.ContactDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * $Revision: 1.10 $ $Author: glacy $ $Date: 2008/04/26 00:40:34 $
 */
public class ContactDaoImpl extends ABaseImplDao implements ContactDao
{
    private static final Logger logger = Logger.getLogger(ContactDaoImpl.class.getName());
    
    /* (non-Javadoc)
     * @see com.newco.marketplace.persistence.iDao.contact.ContactDao#query(java.lang.Integer)
     */
    public Contact query(Integer contactId) throws DataServiceException {
    	
    	Contact contact = null;
        try {
            contact = (Contact) queryForObject("contact.query", contactId);
        }
        catch (Exception ex) {
        	logger.info("Unable to retrieve Contact data for contactId:" + contactId, ex);
            throw new DataServiceException("Unable to retrieve Contact data for contactId:" + contactId, ex);
        }                
            
        return contact;
    }

    /* (non-Javadoc)
     * @see com.newco.marketplace.persistence.iDao.contact.ContactDao#queryByName(java.lang.String)
     */
    public Contact queryByName(String userName) throws DataServiceException {
    	
    	Contact contact = null;
        try {
            contact = (Contact) queryForObject("contact.query_by_user_name", userName);
        }
        catch (Exception ex) {
        	logger.info("Unable to retrieve Contact data for user_name:" + userName, ex);
            throw new DataServiceException("Unable to retrieve Contact data for user_name:" + userName, ex);
        }                
            
        return contact;
    }
    
    /* (non-Javadoc)
     * @see com.newco.marketplace.persistence.iDao.contact.ContactDao#queryByName(java.lang.String)
     */
    public Contact queryBySOID(String soId) throws DataServiceException {
    	
    	Contact contact = null;
        try {
            contact = (Contact) queryForObject("contact.query_by_soid", soId);
        }
        catch (Exception ex) {
        	logger.info("Unable to retrieve Contact data for soId:" + soId, ex);
            throw new DataServiceException("Unable to retrieve Contact data for soId:" + soId, ex);
        }                
            
        return contact;
    }
    
    //To insert contact info for new buyer registration
    public Contact insert(Contact contact) throws DataServiceException
    {
        Integer id = null;
        try {
            id = (Integer)getSqlMapClient().insert("new_contact.insert", contact);
            contact.setContactId(id.intValue());
        }
        catch (Exception ex) {
             ex.printStackTrace();
             logger.info("General Exception @ContactDaoImpl.insert() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @ContactDaoImpl.insert() due to "+ex.getMessage());
        }

        return contact;
    }
    
    public void updateSimpleBuyer(Contact contact) throws DataServiceException {
    	Integer id = null;
        try {
            id = (Integer)getSqlMapClient().update("update_simple_buyer_contact.update", contact);
        }
        catch (Exception ex) {
             logger.error("General Exception @ContactDaoImpl.updateSimpleBuyer() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @ContactDaoImpl.updateSimpleBuyer() due to "+ex.getMessage());
        }

    }
    
}
