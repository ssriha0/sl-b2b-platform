package com.newco.marketplace.persistence.dao.buyerleadmanagement;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadLookupVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadManagementCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadDetailsCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadCustVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface BuyerLeadManagementDao {
	public List<LeadInfoVO> getBuyerLeadManagementDetails(BuyerLeadManagementCriteriaVO buyerLeadManagementPagination);
	public List<BuyerLeadLookupVO> loadStates();
   
	public Integer getBuyerLeadManagementCount(BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO);
    
    //
    
    public LeadInfoVO getBuyerLeadManagementSummary(String leadId);
    public List<ProviderInfoVO> getBuyerLeadManagementProviderInfo(String leadId);
    public ProviderInfoVO getBuyerLeadManagementResourceInfo(ProviderInfoVO leadProviderDetails);
    public ProviderInfoVO getBuyerLeadManagementResourceScore(ProviderInfoVO resourceDetails);
    public List<SLLeadNotesVO> getBuyerLeadViewNotes(BuyerLeadManagementCriteriaVO buyerLeadViewNotesCriteriaVO);	
    public List<SLLeadHistoryVO> getBuyerLeadHistory(String leadId);
    public List<SLLeadHistoryVO> getBuyerLeadHistory(String leadId,String actionId);
    public Map<String,Integer> getBuyerLeadReasons(String reasonType,String roleType);
    public List<String> getBuyerLeadNoteCategory();
    public List<String> getLeadReasons(String reason);
    public Map<String,String> getRewardPoints();
    
    public void updateBuyerLeadCustomerInfo(LeadDetailsCriteriaVO  leadDeatilsCriteriaVO);

    //
    public int cancelLead(CancelLeadVO cancelLeadVO) throws DataServiceException;
    
    public void addOrRevokeShopYourWayPoints(int points,String leadId,String menberShipId,String modifiedBy);
    public List<LeadHistoryVO> getShowYourWayRewardsHistoryForLeadMember(String leadId,String menberShipId); 
    public void insertShowYourWayRewardsHistoryForLeadMember(LeadHistoryVO leadHistoryVO);
    public Integer selectShopYourWayPointsForLeadMember(String leadId,String memberShipId);
    
    //get documents uploaded for a lead
	public List<BuyerLeadAttachmentVO> getAttachmentDetails(String leadId);
	//get document by ID
	public BuyerLeadAttachmentVO retrieveDocumentById(Integer documentId);
	public List<CancelLeadVO> getCancelLeadEmailDetails(CancelLeadMailVO cancelLeadMailVO);
	public List<ProviderInfoVO> getBuyerLeadManagementCancelledProviderInfo(String leadId);
	
public Integer  saveInsidesSalesLeadInfo(InsideSalesLeadVO insideSalesLeadVO) throws Exception;
	
	public void  saveInsidesSalesLeadCustInfo(List<InsideSalesLeadCustVO> custInfoList) throws Exception;
	public InsideSalesLeadVO getLead(String leadId)
			throws Exception;
	public void deleteLead(Integer leadId)
			throws Exception;
	public void deleteLeadCustomFields(Integer leadId)
			throws Exception;
	public void updateLeadStatusHistory(InsideSalesLeadVO insideSalesLeadVO)
			throws Exception;
	public void updateIsLeadStatus(InsideSalesLeadVO insideSalesLeadVO)
			throws Exception;
	public void updateIsLeadStatusHistory(InsideSalesLeadVO insideSalesLeadVO)
			throws Exception;
     
}
