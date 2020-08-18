/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.vendor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;


import com.newco.marketplace.vo.provider.BackgroundChkStatusVO;
import com.newco.marketplace.vo.provider.ExpiryNotificationVO;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.api.mobile.beans.sodetails.InvoiceDocumentVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class VendorResourceImpl extends ABaseImplDao implements
		 VendorResourceDao {
	private static final Logger logger = Logger
			.getLogger(VendorResourceImpl.class.getName());

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.newco.provider.registration.dao.VendorResourceDao#update(com.newco.provider.registration.dao.VendorResource)
	 */
	public int update(VendorResource vendorResource)
			throws DataServiceException {
		try {
			return update("vendorResource.update", vendorResource);

		}// end try
		catch (DataAccessException dae) {
			logger.info("[Exception thrown updating a vendor resource] "
					+ StackTraceHelper.getStackTrace(dae));
			throw new DataServiceException("dataaccess.failedinsert", dae);

		}// end catch
		catch (Exception e) {
			logger.info("[Exception thrown updating a vendor resource] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);

		}// end catch (Exception

	}// end method

	public int updateResourceLocationType(VendorResource vendorResource)
			throws DataAccessException {
		return update("vendorResource.updateResourceLocationType",
				vendorResource);
	}

	public VendorResource query(VendorResource vendorResource)
			throws DataAccessException {
		return (VendorResource) queryForObject("vendorResource.query",
				vendorResource);
	}
	
	/**
	 * @param resourceIds
	 * @return List<VendorResource>
	 * @throws DataServiceException
	 */
	public List<VendorResource> getVendorList(List<Integer> resourceIds)
			throws DataServiceException {
		List<VendorResource> resourceList = new ArrayList<VendorResource>();
		try {
			resourceList = (ArrayList<VendorResource>) queryForList(
					"vendorResourceList.query", resourceIds);
		} catch (Exception ex) {
			logger.info("[getVendorList.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return resourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.newco.provider.registration.dao.VendorResourceDao#insert(com.newco
	 * .provider.registration.dao.VendorResource)
	 */
	public VendorResource insert(VendorResource vendorResource)
			throws DataServiceException {
		Integer id = null;

		try {
			id = (Integer) insert("vendorResource.insert", vendorResource);

		}// end try
		catch (Exception e) {
			logger.info("[Exception thrown inserting a vendor resource] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);

		}// end catch

		vendorResource.setResourceId(id);
		return vendorResource;

	}// end method insert()

	public List queryList(VendorResource vendorResource)
			throws DataAccessException {
		return queryForList("vendorResource.query", vendorResource);
	} // queryList

	public String getTechnicianSsn(Integer techId) throws DataServiceException {
		return (String) queryForObject("vendorResource.getTechnicianSsn",
				techId);
	}

	public String getSsnFrmDB(Integer techId) throws DataServiceException {
		return (String) queryForObject("vendorResource.getlastSsnFrmDB", techId);
	}
	
	//sl-19667 get resourceId based on plusOne key.
	public Integer getResourceIdFromPlusOneKey(String plusOneKey) throws DataServiceException{
		try{
		return (Integer) queryForObject("vendorResource.getResourceIdFromPlusOneKey",
				plusOneKey);
		}catch(Exception e){
			logger.info(" error in getting the resourceId from the plusOne Key" + e);
			return null;
		}
	}


	public int updateBackgroundCheckStatus(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {
		// get SSN
		try{
		return (update(
				"vendorResource.updateBackgroundCheckStatusAndCertificationDate",
				tmbcVO));
		}
		catch(Exception e){
			logger.info("error in updating background check status"+ e);
			return 0;
		}
	}// updateBackgroundCheckStatus

	public String getBackgroundCheckStatus(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {
		String rvString = (String) queryForObject(
				"vendorResource.getBackgroundCheckStatus",
				tmbcVO.getResourceId());
		if (null == rvString) {
			rvString = "";
		}
		return (rvString);
	}// getBackgroundCheckStatus

	// Code Added for Jira SL-19475
	// To Get Background Check dates
	/**
	 * 
	 * @param tmbcVO
	 * @return
	 * @throws DataServiceException
	 */
	public TMBackgroundCheckVO getBackgroundCheckDates(
			TMBackgroundCheckVO tmbcVO) throws DataServiceException {
		String resourceId = tmbcVO.getResourceId();
		TMBackgroundCheckVO TMBackroundcheckdates = (TMBackgroundCheckVO) queryForObject(
				"vendorResource.getBackgroundCheckDates", resourceId);

		return TMBackroundcheckdates;
	}
	
	// sl-19667 get last 4 ssn, first name , last name for resource
		public TMBackgroundCheckVO getPersonalInfoForResource(Integer resourceId) throws DataServiceException{
			
			TMBackgroundCheckVO personalInfo = (TMBackgroundCheckVO) queryForObject(
					"vendorResource.getPersonalInfoForResource", resourceId);

			return personalInfo;
		}

	// Code Added for Jira SL-19475
	// To Update Background Check dates
	/**
	 * 
	 * @param tmbcVO
	 * @return
	 * @throws DataServiceException
	 */
	public int updateBackgroundCheckDates(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {

		return (update("vendorResource.updateBackgroundCheckDates", tmbcVO));
	}

	public String getTeamMemberEmail(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {
		String rvString = (String) queryForObject(
				"vendorResource.getTeamMemberEmail", tmbcVO.getResourceId());
		if (null == rvString) {
			rvString = "";
		}
		return (rvString);

	}// getTeamMemberEmail

	public String getVendorEmail(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {
		String rvString = (String) queryForObject(
				"vendorResource.getVendorEmail", tmbcVO.getResourceId());
		if (null == rvString) {
			rvString = "";
		}
		return (rvString);
	}// getVendorEmail

	public int updateWFState(VendorResource vendorResource)
			throws DataServiceException {
		try {
			return update("vendorResource.updateWfStateId", vendorResource);

		}// end try
		catch (Exception dae) {
			logger.info("[Exception thrown updating a vendor resource] "
					+ StackTraceHelper.getStackTrace(dae));
			throw new DataServiceException("dataaccess.failedinsert", dae);

		}// end catch

	}// end method
	
	
	
	public Integer getBackgroundStateId(String bcStatus)
			throws DataServiceException{
		return (Integer) queryForObject("vendorResource.getBackgroundStateId",
				bcStatus);
	}
	
	// sl-19667 insert background history
	public void insertBackgroundHistory(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {

		try {
		 insert("backgroundHistory.insert", tmbcVO);

		}// end try
		catch (Exception e) {
			logger.info("[Exception thrown inserting background resource] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);

		}	

	}
	
	//sl-19677 insert background check error
	public void insertBackgroundError(BackgroundChkStatusVO backgroundCheckError)
			throws DataServiceException{
		try {
		 insert("backgroundChkError.insert", backgroundCheckError);
		}// end try
		catch (Exception e) {
			logger.info("[Exception thrown inserting background resource] "
					+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("dataaccess.failedinsert", e);

		}	
	}
	
	
	//sl-19667 
	public TMBackgroundCheckVO getBackgroundCheckInformation(
			TMBackgroundCheckVO tmbcVO) throws DataServiceException {
		String resourceId = tmbcVO.getResourceId();
		TMBackgroundCheckVO TMBackroundcheckdates = (TMBackgroundCheckVO) queryForObject(
				"vendorResource.getBackgroundCheckInformation", resourceId);

		return TMBackroundcheckdates;
	}
	
	// clear recertification_status coulumn 
	public int clearRecertificationstatus()
			throws DataServiceException{
		try {
			return update("backgroundCheck.clearRecertificationstatus", null);

		}
		catch (Exception e) {
			logger.info("[Exception thrown updating recertification stsatus] "
					+ StackTraceHelper.getStackTrace(e));
              return 0;
		}	
	}
	
	// update recertification_status coulumn 
	public int updateRecertificationstatus()
			throws DataServiceException {
		try {
			return update("backgroundCheck.updateRecertStatus", null);

		}
		catch (Exception e) {
			logger.info("[Exception thrown updating recertification stsatus] "
					+ StackTraceHelper.getStackTrace(e));
			return 0;

		}

	}
	
	// update recertification_status coulumn 
	public int updateRecertificationstatusInProcess()
			throws DataServiceException {
		try {
			return update("backgroundCheck.updateRecertStatusInProcess",null);

		}
		catch (Exception e) {
			logger.info("[Exception thrown updating recertification stsatus] "
					+ StackTraceHelper.getStackTrace(e));
			return 0;

		}

	}
//Performance issue
	public Integer getBgCheckId(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {
		return (Integer) queryForObject("vendorResource.getBgStateId",tmbcVO);
	}

	public int updateBgDetails(TMBackgroundCheckVO tmbcVO)
			throws DataServiceException {
		try{
			return (update("vendorResource.updateBgDetails",tmbcVO));
			}
			catch(Exception e){
				logger.info("error in updateBgDetails"+ e);
				return 0;
			}
		
	}
	
	/**
	 * @param firmIds
	 * @return List<Integer>
	 * @throws DataServiceException
	 */
	public List<Integer> getValidVendorList(List<Integer> firmIds)
			throws DataServiceException {
		List<Integer> firmList = new ArrayList<Integer>();
		try {
			firmList = (ArrayList<Integer>) queryForList(
					"getValidVendors.query", firmIds);
		} catch (Exception ex) {
			throw new DataServiceException("Exception in VendorResourceImpl.getValidVendorList- Exception:", ex);
		}
		return firmList;
	}
	
}
