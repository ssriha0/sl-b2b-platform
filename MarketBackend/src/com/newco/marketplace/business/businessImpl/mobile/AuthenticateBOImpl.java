package com.newco.marketplace.business.businessImpl.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.UpdateMobileAppVerReq;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.authenticate.vo.AuthenticateUserVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IAuthenticateUserDao;
import com.newco.marketplace.persistence.iDao.provider.ILoginDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.CryptoUtil.CannotPerformOperationException;
import com.newco.marketplace.utils.CryptoUtil.InvalidHashException;
import com.newco.marketplace.vo.mobile.AppVersionData;
import com.newco.marketplace.vo.mobile.AppVersionDataList;
import com.newco.marketplace.vo.mobile.FirmDetailsVO;
import com.newco.marketplace.vo.mobile.LocationResponseData;
import com.newco.marketplace.vo.mobile.UserDetailsVO;
import com.newco.marketplace.vo.mobile.UserProfileData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceDataList;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;



public class AuthenticateBOImpl implements IAuthenticateUserBO {

private  IAuthenticateUserDao authenticateUserDao;
private Cryptography cryptography;
private ILoginDao iLoginDao;
private static int _maxLoginAttempts = Integer.MIN_VALUE;

public ILoginDao getiLoginDao() {
	return iLoginDao;
}
public void setiLoginDao(ILoginDao iLoginDao) {
	this.iLoginDao = iLoginDao;
}

public AuthenticateBOImpl(ILoginDao loginDao) {
	this.iLoginDao = loginDao;
}


private IApplicationProperties applicationProperties;	

	public IApplicationProperties getApplicationProperties() {
	return applicationProperties;
}
public void setApplicationProperties(
		IApplicationProperties applicationProperties) {
	this.applicationProperties = applicationProperties;
}
	public Cryptography getCryptography() {
	return cryptography;
}
public void setCryptography(Cryptography cryptography) {
	this.cryptography = cryptography;
}
	public IAuthenticateUserDao getAuthenticateUserDao() {
		return authenticateUserDao;
	}
	public void setAuthenticateUserDao(IAuthenticateUserDao authenticateUserDao) {
		this.authenticateUserDao = authenticateUserDao;
	}

	private Logger logger = Logger.getLogger(AuthenticateBOImpl.class);
	
	
	//Method to get location details of the user
	public List<LocationResponseData> getLocationDetails(int contactId) throws Exception{
		List<LocationResponseData> locationList =  new ArrayList<LocationResponseData>(); 
		locationList = authenticateUserDao.getLocationDetails(contactId);
		return locationList;
	}
	
	//Method to get Firm details
	public FirmDetailsVO  getFirmDetails(int contactId) throws Exception{
		FirmDetailsVO firmDetailsVO = authenticateUserDao.getFirmDetails(contactId);
		return firmDetailsVO;
	}
	//Method to Check whether user name and password is blank or not.
	public Integer validateUserCredentials(String userName,String password){
		
		if(StringUtils.isBlank(userName) && StringUtils.isBlank(password))
		{
			return 1;
		}
		else if(StringUtils.isBlank(userName)){
			return 2;			
		}
		else if(StringUtils.isBlank(password))
		{
		      return 3; 	
		}
	
		return 0;
	}
	
	//Method to authenticate username from DB
	public UserProfileData authenticateUser(String userName,String credential) throws Exception{
		
		UserProfileData userProfileData = authenticateUserDao.findById(userName);
		
		if(userProfileData != null && !StringUtils.isBlank(userProfileData.getUserName()) &&  !StringUtils.isBlank(userProfileData.getPassword()) ) {
			 
			
			Integer flag= validateCredentials(userName,userProfileData.getUserName(),credential,userProfileData.getPassword());
			if(flag==1)
			{
				userProfileData.setFlagForUsername(false);
				return userProfileData;
			}
			else if(flag==2)
			{
				userProfileData.setFlagForUsername(true);
				return userProfileData;
			}
			else
			{
				return null;
			}
		
		}
		return null;
	
	}
	
	//Method to check whether username and password is valid
	public Integer validateCredentials(String inputUserName,String expectedUserName,String inputPassword,
			String expectedPassword) {
		
		Integer flag=0;
		
		//Moved this call to controller to encrypt password before it is passed down stream.
		//String encrytedPassword=CryptoUtil.generateHash(inputPassword);
		
		if(null!=inputPassword)
		{
			try {
				if(expectedUserName.equalsIgnoreCase(inputUserName) && CryptoUtil.verifyPassword(inputPassword, expectedPassword)){
				flag=1;
				}
				else if(expectedUserName.equalsIgnoreCase(inputUserName))
				{
				flag=2;
				}
			} catch (CannotPerformOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidHashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;

	}

	//Method to get resource level of the user
	public Integer getResourceLevel(String username) throws DataServiceException
	{
		Integer resourceLevel=1;
		List<AuthenticateUserVO> activityPermissionList=authenticateUserDao.getUserActivityRolesList(username);
		for(AuthenticateUserVO Permissions:activityPermissionList)
		{
			if((!(Permissions.getActivityName().equals("View Order Pricing"))))
			{
				resourceLevel=1;
			}
			else if(Permissions.getActivityName().equals("View Order Pricing"))
			{
				resourceLevel=2;
			}
			

		}
		return resourceLevel;
	}
	
	//Method to generate unique token for mobile 
	 public String generateUniqueToken(String deviceId,Integer resourceId,
			 String deviceOS,String currentAppVersion) throws Exception{
		   Integer expiryDateLimit =  Integer.parseInt(
					applicationProperties.getPropertyFromDB(MPConstants.TOKEN_EXPIRY_DATE_LIMIT));
		    Date createdDate=new Date();
		    Date expiryDate=DateUtils.addDays(createdDate,expiryDateLimit);

		    String uniqueToken=deviceId+"&"+resourceId+"&"+createdDate+"&"+expiryDate;
		    CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(uniqueToken);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);		
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
			
			// Check if there are any active tokens for the same user/device 
			// If yes, update the status to expired. 
			authenticateUserDao.expireMobileToken(deviceId, resourceId,MPConstants.ACTIVE);
			
			String token=null;
			if(null!=cryptographyVO.getResponse()){
				token=cryptographyVO.getResponse();
				token=cryptographyVO.getResponse().replaceAll("\r\n","").replaceAll("\r","").replaceAll("\n","");
				
					authenticateUserDao.insertMobileToken(deviceId, resourceId, token,
							createdDate,expiryDate,MPConstants.ACTIVE,deviceOS,currentAppVersion);
			}
			
		   return token;
	   }
	
	 
	 public LoginVO getLoginDetails(String username)throws Exception
	 {
		 LoginVO objLoginVO=new LoginVO();
		 objLoginVO.setUsername(username);
		 
		 LoginVO dbLoginVO = iLoginDao.query(objLoginVO);
		 
		 return dbLoginVO;
		 
	 }
	 
	 
	 public boolean updateLoginDetails(LoginVO objLoginVO) throws Exception
	 {
		boolean updated= iLoginDao.updateLoginInd(objLoginVO);
		return updated;
	 }
	 
	 
	public int getMaxLoginAttempts() {
		if (_maxLoginAttempts == Integer.MIN_VALUE) {
			_maxLoginAttempts = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MAX_LOGIN_ATTEMPTS_LIMIT));
		}
		return _maxLoginAttempts;
	}
	
	public Boolean updatePassword(ChangePasswordVO changePasswordVO) throws Exception {
		List pwdList = null;

		pwdList = iLoginDao.getPasswordList(changePasswordVO.getUserName());
		if (pwdList != null) {
			if (pwdList.contains(changePasswordVO.getPassword())) {
				return false;
			}
		}
		return iLoginDao.updatePassword(changePasswordVO);
	}
	public UserProfileData getProviderUserProfileByUserName(String userName) throws Exception{
		UserProfileData userProfileData = authenticateUserDao.findById(userName);
		if(null != userProfileData){
			return userProfileData;
		}else{
			return null;
		}
	}
	 
	/**
	 * R14_0 method returns the role of the resource
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getRoleOfResource(final String userName, Integer resourceId) throws BusinessServiceException{

		List<UserDetailsVO> userDetailList = null;

		boolean isSOMPermission = false;
		boolean isViewOrderPricingPermission = false;
		boolean isPrimaryInd = false;
		boolean isAdminInd = false;
		boolean isDispatchInd = false;
		Integer roleId = 0;

		try{
			userDetailList = authenticateUserDao.getUserRoleDetails(userName, resourceId);
		}catch(Exception e){
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getRoleOfResource()");
			throw new BusinessServiceException(e.getMessage());
		}

		if(null != userDetailList && userDetailList.size()>0){
			isPrimaryInd = userDetailList.get(0).isPrimaryInd();
			isAdminInd = userDetailList.get(0).isAdminInd();
			isDispatchInd = userDetailList.get(0).isDispatcherInd();

			for(UserDetailsVO userDetailsVO: userDetailList){

				int activityId = userDetailsVO.getActivityId().intValue();

				if(!isSOMPermission && MPConstants.SOM_PERMISSION == activityId){
					isSOMPermission = true;
				}
				if(!isViewOrderPricingPermission && MPConstants.VIEW_ORDER_PRICING_PERMISSION == activityId){
					isViewOrderPricingPermission = true;
				}
			}
		}
		
		// Default value is false - Role 2 will be returned instead of 3
		String returnRoleThree = MPConstants.FALSE;
		try {
			returnRoleThree = applicationProperties.getPropertyFromDB(MPConstants.RETURN_ROLE_THREE_FOR_MOBILE);
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SL-20898 Code to return the role as 3 if the resource id is present in the new table
				if(StringUtils.isNotBlank(returnRoleThree) && returnRoleThree.equalsIgnoreCase(MPConstants.FALSE)){
					boolean isExistsResourceId=false;
					if(null == resourceId){
						try {
							//fetch the resourceId of the given username
							resourceId=authenticateUserDao.fetchResourceIdFromUsername(userName);
						}catch (DataServiceException e) {
							logger.error("Exception Occured in AuthenticateBOImpl-->getRoleOfResource()-->returnRoleThree");
							e.printStackTrace();
						}
					}
					try {
						isExistsResourceId = authenticateUserDao.isResourceIdExistsInBetaTable(resourceId);
					} catch (DataServiceException e) {
						logger.error("Exception Occured in AuthenticateBOImpl-->getRoleOfResource()-->returnRoleThree");
						e.printStackTrace();
					}
					if(isExistsResourceId){
						returnRoleThree =  MPConstants.TRUE;
					}
				}
				
				//Sl-20898 changes END
		//Set role
		//if only SOMPermission --> role = 1
		//if SOMPermission and ViewOrderPricingPermission and none of isPrimaryInd,isAdminInd,isDispatchInd --> role = 2
		//if SOMPermission and ViewOrderPricingPermission and any of isPrimaryInd,isAdminInd,isDispatchInd --> role = 3
		if(isSOMPermission && isViewOrderPricingPermission && (isPrimaryInd || isAdminInd || isDispatchInd)){
			if(StringUtils.equalsIgnoreCase(returnRoleThree, MPConstants.TRUE)){
				roleId = 3;
			}else if(StringUtils.equalsIgnoreCase(returnRoleThree, MPConstants.FALSE)){
				roleId = 2;
			}
		}else if(isSOMPermission && isViewOrderPricingPermission){
			roleId = 2;
		}else if (isSOMPermission){
			roleId = 1;
		}
		return roleId;
	}
	
	public AppVersionData validateAppVersion(String deviceOS,String currentVer) throws Exception{
		return authenticateUserDao.validateAppVersion(deviceOS,currentVer);
	}
	
	public String discontinueOldVersion(String key) throws Exception{
		return authenticateUserDao.discontinueOldVersion(key);
	}
	
	public AppVersionDataList fetchMobileAppVersions() throws Exception{
		return authenticateUserDao.fetchMobileAppVersions();
	}
	
	public void updateMobileAppVersions(UpdateMobileAppVerReq updateMobileAppVerReq) throws Exception{
		authenticateUserDao.updateMobileAppVersions(updateMobileAppVerReq);
	}	
	
	public MobileDeviceDataList fetchMobileDevices(String deviceName)
			throws com.newco.marketplace.exception.core.BusinessServiceException {
		try {
			return authenticateUserDao.fetchMobileDevices(deviceName);
		}catch(Exception e){
			logger.error("Exception occured in AuthenticateBOImpl-->fetchMobileDevices()");
			throw new BusinessServiceException(e.getMessage());
		}

				
		
	}
	
	public void updateMobileDeviceAppVersion(MobileDeviceDataList mobileDeviceDataList)
			throws com.newco.marketplace.exception.core.BusinessServiceException {
		List<MobileDeviceData>  mobileDeviceData = mobileDeviceDataList.getMobileDeviceDataList();
		try {
			authenticateUserDao.updateMobileDeviceAppVersion(mobileDeviceData);
		}catch(Exception e){
			logger.error("Exception occured in AuthenticateBOImpl-->updateMobileDeviceAppVersion()");
			throw new BusinessServiceException(e.getMessage());
		}

		
	}
	
	
}
