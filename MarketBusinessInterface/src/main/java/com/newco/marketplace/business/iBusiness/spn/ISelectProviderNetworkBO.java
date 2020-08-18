package com.newco.marketplace.business.iBusiness.spn;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMemberSearchResults;
import com.newco.marketplace.dto.vo.spn.SPNNetworkResourceVO;
import com.newco.marketplace.dto.vo.spn.SPNProviderProfileBuyerVO;
import com.newco.marketplace.dto.vo.spn.SPNSummaryVO;
import com.newco.marketplace.dto.vo.spn.SPNetCommMonitorVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;

public interface ISelectProviderNetworkBO extends SPNConstants {

	/**
	 * Returns a list of SPNSummaryVOs for the provider buyer id.  If no SPNs are
	 * found, then an empty list is returned.
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<SPNSummaryVO> getSPNByBuyerId (Integer buyerId) throws BusinessServiceException;
	
	/**
	 * Creates a new SPN
	 * @param spnHeaderVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer insertSPN(SPNHeaderVO spnHeaderVO) throws BusinessServiceException;
	
	/**
	 * Updates an existing SPN
	 * @param spnHeaderVO
	 * @throws BusinessServiceException
	 */
	public void updateSPN(SPNHeaderVO spnHeaderVO) throws BusinessServiceException;
	
	/**
	 * Deletes an existing SPN and removes it from any templates that used it
	 * @param spnId
	 * @throws BusinessServiceException
	 */
	public void deleteSPN(Integer spnId) throws BusinessServiceException;
	
	/**
	 * Returns a SPNHeaderVO for the SPN Id provided.  If no record is found,
	 * then NULL is returned.
	 * @param spnId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SPNHeaderVO getSPNBySPNId (Integer spnId) throws BusinessServiceException;
	
	/**
	 * Returns the criteria for the SPN Id provided.  If no records are found,
	 * then an empty SPNCriteriaVO is returned.
	 * @param spnId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SPNCriteriaVO getSPNCriteriaBySPNId (Integer spnId) throws BusinessServiceException;
	
	/**
	 * This method will loop through all Select Provider Networks and recalculate
	 * the summary counts by status.
	 * @throws BusinessServiceException
	 */
	public void loadSPNSummaryTable() throws BusinessServiceException;
		
	/**
	 * This method will loop through all Select Provider Networks and recalculate
	 * the summary counts by status.
	 * @throws BusinessServiceException
	 */
	public List<SPNCampaignVO> loadAllSPNCampaigns(Integer spnId) throws BusinessServiceException;
	
	/**
	 * This method will loop through all Select Provider Networks and recalculate
	 * the summary counts by status.
	 * @throws BusinessServiceException
	 */
	public Integer createNewSPNCampaign(SPNCampaignVO campaign) throws BusinessServiceException;
	
	/**
	 * This method will loop through all Select Provider Networks and recalculate
	 * the summary counts by status.
	 * @throws BusinessServiceException
	 */
	public Integer deleteSPNCampaign(Integer campaignId) throws BusinessServiceException;
	
	/**
	 * Retrieves a list of Select Provider Networks 
	 * @param spnId
	 * @throws BusinessServiceException
	 */
	public List<SPNHeaderVO> getAllSPNS() throws BusinessServiceException;

	/**
	 * Returns Business name for given buyer
	 * @param buyerId
	 * @return
	 * @throws Exception
	 */
	public String getBuyerBusinessName(Integer buyerId)throws Exception;
	
	/**updates status of spn in spn_network based on provider's response as Interested or NotInterested
	 * @param networkResourceVO
	 * @throws BusinessServiceException
	 */
	public void updateStatusByProviderResponse(SPNNetworkResourceVO networkResourceVO) throws BusinessServiceException;
	
	/**
	 * @throws BusinessServiceException
	 */
	public void runSPNCampaign() throws BusinessServiceException;
	
	public List<SPNHeaderVO> loadAllSpnNetworkInvites(Integer resourceId, Integer companyId) throws BusinessServiceException;
	
	public boolean isProviderAdmin(Integer resourceId) throws BusinessServiceException;
	
	/* Provider Profile Page */
	
	public List<SPNProviderProfileBuyerVO> getProviderProfileBuyers(Integer resourceId) throws BusinessServiceException;
		
	
	
	/**
	 * This method will change the status to Member for all of the SPN Network Id's passed in.
	 * @param spnNetworkIds
	 * @throws BusinessServiceException
	 */
	public void approveMembers(List<Integer> spnNetworkIds) throws BusinessServiceException;

	/**
	 * This method will change the status to Removed for all of the SPN Network Id's passed in.
	 * @param spnNetworkIds
	 * @throws BusinessServiceException
	 */
	public void removeMembers(List<Integer> spnNetworkIds) throws BusinessServiceException;
	
	/**
	 * @param criteriaMap
	 * @return
	 * @throws BusinessServiceException
	 */
	public SPNMemberSearchResults getSPNResources (CriteriaMap criteriaMap) throws BusinessServiceException;
	/**
	 * Returns a list of SPNDocumentVO for the spn id.  If no documents are
	 * found, then an empty list is returned.
	 * @param spnId
	 * @return List<SPNDocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModal (Integer spnId) throws BusinessServiceException;
	/**
	 * Returns a list of SPNDocumentVO for the document id. 
	 * @param docId
	 * @return List<SPNDocumentVO>
	 * @throws BusinessServiceException
	 */
	public List<SPNDocumentVO> getSPNBuyerAgreeModalDocument (Integer docId) throws BusinessServiceException;

	/**
	 * Submits Buyer Agreements of a SPN by a provider. 
	 * @param firmId
	 * @param spnId
	 * @throws BusinessServiceException
	 */
	public void submitSPNBuyerAgreement (Integer firmId,Integer spnId,String modifiedBy, Boolean auditRequired) throws BusinessServiceException;
	
	/**
	 * Submits Buyer Agreements of a SPN Doc by a provider. 
	 * @param firmId
	 * @param spnDocId
	 * @throws BusinessServiceException
	 */
	public void submitSPNBuyerAgreementForDoc (Integer firmId, String username, Integer spnDocId) throws BusinessServiceException;
	
	/**
	 * Get the details of SPN to be displayed in Provider invitation page. 
	 * @param spnId
	 * @param vendorId
	 * @throws BusinessServiceException
	 */
	public SPNMainMonitorVO loadProviderInvitation(Integer spnId, Integer vendorId) throws BusinessServiceException;
	
	/**
	 * Changes the state of the provider to PF SPN NOT INTRESTED
	 * @param spnId
	 * @param vendorId
	 * @param rejectId
	 * @param rejectReason
	 * @throws BusinessServiceException
	 */
	public void rejectInvite(String rejectId,String rejectReason,Integer spnId,Integer vendorId,String modifiedBy) throws BusinessServiceException;
	/**
	 * @param companyId
	 * @return SPNetCommMonitorVO
	 * @throws BusinessServiceException
	 */
	public List<SPNetCommMonitorVO> loadSPNetCommDetailsForPA(Integer companyId) throws BusinessServiceException;
}
