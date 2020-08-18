/**
 * 
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.VendorHdr;

/**
 * @author KSudhanshu
 *
 */
public interface IActivityRegistryBO {

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
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean sendEmailTeamMemberRegistration(Integer resourceId, String provUserName) throws BusinessServiceException;
	
	
	/**
	 * @param vendorHdr
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public boolean submitRegistration(VendorHdr vendorHdr, Integer resourceId, String provUserName) throws Exception;	
	
	/**
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public String getResourcePriInd(String resourceId) throws Exception;	
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public List checkActivityStatus(int vendorId) throws BusinessServiceException;
	
	public boolean sendEmailForTeamMemberRegistration(Integer resourceId, String provUserName) throws BusinessServiceException;
}
