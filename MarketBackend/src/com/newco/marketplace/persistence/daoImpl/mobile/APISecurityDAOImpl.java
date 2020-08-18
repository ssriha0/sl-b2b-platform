package com.newco.marketplace.persistence.daoImpl.mobile;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.mobile.APISecurityDAO;
import com.newco.marketplace.vo.api.APISecurityVO;
import com.newco.marketplace.vo.api.APIVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class APISecurityDAOImpl extends ABaseImplDao implements APISecurityDAO{

	public String getOAuthConsumerSecret(String consumerKey) throws DataServiceException {
		String secret;
		try {
			secret = (String) queryForObject("security.oauth.queryConsumerSecret", consumerKey);
		} catch (Exception ex) {
			logger.error("[APISecurityDAOImpl - Exception] ", ex);
			throw new DataServiceException("", ex);
		}

		return secret;
	}

	@SuppressWarnings("unchecked")
	public List<APISecurityVO> getApplicationList() throws DataServiceException {
		List<APISecurityVO> list;
		try {
			list = (List<APISecurityVO>) getSqlMapClientTemplate().queryForList("api.security.applications");
		} catch (Exception ex) {
			logger.error("[APISecurityDAOImpl.select - Exception] ", ex);
			throw new DataServiceException("", ex);
		}
		return list;
	}

	public boolean isUserExisting(int userId, String consumerKey, int roleId)
		throws DataServiceException {
		Integer count = null;
		if(roleId == 0) { // external application
			Map paramMap = new HashMap();
			paramMap.put("userId", userId);
			paramMap.put("consumerKey", consumerKey);
			if (getSqlMapClientTemplate().queryForObject("api.user.security", paramMap)!= null) {
				count = (Integer)getSqlMapClientTemplate().queryForObject("api.user.security", paramMap);
			}
		} else if(roleId == OrderConstants.BUYER_ROLEID) {// check against buyer company
			count = (Integer) queryForObject("api.user.security.buyer", userId);
		} else if(roleId == OrderConstants.PROVIDER_ROLEID) {//check against provider company
			count = (Integer) queryForObject("api.user.security.provider", userId);
		} else {
			throw new DataServiceException ("isUserExisting method: Unsupported consumerFlag");
		}
		return (count == 0 ? false: true);
	}
	
	
	public boolean isResourceValid(int resourceId)
			throws DataServiceException {
			Integer count = null;
			
				Map paramMap = new HashMap();
				paramMap.put("resourceId", resourceId);
				if (getSqlMapClientTemplate().queryForObject("api.mobile.user.security", paramMap)!= null) {
					count = (Integer)getSqlMapClientTemplate().queryForObject("api.mobile.user.security", paramMap);
				}
			 else {
				throw new DataServiceException ("Inside isResourceValid method: ");
			}
			return (count == 0 ? false: true);
		}
	
	public boolean isMobileTokenValid(String outhToken,String resourceId,Date serverDate)
			throws DataServiceException {
			Integer count = null;
				
		 SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
		 String serverDateNow=defaultDateFormat.format(serverDate);		
		 
		 
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("outhToken", outhToken);
				paramMap.put("resourceId", resourceId);
				paramMap.put("serverDateNow", serverDateNow);
				if (getSqlMapClientTemplate().queryForObject("api.mobile.user.token.security", paramMap)!= null) {
					count = (Integer)getSqlMapClientTemplate().queryForObject("api.mobile.user.token.security", paramMap);
				}
			 else {
				throw new DataServiceException ("Inside isMobileTokenValid method:");
			}
				
			return (count == 0 ? false: true);
		}
	
	


	public List<String> getAPISecurityFieldRules(String consumerKey) throws DataServiceException {
		List<String> list =  getSqlMapClientTemplate().queryForList("api.user.security.mask.field", consumerKey);
		return list;
	}

	public List<APISecurityVO> getAPIListForApplication(String consumerKey) throws DataServiceException {
		try {
			List<APISecurityVO> apiSecurityVO =  getSqlMapClientTemplate().queryForList("api.security", consumerKey);
			return apiSecurityVO;
		} catch(Exception e) {
			logger.error("[SecurityDaoImpl exception:api.security]" + e.getMessage());
			throw new DataServiceException(e.toString());
		}
	}

	public List<APIVO> getAPIList() throws DataServiceException {
		try {
			List<APIVO> api =  getSqlMapClientTemplate().queryForList("api.get.list");
			return api;
		} catch(Exception e) {
			logger.error("[SecurityDaoImpl exception:api.security]" + e.getMessage());
			throw new DataServiceException(e.toString());
		}
	}

	public void updatePassword(String consumerKey, String password)
			throws DataServiceException {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("consumerKey", consumerKey);
			map.put("consumerSecret", password);
			getSqlMapClientTemplate().update("api.app.update.password", map);
		} catch(Exception e) {
			logger.error("[SecurityDaoImpl exception:api.security]" + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public void addApplication(String name, String consumerKey, String password) throws DataServiceException {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("consumerName", name);
			map.put("consumerKey", consumerKey);
			map.put("consumerSecret", password);
			getSqlMapClientTemplate().insert("api.app.add", map);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataServiceException("Application already present");
		} catch(Exception e) {
			logger.error("[SecurityDaoImpl exception:api.security]", e);
			throw new DataServiceException(e.getMessage());
		}
	}
	
	public void removeApplication(String consumerKey) throws DataServiceException {
		//First remove all permissions.
		getSqlMapClientTemplate().delete("api.revoke.permission.all", consumerKey);
		getSqlMapClientTemplate().delete("api.app.remove", consumerKey);
	}
	
	public void addPermissions(String consumerKey, List<Integer> apiIdList) throws DataServiceException {
		getSqlMapClientTemplate().delete("api.revoke.permission.all", consumerKey);
		List<Map<String, Serializable>> objList = new ArrayList<Map<String, Serializable>>();
		for (Integer id:apiIdList) {
			HashMap<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("consumerKey", consumerKey);
			map.put("apiId", id);
			objList.add(map);
		}
		batchInsert("api.add.permission", objList);
	}
	
	/*
	public void revokePermissions(String consumerKey, List<Integer> apiIdList) throws DataServiceException {
		for (Integer id:apiIdList) {
			HashMap<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("consumerKey", consumerKey);
			map.put("apiId", id);
			getSqlMapClientTemplate().delete("api.revoke.permission", map);
		}
	} */
	
	public void logActivity(String consumerKey, int activityId, String description, String userId) throws DataServiceException {
			HashMap<String, Serializable> map = new HashMap<String, Serializable>();
			map.put("consumerKey", consumerKey);
			map.put("activityId", activityId);
			map.put("description", description);
			map.put("userId", userId);
			getSqlMapClientTemplate().insert("api.permission.logging", map);
	}

	public void addApiUser(String consumerKey, int userId)
			throws DataServiceException {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("consumerKey", consumerKey);
		map.put("userId", userId);
		getSqlMapClientTemplate().insert("api.add.app.user", map);
	
		
	}

	public List<Integer> getAPIUserList(String consumerKey)
			throws DataServiceException {
		List<Integer> list =  getSqlMapClientTemplate().queryForList("api.get.app.user", consumerKey);
		return list;
	}

	public void removeApiUser(String consumerKey, int userId)
			throws DataServiceException {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("consumerKey", consumerKey);
		map.put("userId", userId);
		getSqlMapClientTemplate().delete("api.del.app.user", map);
		
	}
}
