package com.newco.marketplace.persistence.daoImpl.newservices;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadLookupVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadManagementCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadMailVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadDetailsCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.CancelLeadVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoProviderDetails;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.UpdateLeadStatusRequestVO;
import com.newco.marketplace.exception.core.DataServiceException;
//import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadCustVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadVO;
import com.newco.marketplace.persistence.dao.buyerleadmanagement.BuyerLeadManagementDao;
import com.sears.os.dao.impl.ABaseImplDao;



/**
 * @author Madhup_Chand
 *
 */
public class BuyerLeadManagementDaoImpl extends ABaseImplDao implements BuyerLeadManagementDao {

public List<LeadInfoVO> getBuyerLeadManagementDetails(BuyerLeadManagementCriteriaVO buyerLeadManagementPagination)
{
try {
		List<LeadInfoVO> leadDetails=new ArrayList<LeadInfoVO>();
		List<LeadInfoProviderDetails> firmDetails = new ArrayList<LeadInfoProviderDetails>();
		leadDetails=(List <LeadInfoVO>) queryForList("getBuyerLeadDetails.query", buyerLeadManagementPagination);
		
		if(leadDetails.size()>0){
			firmDetails = (List <LeadInfoProviderDetails>) queryForList("getFirmDetails.query", getLeads(leadDetails));
			if(firmDetails.size()>0){
				leadDetails = mapFirmDetailsToLead(firmDetails,leadDetails);
			}
		}
		return leadDetails;
	} catch (NoResultException e) {
		return null;
	}
	
}

private List<String> getLeads(List<LeadInfoVO> leadDetails){
	List<String> leadsList = new ArrayList<String>();
	for(LeadInfoVO vo:leadDetails){
		if(null != vo &&  StringUtils.isNotBlank(vo.getSlLeadId())){
			leadsList.add(vo.getSlLeadId());
		}
	}
	return leadsList;
}

private List<LeadInfoVO> mapFirmDetailsToLead(List<LeadInfoProviderDetails> firmDetails, List<LeadInfoVO> leadDetails){
	/*for(LeadInfoVO leadDetail: leadDetails){
		for(LeadInfoProviderDetails firmDetail:firmDetails){
			if(leadDetail.getSlLeadId().equalsIgnoreCase(firmDetail.getLeadId())){
				if(null == leadDetail.getFirmDetails()){
					List<LeadInfoProviderDetails> leadInfoProviderDetails=new ArrayList<LeadInfoProviderDetails>();
					leadInfoProviderDetails.add(firmDetail);
					leadDetail.setFirmDetails(leadInfoProviderDetails);
				}else{
					leadDetail.getFirmDetails().add(firmDetail);
				}
			}
		}
	}*/
	//create a map with leadid as key and List<LeadInfoProviderDetails> as value
	Map<String,List<LeadInfoProviderDetails>> firmDetailsMap = new HashMap<String, List<LeadInfoProviderDetails>>();
	for(LeadInfoProviderDetails firmDetail:firmDetails){
		String slLeadId = firmDetail.getLeadId();
		if(null != firmDetailsMap.get(slLeadId)){
			List<LeadInfoProviderDetails> firmDetailsList = firmDetailsMap.get(slLeadId);
			firmDetailsList.add(firmDetail);
			firmDetailsMap.put(slLeadId, firmDetailsList);
		}else{
			List<LeadInfoProviderDetails> firmDetailsList = new ArrayList<LeadInfoProviderDetails>();
			firmDetailsList.add(firmDetail);
			firmDetailsMap.put(slLeadId, firmDetailsList);
		}
	}
	
	//iterate the main leadInfoVO and set the firm details object
	for(LeadInfoVO leadDetail: leadDetails){
		leadDetail.setFirmDetails(firmDetailsMap.get(leadDetail.getSlLeadId()));
	}
	return leadDetails;
}
public List<BuyerLeadLookupVO> loadStates()
{
	List<BuyerLeadLookupVO> buyerLeadStates=new ArrayList<BuyerLeadLookupVO>();
	buyerLeadStates=(List <BuyerLeadLookupVO>) queryForList("getBuyerLeadStates.query");
	return buyerLeadStates;
	
}

@Transactional(propagation=Propagation.SUPPORTS)
public Integer getBuyerLeadManagementCount(BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO) {
	Integer count= (Integer) queryForObject("getBuyerLeadCount.query",buyerLeadManagementCriteriaVO);
	return count;
	}


@Transactional(propagation=Propagation.SUPPORTS)
public LeadInfoVO getBuyerLeadManagementSummary(String leadId)
{
try {
		LeadInfoVO leadDetails=new LeadInfoVO();
		leadDetails=(LeadInfoVO) queryForObject("getOrderInfo.query",leadId);
				
		return leadDetails;
		
		
		
	} catch (NoResultException e) {
		return null;
	}

	
}

@Transactional(propagation=Propagation.SUPPORTS)
public List<ProviderInfoVO> getBuyerLeadManagementProviderInfo(String leadId)
{
try {
		List<ProviderInfoVO> ProviderDetails=new ArrayList<ProviderInfoVO>();
		ProviderDetails=(List <ProviderInfoVO>) queryForList("getProviderInfo.query",leadId);
		return ProviderDetails;
	} catch (NoResultException e) {
		return null;
	}

	
}

public List<ProviderInfoVO> getBuyerLeadManagementCancelledProviderInfo(String leadId)
{
try {
		List<ProviderInfoVO> ProviderDetails=new ArrayList<ProviderInfoVO>();
		ProviderDetails=(List <ProviderInfoVO>) queryForList("getCancelledProviderInfo.query",leadId);
		return ProviderDetails;
	} catch (NoResultException e) {
		return null;
	}

	
}

@Transactional(propagation=Propagation.SUPPORTS)
public List<SLLeadNotesVO> getBuyerLeadViewNotes(BuyerLeadManagementCriteriaVO buyerLeadViewNotesCriteriaVO)
{
	List<SLLeadNotesVO>  leadNotes= new ArrayList<SLLeadNotesVO>();
	leadNotes=(List <SLLeadNotesVO>) queryForList("getBuyerLeadViewNotes.query",buyerLeadViewNotesCriteriaVO);
	return leadNotes;
}

public void addOrRevokeShopYourWayPoints(int points,String leadId,String menberShipId,String modifiedBy) {
	// TODO Auto-generated method stub
	HashMap<String,String> params = new HashMap<String, String>();
	params.put("rewardpoints", Integer.toString(points));
	params.put("leadId", leadId);
	params.put("swyrMemberId", menberShipId);
	params.put("modifiedBy", modifiedBy);
	update("buyerleadsmanagement.addOrRevokeShopYourWayPointsForLeadMember", params);
}
public List<LeadHistoryVO> getShowYourWayRewardsHistoryForLeadMember(
		String leadId,String menberShipId) {
	
	HashMap<String,String> params = new HashMap<String, String>();
	params.put("actionId", "9");
	params.put("leadId", leadId);
	params.put("swyrMemberId", menberShipId);
	List<LeadHistoryVO>  leadHistoryVOlst= (List <LeadHistoryVO>) queryForList("buyerleadsmanagement.getHistory",params);
	return leadHistoryVOlst;
	
}
public void insertShowYourWayRewardsHistoryForLeadMember(
		LeadHistoryVO leadHistoryVO) {
	insert("buyerleadsmanagement.insertHistory",leadHistoryVO);
}
public Integer selectShopYourWayPointsForLeadMember(String leadId,
		String memberShipId) {
	
	HashMap<String,String> params = new HashMap<String, String>();
	params.put("leadId", leadId);
	params.put("swyrMemberId", memberShipId);
	Integer shopYourWayPoints= (Integer) queryForObject("buyerleadsmanagement.selectShopYourWayPointsForLeadMember",params);
	return shopYourWayPoints;
}


/**
 * @author NaveenKomanthakal_V
 * @throws DataServiceException 
 *
 */
public int cancelLead(CancelLeadVO cancelLeadVO) throws DataServiceException{
	Map<String,Object> paramMap = new HashMap<String, Object>();
	try {
		paramMap.put("leadId", cancelLeadVO.getLeadId());
		paramMap.put("reasonCode",cancelLeadVO.getReasonCode());
		paramMap.put("comments", cancelLeadVO.getComment());
		paramMap.put("chkAllProInd", cancelLeadVO.isChkAllProviderInd());
		paramMap.put("buyerName",cancelLeadVO.getBuyer_name());
		paramMap.put("cancelledDate", new Timestamp(new Date(System.currentTimeMillis()).getTime()));
		paramMap.put("cancelInitiatedByRoleType",cancelLeadVO.getCancelInitiatedByRoleType());
		
		
		//paramMap.put("providerList", cancelLeadVO.getProviderList());
		
		//insert into lead_cancel
		for(Integer vendorId:cancelLeadVO.getProviderList())
		{
			paramMap.put("vendorId",vendorId);
			insert("buyerleadsmanagement.insertCancelLead",paramMap);
		}
		insert("buyerleadsmanagement.insertCancelLead",paramMap);
		if(cancelLeadVO.isChkAllProviderInd()){
		paramMap.put("status",NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_STATUS_CANCELLED);
		update("buyerleadsmanagement.cancelLead", paramMap);
		}
		cancelLeadVO.setStatus(NewServiceConstants.BUYER_LEAD_MANAGEMENT_LEAD_STATUS_CANCELLED_PROVIDERS);
		int j=update("buyerleadsmanagement.cancelProviderLead",cancelLeadVO);
		if(cancelLeadVO.isRevokePointsInd())
		{
		update("buyerleadsmanagement.revokePoints", paramMap);
		}
		
		//check role id in lead history
		//insertHistoryForLeadCancellation(cancelLeadVO);
		
		if(j > 0)
			return j;
		else
			return 0;
	} catch (Exception ex) {
		logger.error("Exception caught inside BuyerLeadManagementDaoImpl.cancelLead():", ex);
		throw new DataServiceException("BuyerLeadManagementDaoImpl --> cancelLead", ex);
		
	}		
}

public void insertHistoryForLeadCancellation(
		CancelLeadVO cancelLeadVO) {
	LeadHistoryVO leadHistoryVO=new LeadHistoryVO();
	//action id 10 for cancellation
	leadHistoryVO.setActionId(10);
	leadHistoryVO.setDescription(cancelLeadVO.getComment());
	leadHistoryVO.setCreatedBy(cancelLeadVO.getBuyer_name());
	leadHistoryVO.setSlLeadId(cancelLeadVO.getLeadId());
	leadHistoryVO.setRoleId(cancelLeadVO.getRoleId());
	
	insert("buyerleadsmanagement.insertHistory",leadHistoryVO);
}

@Transactional(propagation=Propagation.SUPPORTS)
public ProviderInfoVO getBuyerLeadManagementResourceInfo(ProviderInfoVO providerDetail)
{
try {
        ProviderInfoVO resourceDetails=new ProviderInfoVO();
        if(providerDetail.getFirmStatus().equals("completed"))
        {
        resourceDetails=(ProviderInfoVO) queryForObject("getCompletedResourceInfo.query", providerDetail);
        
        }
        else  if(providerDetail.getFirmStatus().equals("cancelled"))
        {
            resourceDetails=(ProviderInfoVO) queryForObject("getCancelledResourceInfo.query", providerDetail);
        }
        else if(providerDetail.getFirmStatus().equals("scheduled"))
        {
            resourceDetails=(ProviderInfoVO) queryForObject("getScheduledResourceInfo.query", providerDetail);
        }
        return resourceDetails;
    } catch (NoResultException e) {
        return null;
    }

   
}

@Transactional(propagation=Propagation.SUPPORTS)
public ProviderInfoVO getBuyerLeadManagementResourceScore(ProviderInfoVO resourceDetails)
{
	ProviderInfoVO resourceDetailsScore=new ProviderInfoVO();
    
	resourceDetailsScore=(ProviderInfoVO) queryForObject("getResourceScoreInfo.query",resourceDetails.getResourceId());
	
	 return resourceDetailsScore;
}

@Transactional(propagation=Propagation.SUPPORTS)
public void updateBuyerLeadCustomerInfo(LeadDetailsCriteriaVO  leadDeatilsCriteriaVO)
{
	
	update("leadsmanagement.updateBuyerLeadCustomerInfo",leadDeatilsCriteriaVO);
	
	
}

@Transactional(propagation=Propagation.SUPPORTS)
public List<SLLeadHistoryVO> getBuyerLeadHistory(String leadId)
{
List<SLLeadHistoryVO>  leadHistory= new ArrayList<SLLeadHistoryVO>();
leadHistory=(List <SLLeadHistoryVO>) queryForList("getBuyerLeadHistory.query",leadId);
return leadHistory;
}
public List<SLLeadHistoryVO> getBuyerLeadHistory(String leadId,String actionId)
{   
	HashMap<String,String> params = new HashMap<String, String>();
	params.put("leadId", leadId);
	params.put("actionId",  actionId);
	List<SLLeadHistoryVO>  leadHistory= new ArrayList<SLLeadHistoryVO>();
	leadHistory=(List <SLLeadHistoryVO>) queryForList("getBuyerLeadHistoryWithActionId.query",params);
	return leadHistory;
}

	//get documents for a lead
	public List<BuyerLeadAttachmentVO> getAttachmentDetails(String leadId){
		List<BuyerLeadAttachmentVO> attachments = null;
		try{
			attachments = (List<BuyerLeadAttachmentVO>)queryForList("leadsmanagement.getAttachmentsOFProviderByBuyer",leadId);
			
		}catch(Exception e){
			logger.error("Exception caught inside BuyerLeadManagementDaoImpl.getAttachmentDetails():", e);
		}
		return attachments;
	}
	
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

	//get buyer lead note category
	@SuppressWarnings("unchecked")
	public List<String> getBuyerLeadNoteCategory(){
		List<String> leadnoteCategory = new ArrayList<String>();
		try{
			leadnoteCategory= queryForList("buyerleadsmanagement.getLeadNoteCategory");
		}catch(Exception e){
		logger.error("Exception caught inside BuyerLeadManagementDaoImpl.getBuyerLeadNoteCategory():", e);
		}
		return leadnoteCategory;
	}


	public Map<String,Integer> getBuyerLeadReasons(String reasonType,String roleType)
	{
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("reasonType", reasonType);
		paramMap.put("roleType",roleType);
		Map<String,Integer> leadReasons=(Map<String, Integer>) queryForMap("buyerleadsmanagement.getCancelLeadReasonMap",paramMap,"key","value");		
		return leadReasons;
		
	}
	public List<String> getLeadReasons(String reason){

		 List<String> leadReasons= (List<String>) queryForList("buyerleadsmanagement.getLeadReasonMap",reason);
		 return leadReasons;
	 }
	 public Map<String,String> getRewardPoints(){
		 Map<String,String> rewardPointsLst=(Map<String, String>) queryForMap("buyerleadsmanagement.getRewardPointsMap",null,"rewardPoints","rewardPointsDesc");
		 return rewardPointsLst;
	 }
	 
	 public List<CancelLeadVO> getCancelLeadEmailDetails(CancelLeadMailVO cancelLeadMailVO)
	 {
		 List<CancelLeadVO> cancelLeadVO = null;
		 try{
			 if(null!=cancelLeadMailVO && null!=cancelLeadMailVO.getCancelMailReceiverType()
					 && cancelLeadMailVO.getCancelMailReceiverType().equals("PROVIDER"))
			 {
		 cancelLeadVO = (List<CancelLeadVO>)queryForList("buyerleadsmanagement.getCancelLeadProviderDetails",cancelLeadMailVO);
			 }
			 else
			 {
		cancelLeadVO = (List<CancelLeadVO>)queryForList("buyerleadsmanagement.getCancelLeadMailDetails",cancelLeadMailVO);		 
			 }
		 }catch(Exception e){
				logger.error("Exception caught inside BuyerLeadManagementDaoImpl.getCancelLeadEmailDetails():", e);
				}
		 return cancelLeadVO;
		 
	 }
	 
	 public Integer saveInsidesSalesLeadInfo(InsideSalesLeadVO insideSalesLeadVO)throws Exception{
			
			return (Integer)insert("leadsmanagement.saveInsidesSalesLeadInfo",insideSalesLeadVO);

				
			}
			
			public void updateIsLeadStatus(InsideSalesLeadVO insideSalesLeadVO)
					throws Exception{
				update("leadsmanagement.updateIsLeadStatus",insideSalesLeadVO);

			}
			
			public void updateIsLeadStatusHistory(InsideSalesLeadVO insideSalesLeadVO)
					throws Exception{
				insert("leadsmanagement.updateIsLeadStatusHistory",insideSalesLeadVO);

					}
			
			public void  saveInsidesSalesLeadCustInfo(List<InsideSalesLeadCustVO> custInfoList) throws Exception{
				
				splitInsert("leadsmanagement.saveInsidesSalesLeadCustInfo",custInfoList);
			}
			
			public InsideSalesLeadVO getLead(String leadId)
					throws Exception{
			    return (InsideSalesLeadVO) queryForObject("leadsmanagement.getIsLead",leadId);
				
			}
			
			public void deleteLead(Integer leadId)
					throws Exception{
				delete("leadsmanagement.deleteLead", leadId);
			}
			
			
			public void deleteLeadCustomFields(Integer leadId)
					throws Exception{
				delete("leadsmanagement.deleteLeadCustomFields", leadId);
			}
			
			public void updateLeadStatusHistory(InsideSalesLeadVO insideSalesLeadVO)
					throws Exception{
				update("leadsmanagement.updateLeadStatusHistory", insideSalesLeadVO);
			}
			
			
			
			private void splitInsert(String query, List<InsideSalesLeadCustVO> custInfoList) throws Exception{
				try{
					int noOfRecords = (custInfoList==null?0:custInfoList.size());
					int noOfIter = 0;
					int count =500;
					if(noOfRecords>0){
						noOfIter = noOfRecords/count;
						if(noOfIter==0){
							count = noOfRecords;
							noOfIter =1;
						}
					}
					int loopCount = 0;
					while(noOfIter!=0){
						int endIndex = (loopCount+1)*count;
						if(noOfIter==1){
							endIndex = custInfoList.size();
						}
						List<InsideSalesLeadCustVO> leadsCustInfo = custInfoList.subList(loopCount*count, endIndex);
						insert(query, leadsCustInfo);
						
						loopCount++;
						noOfIter--;
					}
				}catch (Exception dse){
					logger.info(dse);
				}
			}

	 
}