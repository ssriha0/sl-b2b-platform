package com.newco.marketplace.persistence.dao.providerleadmanagement;

import java.util.List;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CallCustomerReasonCodeVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ProviderLeadManagementDao {
	//To Retrieve document
	public BuyerLeadAttachmentVO retrieveDocumentById(Integer documentId);
	//To remove document
	public DocumentVO removeDocumentById(Integer documentId);
	//To upload a document
	public Integer updateDocumentId(DocumentVO documentVO);
	//To insert history(logging)
	public void insertHistoryForLead(LeadHistoryVO leadHistoryVO);
	
	public void insertHistoryForLeadProvider(LeadHistoryVO leadHistoryVO);

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<CallCustomerReasonCodeVO> getCustInterestedReasonCodes()throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<CallCustomerReasonCodeVO> getCustNotInterestedReasonCodes()throws DataServiceException;
	
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<CallCustomerReasonCodeVO> getcustNotRespondedReasonCodes()throws DataServiceException;
	// to fetch provdier info for the lead
	public List<ProviderInfoVO> getProviderInfoForLead(String leadId);
	// To fetch the list of history for a lead
	public List<LeadHistoryVO> getHistoryListForLead(String leadId);
	// to fetch cancelled providers info
	public List<ProviderInfoVO> getCancelledProviderInfo(String leadId);
	//To get Provider cancel Reason codes
	public Map<String,Integer> getProviderLeadReasons(String reasonType,String roleType);
	//To get Customer cancel Reason codes
	public Map<String,Integer> getCustomerLeadReasons(String reasonType,String roleType);
	//To get BusinessNameFor Vendor
	public String getBusinessNameOfVendor(Integer vendorId) throws DataServiceException;
	/**
	 * @param leadId
	 * @param vendorid
	 * @return
	 * @throws DataServiceException
	 */
	public String getLeadFirmStatus(String leadId, String vendorid)throws DataServiceException;
	
}
