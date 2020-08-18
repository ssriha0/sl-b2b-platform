/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.vo.provider.ActivityRegistry;

/**
 * @author KSudhanshu
 * 
 */
public class ActivityRegistryDaoImpl extends SqlMapClientDaoSupport implements
		IActivityRegistryDao {

	private final Log logger = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao#getProviderActivityStatus(java.lang.String)
	 */
	public Map<String, String> getProviderActivityStatus(String vendorId)
			throws Exception {
		Map result = null;
		try {
			ActivityRegistry activityRegistry = new ActivityRegistry();
			if (vendorId != null){
				activityRegistry.setActLinkKey(Integer.parseInt(vendorId));
				activityRegistry.setActLinkKeyType("Vendor");
				result = getSqlMapClient().queryForMap(
					"activity_registry.getProviderStatus", activityRegistry,
					"act_name", "status");
			}
			else{
				throw new Exception("Baddness Got a null VendorID");
			}
		} catch (Exception ex) {
			logger
					.info("[ActivityRegistryDaoImpl.getProviderActivityStatus - Exception] "
							+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao#updateActivityStatus(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean updateActivityStatus(String vendorId, String activityName)
			throws Exception {
		int result = 0;
		ActivityRegistry ar = new ActivityRegistry();
		if (vendorId != null)
			ar.setActLinkKey(Integer.parseInt(vendorId));
		ar.setActCompleted(1);
		ar.setActivityId(getActivityId(activityName));

		
		logger.info("in update activity->>" +activityName+": for vendor :"+ vendorId);

		try {
			result = getSqlMapClient().update("activity_registry.update",
					ar);
			System.out.println("updated record count--" + result);
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.updateActivityStatus - Exception] "
					+ ex.getStackTrace());
			throw ex;
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	
	
	public void updateActivityStatusIds(Integer vendorId, List<Integer> activityIdList)
			throws Exception {
		int result = 0; 
		ActivityRegistry ar = new ActivityRegistry();
		if (vendorId != null)
			ar.setActLinkKey(vendorId);
		ar.setActivityIds(activityIdList);
		
		try {
			result = getSqlMapClient().update("activity_registry.updateIds",
					ar);
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.updateActivityStatus - Exception] "
					+ ex.getStackTrace());
			throw ex;
		}
		 
	}
	
	
	
	
	
	
	
	/**
	 * Added to Update Incomplete status.
	 * Fix for Sears00045965.
	 */
	public boolean updateActivityStatus(String vendorId, String activityName, int credStatus)
			throws Exception {
		int result = 0;
		ActivityRegistry ar = new ActivityRegistry();
		if (vendorId != null)
			ar.setActLinkKey(Integer.parseInt(vendorId));
		ar.setActCompleted(credStatus);
		ar.setActivityId(getActivityId(activityName));
		
		logger.info("in update activity->>" +activityName+": for vendor :"+ vendorId);
		
		try {
			result = getSqlMapClient().update("activity_registry.update",
					ar);
			System.out.println("updated record count--" + result);
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.updateActivityStatus - Exception] "
					+ ex.getStackTrace());
			throw ex;
		}
		if (result > 0)
			return true;
		else
			return false;
	}

	public int getActivityId(String activityName) throws Exception {
		int activityId = -1;
		try {
			activityId = (Integer) getSqlMapClient().queryForObject(
					"getActivityId.query", activityName);
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.getActivityId - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return activityId;
	}

	//Added by Mayank for vendor_activity_resgistry @ provider registration
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao#insertActivityStatus(java.lang.String)
	 */
	public void insertActivityStatus(String vendorId) throws Exception{
		logger.info("I am in insertActivityStatus() of ActivityRegistryDaoImpl for Firm using vendor_id");
		ActivityRegistry ar = new ActivityRegistry();
		if (vendorId != null)
			logger.info("Firm vendorId after parsing is:"+Integer.parseInt(vendorId));
		ar.setActLinkKey(Integer.parseInt(vendorId));
		ar.setActCompleted(0);
		try {
			for (int i =1;i<6;i++){
				ar.setActivityId(i);
				getSqlMapClient().insert("activity_registry.insert",ar);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}//End of insertActivityStatus()
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao#insertResourceActivityStatus(java.lang.String)
	 */
	public void insertResourceActivityStatus(String resourceId) throws Exception{
		logger.info("I am in insertActivityStatus() of ActivityRegistryDaoImpl for Firm Resourse");
		ActivityRegistry ar = new ActivityRegistry();
		if (resourceId != null)
		logger.info("Firm resourseId after parsing is:"+ Integer.parseInt(resourceId));
		ar.setActLinkKey(Integer.parseInt(resourceId));
		ar.setActCompleted(0);
		try {
			//This is for RESOURCE and hence will have access to limited TABSs
			for (int i =7;i<=12;i++){
				ar.setActivityId(i);
				getSqlMapClient().insert("activity_registry.insert",ar);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}//End of insertActivityStatus()
	//End of changes for vendor_activity_resgistry @ provider registration

	/**
	 * Added for Skills & Service Types Activity insert
	 */
	public Integer insert(ActivityRegistry activityRegistry) throws DBException{
		 Integer result =0;
		try {
			result = (Integer) getSqlMapClient().insert("activity_registry.insert", activityRegistry);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ActivityRegistryDaoImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ActivityRegistryDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ActivityRegistryDaoImpl.insert() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ActivityRegistryDaoImpl.insert() due to "
							+ ex.getMessage());
		}

	        return result;
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao#getResourceActivityStatus(java.lang.String)
	 */
	public Map<String, String> getResourceActivityStatus(String resourceId)
			throws Exception {
		Map result = null;
		try {
			ActivityRegistry activityRegistry = new ActivityRegistry();
			if (resourceId != null){
				activityRegistry.setActLinkKey(Integer.parseInt(resourceId));
				activityRegistry.setActLinkKeyType("Resource");
				result = getSqlMapClient().queryForMap(
					"activity_registry.getProviderStatus", activityRegistry,
					"act_name", "status");
			}
			else{
				throw new Exception("Baddness Got a null resourceID");
			}
		} catch (Exception ex) {
			logger
					.info("[ActivityRegistryDaoImpl.getResourceActivityStatus - Exception] "
							+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @param activityName
	 * @return
	 * @throws Exception
	 */
	private int getResourceActivityId(String activityName) throws Exception {
		int activityId = -1;
		try {
			activityId = (Integer) getSqlMapClient().queryForObject(
					"getResourceActivityId.query", activityName);
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.getResourceActivityId - Exception] "
					+ ex.getStackTrace());
			ex.printStackTrace();
			throw ex;
		}

		return activityId;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao#updateResourceActivityStatus(java.lang.String, java.lang.String)
	 */
	public boolean updateResourceActivityStatus(String resourceId,
			String activityName) throws Exception {
		int result = 0;
		ActivityRegistry ar = new ActivityRegistry();
		if (resourceId != null)
			ar.setActLinkKey(Integer.parseInt(resourceId));
		ar.setActCompleted(1);
		ar.setActivityId(getResourceActivityId(activityName));
		
		logger.info("in update activity->>" +activityName+": for resource :"+ resourceId);
		try {
			result = getSqlMapClient().update("activity_registry.update",
					ar);
			System.out.println("updated record count--" + result);
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.updateResourceActivityStatus - Exception] "
					+ ex.getStackTrace());
			throw ex;
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Added to Update Incomplete status.
	 * Fix for Sears00045965.
	 */
	public boolean updateResourceActivityStatus(String resourceId,
			String activityName, int actStatus) throws Exception {
		int result = 0;
		ActivityRegistry ar = new ActivityRegistry();
		if (resourceId != null)
			ar.setActLinkKey(Integer.parseInt(resourceId));
		ar.setActCompleted(actStatus);
		ar.setActivityId(getResourceActivityId(activityName));
		
		logger.info("in update activity->>" +activityName+": for resource :"+ resourceId);
		try {
			
			result = getSqlMapClient().update("activity_registry.update",
					ar);
			System.out.println("updated record count--" + result);
					
		} catch (Exception ex) {
			logger.info("[ActivityRegistryDaoImpl.updateResourceActivityStatus - Exception] "
					+ ex.getStackTrace());
			throw ex;
		}
		if (result > 0)
			return true;
		else
			return false;
	}

	public List queryList(ActivityRegistry activityRegistry) throws DBException{
		List result = new ArrayList();
		try {
			result = getSqlMapClient().queryForList("activity_registry.query", activityRegistry);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @ActivityRegistryDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"SQL Exception @ActivityRegistryDaoImpl.queryList() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @ActivityRegistryDaoImpl.queryList() due to"
							+ ex.getMessage());
			throw new DBException(
					"General Exception @ActivityRegistryDaoImpl.queryList() due to "
							+ ex.getMessage());
		}
	    return result;
		}

	 /**
     * 
     * @param vendorId
     * @return
     * @throws DataServiceException
     */
    public List queryResourceActivityStatus(int vendorId)throws DataServiceException
    {
    	List resourceActKeyList = null;
    	try
    	{
    		resourceActKeyList =  getSqlMapClient().queryForList("teamProfile.checkActivityStatus", vendorId);
    		
    	}catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @@ActivityRegistryDaoImpl.queryResourceActivityStatus() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @@ActivityRegistryDaoImpl.queryResourceActivityStatus() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @@ActivityRegistryDaoImpl.queryResourceActivityStatus() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @@ActivityRegistryDaoImpl.queryResourceActivityStatus() due to "
							+ ex.getMessage());
		}
    	
    	
    	return resourceActKeyList;
    }
	
}
