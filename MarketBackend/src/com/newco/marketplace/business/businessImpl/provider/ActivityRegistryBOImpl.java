/**
 * 
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.util.List;
import java.util.Map;
 
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.IResourceStatusDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;


/**
 * @author KSudhanshu
 *
 */
public class ActivityRegistryBOImpl extends ABaseBO implements IActivityRegistryBO {

	private IActivityRegistryDao activityRegistryDao;
	private IProviderEmailBO iProviderEmailBO;
	private IVendorHdrDao iVendorHdrDao;
	private IUserProfileDao iUserProfileDao;
	private IVendorResourceDao iVendorResourceDao;
	private IContactDao iContactDao;
	private IResourceStatusDao iResourceStatusDao;
	private IAuditBO iAuditBO;
	private static final Logger logger = Logger
	.getLogger(ActivityRegistryBOImpl.class.getName());

	
	/**
	 * @param activityRegistryDao
	 */
	public ActivityRegistryBOImpl(IActivityRegistryDao activityRegistryDao, 
			IProviderEmailBO iProviderEmailBO,
			IVendorHdrDao iVendorHdrDao,
			IResourceStatusDao iResourceStatusDao,
			IUserProfileDao userProfileDao,
			IVendorResourceDao vendorResourceDao,
			IContactDao contactDao,
			IAuditBO auditBO) {
		this.activityRegistryDao = activityRegistryDao;
		this.iProviderEmailBO = iProviderEmailBO;
		this.iVendorHdrDao = iVendorHdrDao;
		this.iResourceStatusDao =  iResourceStatusDao;
		this.iUserProfileDao = userProfileDao;
		this.iVendorResourceDao = vendorResourceDao;
		this.iContactDao = contactDao;
		this.iAuditBO = auditBO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO#getProviderActivityStatus(java.lang.String)
	 */
	public Map<String, String> getProviderActivityStatus(String vendorId)
			throws Exception {
		
		return activityRegistryDao.getProviderActivityStatus(vendorId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO#updateActivityStatus(java.lang.String, java.lang.String)
	 */
	public boolean updateActivityStatus(String vendorId, String activityName)
			throws Exception {
		
		return activityRegistryDao.updateActivityStatus(vendorId, activityName);
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO#getResourceActivityStatus(java.lang.String)
	 */
	public Map<String, String> getResourceActivityStatus(String resourceId)
			throws Exception {
		
		return activityRegistryDao.getResourceActivityStatus(resourceId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO#updateResourceActivityStatus(java.lang.String, java.lang.String)
	 */
	public boolean updateResourceActivityStatus(String resourceId,
			String activityName) throws Exception {
	
		return activityRegistryDao.updateResourceActivityStatus(resourceId, activityName);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO#sendEmailTeamMemberRegistration()
	 */
	public boolean sendEmailTeamMemberRegistration(Integer resourceId, String provUserName) throws BusinessServiceException{
		boolean statusEmail = false;
		try {
			
			VendorResource findResource = new VendorResource();
			findResource.setResourceId(resourceId);
			VendorResource vendorResource = null;
			vendorResource = iVendorResourceDao.query(findResource);
			UserProfile findUserProfile = new UserProfile();
			UserProfile tempUserProfile = null;
			String userId = null;
			String emailId = null;
			String altEmail = null;
			String password = null;
			String name = null;
			UserProfile adminProfile = null;
						
			if (vendorResource != null){
				userId = vendorResource.getUserName();
				findUserProfile.setUserName(userId);
				tempUserProfile = iUserProfileDao.query(findUserProfile);
				if (tempUserProfile != null) {
					emailId = tempUserProfile.getEmail();
					name = tempUserProfile.getLastName() + " " + tempUserProfile.getFirstName();
					password = tempUserProfile.getPassword();
					altEmail = tempUserProfile.getAltEmail();
				}
			}
			if(null != userId)
			{
				if (password == null || password.trim().length() ==0) {
					logger.info("Generating password for user--" + userId);
					password =  AdminUtil.generatePassword();
					
					UserProfile userProfile = new UserProfile();
					userProfile.setUserName(userId);
					
					userProfile.setPassword(CryptoUtil.encrypt(password));
					
					//Gets the Provider Admin's details based on the Provider User Name
					adminProfile  = iUserProfileDao.getProvAdminDetails(provUserName);
					
					if (adminProfile == null)
						throw new BusinessServiceException(" Error while fetching Provider Administrator Details. Confirmation Mail to the new Team Member is not sent");
					
					iUserProfileDao.updatePassword(userProfile);
					
					this.iProviderEmailBO.sendProviderMemberRegistrationConfirmationMail(userId, password, emailId,altEmail, resourceId, adminProfile);
				}
			}
			
			statusEmail = true;
		}catch (Exception bse){
			throw new BusinessServiceException(bse);
		}
		return statusEmail;
	}
	
	public boolean submitRegistration(VendorHdr vendorHdr, Integer resourceId, String provUserName) throws BusinessServiceException{
		 boolean submitReg = false;
		boolean approvalFlag = false;
		AuditVO auditVO = null;	
		
		try {					
			iAuditBO.updateStatusVendorResource(resourceId, IAuditStates.RESOURCE_PENDING_APPROVAL);
			
			Integer vendorWFStateId = iVendorHdrDao.getVendorWFStateId(vendorHdr.getVendorId());			
			if((vendorWFStateId == 3) ||(vendorWFStateId == 26)||(vendorWFStateId == 34))
			{
				approvalFlag = true;			
			}
			else
			{
				vendorHdr.setVendorStatusId(2);	
				iVendorHdrDao.updateWFStateId(vendorHdr);					
			}
			
			auditVO = new AuditVO(vendorHdr.getVendorId(), 0,
					AuditStatesInterface.VENDOR,
					AuditStatesInterface.VENDOR_PENDING_APPROVAL);
			auditVO.setAuditKeyId(vendorHdr.getVendorId());
			iAuditBO.auditVendorHeader(auditVO,approvalFlag);
			
			sendEmailTeamMemberRegistration(resourceId, provUserName);
		
			submitReg = true;
		} catch (DBException dae) {
			logger
					.info("DB Exception @ActivityRegistryBOImpl.submitRegistration()::" + dae.getMessage());
			throw new BusinessServiceException(
					"DB Layer Exception Occurred while submitting team member",
					dae);
		} catch (Exception e) {
			logger.info("[Genaral Exception]@ ActivityRegistryBOImpl.submitRegistration()::" + e.getMessage());
			throw new BusinessServiceException(
					"General Exception Occured while submitting team member", e);
		}
		return submitReg;
	}
	
	public String getResourcePriInd(String resourceId) throws Exception
	{
		String primaryInd = null;
		try
		{
			primaryInd = iVendorResourceDao.getResourcePrimaryInd(resourceId);
		}catch(Exception a_Ex)
		{
			logger.info("[Genaral Exception]@ ActivityRegistryBOImpl.getResourcePriInd():" + a_Ex.getMessage());
			throw new BusinessServiceException(
					"General Exception Occured while team member's Primary Indicator", a_Ex);
			
		}
		return primaryInd;
	}
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public List checkActivityStatus(int vendorId) throws BusinessServiceException
	{
		List resourceActKeyList = null;
		try
		{
			resourceActKeyList = activityRegistryDao.queryResourceActivityStatus(vendorId);
		}
		catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}
		return resourceActKeyList;
	}
	
	
	public boolean sendEmailForTeamMemberRegistration(Integer resourceId, String provUserName) throws BusinessServiceException{
		boolean statusEmail = false;
		try {
			
			VendorResource findResource = new VendorResource();
			findResource.setResourceId(resourceId);
			VendorResource vendorResource = null;
			vendorResource = iVendorResourceDao.query(findResource);
			UserProfile findUserProfile = new UserProfile();
			UserProfile tempUserProfile = null;
			String userId = null;
			String emailId = null;
			String altEmail = null;
			String password = null;
			String name = null;
			UserProfile adminProfile = null;
						
			if (vendorResource != null){
				userId = vendorResource.getUserName();
				findUserProfile.setUserName(userId);
				tempUserProfile = iUserProfileDao.query(findUserProfile);
				if (tempUserProfile != null) {
					emailId = tempUserProfile.getEmail();
					name = tempUserProfile.getLastName() + " " + tempUserProfile.getFirstName();
					password = tempUserProfile.getPassword();
					altEmail = tempUserProfile.getAltEmail();
					
					if(null == altEmail)
					{
						altEmail = StringUtils.EMPTY;
					}
				}
			}
			if(null != userId)
			{
				if (password == null || password.trim().length() ==0) {
					logger.info("Generating password for user--" + userId);
					password =  AdminUtil.generatePassword();
					
					UserProfile userProfile = new UserProfile();
					userProfile.setUserName(userId);
					
					userProfile.setPassword(CryptoUtil.encrypt(password));
					
					//Gets the Provider Admin's details based on the Provider User Name
					adminProfile  = iUserProfileDao.getProvAdminDetails(provUserName);
					
					if (adminProfile == null)
						throw new BusinessServiceException(" Error while fetching Provider Administrator Details. Confirmation Mail to the new Team Member is not sent");
					
					iUserProfileDao.updatePassword(userProfile);
					
					this.iProviderEmailBO.sendProviderMemberRegistrationConfirmationMail(userId, password, emailId,altEmail, resourceId, adminProfile);
				}
			}
			
			statusEmail = true;
		}catch (Exception bse){
			throw new BusinessServiceException(bse);
		}
		return statusEmail;
	}
	
}
