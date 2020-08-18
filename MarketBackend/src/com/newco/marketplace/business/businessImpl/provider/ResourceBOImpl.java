package com.newco.marketplace.business.businessImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.provider.IResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.vo.provider.ResourceVO;

public class ResourceBOImpl extends ABaseBO implements IResourceBO {
	
	private IResourceDao iResourceDao; 
	private IVendorResourceDao vendorResourceDao;

	public ResourceBOImpl(IResourceDao iResourceDao) {
		this.iResourceDao = iResourceDao;
	}
	
	public ResourceVO getResourceName(ResourceVO resourceVO) throws BusinessServiceException {
		try {
			resourceVO = iResourceDao.getResourceName(resourceVO);
		}catch (DBException ex) {
    		logger.error("DB Exception @ResourceBOImpl.getResourceName() due to DBException", ex);
    		throw new BusinessServiceException(ex.getMessage(), ex);
    	} catch (Exception ex) {
    		logger.error("General Exception @ResourceBOImpl.getResourceName() due to Exception", ex);
    		throw new BusinessServiceException("General Exception @ResourceBOImpl.getResourceName() due to Exception", ex);
    	}
		return resourceVO;
	}

	/**
	 * Checks whether a resource is associated with the logged in/adopted user
	 * @param resourceId
	 * @param loggedInUserCompanyId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean isResourceAssociatedWithLoggedUser(String resourceId, Integer loggedInUserCompanyId) throws BusinessServiceException {
		boolean result = false;
		Integer vendorId = 0;
		try {
			vendorId = iResourceDao.getVendorId(resourceId);
			if(loggedInUserCompanyId.equals(vendorId)){
				result = true;
			}
		} catch (DBException ex) {
    		logger.error("DB Exception @ResourceBOImpl.isResourceAssociatedWithLoggedUser() due to DBException", ex);
    		throw new BusinessServiceException(ex.getMessage(), ex);
    	} catch (Exception ex) {
    		logger.error("General Exception @ResourceBOImpl.isResourceAssociatedWithLoggedUser() due to Exception", ex);
    		throw new BusinessServiceException("General Exception @ResourceBOImpl.isResourceAssociatedWithLoggedUser() due to Exception", ex);
    	}
		return result;
	}
	
	public Contact getVendorResourceContact(int resourceId) throws BusinessServiceException{
	    try{
	        return vendorResourceDao.getVendorResourceContact(resourceId);
	    }catch(Exception e){
	        String message = String.format("Unknown Exception when trying to read Contact for VendorResource (%1)",resourceId);
	        throw new BusinessServiceException(message, e);
	    }
	}
	
	public void setVendorResourceDao(IVendorResourceDao impl){
	    vendorResourceDao = impl;
	}

}
