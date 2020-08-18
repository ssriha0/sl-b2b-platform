package com.newco.marketplace.persistence.iDao.spn;

import java.util.Date;
import java.util.List;


import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;
import com.newco.marketplace.dto.vo.spn.ComplianceCriteriaVO;
import com.newco.marketplace.dto.vo.spn.ProviderMatchApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.ProviderMatchingCountsVO;
import com.newco.marketplace.dto.vo.spn.SPNApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNComplianceVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResultVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNNetworkResourceVO;
import com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileBuyerVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNSummaryVO;
import com.newco.marketplace.dto.vo.spn.SPNetCommMonitorVO;
import com.newco.marketplace.dto.vo.spn.SearchBackgroundInfoProviderVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.dto.vo.spn.SPNExclusionsVO;
import com.newco.marketplace.dto.vo.spn.BackgroundCheckHistoryVO;



/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.3 $ $Author: akashya $ $Date: 2008/05/21 22:54:05 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public interface SPNDao extends SPNConstants {

	/**
	 * Returns a list SPNSummaryVOs for the given buyer sorted by spn_id.  If there are
	 * no records found an empty List will be returned.
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNSummaryVO> getSPNSummariesByBuyerId(Integer buyerId) throws DataServiceException;
	
	/**
	 * Returns the SPN Header data.  If the header record is not found, NULL will
	 * be returned.
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public SPNHeaderVO getSPNHeaderBySPNId(Integer spnId) throws DataServiceException;
	
	/**
	 * Returns a SPNCriteriaVO object containing the criteria for the given SPN id.  If
	 * no records are found, an empty SPNCriteriaVO object will be returned.
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public SPNCriteriaVO getSPNCriteriaBySPNId(Integer spnId) throws DataServiceException;
	
	/**
	 * Creates a SPN header and all related data records
	 * @param spnHeaderVO
	 * @return
	 * @throws DataServiceException
	 */
	public Integer insertSPN(SPNHeaderVO spnHeaderVO) throws DataServiceException;
	
	/**
	 * Updates a SPN
	 * @param spnHeaderVO
	 * @throws DataServiceException
	 */
	public void updateSPN(SPNHeaderVO spnHeaderVO) throws DataServiceException;
	
	/**
	 * Deletes a SPN
	 * @param spnId
	 * @throws DataServiceException
	 */
	public void deleteSPN(Integer spnId) throws DataServiceException;
	
	/**
	 * Deletes all of the Select Provider Network summary records
	 * @throws DataServiceException
	 */
	public void deleteSPNHeaderSummary() throws DataServiceException;
	
	/**
	 * Loads the Select Provider Network summary records for all Select Provider Networks
	 * @throws DataServiceException
	 */
	public void loadSPNHeaderSummarty() throws DataServiceException;
	
	/**
	 * Sets the lock indicator for the Select Provider Network
	 * @param spnId
	 * @throws DataServiceException
	 */
	public void lockSPN(Integer spnId) throws DataServiceException;
	
	/**
	 * Retrieves a list of Select Provider Network Campaigns
	 * @param spnId
	 * @throws DataServiceException
	 */
	public List<SPNCampaignVO> loadAllSPNCampaigns(Integer spnId) throws DataServiceException;
	
	/**
	 * Creates a new Select Provider Network Campaign entry
	 * @param campaign - Value object used to represent input data for a SPN Campaign
	 * @throws DataServiceException
	 */
	public Integer createNewSPNCampaign(SPNCampaignVO campaign) throws DataServiceException;
	
	/**
	 * Used to create a 'soft delete' on a Select Provider Network Campaign
	 * Data is not permanetly removed 
	 * @param campaignId - id of current selected campaign
	 * @throws DataServiceException
	 */
	public Integer deleteSPNCampaign(Integer campaignId) throws DataServiceException;
	
	/**
	 * updates spn status and set Application time if Interested
	 * @param response
	 * @param spnId
	 * @param providerResourceId
	 * @throws DataServiceException
	 */
	public void updateProviderNetworkStatus(SPNNetworkResourceVO networkResourceVO) 
	                                                                throws DataServiceException;
	
	/**
	 * @param spnId
	 * @param servicePros
	 * @throws DataServiceException
	 */
	public void insertSPNNetworkRecords(Integer spnId, List<ProviderResultVO> servicePros) throws DataServiceException; 

	
	/**
	 * Retrieves a list of Select Provider Networks 
	 * @param spnId
	 * @throws DataServiceException
	 */
	public List<SPNHeaderVO> getAllSPNS() throws DataServiceException;
	
	public List<SPNHeaderVO> getSPNInviteListForResource(Integer resourceId) throws DataServiceException;
	public List<SPNHeaderVO> getSPNInviteListForAdmin(Integer vendorId) throws DataServiceException;
	
	public Integer getIsAdminIndicator(Integer resourceId) throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNCampaignVO> getActiveSPNCampaigns() throws DataServiceException;
	
	/**
	 * @param spnCampaignId
	 * @param matchCount
	 * @throws DataServiceException
	 */
	public void updateSPNCampaignMatchCnt (Integer spnCampaignId, Integer matchCount) throws DataServiceException;
	
	/**
	 * @param spnId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public SPNNetworkResourceVO getNetworkResourceBySPNIdAndResourceId (Integer spnId, Integer resourceId) throws DataServiceException;
	
	/**
	 * Sets the service pro's spn network status to member and updates the member date time
	 * @param spnNetworkIds
	 * @throws DataServiceException
	 */
	public void approveMembers (List<Integer> spnNetworkIds) throws DataServiceException;

	/**
	 * Sets the service pro's spn network status to removed and updates the removed date time
	 * @param spnNetworkIds
	 * @throws DataServiceException
	 */
	public void removeMembers (List<Integer> spnNetworkIds) throws DataServiceException;
	
	/**
	 * @param criteria
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNMemberSearchResultVO> getMembersByStatus(CriteriaMap criteria) throws DataServiceException;
	
	/**
	 * Returns the number of records found for the search
	 * @param criteria
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getPaginationCount(CriteriaMap criteria) throws DataServiceException;
	
		
	/**
	 * get the list of SPN buyers for provider 
	 * @param providerId
	 * @return List<SPNProviderProfileBuyerVO> 
	 * @throws DataServiceException
	 */
	public List<SPNProviderProfileBuyerVO> getProviderProfileSpns(Integer providerId) throws DataServiceException;
	/**
	 * Returns a list of SPNDocumentVO for the spn id.  If no documents are
	 * found, then an empty list is returned.
	 * @param spnId
	 * @return List<SPNDocumentVO>
	 * @throws DataServiceException
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModal (Integer spnId) throws DataServiceException;
	/**
	 * Returns a list of SPNDocumentVO for the document id. 
	 * @param docId
	 * @return List<SPNDocumentVO>
	 * @throws DataServiceException
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModalDocument (Integer docId) throws DataServiceException;
	/**
	 * Submits Buyer Agreements of a SPN by a provider. 
	 * @param firmId
	 * @param spnId
	 * @throws DataServiceException
	 */
	public void submitSPNBuyerAgreement (Integer firmId,Integer spnId,String modifiedBy, Boolean auditRequired) throws DataServiceException;
	
	/**
	 * Submits Buyer Agreements of a SPN by a provider. 
	 * @param firmId
	 * @param spnDocId
	 * @throws DataServiceException
	 */
	public void submitSPNBuyerAgreementForDoc (Integer firmId, String username, Integer spnDocId) throws DataServiceException;
	
	/**
	 * Get the details of SPN to be displayed in Provider invitation page.  
	 * @param spnId
	 * @param vendorId
	 * @throws DataServiceException
	 */
	public SPNMainMonitorVO loadProviderInvitation(Integer spnId, Integer vendorId) throws DataServiceException;
	
	/**
	 * Changes the state of the provider to PF SPN NOT INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @param rejectId
	 * @param rejectReason
	 * @throws DataServiceException
	 */
	public void rejectInvite(String rejectId,String rejectReason,Integer spnId,Integer vendorId,String modifiedBy) throws DataServiceException;
		/**
	 * Returns a list of SPNMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNMonitorVO> getSPNMonitorList (Integer vendorId) throws DataServiceException;
	/**
	 * Returns the count if the vendor has applied for atleast one SPN invitation.  If no SPNs are
	 * found, then zero is returned.
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer isVendorSPNApplicant (Integer vendorId) throws DataServiceException;
	
	/**
	 * gets the SPN details for provider admin
	 * @param companyId
	 * @return List<SPNetCommMonitorVO> 
	 * @throws DataServiceException
	 */
	public List<SPNetCommMonitorVO> getSPNetCommDetailsForPA(Integer companyId) throws DataServiceException;
	/**
	 * Returns a list of SPNMainMonitorVO for the vendor id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @return SPNMainMonitorVO list
	 * @throws DataServiceException
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList (Integer vendorId) throws DataServiceException;
	/**
	 * Returns a list of SPNMainMonitorVO for the vendor id and specific spnId .  If no SPNs are
	 * found, then an empty list is returned.
	 * @param vendorId
	 * @param spnId
	 * @return SPNMainMonitorVO list
	 * @throws DataServiceException
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorList (Integer vendorId,Integer spnId) throws DataServiceException;
	/**
	 * Changes the state of the provider to PF SPN INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @throws DataServiceException
	 */
	public void acceptInvite(Integer spnId,Integer vendorId,String modifiedBy) throws DataServiceException;
	/**
	 * Inserts into spnet_uploaded_document_state table for provider uploaded document
	 * @param vendorId
	 * @return SPNMainMonitorVO list
	 * @throws DataServiceException
	 */
	public int saveUploadedDocumentState (SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException;
	/**
	 * Inserts into spnet_provider_firm_document table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return id
	 * @throws DataServiceException
	 */
	public int saveProviderUploadedDocument (SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException;
	/**
	 * Updates spnet_provider_firm_document table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return 
	 * @throws DataServiceException
	 */
	public void updateProviderDocumetStatus(SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException;
	/**
	 * Updates spnet_uploaded_document_state table for provider uploaded document
	 * @param spnProvUploadedDocsVO
	 * @return 
	 * @throws DataServiceException
	 */
	public void updateDocumetUploadedStatus(SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException;
	/**
	 * 
	 * @param spnProvUploadedDocsVO
	 * @throws DataServiceException
	 */
	public void uploadDocumentUploadedIdAndStatus(SPNProvUploadedDocsVO spnProvUploadedDocsVO) throws DataServiceException; 
	/**
	 * Returns the list of approval criteria for the given SPN
	 * @param spnId
	 * @return List<SPNApprovalCriteriaVO>
	 * @throws DataServiceException
	 */
	public List<SPNApprovalCriteriaVO> getApprovalCriteriaList(Integer spnId) throws DataServiceException;
	
	/**
	 * Returns the list of matching providers count
	 * @param spnId
	 * @return
	 * @throws DataServiceException
	 */
	public List<ProviderMatchingCountsVO> getMatchingProviderstList(ProviderMatchApprovalCriteriaVO providerMatchApprovalCriteriaVO) throws DataServiceException;
	
	/**
	 * Retu
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getTotalProviderCount (Integer vendorId) throws DataServiceException;
	/**
	 * Returns the count if the vendor has applied for atleast one SPN invitation.  If no SPNs are
	 * found, then zero is returned.
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer isProviderSPNApplicant(Integer vendorId) throws DataServiceException;
	/**
	 * Returns the list of matching Provider Requirements
	 * @param spnId
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getMatchingProviderRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws DataServiceException;
	/**
	 * Returns the complete list of provider requirements for the given spn
	 * @param spnId
	 * @return List<SPNProviderRequirementsVO>
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getSPNProviderRequirementsList(Integer spnId) throws DataServiceException;
	/**
	 * Returns the list of matching Provider Requirements
	 * @param spnId
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getMatchingCompanyRequirementsList(SPNMainMonitorVO spnMainMonitorVO) throws DataServiceException;
	/**
	 * Returns the complete list of provider requirements for the given spn
	 * @param spnId
	 * @return List<SPNProviderRequirementsVO>
	 * @throws DataServiceException
	 */
	public List<SPNProviderRequirementsVO> getSPNCompanyRequirementsList(Integer spnId) throws DataServiceException;

	
	/**
	 * 
	 * 	//SL_18018- to get spn list applying filters
	 * 
	 * @param vendorId
	 * @param filterAppliedInd
	 * @param selectedBuyerValues
	 * @param selectedMemStatus
	 * @return
	 */
	public List<SPNMainMonitorVO> getSPMMainMonitorListWithFilters(
			Integer vendorId, Boolean filterAppliedInd,
			List<String> selectedBuyerValues, List<String> selectedMemStatus)throws DataServiceException;	
	public Date getFirmComplianceDate() throws DataServiceException ;
	public Date getProviderComplianceDate() throws DataServiceException ;

	public List<SPNComplianceVO> getFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;
	/**
	 * @param spnId 
	 * @return
	 * SL-18018: method to fetch firm Compliance.
	 */
	
	public List<SPNComplianceVO> getProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;
	
	public Integer getProviderComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;
	public Integer getFirmComplianceCount(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;


   public List<SPNComplianceVO> getRequirementsforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException; 
	
	
	public List<String> getBuyersforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;
	
	public List<String> getSPNforFirmCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;

	public List<SPNComplianceVO> getRequirementsforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException; 
	
	
	public List<String> getBuyersforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;
	
	public List<String> getSPNforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;
	
	public List<SPNComplianceVO> getProviderNamesforProviderCompliance(ComplianceCriteriaVO complianceCriteriaVO) throws DataServiceException ;

	/**
	 * 
	 * method fpr fetching exceptions
	 * 
	 * @param spnId
	 * @param vendorId
	 * @return
	 */
	public List<SPNExclusionsVO> getCompanyExceptionsApplied(int spnId, int vendorId);

	public List<SPNExclusionsVO> getResourceExceptionsApplied(int spnId, int vendorId);
	
	
	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return Integer
	 * @throws DataServiceException
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db
	public Integer getBackgroundInformationCount(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws DataServiceException;

	/**
	 * @param searchBackgroundInfoProviderVO
	 * @return List<BackgroundInfoProviderVO>
	 * @throws DataServiceException
	 */
	//SL-19387
	//Fetching Background Check details of resources from db
	public List<BackgroundInfoProviderVO> getBackgroundInformation(SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO) throws DataServiceException;

	
	//SL-19387
	public List<SPNMonitorVO> getSPNProviderList (Integer vendorId) throws DataServiceException;

	public List<BackgroundCheckHistoryVO> getBackgroundCheckHistoryDetails(BackgroundCheckHistoryVO bgHistVO)throws DataServiceException;

	public String getProviderName(Integer resourceId)throws DataServiceException;
	
}
/*
 * Maintenance History:
 * $Log: SPNDao.java,v $
 * Revision 1.3  2008/05/21 22:54:05  akashya
 * I21 Merged
 *
 * Revision 1.2.2.1  2008/05/07 22:48:22  mhaye05
 * updates for B2C
 *
 * Revision 1.2  2008/05/02 21:23:39  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.13  2008/04/29 15:53:23  mhaye05
 * updates for spn member manager
 *
 * Revision 1.1.2.12  2008/04/29 04:06:43  rgurra0
 * added getProviderProfileSpns
 *
 * Revision 1.1.2.11  2008/04/24 16:01:49  mhaye05
 * fixes for spn campaign running batch job
 *
 * Revision 1.1.2.10  2008/04/23 04:17:03  rgurra0
 *  getIsAdminIndicator method added
 *
 * Revision 1.1.2.9  2008/04/22 20:37:07  cgarc03
 * Latest changes/additions related to loading SPN Invite list on dashboard.
 *
 * Revision 1.1.2.8  2008/04/18 22:46:04  mhaye05
 * updated to allow for spn save and the running of spn campaigns
 *
 * Revision 1.1.2.7  2008/04/18 22:09:46  dmill03
 * *** empty log message ***
 *
 * Revision 1.1.2.6  2008/04/17 16:47:20  rgurra0
 * query to update SPN status
 *
 * Revision 1.1.2.5  2008/04/16 23:04:01  dmill03
 * added campaign backend functions
 *
 * Revision 1.1.2.4  2008/04/10 19:07:24  mhaye05
 * added additional attributes for skills
 *
 * Revision 1.1.2.3  2008/04/10 16:18:00  mhaye05
 * updates to all for a scheduled job to load the summary table
 *
 * Revision 1.1.2.2  2008/04/10 00:50:49  mhaye05
 * added additional functions
 *
 * Revision 1.1.2.1  2008/04/08 21:37:04  mhaye05
 * Initial check in
 *
 */