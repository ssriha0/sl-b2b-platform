/**
 * 
 */
package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.dto.provider.VendorHdrDto;

/**
 * @author KSudhanshu
 *
 */
public class ActivityRegistryDelegateImpl implements IActivityRegistryDelegate {

	private IActivityRegistryBO activityRegistryBO;
	private IAuditBO iAuditBO;
	
	private static final Logger logger = Logger.getLogger(ActivityRegistryDelegateImpl.class.getName());
	/**
	 * @param activityRegistryBO
	 */
	public ActivityRegistryDelegateImpl(IActivityRegistryBO activityRegistryBO,
			IAuditBO auditBO) {
		this.activityRegistryBO = activityRegistryBO;
		this.iAuditBO = auditBO;
	}

	/* (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate#getProviderActivityStatus(java.lang.String)
	 */
	public Map<String, String> getProviderActivityStatus(String vendorId)
			throws Exception {
		
		return activityRegistryBO.getProviderActivityStatus(vendorId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate#updateActivityStatus(java.lang.String, java.lang.String)
	 */
	public boolean updateActivityStatus(String vendorId, String activityName)
			throws Exception {
		
		return activityRegistryBO.updateActivityStatus(vendorId, activityName);
	}

	public Map<String, String> getResourceActivityStatus(String resourceId)
			throws Exception {
		
		return activityRegistryBO.getResourceActivityStatus(resourceId);
	}

	public boolean updateResourceActivityStatus(String resourceId,
			String activityName) throws Exception {

		return activityRegistryBO.updateResourceActivityStatus(resourceId, activityName);
	}

	
	/**
	 * @param vendorHdrDto
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public boolean submitRegistration(VendorHdrDto vendorHdrDto, Integer resourceId, String provUserName) throws Exception{

		VendorHdr vendorHdr = new VendorHdr();
		vendorHdr.setVendorId(vendorHdrDto.getVendorId());
		return activityRegistryBO.submitRegistration(vendorHdr, resourceId, provUserName);
	}
	
	public String getVendorResourceStateStatus(String resourceId) throws Exception {
		return iAuditBO.getVendorResourceStatus(resourceId);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate#getProviderStatus(java.lang.String)
	 */
	public String getProviderStatus(String vendorId) throws Exception{
		
		Map activityStatus = getProviderActivityStatus(vendorId);
		if (activityStatus == null || activityStatus.size() == 0) 
			return ActivityRegistryConstants.NOT_STARTED;

		Boolean businessinfoStatus = (Boolean)activityStatus.get(ActivityRegistryConstants.BUSINESSINFO);
		Boolean licenceStatus = (Boolean)activityStatus.get(ActivityRegistryConstants.LICENSE);
		Boolean insuranceStatus = (Boolean)activityStatus.get(ActivityRegistryConstants.INSURANCE);
		Boolean warrantyStatus = (Boolean)activityStatus.get(ActivityRegistryConstants.WARRANTY);
		Boolean termsAndCondStatus = (Boolean)activityStatus.get(ActivityRegistryConstants.TERMSANDCOND);
		logger.info("BusinessinfoStatus:" + businessinfoStatus);
		logger.info("LicenceStatus:" + licenceStatus);
		logger.info("InsuranceStatus:" + insuranceStatus);
		logger.info("WarrantyStatus:" + warrantyStatus);

		
		try{
		if (businessinfoStatus && licenceStatus && insuranceStatus && warrantyStatus && termsAndCondStatus)
			return ActivityRegistryConstants.COMPLETE;
		if (!businessinfoStatus && !licenceStatus && !insuranceStatus && !warrantyStatus && !termsAndCondStatus)
			return ActivityRegistryConstants.NOT_STARTED;
		}catch (NullPointerException npe){
			return ActivityRegistryConstants.NOT_STARTED;
			// This should not happen if the provider is registering through normal paths
			// However this will happen for existing users that dont have an y entries in the activity registry.
		}
		return ActivityRegistryConstants.INCOMPLETE;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate#getResourceStatus(java.lang.String)
	 */
	public String getResourceStatus(String resourceId) throws Exception{
		
		Map resourceActivityStatus = getResourceActivityStatus(resourceId);
		
		if (resourceActivityStatus == null || resourceActivityStatus.size()== 0) 
			return ActivityRegistryConstants.NOT_STARTED;

		Boolean generalInfo = (Boolean)resourceActivityStatus.get(ActivityRegistryConstants.RESOURCE_GENERALINFO);
		Boolean backgourndCheck = (Boolean)resourceActivityStatus.get(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
		Boolean credentials = (Boolean)resourceActivityStatus.get(ActivityRegistryConstants.RESOURCE_CREDENTIALS);
		Boolean marketplace = (Boolean)resourceActivityStatus.get(ActivityRegistryConstants.RESOURCE_MARKETPLACE);
		Boolean skills = (Boolean)resourceActivityStatus.get(ActivityRegistryConstants.RESOURCE_SKILLS);
		Boolean termsAndCond = (Boolean)resourceActivityStatus.get(ActivityRegistryConstants.RESOURCE_TERMSANDCOND);
		logger.info("GeneralInfo:" + generalInfo);
		logger.info("BackgourndCheck:" + backgourndCheck);
		logger.info("Credentials:" + credentials);
		logger.info("Marketplace:" + marketplace);
		logger.info("Skills:" + skills);
		logger.info("TermsAndCond:" + termsAndCond);
		
		try{
		if (generalInfo && backgourndCheck && credentials && marketplace && skills && termsAndCond)
			return ActivityRegistryConstants.COMPLETE;
		if (!generalInfo && !backgourndCheck && !credentials && !marketplace && !skills && !termsAndCond)
			return ActivityRegistryConstants.NOT_STARTED;
		}catch (NullPointerException npe){
			return ActivityRegistryConstants.NOT_STARTED;
			// This should not happen if the provider is registering through normal paths
			// However this will happen for existing users that dont have an y entries in the activity registry.
		}
		return ActivityRegistryConstants.INCOMPLETE;
	}	
	
	
	public String getVendorHeaderStatus(String vendorId) throws Exception
	{
		String vendorStatus = null;
		try
		{
			vendorStatus = iAuditBO.getVendorHeaderStatus(vendorId);
		}
		catch(Exception ex)
		{
			logger.info("Exception in activityregistrydelegateImpl: getVendorHeaderStatus"+ex);
			
		}
		return vendorStatus;
	}
	
	public String getResPrimaryIndicator(String resourceId) throws Exception
	{
		String primaryInd = null;
		try
		{
			primaryInd = activityRegistryBO.getResourcePriInd(resourceId);
		}catch(Exception a_Ex)
		{
			logger.info("Exception in activityregistrydelegateImpl: getResPrimaryIndicator " + a_Ex);
		}
		
		return primaryInd;
	}
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws DelegateException
	 */
	public List checkActivityStatus(int vendorId) throws DelegateException
	{
		List resourceActKeyList = null;
		try
		{
			resourceActKeyList = activityRegistryBO.checkActivityStatus(vendorId);
		}
		catch (BusinessServiceException ex) {
			throw new DelegateException(	"General Exception @activityregistrydelegateImpl.checkActivityStatus() due to "
							+ ex.getMessage());			
		}
		return resourceActKeyList;
	}
}
