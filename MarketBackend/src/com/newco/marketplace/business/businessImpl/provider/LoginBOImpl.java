package com.newco.marketplace.business.businessImpl.provider;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.ILoginDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.common.LoginUserProfile;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.security.SecurityVO;

/**
 *
 * $Revision: 1.13 $ $Author: glacy $ $Date: 2008/04/26 00:40:25 $
 *
 */ 
public class LoginBOImpl implements ILoginBO {

	private ILoginDao iLoginDao;
	private IVendorHdrDao vendorHdrDao;

	private IVendorResourceDao vendorResourceDao;
	private static final Logger logger = Logger.getLogger(LoginBOImpl.class.getName());

	private static int _maxLoginAttempts = Integer.MIN_VALUE;
	private static int _lockingPeriod = Integer.MIN_VALUE;

	public LoginBOImpl(ILoginDao loginDao, IVendorHdrDao vendorHdrDao, IVendorResourceDao vendorResourceDao) {
		this.iLoginDao = loginDao;
		this.vendorHdrDao = vendorHdrDao;
		this.vendorResourceDao = vendorResourceDao;

	}

	private int getMaxLoginAttempts() {
		if (_maxLoginAttempts == Integer.MIN_VALUE) {
			_maxLoginAttempts = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MAX_LOGIN_ATTEMPTS_LIMIT));
		}
		return _maxLoginAttempts;
	}

	private int getLockingPeriod() {
		if (_lockingPeriod == Integer.MIN_VALUE) {
			String lockingPeriodStr = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.ACCOUNT_LOCK_TIME);
			if (lockingPeriodStr != null) {
				_lockingPeriod = Integer.parseInt(lockingPeriodStr.trim());
			} else {
				_lockingPeriod = 1800000;
			}
		}
		return _lockingPeriod;
	}

	/* 
	 * authorize implementation for the publicAPI
	 * 
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.ILoginBO#authorize(java.lang.String, java.lang.String, java.lang.String)
	 * 
	 */
	public int authorize(String userName, String apiKey, String ipAddress) throws Exception {
		int appId = 0;
		SecurityVO securityVO = iLoginDao.authorizeUser(userName);
		securityVO.setAppKey(apiKey);
		securityVO.setIpAddress(ipAddress);
		SecurityVO securityVOCheck = iLoginDao.checkUserCredentials(securityVO);
		if (null != securityVOCheck) {
			appId = Integer.parseInt(securityVOCheck.getAppId());
		}
		return appId;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.login.ILoginBO#isTempPassword()
	 */
	public int validatePassword(LoginVO objLoginVO) throws Exception {

		String userPassword = objLoginVO.getPassword();
		LoginVO dbLoginVO = iLoginDao.query(objLoginVO);

		if (dbLoginVO == null) {
			logger.debug("object is null");
			return -2;
		}

		boolean updateLoginInd = false;
		int unsuccessLoginInd = 0;
		int lockedInd = 0;
		String stateCd = null;
		Date now = new Date();
		Date lockExpiry = new Date();
		lockExpiry.setTime(dbLoginVO.getModDate().getTime() + getLockingPeriod()); //set the expiry time 30 mins from the modified date.

		if (dbLoginVO.getUnsuccessLoginInd() != null) {
			unsuccessLoginInd = Integer.parseInt(dbLoginVO.getUnsuccessLoginInd());
		}
		if (dbLoginVO.getLockedInd() != null)
			lockedInd = Integer.parseInt(dbLoginVO.getLockedInd());
		if (logger.isDebugEnabled()) {
			logger.debug("-----------------dbLoginVO.getUsername()--inBO Impl-------------------" + dbLoginVO.getUsername());
			logger.debug("-----------------dbLoginVO.getLockedInd()--inBO Impl-------------------" + dbLoginVO.getLockedInd());
			logger.debug("-----------------dbLoginVO.getUnsuccessLoginInd()--inBO Impl-----------" + dbLoginVO.getUnsuccessLoginInd());
		}

		//if provider then check for their state
		if (dbLoginVO.getRole().intValue() == OrderConstants.PROVIDER_ROLEID) {
			String vendorId = iLoginDao.getVendorId(objLoginVO.getUsername());
			stateCd = iLoginDao.getValidState(vendorId);
			if (stateCd == null) {
				return 4;
			}
		}

		//Check if the locking time (currently set to 30 mins) is over to unlock the user.  
		if (now.after(lockExpiry)) {
			lockedInd = 0;
			objLoginVO.setLockedInd("0");
		}

		//User Id has been locked
		if (lockedInd == 1) {
			return 2; // user locked verifyPassword(userPassword,dbLoginVO.getPassword())
		} else if (userPassword != null && !CryptoUtil.verifyPassword(userPassword,dbLoginVO.getPassword())) { //Update unsuccessLoginInd & LockedInd if user entered password is not matching with db password
			logger.debug("Inside invalid Password!!");
			unsuccessLoginInd++;
			if (unsuccessLoginInd >= getMaxLoginAttempts()) {
				objLoginVO.setLockedInd("1");
			} else {
				objLoginVO.setLockedInd("0");
			}

			objLoginVO.setUnsuccessLoginInd(Integer.toString(unsuccessLoginInd));
			try {
				updateLoginInd = iLoginDao.updateLoginInd(objLoginVO);
			} catch (Exception e) {
				logger.info("Caught Exception and ignoring", e);
			}
			return -1;
		} else if (dbLoginVO.getIsTempPassword() != null && dbLoginVO.getIsTempPassword().equals("1")) {
			return 1;
		}

		//Reset UnsuccessLoginInd & LockedInd if User entered password is matching with db password.
		else {
			logger.debug("---------------------Successful Login----------------- -------------------");
			objLoginVO.setLockedInd("0");
			objLoginVO.setUnsuccessLoginInd("0");
			try {
				updateLoginInd = iLoginDao.updateLoginInd(objLoginVO);
			} catch (Exception e) {
				logger.info("Caught Exception and ignoring", e);
			}
			return 0;

		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.login.ILoginBO#updatePassword(com.newco.marketplace.vo.ChangePasswordVO)
	 */
	public Boolean updatePassword(ChangePasswordVO changePasswordVO) throws Exception {
		List pwdList = null;

		try {
			pwdList = iLoginDao.getPasswordList(changePasswordVO.getUserName());
			if (pwdList != null) {

				if (pwdList.contains(changePasswordVO.getPassword())) {
					return false;
				}
			}
			return iLoginDao.updatePassword(changePasswordVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LoginBOImpl.getVendorDetails() due to" + ex.getMessage());
			throw new BusinessServiceException("General Exception @LoginBOImpl.getVendorDetails() due to " + ex.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILoginBO#getSecretQuestionList()
	 */
	public Map getSecretQuestionList() {
		return iLoginDao.getSecretQuestionList();
	}

	/**
	 * @param loginDao
	 */
	public LoginBOImpl(ILoginDao loginDao) {
		iLoginDao = loginDao;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.ILoginBO#getVendorId(java.lang.String)
	 */
	public String getVendorId(String username) throws Exception {
		return iLoginDao.getVendorId(username);

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String getProviderName(String username) throws Exception {
		return iLoginDao.getProviderName(username);
	}

	public VendorHdr getVendorDetails(String vendorId) throws BusinessServiceException {
		VendorHdr vo = new VendorHdr();
		try {
			logger.debug(" VID in getVendorDetails" + vendorId);

			vo.setVendorId(Integer.parseInt(vendorId));
			vo = vendorHdrDao.query(vo);
			if (vo != null)
				logger.debug(" get status id?" + vo.getVendorStatusId());

		} catch (DBException ex) {
			logger.info("DB Exception @LoginBOImpl.getVendorDetails() due to" + ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LoginBOImpl.getVendorDetails() due to" + ex.getMessage());
			throw new BusinessServiceException("General Exception @LoginBOImpl.getVendorDetails() due to " + ex.getMessage());
		}
		return vo;
	}

	/**
	 *
	 */

	public String getPrimaryIndicator(Integer vendorId) throws BusinessServiceException {
		String primary_ind = "";
		try {
			primary_ind = vendorResourceDao.getPrimaryIndicator(vendorId);
		} catch (DBException ex) {
			logger.info("DB Exception @LoginBOImpl.getPrimaryIndicator() due to" + ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @LoginBOImpl.getPrimaryIndicator() due to" + ex.getMessage());
			throw new BusinessServiceException("General Exception @LoginBOImpl.getPrimaryIndicator() due to " + ex.getMessage());
		}
		return primary_ind;
	}

	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

	public Integer retrieveResourceIDByUserName(String username) {
		return iLoginDao.retrieveResourceIDByUserName(username);
	}

	public boolean getTempPasswordIndicator(String username) {
		boolean temp = false;
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername(username);
		LoginVO retLoginVO = new LoginVO();
		try {
			retLoginVO = iLoginDao.query(loginVO);
		} catch (Exception e) {
			logger.error("There was an error retrieving the temporary password indicator.");
			e.printStackTrace();
		}
		if (retLoginVO != null) {
			if (retLoginVO.getIsTempPassword() != null && retLoginVO.getIsTempPassword().equals("1")) {
				temp = true;
			}
		}
		return temp;
	}

	/* 
	 * Get the passwowrd based on the username and returns it in LoginVO, populated with rest of the VO
	 */
	public LoginVO getPassword(String username) {
		boolean temp = false;
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername(username);
		LoginVO retLoginVO = new LoginVO();
		try {
			retLoginVO = iLoginDao.query(loginVO);
		} catch (Exception e) {
			logger.error("There was an error retrieving password for user");
			e.printStackTrace();
		}

		return retLoginVO;
	}

	public LoginUserProfile getLoginInfoForBuyer(String userName) {
		LoginUserProfile loginUserProfile = new LoginUserProfile();
		LoginUserProfile retLoginUserProfile = new LoginUserProfile();
		loginUserProfile = iLoginDao.getBuyerLoginInfo(userName);
		if(null==loginUserProfile){
			return null;
		}
		retLoginUserProfile = iLoginDao.getContactInfo(loginUserProfile.getContactId());
		if(null==retLoginUserProfile){
			return null;
		}
		retLoginUserProfile.setEntityId(loginUserProfile.getEntityId());
		retLoginUserProfile.setResourceId(loginUserProfile.getResourceId());
		
		return retLoginUserProfile;
	}

	public LoginUserProfile getLoginInfoForProvider(String userName) {
		LoginUserProfile loginUserProfile = new LoginUserProfile();
		LoginUserProfile retLoginUserProfile = new LoginUserProfile();
		loginUserProfile = iLoginDao.getProviderLoginInfo(userName);
		if(null==loginUserProfile){
			return null;
		}
		retLoginUserProfile = iLoginDao.getContactInfo(loginUserProfile.getContactId());
		if(null==retLoginUserProfile){
			return null;
		}
		retLoginUserProfile.setEntityId(loginUserProfile.getEntityId());
		retLoginUserProfile.setResourceId(loginUserProfile.getResourceId());
		
		return retLoginUserProfile;
	}
	
	//SL-17698: method to fetch show_member_offers_ind from vendor_resource
	public Integer showMemberOffers(Integer vendorId){
		Integer indicator = iLoginDao.showMemberOffers(vendorId);
		return indicator;
	}
	
	//SL-19293: method to fetch new leads T&C indicator from vendor_lead_profile
	public Integer showLeadsTCIndicator(Integer vendorId){
		Integer indicator = iLoginDao.showLeadsTCIndicator(vendorId);
		return indicator;
	}
	
	//checks whether provider has Manage Business Profile Permission
	public int getPermission(Integer resourceId){
		return iLoginDao.getPermission(resourceId);
	}
	
	//checks whether provider has any un-archived CAR rules
	public int getUnarchivedCARRulesCount(Integer vendorId){
		return iLoginDao.getUnarchivedCARRulesCount(vendorId);
		
	}
	
	//checks whether provider has any active pending CAR rules
	public int getActivePendingCARRulesCount(Integer vendorId){
		return iLoginDao.getActivePendingCARRulesCount(vendorId);
		
	}
	
	//checks whether vendor has Leads accnt
	public Integer showLeadsSignUp(Integer vendorId){
		return iLoginDao.showLeadsSignUp(vendorId);
	}
	//SL 19293 update new T&C indicator
	public void updateNewTandC(Integer vendorId, String userName){
		iLoginDao.updateNewTandC(vendorId, userName);
	}
	public boolean isNonFundedBuyer(Integer buyerId) {
		return iLoginDao.isNonFundedBuyer(buyerId);
	}
	
	public String buyerSkuPrimaryIndustry(Integer vendorId){
		logger.info("Inside LoginBOImpl.buyerSkuPrimaryIndustry");
		String count = "";
		try {
			count = vendorHdrDao.buyerSkuPrimaryIndustry(vendorId);
		} catch (DataServiceException e) {
			logger.error("Exception in LoginBOImpl.buyerSkuPrimaryIndustry",e);
		}
		return count;
	}
	
}
