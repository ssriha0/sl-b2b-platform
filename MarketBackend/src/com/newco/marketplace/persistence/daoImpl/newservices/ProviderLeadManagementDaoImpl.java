package com.newco.marketplace.persistence.daoImpl.newservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CallCustomerReasonCodeVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.persistence.dao.providerleadmanagement.ProviderLeadManagementDao;
import com.sears.os.dao.impl.ABaseImplDao;


public class ProviderLeadManagementDaoImpl extends ABaseImplDao implements ProviderLeadManagementDao {

	//get document for ID
		public BuyerLeadAttachmentVO retrieveDocumentById(Integer documentId){
			BuyerLeadAttachmentVO attachment = null;
			try{
				attachment = (BuyerLeadAttachmentVO)queryForObject("leadsmanagement.getDocument",documentId);
				
			}catch(Exception e){
				logger.error("Exception caught inside BuyerLeadManagementDaoImpl.retrieveDocumentById():", e);
			}
			return attachment;
		}

		public DocumentVO removeDocumentById(Integer documentId) {
			DocumentVO attachment = null;
			try{
				attachment = (DocumentVO)queryForObject("leadsmanagement.getDocumentByDocId",documentId);
				
			}catch(Exception e){
				logger.error("Exception caught inside BuyerLeadManagementDaoImpl.retrieveDocumentById():", e);
			}
			return attachment;
		}

		public Integer updateDocumentId(DocumentVO documentVO) {
		Integer leadDocId=null;
			try{
				leadDocId = (Integer)insert("leadsmanagement.insertDocument",documentVO);
				
			}catch(Exception e){
				logger.error("Exception while inserting document into leadDocuments:", e);
			}
			return leadDocId;
		}
		
		public void insertHistoryForLead(LeadHistoryVO leadHistoryVO) {
			try{
				insert("buyerleadsmanagement.insertHistory",leadHistoryVO);
			}catch(Exception e){
				logger.error("Exception while inserting history into lead history:", e);
			}
		}
		
		public void insertHistoryForLeadProvider(LeadHistoryVO leadHistoryVO) {
			try{
				insert("buyerleadsmanagement.insertHistoryProvider",leadHistoryVO);
			}catch(Exception e){
				logger.error("Exception while inserting history into lead history:", e);
			}
		}
		
		public List<ProviderInfoVO> getProviderInfoForLead(String leadId) {
			List<ProviderInfoVO> providerInfoVOs = new ArrayList<ProviderInfoVO>();
			try{
				providerInfoVOs = queryForList("leadsmanagement.getProviderInfo.query",leadId);
				
			}catch(Exception e){
				logger.error("Exception caught inside BuyerLeadManagementDaoImpl.retrieveDocumentById():", e);
			}
			return providerInfoVOs;
		}
		
		public List<LeadHistoryVO> getHistoryListForLead(String leadId) {
			List<LeadHistoryVO> leadHistoryVOLst = new ArrayList<LeadHistoryVO>();
			try{
				leadHistoryVOLst = queryForList("leadsmanagement.getHistoryVOLst.query",leadId);
				
			}catch(Exception e){
				logger.error("Exception caught inside BuyerLeadManagementDaoImpl.retrieveDocumentById():", e);
			}
			return leadHistoryVOLst;
		}
		
		

		public List<ProviderInfoVO> getCancelledProviderInfo(String leadId) {
			List<ProviderInfoVO> providerInfoVOs = new ArrayList<ProviderInfoVO>();
			try{
				providerInfoVOs = queryForList("leadsmanagement.getCancelledProviderInfo.query",leadId);
				
			}catch(Exception e){
				logger.error("Exception caught inside BuyerLeadManagementDaoImpl.retrieveDocumentById():", e);
			}
			return providerInfoVOs;
		}
		public Map<String,Integer> getProviderLeadReasons(String reasonType,String roleType)
		{
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("reasonType", reasonType);
			paramMap.put("roleType",roleType);
			Map<String,Integer> leadReasons=(Map<String, Integer>) queryForMap("leadsmanagement.getProviderCancelLeadReasonMap",paramMap,"key","value");		
			return leadReasons;
			
		}
		public Map<String,Integer> getCustomerLeadReasons(String reasonType,String roleType)
		{
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("reasonType", reasonType);
			paramMap.put("roleType",roleType);
			Map<String,Integer> leadReasons=(Map<String, Integer>) queryForMap("leadsmanagement.getCustomerCancelLeadReasonMap",paramMap,"key","value");		
			return leadReasons;
			
		}

		public List<CallCustomerReasonCodeVO> getCustInterestedReasonCodes()throws DataServiceException {
			List<CallCustomerReasonCodeVO> custInterested = null;
			Map TypeRoleMap=new HashMap();
			TypeRoleMap.put("Type","CALL_CUSTOMER");
			TypeRoleMap.put("Role","customer");
			try{
				custInterested = queryForList("buyerleadsmanagement.getCallCustomerReasonCodes",TypeRoleMap);
			}catch(Exception e){
				logger.info("Exception in getting the available reason codes for Call customer"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return custInterested;
		}

		public List<CallCustomerReasonCodeVO> getCustNotInterestedReasonCodes()throws DataServiceException {
			List<CallCustomerReasonCodeVO> custNotInterested = null;
			Map TypeRoleMap=new HashMap();
			TypeRoleMap.put("Type","CANCELLATION");
			TypeRoleMap.put("Role","customer");
			try{
				custNotInterested=queryForList("buyerleadsmanagement.getCallCustomerReasonCodes",TypeRoleMap);
			}catch(Exception e){
				logger.info("Exception in getting the available reason codes for Call customer"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return custNotInterested;
		}

		public List<CallCustomerReasonCodeVO> getcustNotRespondedReasonCodes()throws DataServiceException {
			List<CallCustomerReasonCodeVO> custNotResponded = null;
			Map TypeRoleMap=new HashMap();
			TypeRoleMap.put("Type","CALL_CUSTOMER");
			TypeRoleMap.put("Role","provider");
			try{
				custNotResponded=queryForList("buyerleadsmanagement.getCallCustomerReasonCodes",TypeRoleMap);
			}catch(Exception e){
				logger.info("Exception in getting the available reason codes for Call customer"+ e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return custNotResponded;
		}
		
		public String getBusinessNameOfVendor(Integer vendorId) throws DataServiceException {
			String businessName = "";
				try{
					businessName = (String) queryForObject("leadsmanagement.getBusinessNameOfVendor",vendorId);
					
				}catch(Exception e){
					logger.error("Exception while getting business name of the vendor:", e);
				}
				return businessName;
			}

		public String getLeadFirmStatus(String leadId, String vendorId)throws DataServiceException {
			String leadFirmStatus="";
			Map parametermap=new HashMap<String,String>();
			parametermap.put("slleadid", leadId);
			parametermap.put("firmid",vendorId);
			try{
			 leadFirmStatus = (String)queryForObject("leadsmanagement.validateFirmAndStatus",parametermap);
			}catch(Exception e){
			 logger.info("Exception in getting Actual lead firm status for stale lead"+e.getMessage());
			}
			return leadFirmStatus;
		}
	
}