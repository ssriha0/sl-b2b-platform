package com.newco.marketplace.business.iBusiness.api;

import java.util.List;

import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.dto.api.APIDto;
import com.newco.marketplace.dto.api.APIGroup;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.api.APIVO;

/**
 * This interface
 *
 * @author Priti Gangrade
 *
 */
public interface IAPISecurity {
	public boolean isUserExisting(int userId, String consumerKey, int internalConsumerFlag) throws BusinessServiceException;
	public APIApplicationDTO getApplication(String consumerKey);
	public boolean isSecurityEnabled();
	public boolean isBasicAuthEnabled();
	public List<String> getAPISecurityFieldRules(String consumerKey);
	public List<APIApplicationDTO> getApplicationList() ;
	public APIApplicationDTO getApplicationNoCache(String consumerKey);
	public APIDto getAPIList();
	public List<Integer> getAPIUserList(String consumerKey);
	public void addAPIUser(String consumerKey, Integer userId, String loggedUserName) throws BusinessServiceException;
	public void removeAPIUser(String consumerKey, Integer userId, String loggedUserName) throws BusinessServiceException;
	public void addApplication(String appName, String loggedUserName) throws BusinessServiceException;
	public void removeApplication(String consumerKey, String loggedUserName) throws BusinessServiceException;
	public void resetPassword(String consumerKey, String loggedUserName) throws BusinessServiceException;
	public void modifyPermissions(String consumerKey, List<Integer> apiIdList, List<Integer> delIdList, String loggedUserName) throws BusinessServiceException;
	//public void revokePermissions(String consumerKey, List<Integer> apiIdList, String loggedUserName) throws BusinessServiceException;
}
