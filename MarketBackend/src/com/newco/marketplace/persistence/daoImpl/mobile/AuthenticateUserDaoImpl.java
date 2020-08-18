package com.newco.marketplace.persistence.daoImpl.mobile;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.UpdateMobileAppVerReq;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.authenticate.vo.AuthenticateUserVO;
import com.newco.marketplace.mobile.authenticate.vo.MobileTokenVO;
import com.newco.marketplace.persistence.iDao.mobile.IAuthenticateUserDao;
import com.newco.marketplace.vo.mobile.AppVersionData;
import com.newco.marketplace.vo.mobile.AppVersionDataList;
import com.newco.marketplace.vo.mobile.FirmDetailsVO;
import com.newco.marketplace.vo.mobile.LocationResponseData;
import com.newco.marketplace.vo.mobile.UserDetailsVO;
import com.newco.marketplace.vo.mobile.UserProfileData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceDataList;
import com.sears.os.dao.impl.ABaseImplDao;


public class AuthenticateUserDaoImpl extends ABaseImplDao implements IAuthenticateUserDao{

	public UserProfileData findById(String userName) throws DBException {
            
		UserProfileData returnValue = null;
        try{
        	// code change for SLT-2112
        	Map<String, String> parameter = new HashMap<String, String>();
        	parameter.put("userName", userName);
            returnValue =  (UserProfileData) getSqlMapClient().queryForObject("login_auth_provider.query", parameter);
        }
        catch (SQLException ex) {
		     logger.info("SQL Exception @AuthenticateUserDaoImpl.query()", ex);
		     throw new DBException("SQL Exception @AuthenticateUserDaoImpl.query()", ex);
        }catch (Exception ex) {
		     logger.info("General Exception @AuthenticateUserDaoImpl.query()", ex);
		     throw new DBException("General Exception @AuthenticateUserDaoImpl.query()", ex);
       }
        
        return returnValue;
	} 
	public List<LocationResponseData> getLocationDetails(int contactId) throws DBException {
        
		List<LocationResponseData>  result = new ArrayList<LocationResponseData>();
        try{
        	result = (List<LocationResponseData> )queryForList("provider_location_details.query", contactId);
        }
        catch (Exception ex) {
		     logger.info("General Exception @AuthenticateUserDaoImpl.query()", ex);
		     throw new DBException("General Exception @AuthenticateUserDaoImpl.query()", ex);
       }
        
        return result;
	}

	public FirmDetailsVO getFirmDetails(int contactId) throws DBException {
        
		FirmDetailsVO result = new FirmDetailsVO();
        try{
        	result = (FirmDetailsVO) getSqlMapClient().queryForObject("firm_details_auth.query", contactId);
        	 
        }
        catch (Exception ex) {
		     logger.info("General Exception @AuthenticateUserDaoImpl.query()", ex);
		     throw new DBException("General Exception @AuthenticateUserDaoImpl.query()", ex);
       }
        
        return result;
	}
	
	/**
	 * get the list of activities for the user
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	public List<AuthenticateUserVO> getUserActivityRolesList(String userName) throws DataServiceException {

		List<AuthenticateUserVO> returnList = null;

		try {

			   returnList = queryForList("activity_authenticate.query", userName);

			logger.info("[list] > " + returnList);
		} catch (Exception ex) {
			logger.error("[AuthenticateUserDaoImpl.getUserActivityRolesList - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		logger.debug("[Exiting] getRoles ");
		return returnList;
	}
	
	
	public void insertMobileToken (String deviceId,Integer resourceId,String outhToken,
			Date createdDate,Date expiryDate,String tokenStatus,
			String deviceOS,String currentAppVersion) throws DataServiceException{
		MobileTokenVO mobileTokenVO = new MobileTokenVO();
	
		mobileTokenVO.setDeviceId(deviceId);
		mobileTokenVO.setResourceId(resourceId);
		mobileTokenVO.setOuthToken(outhToken);
		mobileTokenVO.setTokenStatus(tokenStatus);
		mobileTokenVO.setCreatedDate(createdDate);
		mobileTokenVO.setExpiryDate(expiryDate);
		mobileTokenVO.setDeviceOS(deviceOS);
		mobileTokenVO.setCurrentAppVersion(currentAppVersion);
		
		// TODO update the existing tokens to expired for the device resource combination
		
		insert("token_authenticate.insert", mobileTokenVO);
	}
	
	
	public void expireMobileToken (String deviceId,Integer resourceId,String tokenStatus) throws DataServiceException{
		MobileTokenVO mobileTokenVO = new MobileTokenVO();
	
		mobileTokenVO.setDeviceId(deviceId);
		mobileTokenVO.setResourceId(resourceId);
		mobileTokenVO.setTokenStatus(tokenStatus);
		
		// TODO update the existing tokens to expired for the device resource combination
		
		update("token_authenticate.update", mobileTokenVO);
	}
	
	public AppVersionData validateAppVersion(String deviceOS,String currentVer) throws DataServiceException{
		AppVersionData appVersionData = new AppVersionData();
		try {			
			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("deviceOS", deviceOS);
			params.put("currentVer", currentVer);
			appVersionData= (AppVersionData)queryForObject("validate_appversion.query", params);
								
		}catch (Exception ex) {
			logger.error("[AuthenticateUserDaoImpl.validateAppVersion - Exception] ", ex);
			throw new DataServiceException("", ex);
		}
		return appVersionData;
	}
	
	
	public String discontinueOldVersion(String key)  throws DataServiceException{
		String discontinue = null;
		try {
			
			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("appKey", key);			
			discontinue = (String)queryForObject("getAppPropValue.query", params);
		}catch (Exception ex) {
			logger.error("[AuthenticateUserDaoImpl.discontinueOldVersion - Exception] ", ex);
			throw new DataServiceException("", ex);
		}
		return discontinue;
	}
	
	/**
	 * R14_0 method returns the user details for identifying the role of the resource
	 * @param resourceId
	 * @return List<UserDetailsVO>
	 * @throws BusinessServiceException
	 */
	public List<UserDetailsVO> getUserRoleDetails(final String userName, final Integer resourceId) throws DataServiceException{

		List<UserDetailsVO> returnList = null;
		try {

			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("userName", userName);
			if(null !=userName){
				returnList = (List<UserDetailsVO>) queryForList("userRoleDetails.query", params);
			}else if(null != resourceId){
				params.put("resourceId", resourceId.intValue());
				returnList = (List<UserDetailsVO>) queryForList("userRoleDetails.query", params);
			}
			
		} catch (Exception ex) {
			logger.error("[AuthenticateUserDaoImpl.getUserRoleDetails - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return returnList;
	}
	
	public AppVersionDataList fetchMobileAppVersions() throws DataServiceException{
		AppVersionDataList appVersionDataList = new AppVersionDataList();
		List<AppVersionData> appVersionList = new ArrayList<AppVersionData>();
		try{
			appVersionList = (ArrayList<AppVersionData>)queryForList("getMobileAppVersions.query");
			appVersionDataList.setAppVersionDataList(appVersionList);
		}catch(Exception ex){
			logger.error("[AuthenticateUserDaoImpl.fetchMobileAppVersions - Exception] ", ex);
			throw new DataServiceException("", ex);
		}
		return appVersionDataList;
		
	}
	
		
	public void updateMobileAppVersions(UpdateMobileAppVerReq updateMobileAppVerReq) throws DataServiceException{		
		try{
			update("mobileAppVersions.update", updateMobileAppVerReq);		
		}
		catch(Exception e){
			logger.error("Exception occured in updateMobileAppVersions() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * SL-20898 Method to fetch the resourceId of the given user name
	 * @param userName
	 * @return
	 * @throws DataServiceException
	 */
	public Integer fetchResourceIdFromUsername(String userName)throws DataServiceException{
		Integer resourceId=null;
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("userName", userName);
			resourceId=(Integer) queryForObject("fetchResourceId.select", parameter);
		}
		catch (Exception ex) {
			throw new DataServiceException("Exception occurred in AuthenticateUserDao.fetchResourceIdFromUsername()", ex);
		}
		return resourceId;
	}
	/**
	 * SL-20898 Method to check whether the resource id is exists in the new table beta_role_three_providers
	 * @param resourceId
	 * @return boolean
	 */
	
	public boolean isResourceIdExistsInBetaTable(Integer resourceId) throws DataServiceException{
		boolean isResourceIdExists=false;
		try{
			// code change for SLT-2112
			Map<String, Integer> parameter = new HashMap<String, Integer>();
			parameter.put("resourceId", resourceId);
			Integer value=(Integer) queryForObject("isResourceIdExistsInBetaTable.select", parameter);
			if(null !=value)
			{
				isResourceIdExists= true;
			}
		}
		catch (Exception ex) {
			throw new DataServiceException("Exception occurred in AuthenticateUserDao.isResourceIdExistsInBetaTable()", ex);
		}
		return isResourceIdExists;
	}
	
	public MobileDeviceDataList fetchMobileDevices(String deviceName) throws DataServiceException{
		
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("deviceName", deviceName);
		
		MobileDeviceDataList mobileDeviceDataList = new MobileDeviceDataList();
		List<MobileDeviceData> deviceDataList = new ArrayList<MobileDeviceData>();
	try{
		deviceDataList = (ArrayList<MobileDeviceData>)queryForList("getMobileDevice.query",parameter);
		mobileDeviceDataList.setMobileDeviceDataList(deviceDataList);
	}catch(Exception ex){
		logger.error("Exception occurred in AuthenticateUserDaoImpl.fetchMobileDevices()", ex);
		throw new DataServiceException("", ex);
	}
	return mobileDeviceDataList;
	
    }
	
	
	public void updateMobileDeviceAppVersion(List<MobileDeviceData> mobileDeviceDataList) throws DataServiceException{
		
		
		try {
			if(null!=mobileDeviceDataList){
		       batchUpdate("updateMobileDeviceAppVersion.update", mobileDeviceDataList);
		       }
		} catch (Exception e) {
			logger.error("Exception occurred in AuthenticateUserDaoImpl.updateMobileDeviceAppVersion"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	} 
	
	public String fetchMobileToken(String deviceName, String resourceId) throws DataServiceException{
		
		String token = null;
		try {
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("deviceName", deviceName);
			parameter.put("resourceId", resourceId);
			token = (String) queryForObject("getMobileAuthToken.query", parameter);
		} catch (Exception e) {
			throw new DataServiceException("", e);
		}
		return token;
	}
	
}
