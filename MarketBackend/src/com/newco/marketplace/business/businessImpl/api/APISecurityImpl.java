package com.newco.marketplace.business.businessImpl.api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.newco.marketplace.business.iBusiness.api.IAPISecurity;
import com.newco.marketplace.dto.api.APIApplicationDTO;
import com.newco.marketplace.dto.api.APIDto;
import com.newco.marketplace.dto.api.APIGroup;
import com.newco.marketplace.dto.api.APIUrlPermission;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.admin.APISecurityDAO;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.vo.api.APISecurityVO;
import com.newco.marketplace.vo.api.APIVO;
import com.sears.os.business.ABaseBO;

/**
 * $Revision: 1.0 $ $Author: Shekhar Nirkhe
 *
 */
public class APISecurityImpl extends ABaseBO implements IAPISecurity {
	private static final Logger logger = Logger.getLogger(APISecurityImpl.class.getName());
	private static final int LOGGING_ACTIVITY_ADD_APP = 1;
	private static final int LOGGING_ACTIVITY_DELETE_APP = 2;
	private static final int LOGGING_ACTIVITY_ADD_USER = 3;
	private static final int LOGGING_ACTIVITY_DELETE_USER = 4;
	private static final int LOGGING_ACTIVITY_ADD_PER = 5;
	private static final int LOGGING_ACTIVITY_DELETE_PER = 6;
	private static final int LOGGING_ACTIVITY_RESET_PASSWORD = 7;

	private String oAuthFilter="on";
	private String basicAuthFilter="on";

	private APISecurityDAO apiSecurityDAO;

	public APISecurityImpl(APISecurityDAO apiSecurityDAO) {
		super();
		this.apiSecurityDAO = apiSecurityDAO;
	}
	
	public boolean isUserExisting(int userId, String consumerKey, int internalConsumerFlag) {
		try {
			String keyName = "/API/Security/application/"+ consumerKey + "/" + internalConsumerFlag + "/" + userId;
			Boolean bool = (Boolean)SimpleCache.getInstance().get(keyName);
			if (bool == null) {
				bool =  apiSecurityDAO.isUserExisting(userId, consumerKey, internalConsumerFlag);
				SimpleCache.getInstance().put(keyName, bool, SimpleCache.FIVE_MINUTES);
			}
			return bool;
		} catch (DataServiceException e) {
			logger.error("[DataServiceException] ", e);
		}
		return false;
	}

	
	public APIApplicationDTO getApplication(String consumerKey){

		String keyName = "/API/Security/Application/" + consumerKey;
		APIApplicationDTO obj = (APIApplicationDTO)SimpleCache.getInstance().get(keyName);
		if (obj == null) {				
			obj = getApplicationNoCache(consumerKey);
			SimpleCache.getInstance().put(keyName, obj, SimpleCache.TEN_MINUTES);
		}
		return obj;		
	}

	/**
	 * No cache in this method
 	 */
	public APIApplicationDTO getApplicationNoCache(String consumerKey){
		try {
			List<APISecurityVO> apiVOList  = apiSecurityDAO.getAPIListForApplication(consumerKey);
			APIApplicationDTO obj = new APIApplicationDTO(apiVOList);
			obj.setConsumerKey(consumerKey);
			return obj;			
		} catch(DataServiceException e) {
			logger.error("[BusinessServiceException] ", e);
		}
		return null;
	}

	public List<String> getAPISecurityFieldRules(String consumerKey){
		try {
			return apiSecurityDAO.getAPISecurityFieldRules(consumerKey);
		} catch(DataServiceException e) {
			logger.error("[BusinessServiceException] ", e);
		}
		return new ArrayList<String>();
	}


	/**
	 * Checks whether OAuth is enabled.
	 * By default it is enabled. To disable it put following flag in jboss/conf local.properties
	 * oauth.authentication=off
	 */
	public boolean isOAuthEnabled() {
		if (oAuthFilter.equalsIgnoreCase("off") || oAuthFilter.equalsIgnoreCase("false"))
			return false;
		return true;
	}

	public boolean isSecurityEnabled() {
		return isOAuthEnabled();
	}

	public boolean isBasicAuthEnabled() {
		if (basicAuthFilter.equalsIgnoreCase("off") || basicAuthFilter.equalsIgnoreCase("false"))
			return false;
		return true;
	}

	/**
	 * This method read consumer secret from DB again consumer key received in HTTP request.
	 * It reads it from supplier.oauth_consumer table.
	 * It also uses read only cache in 10 minutes interval.
	 *
	 */
	public String getConsumerSecret(String consumerKey) {
		APIApplicationDTO dto = getApplication(consumerKey);
		if (dto != null)
			return dto.getConsumerPassword();
		return null;
	}

	public String getoAuthFilter() {
		return oAuthFilter;
	}

	public void setoAuthFilter(String oAuthFilter) {
		this.oAuthFilter = oAuthFilter;
	}

	public List<APIApplicationDTO> getApplicationList() {
		try {
			List<APISecurityVO> voList =  apiSecurityDAO.getApplicationList();

			List<APIApplicationDTO> dtoList = new ArrayList<APIApplicationDTO>();
			for (APISecurityVO vo:voList) {
				APIApplicationDTO dto = new APIApplicationDTO(vo);
				dtoList.add(dto);
			}
			return dtoList;
		} catch (DataServiceException e) {
			logger.error("DataServiceException", e);
			return new ArrayList<APIApplicationDTO>();
		}
	}

	public APIDto getAPIList() {		
		APIDto apiDto = new APIDto();
		try {
			List<APIVO> voList =  apiSecurityDAO.getAPIList();
			HashMap<String, APIGroup> map = new HashMap<String, APIGroup>();
			List<APIGroup> dtoList = new ArrayList<APIGroup>();
				
			for (APIVO vo:voList) {
				String groupName = vo.getGroupName();

				APIGroup dto = map.get(groupName);
				if (dto == null) {
					dto = new APIGroup(groupName);
					map.put(groupName, dto);
				}

				APIUrlPermission urlP = dto.getPermission(vo.getId());
				if (urlP == null) {
					urlP = new APIUrlPermission(vo.getUrl(), vo.getId());
					dto.addPermission(urlP);
					urlP.setName(vo.getName());
					//System.out.println("For "+ groupName + "adding " + vo.getId() + "(" + vo.getUrl() +")");
				}
				urlP.setHttpMethod(vo.getHttpMethod(), false);
				apiDto.putMapping(vo.getId(), APIApplicationDTO.getURLKey(vo.getUrl(), vo.getHttpMethod()));
			}
			for (String vo:map.keySet()) {
				dtoList.add(map.get(vo));
			}
			
			apiDto.setGroupList(dtoList);			
			return apiDto;
		} catch (DataServiceException e) {
			e.printStackTrace();
			return null;
		}


	}

	public void addApplication(String appName, String loggedUserName) throws BusinessServiceException {
		Map<String, String> map = OAuthKeyGenerator.generate(appName);
		try {
			String consumerKey = map.get(OAuthKeyGenerator.OAUTH_KEY);
			apiSecurityDAO.addApplication(appName, consumerKey, map.get(OAuthKeyGenerator.OAUTH_SECRET));
			String description= "Application Name: " + appName;
			apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_ADD_APP, description, loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public void resetPassword(String consumerKey, String loggedUserName) throws BusinessServiceException {
		String pass = OAuthKeyGenerator.resetPassword(consumerKey);
		try {
			apiSecurityDAO.updatePassword(consumerKey, pass);			
			apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_RESET_PASSWORD, "", loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public APISecurityDAO getApiSecurityDAO() {
		return apiSecurityDAO;
	}

	public void setApiSecurityDAO(APISecurityDAO apiSecurityDAO) {
		this.apiSecurityDAO = apiSecurityDAO;
	}
	
	public void modifyPermissions(String consumerKey, List<Integer> addIdList, List<Integer> delIdList, String loggedUserName) throws BusinessServiceException {
		try {
			apiSecurityDAO.addPermissions(consumerKey, addIdList);
			
			if (addIdList.size() > 0)
			  apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_ADD_PER, getIdListForLogging(addIdList), loggedUserName);
			
			if (delIdList.size() > 0)
			  apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_DELETE_PER,  getIdListForLogging(delIdList), loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}		
	}
	
	/*
	public void revokePermissions(String consumerKey, List<Integer> apiIdList, String loggedUserName) throws BusinessServiceException {
		try {
			apiSecurityDAO.revokePermissions(consumerKey, apiIdList);
			apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_DELETE_PER,  getIdListForLogging(apiIdList), loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	} */
	
	public void removeApplication(String consumerKey, String loggedUserName) throws BusinessServiceException {
		try {
			apiSecurityDAO.removeApplication(consumerKey);
			apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_DELETE_APP, "", loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	private String getIdListForLogging(List<Integer> apiIdList) {
		StringBuilder bld = new StringBuilder();
		bld.append("ids:");
		int c = 0;
		for (Integer i:apiIdList) {
			if (c == 0)
				bld.append(i);
			else 
				bld.append("," + i );
			c++;
		}
		return bld.toString();
	}

	public void addAPIUser(String consumerKey, Integer userId, String loggedUserName)
			throws com.newco.marketplace.exception.core.BusinessServiceException {
		try {
			apiSecurityDAO.addApiUser(consumerKey, userId);
			apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_ADD_USER, "userId:" + userId, loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}
		
	}
	
	public void removeAPIUser(String consumerKey, Integer userId, String loggedUserName)
	throws com.newco.marketplace.exception.core.BusinessServiceException {
		try {
			apiSecurityDAO.removeApiUser(consumerKey, userId);
			apiSecurityDAO.logActivity(consumerKey, LOGGING_ACTIVITY_DELETE_USER, "userId:" + userId, loggedUserName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage());
		}

	}

	public List<Integer> getAPIUserList(String consumerKey) {
		try {
			return apiSecurityDAO.getAPIUserList(consumerKey);
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		return new ArrayList<Integer>();
	}
}
