/**
 * 
 */
package com.newco.marketplace.persistence.iDao.vendor;

import java.util.List;


import org.springframework.dao.DataAccessException;


import com.newco.marketplace.vo.provider.BackgroundChkStatusVO;
import com.newco.marketplace.vo.provider.ExpiryNotificationVO;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.core.DataServiceException;

public interface VendorResourceDao {
	/**
	 * 
	 * @param vendorResource
	 * @return
	 * @throws DataServiceException
	 */
	public int update(VendorResource vendorResource)
			throws DataServiceException;

	public int updateResourceLocationType(VendorResource vendorResource)
			throws DataServiceException;

	public VendorResource query(VendorResource vendorResource)
			throws DataServiceException;

	/**
	 * Inserts a vendor resource into the datastore
	 * 
	 * @param vendorResource
	 * @return
	 * @throws DataServiceException
	 */
	public VendorResource insert(VendorResource vendorResource)
			throws DataServiceException;

	public List queryList(VendorResource vo) throws DataAccessException;

	public int updateBackgroundCheckStatus(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException;

	public String getTechnicianSsn(Integer techId) throws DataServiceException;

	public String getBackgroundCheckStatus(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException;

	public String getTeamMemberEmail(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException;

	public String getVendorEmail(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException;

	public int updateWFState(VendorResource vendorResource)
			throws DataServiceException;

	public String getSsnFrmDB(Integer techId) throws DataServiceException;
	
	//sl-19667 get resourceId based on plusOne key.
	public Integer getResourceIdFromPlusOneKey(String plusOneKey) throws DataServiceException;


	// Code Added for Jira SL-19475

	// For getting Background Check information :verification
	// and reverification dates from DB

	/**
	 * 
	 * @param tmbcVO
	 * @return
	 * @throws DataServiceException
	 */
	public TMBackgroundCheckVO getBackgroundCheckDates(
			TMBackgroundCheckVO tmbcVO) throws DataServiceException;

	// For updating Background Check information :verification
	// and reverification dates in DB
	/**
	 * 
	 * @param tmbcVO
	 * @return
	 * @throws DataServiceException
	 */
	public int updateBackgroundCheckDates(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException;
	
	
	public Integer getBackgroundStateId(String bcStatus)
			throws DataServiceException;
	
	// sl-19667 insert background history
	public void insertBackgroundHistory(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException;
	
	public TMBackgroundCheckVO getBackgroundCheckInformation(
			TMBackgroundCheckVO tmbcVO) throws DataServiceException;
	
	public int clearRecertificationstatus()
			throws DataServiceException;
	
	public int updateRecertificationstatus()
			throws DataServiceException;
	
	public int updateRecertificationstatusInProcess()
			throws DataServiceException;
	
	// sl-19667 insert background check error
	public void insertBackgroundError(BackgroundChkStatusVO backgroundCheckError)
				throws DataServiceException;
	// sl-19667 get last 4 ssn, first name , last name for resource
	public TMBackgroundCheckVO getPersonalInfoForResource(Integer techId) throws DataServiceException;

	public Integer getBgCheckId(TMBackgroundCheckVO tmbcVO)throws DataServiceException;

	public int updateBgDetails(TMBackgroundCheckVO tmbcVO)throws DataServiceException;
	
	/**
	 * @param resourceIds
	 * @return List<VendorResource>
	 * @throws DataServiceException
	 */
	public List<VendorResource> getVendorList(List<Integer> resourceIds) throws DataServiceException;
   
	/**
	 * @param firmIds
	 * @return List<Integer>
	 * @throws DataServiceException
	 */
	public List<Integer> getValidVendorList(List<Integer> firmIds)
			throws DataServiceException;
}
