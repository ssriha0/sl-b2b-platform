package com.newco.marketplace.business.iBusiness.mobile;

import java.util.List;

import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.UpdateMobileAppVerReq;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.AppVersionData;
import com.newco.marketplace.vo.mobile.AppVersionDataList;
import com.newco.marketplace.vo.mobile.FirmDetailsVO;
import com.newco.marketplace.vo.mobile.LocationResponseData;
import com.newco.marketplace.vo.mobile.UserProfileData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceDataList;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.LoginVO;



public interface IAuthenticateUserBO {
	
	public List<LocationResponseData> getLocationDetails(int contactId) throws Exception;
	public FirmDetailsVO  getFirmDetails(int contactId) throws Exception;
	
	public Integer validateUserCredentials(String userName,String password);
	
	
	public Integer validateCredentials(String inputUserName,String expectedUserName,String inputPassword,
			String expectedPassword);
	
	public UserProfileData authenticateUser(String userName,String credential) throws Exception;
	
	public Integer getResourceLevel(String username) throws Exception;
	
	public String generateUniqueToken(String deviceId,Integer resourceId,String deviceOS,String currentAppVersion)throws Exception;
	 
	public LoginVO getLoginDetails(String username) throws Exception;
	 
	public boolean updateLoginDetails(LoginVO objLoginVO) throws Exception;
	 
	public int getMaxLoginAttempts() throws Exception;
	
	//Create New Password
	
	public Boolean updatePassword(ChangePasswordVO changePasswordVO) throws Exception;
	
	public UserProfileData getProviderUserProfileByUserName(String userName) throws Exception;
	
	public AppVersionData validateAppVersion(String deviceOS,String currentVer) throws Exception;
	
	public String discontinueOldVersion(String key) throws Exception;
	
	/**
	 * R14_0 method returns the role of the resource
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getRoleOfResource(final String userName, Integer resourceId) throws BusinessServiceException;
	public AppVersionDataList fetchMobileAppVersions() throws Exception;
	
	public void updateMobileAppVersions(UpdateMobileAppVerReq updateMobileAppVerReq) throws Exception;
	
	public MobileDeviceDataList fetchMobileDevices(String deviceName) throws BusinessServiceException;
	
	public void updateMobileDeviceAppVersion(MobileDeviceDataList mobileDeviceDataList)throws BusinessServiceException;
	
}
