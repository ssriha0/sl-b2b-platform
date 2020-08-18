package com.newco.marketplace.persistence.iDao.mobile;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.UpdateMobileAppVerReq;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.authenticate.vo.AuthenticateUserVO;
import com.newco.marketplace.vo.mobile.AppVersionData;
import com.newco.marketplace.vo.mobile.AppVersionDataList;
import com.newco.marketplace.vo.mobile.FirmDetailsVO;
import com.newco.marketplace.vo.mobile.LocationResponseData;
import com.newco.marketplace.vo.mobile.UserDetailsVO;
import com.newco.marketplace.vo.mobile.UserProfileData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceDataList;

public interface IAuthenticateUserDao {
	public UserProfileData findById(String userName) throws Exception ;
	public List<LocationResponseData> getLocationDetails(int contactId) throws Exception;
	public FirmDetailsVO  getFirmDetails(int contactId) throws Exception;

	public List<AuthenticateUserVO> getUserActivityRolesList(String userName) throws DataServiceException;

	public void insertMobileToken (String deviceId,Integer resourceId,String outhToken,Date createdDate,Date expiryDate,String tokenStatus,String deviceOS,String currentAppVersion) throws DataServiceException;
	
	public void expireMobileToken (String deviceId,Integer resourceId,String tokenStatus) throws DataServiceException;
	
	public AppVersionData validateAppVersion(String deviceOS,String currentVer) throws DataServiceException;
	
	public String discontinueOldVersion(String key) throws DataServiceException;
	
	public AppVersionDataList fetchMobileAppVersions() throws DataServiceException;	
	
	public void updateMobileAppVersions(UpdateMobileAppVerReq updateMobileAppVerReq) throws DataServiceException;
	
	public List<UserDetailsVO> getUserRoleDetails(final String userName, final Integer resourceId) throws DataServiceException;
	/**
	 * SL-20898 Method to fetch the resourceId of the given username
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchResourceIdFromUsername(String userName)throws DataServiceException;
	/**
	 * SL-20898 Method to check whether the resource id is exists in the new table beta_role_three_providers
	 * @param resourceId
	 * @return boolean
	 */
	public boolean isResourceIdExistsInBetaTable(Integer resourceId) throws DataServiceException;
	
	public MobileDeviceDataList fetchMobileDevices(String DeviceName) throws DataServiceException;
	public void updateMobileDeviceAppVersion(List<MobileDeviceData> mobileDeviceData) throws DataServiceException;
	public String fetchMobileToken(String deviceName, String resourceId) throws DataServiceException;
	
}

