package com.newco.marketplace.business.iBusiness.spn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import jxl.write.WriteException;

import com.newco.marketplace.dto.vo.spn.BackgroundCheckHistoryVO;
import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;
import com.newco.marketplace.dto.vo.spn.ComplianceCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNCompanyProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNComplianceVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO;
import com.newco.marketplace.dto.vo.spn.SearchBackgroundInfoProviderVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;



public interface ISPNMonitorBO extends SPNConstants {
	/**
	 * Returns a list of SPNMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<SPNMonitorVO> getSPNMonitorList (Integer vendorId) throws BusinessServiceException;
	/**
	 * Returns the count if the vendor has applied for atleast one SPN invitation.  If no SPNs are
	 * found, then zero is returned.
	 * @param vendorId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer isVendorSPNApplicant(Integer vendorId) throws BusinessServiceException;
	/**
	 * Returns a list of SPNMainMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return SPNMainMonitorVO
	 * @throws BusinessServiceException
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList(Integer vendorId) throws BusinessServiceException;
	/**
	 * Returns a list of SPNMainMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return SPNMainMonitorVO
	 * @throws BusinessServiceException
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList(Integer vendorId,Integer spnId) throws BusinessServiceException;
	/**
	 * Changes the state of the provider to PF SPN INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @throws BusinessServiceException
	 */
	public void acceptInvite(Integer spnId,Integer vendorId,String modifiedBy) throws BusinessServiceException;
	/**
	 * Saves the document uploaded the vendor.
	 * @param spnProvUploadedDocsVO
	 * @return count
	 * @throws BusinessServiceException
	 */
	public int uploadDocument(SPNProvUploadedDocsVO spnProvUploadedDocsVO, String buttonType)throws BusinessServiceException;
	/**
	 * Returns the count of qualified providers for the given SPN
	 * @param spnId
	 * @param vendorId
	 * @return int qualifiedProviderCount
	 * @throws BusinessServiceException
	 */
	public int getProviderCountsForSPN(Integer spnId,Integer vendorId) throws BusinessServiceException; 
	/**
	 * Returns the total number of providers for the given provider firm
	 * @param vendorId
	 * @return int totalProviderCount
	 * @throws BusinessServiceException
	 */
	public int getTotalProvidersForVendor(Integer vendorId) throws BusinessServiceException;
	
	/**
	 * Returns the provider requirements match details
	 * @param SPNMainMonitorVO spnMainMonitorVO
	 * @return SPNCompanyProviderRequirementsVO
	 * @throws BusinessServiceException
	 */
	public SPNCompanyProviderRequirementsVO getProviderRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws BusinessServiceException;
	/**
	 * Returns the provider requirements match details
	 * @param SPNMainMonitorVO spnMainMonitorVO
	 * @return SPNCompanyProviderRequirementsVO
	 * @throws BusinessServiceException
	 */
	public SPNCompanyProviderRequirementsVO getComapnyRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws BusinessServiceException;
	

	
	/**
	 * method to fetch SPN List when filters are applied
	 * 
	 * @param vendorId
	 * @param filterAppliedInd
	 * @param selectedBuyerValues
	 * @param selectedMemStatus
	 * @return
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorListWithFilters(Integer vendorId,
			Boolean filterAppliedInd, List<String> selectedBuyerValues,
			List<String> selectedMemStatus)throws BusinessServiceException;
	
	

	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */

	public List<SPNComplianceVO> getFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	
	public List<SPNComplianceVO> getProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;
	public Integer getProviderComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;
	public Integer getFirmComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException;
	public Date getProviderComplianceDate() throws BusinessServiceException ;

	public Date getFirmComplianceDate() throws BusinessServiceException ;

	public List<SPNComplianceVO> getRequirementsforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException; 
	
	
	public List<String> getBuyersforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;
	
	public List<String> getSPNforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;

	public List<SPNComplianceVO> getRequirementsforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException; 
	
	
	public List<String> getBuyersforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;
	
	public List<String> getSPNforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException ;
	public List<SPNComplianceVO> getProviderNamesforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws BusinessServiceException; 

	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return Integer
	 * @throws BusinessServiceException
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db
	public Integer getBackgroundInformationCount(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws BusinessServiceException;
	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return List<BackgroundInfoProviderVO>
	 * @throws BusinessServiceException
	 */
	//SL-19387
	//Fetching Background Check details of resources from db
	public List<BackgroundInfoProviderVO> getBackgroundInformation(SearchBackgroundInfoProviderVO searchBackgroundInformationVO) throws BusinessServiceException;
	
	//SL-19387
	public List<SPNMonitorVO> getSPNProviderList(Integer vendorId) throws BusinessServiceException;
	
	
	public ByteArrayOutputStream getExportToExcel(ByteArrayOutputStream outFinal, List<BackgroundInfoProviderVO> bckgdInfoVO) throws IOException, WriteException;

	public StringBuffer getExportToCSVComma(List<BackgroundInfoProviderVO> bckgdInfoVO) throws BusinessServiceException;
	
	public StringBuffer getExportToCSVPipe(List<BackgroundInfoProviderVO> bckgdInfoVO) throws BusinessServiceException;
	
	public String getProviderName(Integer resourceId)throws BusinessServiceException;
	
	public List<BackgroundCheckHistoryVO> getBackgroundCheckHistoryDetails(BackgroundCheckHistoryVO bgHistVO) throws BusinessServiceException;
}
