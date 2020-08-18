/**
 * 
 */
package com.newco.marketplace.web.delegates.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.dto.provider.VendorHdrDto;

/**
 * @author KSudhanshu
 *
 */
public interface IActivityRegistryDelegate {

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
	 * @param vendorHdrDto
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public boolean submitRegistration(VendorHdrDto vendorHdrDto, Integer resourceId, String provUserName) throws Exception;
	
	/**
	 * @param vendorId
	 * @return
	 * @throws Exception
	 */
	public String getProviderStatus(String vendorId) throws Exception;
	
	/**
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public String getResourceStatus(String resourceId) throws Exception;
	
	/**
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public String getVendorResourceStateStatus(String resourceId) throws Exception;
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws Exception
	 */
	public String getVendorHeaderStatus(String vendorId) throws Exception;
	
	public String getResPrimaryIndicator(String resourceId) throws Exception;
	/**
	 * 
	 * @param vendorId
	 * @return
	 * @throws DelegateException
	 */
	public List checkActivityStatus(int vendorId) throws DelegateException;
}
