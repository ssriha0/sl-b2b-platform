package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.provider.ActivityRegistry;


/**
 * @author KSudhanshu
 *
 */
public interface IActivityRegistryDao 
{
	/**
	 * @param vendorId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getProviderActivityStatus(String vendorId) throws Exception;
	
	/**
	 * @param vendorId
	 * @param activityName
	 * @return
	 * @throws Exception
	 */
	public boolean updateActivityStatus(String vendorId, String activityName) throws Exception;
	
	public void updateActivityStatusIds(Integer vendorId, List<Integer> activityIdList)
			throws Exception;
	
	/**
	 * Added to Update Incomplete status.
	 * Fix for Sears00045965.
	 */
	public boolean updateActivityStatus(String vendorId, String activityName, int credStatus) throws Exception;
	
	
	/**
	 * @param vendorId
	 * @throws Exception
	 */
	public void insertActivityStatus(String vendorId) throws Exception;
	
	
	/**
	 * @param resourceId
	 * @throws Exception
	 */
	public void insertResourceActivityStatus(String resourceId) throws Exception;
	
	/**
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getResourceActivityStatus(String resourceId) throws Exception;
	
	/**
	 * @param resourceId
	 * @param activityName
	 * @return
	 * @throws Exception
	 */
	public boolean updateResourceActivityStatus(String resourceId, String activityName) throws Exception;
	
	/**
	 * Added to Update Incomplete status.
	 * Fix for Sears00045965.
	 * @param resourceId
	 * @param activityName
	 * @param actStatus
	 * @return
	 * @throws Exception
	 */
	public boolean updateResourceActivityStatus(String resourceId, String activityName, int actStatus) throws Exception;
	
	/*
	public int update(ActivityRegistry activityRegistry);
    public ActivityRegistry query(ActivityRegistry activityRegistry);
    public Integer insert(ActivityRegistry activityRegistry);
    public List queryList(ActivityRegistry activityRegistry);
    */
    
    /**
	 *  Added for Skill s& Service Types activity insert
	 */
	public Integer insert(ActivityRegistry activityRegistry) throws DBException;
	
	/**
	 * 
	 * @param activityRegistry
	 * @return
	 * @throws DBException
	 */
	 public List queryList(ActivityRegistry activityRegistry) throws DBException;
	 /**
	  * 
	  * @param vendorId
	  * @return
	  * @throws DataServiceException
	  */
	 
	 public List queryResourceActivityStatus(int vendorId)throws DataServiceException;
	
}
