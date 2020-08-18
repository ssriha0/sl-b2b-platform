package com.newco.marketplace.persistence.iDao.mobile;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.api.APISecurityVO;
import com.newco.marketplace.vo.api.APIVO;

public interface APISecurityDAO {
	//API Security methods
	public boolean isUserExisting(int userId, String consumerKey, int internalConsumerFlag) throws DataServiceException;
	public List<APISecurityVO> getAPIListForApplication(String consumerKey) throws DataServiceException;
	public List<String> getAPISecurityFieldRules(String consumerKey) throws DataServiceException;
	public List<APISecurityVO> getApplicationList() throws DataServiceException;
	public List<APIVO> getAPIList() throws DataServiceException;
	public void updatePassword(String consumerKey, String password) throws DataServiceException;
	public void addApplication(String name, String consumerKey, String password) throws DataServiceException;
	public void addPermissions(String consumerKey, List<Integer> apiIdList) throws DataServiceException;
	//public void revokePermissions(String consumerKey, List<Integer> apiIdList) throws DataServiceException;
	public void removeApplication(String consumerKey) throws DataServiceException;
	public void logActivity(String consumerKey, int activityId, String description, String userId) throws DataServiceException;
	public void addApiUser(String consumerKey, int userId) throws DataServiceException;
	public void removeApiUser(String consumerKey, int userId) throws DataServiceException;
	public List<Integer> getAPIUserList(String consumerKey) throws DataServiceException;
	
	public boolean isResourceValid(int resourceId) throws DataServiceException;
	public boolean isMobileTokenValid(String outhToken,String resourceId,Date serverDate)throws DataServiceException;  
	public String getOAuthConsumerSecret(String consumerKey) throws DataServiceException;
}
