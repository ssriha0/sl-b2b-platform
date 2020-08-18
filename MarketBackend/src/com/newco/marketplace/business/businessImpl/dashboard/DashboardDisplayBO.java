package com.newco.marketplace.business.businessImpl.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.newco.marketplace.business.iBusiness.dashboard.IDashboardDisplayBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.ledger.ReceivedAmountVO;
import com.newco.marketplace.dto.vo.serviceorder.LeadVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AdminConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.ProviderDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.vo.provider.VendorResource;
import com.sears.os.business.ABaseBO;
import com.servicelive.routingrulesengine.vo.RuleErrorDisplayVO;

/**
 * @author zizrale
 *
 */
public class DashboardDisplayBO extends ABaseBO implements IDashboardDisplayBO{
//	private static final Logger logger = Logger.getLogger(DashboardDisplayBO.class.getName());
	
	private ILedgerFacilityBO accTranMan;
	private ProviderDao provider;
	private ISurveyBO survey;
	private ServiceOrderDao sodao;
	private IVendorHdrDao vendorHdrDao;
	private String staleAfter;
	private String goal;
	
	/** (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.dashboard.IDashboardDisplayBO#getDashBoardWidgetDetails(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public SODashboardVO getDashBoardWidgetDetails(String entityId,String resourceId, Integer entityTypeId,
			SurveyRatingsVO surveyRatings, boolean manageSoFlag,boolean leadFlag) throws BusinessServiceException{
		logger.info("Entered getDashBoardWidgetDetails in DashboardDisplayBO.");
		SODashboardVO dashboardDetails = new SODashboardVO();
		
		// set up the VO
		AjaxCacheVO vendorInfo = new AjaxCacheVO();
		vendorInfo.setCompanyId((entityId != null)? new Integer(Integer.parseInt(entityId)) : new Integer(-1));
		if (entityTypeId.intValue() == OrderConstants.BUYER_ROLEID){
			vendorInfo.setRoleType(OrderConstants.BUYER_ROLE);
		} else if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID){
			vendorInfo.setRoleType(OrderConstants.PROVIDER_ROLE);
		} else if (entityTypeId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID){ 
			vendorInfo.setRoleType(OrderConstants.SIMPLE_BUYER_ROLE);
		}
		vendorInfo.setVendBuyerResId((resourceId != null)? new Integer(Integer.parseInt(resourceId)) : new Integer(-1));
		vendorInfo.setManageSoFlag(manageSoFlag);
		
		logger.info("Set up the VO - company id = " + vendorInfo.getCompanyId() + " resource id = " + vendorInfo.getVendBuyerResId() + " role type = " + vendorInfo.getRoleType());
		
		HashMap<String, Integer> soOrderCountsList = getCountsInfo(entityTypeId, vendorInfo);
		
		if(leadFlag){
			
			// for lead Management statitics
			if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID){
		HashMap<String, Integer> leadCountsList = getLeadCountsInfo( vendorInfo.getCompanyId());
		
		// need to change the constant
		String staleAfter=getStaleAfter();
		//Integer stale=getStaleInfo(vendorInfo.getCompanyId(), staleAfter);
		
		HashMap<String, Integer> staleCountsList = getStaleInfoCount(vendorInfo.getCompanyId(), staleAfter);
		
		
		
		// to find the average response time for the vendor
		
		
		List<LeadVO> dateList = getDateForMatchedStatus(vendorInfo.getCompanyId());
		int seconds=0;
		
		if(null!=dateList && dateList.size()>0){
			for(LeadVO lead:dateList){
				seconds=seconds+lead.getDifference();
			}
			
		}
			
		// find the average response
		double averageresponse=(double)seconds/(double)60.00;
		if(dateList!=null & dateList.size()>0){
		averageresponse=averageresponse/ (double)dateList.size();
		}		
		
		int roundedValue=(int) Math.round(averageresponse);
		
		int newTotal=leadCountsList.get("NEW")-staleCountsList.get("STALENEW");
		int newWorking=leadCountsList.get("WORKING")-staleCountsList.get("STALEWORKING");
		int staleTotal=staleCountsList.get("STALENEW")+staleCountsList.get("STALEWORKING");
				
					dashboardDetails.setStatusNew(newTotal);
					
					dashboardDetails.setWorking(newWorking);
					
					dashboardDetails.setScheduled(leadCountsList.get("SCHEDULED"));
					
					dashboardDetails.setCompleted(leadCountsList.get("COMPLETED"));
					
					dashboardDetails.setCancelled(leadCountsList.get("CANCELLED"));
					
					dashboardDetails.setStale(staleTotal);
					dashboardDetails.setTotalLeads(leadCountsList.get("TOTALLEADS"));
					dashboardDetails.setConversionRate(leadCountsList.get("CONVERSIONRATE"));
					dashboardDetails.setAverageResponse(roundedValue);
					dashboardDetails.setGoal(getGoal());
				}
			
			else if (entityTypeId.intValue() == OrderConstants.BUYER_ROLEID){
				
				// buyer lead statitics
				HashMap<String, Integer> leadCountsList = getBuyerLeadCountsInfo(vendorInfo.getCompanyId());
				
				//int unmatched=leadCountsList.get("UNMATCHED")+leadCountsList.get("NON LAUNCH MARKET");
				int unmatched=leadCountsList.get("UNMATCHED")+leadCountsList.get("OUT-OF-AREA");
				logger.info("UNMATCHED   Count : " + leadCountsList.get("UNMATCHED"));
				logger.info("OUT-OF-AREA Count : " + leadCountsList.get("OUT-OF-AREA"));
				
				dashboardDetails.setBuyerMatched(leadCountsList.get("MATCHED"));
				dashboardDetails.setBuyerUnMatched(unmatched);
				dashboardDetails.setBuyerCancelled(leadCountsList.get("CANCELLED"));			
				dashboardDetails.setBuyerCompleted(leadCountsList.get("COMPLETED"));			
				dashboardDetails.setTotalLeads(leadCountsList.get("TOTALLEADS"));
				
				
			}
				
		}

		/**
		 * Below change made to show only company level ratings information when the user logged in
		 * is a Provider.
		 * 
		 * Sending value 0 for resourceId in getRatingsInfo(entityId, resourceId, entityTypeId)
		 * when the entityTypeId tells the user role is PROVIDER. This is done to get the ratings information
		 * at the provider company level as opposed to the individual provider.
		 */
		//SurveyRatingsVO surveyRatings = null;
		
		if (surveyRatings == null) {
			if(entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID) {
				surveyRatings = getRatingsInfo(entityId, "0" /*Provider*/, entityTypeId); 
			} else if(entityTypeId.intValue() == OrderConstants.BUYER_ROLEID) { //Fix for JIRA: 6554: Don't get ratings for simple buyers. 
				//Get first from Session and if not found than read it from db			
				surveyRatings = getRatingsInfo(entityId, resourceId /*Buyer*/, entityTypeId);			
			}
		}
		
		ReceivedAmountVO receivedAmt = null; 
		if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID){
			// Received should not take into account ZERO_BID_PRICE amounts
			receivedAmt = getReceivedAmount(vendorInfo);
		}
		
		dashboardDetails.setCurrentBalance(accTranMan.getCurrentBalance(vendorInfo));
		dashboardDetails.setAvailableBalance(accTranMan.getAvailableBalance(vendorInfo));			
		dashboardDetails.setAccepted(soOrderCountsList.get(OrderConstants.TAB_ACCEPTED));
		
		//R12_1
		//SL-20362
		dashboardDetails.setPendingReschedule(soOrderCountsList.get(OrderConstants.PENDINGRESCHEDULE));
		
		
		
		if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID){ // provider received
			dashboardDetails.setReceived(soOrderCountsList.get("RECEIVED"));
			dashboardDetails.setBid(soOrderCountsList.get("BID"));
			dashboardDetails.setBulletinBoard(soOrderCountsList.get("BBOARD"));
			dashboardDetails.setTotalOrders(soOrderCountsList.get("RECEIVED"));
			dashboardDetails.setEstimationRequest(soOrderCountsList.get("ESTIMATIONREQUEST"));
		}
		
		if (entityTypeId.intValue() == OrderConstants.BUYER_ROLEID){ // buyer posted and drafted
			dashboardDetails.setPosted(soOrderCountsList.get("SENT"));  // SENT = posted?
			dashboardDetails.setDrafted(soOrderCountsList.get("DRAFT"));
		}
		
		if (entityTypeId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID){ // buyer posted and drafted
			dashboardDetails.setPosted(soOrderCountsList.get("SENT"));  // SENT = posted?
			dashboardDetails.setDrafted(soOrderCountsList.get("DRAFT"));
		}
		
		dashboardDetails.setProblem(soOrderCountsList.get(OrderConstants.TAB_PROBLEM));
		dashboardDetails.setTodays(soOrderCountsList.get("TODAY"));
		dashboardDetails.setPendingCancel(soOrderCountsList.get("PENDINGCANCEL"));

		if (surveyRatings != null){
			dashboardDetails.setNumRatingsGiven(surveyRatings.getNumberOfRatingsGiven());
			dashboardDetails.setNumRatingsReceived(surveyRatings.getNumberOfRatingsReceived());
			dashboardDetails.setCurrentRating(surveyRatings.getCurrentRating());
			dashboardDetails.setLifetimeRating(surveyRatings.getHistoricalRating());
			dashboardDetails.setGivenRating(surveyRatings.getGivenRating());
		}
		
		if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID && receivedAmt != null){
			double totalReceivedAmountCompany = 0.0;
			if ( receivedAmt != null)
			{
				totalReceivedAmountCompany = receivedAmt.getTotalReceived();
			}
				dashboardDetails.setTotalDollars(totalReceivedAmountCompany);
		} else {
			logger.info("Either its a buyer role, or there are no records for Received Amount.");
			dashboardDetails.setTotalDollars(0.0); //received amount - status received
		}
		
		// Overall Status Monitor module
		if(dashboardDetails.getNumTechniciansUnapproved() != null && dashboardDetails.getNumTechniciansUnapproved() > 0)
		{
			dashboardDetails.setProfileChanges("Unapproved CEG");
		}
		else
		{
			dashboardDetails.setProfileChanges("What goes here?");
		}
		
		// Num Issues needed for both buyer and provider
		dashboardDetails.setNumIssues(-444);
		
		logger.info("Returning dashboard info... end getDashBoardWidgetDetails in DashboardDisplayBO.");
		return dashboardDetails;
	}


	public SODashboardVO getApprovedUnapprovedCountsAndFirmStatus(Integer vendorId)throws BusinessServiceException{
		SODashboardVO soDashboardVO = new SODashboardVO();
		List<VendorResource> result = null;
		String firmStatus = null;
		int approvedCount = 0;
		int unApprovedCount = 0;
		// sl-19667 - show background check status count in Dashboard
		HashMap<String, Integer> bcCountList = new HashMap<String, Integer>();

		try {
			if(vendorId != null){
				result = getVendorHdrDao().getResourceAndStatus(vendorId);
				firmStatus = getVendorHdrDao().getVendorCurrentStatus(vendorId);
				bcCountList=getVendorHdrDao().getBcCount(vendorId);
				
			}
			
			if(result != null){
				for(int i=0;i<result.size();i++){
					VendorResource vendorResource = result.get(i);					
					if(vendorResource != null){
						if(vendorResource.getWfStateId() != null
								&& vendorResource.getWfStateId().intValue() 
									!= AdminConstants.TEAM_MEMBER_APPROVED_ID.intValue()){
							unApprovedCount += 1;
						}else{
							approvedCount += 1;
						}
					}	
				}
			}
			soDashboardVO.setNumTechniciansApproved(approvedCount);
			soDashboardVO.setNumTechniciansUnapproved(unApprovedCount);
			soDashboardVO.setFirmStatus(firmStatus);

			// sl-19667 - show background check status count in Dashboard
			soDashboardVO.setBcClear(bcCountList.get("Clear"));
			soDashboardVO.setBcInProcess(bcCountList.get("In Process"));
			soDashboardVO.setBcPendingSubmission(bcCountList.get("Pending Submission"));
			soDashboardVO.setBcNotCleared(bcCountList.get("Not Cleared"));
			soDashboardVO.setBcRecertificationDue(bcCountList.get("RecertificationDue"));
			soDashboardVO.setBcNotStarted(bcCountList.get("Not Started"));
			
			
			
		} catch (DBException e) {
			logger.error("[DataServiceException] Error getting resource and status" + e.getMessage());
            throw new BusinessServiceException("[DataServiceException] Error  getting resource and status", e);
		}
		return soDashboardVO;

	}	
	
	private HashMap<String, Integer> getCountsInfo(Integer entityTypeId, AjaxCacheVO vendorInfo) throws BusinessServiceException{
		// States counts info:
		HashMap<String, Integer> soOrderCountsList = new HashMap<String, Integer>();
		try{
			if (entityTypeId.intValue() == OrderConstants.BUYER_ROLEID){//3 is buyer, 1 is provider)
				soOrderCountsList = sodao.getSummaryCountsBuyer(vendorInfo);
			} else if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID){
				soOrderCountsList = sodao.getSummaryCountsProvider(vendorInfo);
			} else if (entityTypeId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID){
				soOrderCountsList = sodao.getSummaryCountsSimpleBuyer(vendorInfo);
			}
		}catch (DataServiceException dse){
			logger.info("Could not retrieve summary counts in getCountsInfo - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve summary counts in getCountsInfo - database error. " , dse);
		}
		return soOrderCountsList;
	}

	
	private List<LeadVO> getDateForMatchedStatus(Integer vendorId) throws BusinessServiceException{
		// States counts info:
		List<LeadVO> dateList=new ArrayList<LeadVO>();
		
		try{
		
			dateList = vendorHdrDao.getDateForMatchedStatus(vendorId);
		
		}catch (Exception dse){
			logger.info("Could not retrieve summary counts in getCountsInfo - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve summary counts in getCountsInfo - database error. " , dse);
		}
		return dateList;
	}
	
	private HashMap<String, Integer> getLeadCountsInfo(Integer vendorId) throws BusinessServiceException{
		// States counts info:
		HashMap<String, Integer> leadCountsList = new HashMap<String, Integer>();
		try{
		
			leadCountsList = vendorHdrDao.getLeadSummaryCountsProvider(vendorId);
		
		}catch (Exception dse){
			logger.info("Could not retrieve summary counts in getCountsInfo - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve summary counts in getCountsInfo - database error. " , dse);
		}
		return leadCountsList;
	}
	
	
	
	private HashMap<String, Integer> getStaleInfoCount(Integer vendorId,String staleAfter) throws BusinessServiceException{
		// States counts info:
		HashMap<String, Integer> leadCountsList = new HashMap<String, Integer>();
		try{
		
			leadCountsList = vendorHdrDao.getStaleInfoCount(vendorId,staleAfter);
		
		}catch (Exception dse){
			logger.info("Could not retrieve summary counts in getCountsInfo - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve summary counts in getCountsInfo - database error. " , dse);
		}
		return leadCountsList;
	}
	
	
	private HashMap<String, Integer> getBuyerLeadCountsInfo(Integer buyerId) throws BusinessServiceException{
		// States counts info:
		HashMap<String, Integer> leadCountsList = new HashMap<String, Integer>();
		try{
		
			leadCountsList = vendorHdrDao.getLeadSummaryCountsBuyer(buyerId);
		
		}catch (Exception dse){
			logger.info("Could not retrieve summary counts in getCountsInfo - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve summary counts in getCountsInfo - database error. " , dse);
		}
		return leadCountsList;
	}
	
	private  Integer getStaleInfo(Integer vendorId,String staleAfter) {
		// States counts info:
		Integer count=null;
		try{
		
			count = vendorHdrDao.getStaleInfo(vendorId, staleAfter);
		
		}catch (Exception dse){
			logger.info("Could not retrieve Stale information ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			
		}
		return count;
	}

	private ReceivedAmountVO getReceivedAmount(AjaxCacheVO vendorInfo) throws BusinessServiceException{
		// Received Dollar amount:
		ReceivedAmountVO receivedAmt = null;
		try{
			receivedAmt = provider.getReceivedAmount(vendorInfo);
		}catch (DataServiceException dse){
			logger.info("Could not retrieve received amount in getReceivedAmount - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve received amount in getReceivedAmount - database error. " , dse);
		}
		return receivedAmt;
	}


	private SurveyRatingsVO getRatingsInfo(String entityId, String resourceId, Integer entityTypeId) throws BusinessServiceException{
		// Rating info:
		SurveyRatingsVO surveyRatings = null;
		int entTypeId = 0;
		if (entityTypeId != null){
			entTypeId = entityTypeId.intValue();
		}
	
		try{
			surveyRatings = survey.getRatings(entTypeId,
					((entityId != null)? Integer.parseInt(entityId) : 0), 
					((resourceId != null)? Integer.parseInt(resourceId) : 0));
		}catch(DataServiceException dse){
			logger.info("Could not retrieve ratings info in getRatingInfo - database error. ",  dse);
			dse.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not retrieve ratings info in getRatingInfo - database error. " , dse);
		}catch(NumberFormatException nfe){
			logger.info("Could not parse the string into a numeric in getRatingsInfo. " +
					"Check entityId and resourceId to make sure there only numeric characters.",  nfe);
			nfe.printStackTrace();
			// throw appropriate exception here...
			throw new BusinessServiceException("Could not parse the string into a numeric in getRatingsInfo. " +
					"Check entityId and resourceId to make sure there only numeric characters.",  nfe);
		}
		return surveyRatings;
	}
	
	//SLT-2235:
	public boolean isLegalNoticeChecked(Integer vendorId)throws BusinessServiceException{
			
		try {
				return vendorHdrDao.isLegalNoticeChecked(vendorId);
					
		} catch (DataServiceException e) {
					logger.error("Exception in isLegalNoticeChecked( ) method"+e.getMessage());
					throw new BusinessServiceException("Exception in isLegalNoticeChecked( ) method"+e);
		}
	}
	/** @return ILedgerFacilityBO */
	public ILedgerFacilityBO getAccTranMan() {
		return accTranMan;
	}
	/** @param accTranMan */
	public void setAccTranMan(ILedgerFacilityBO accTranMan) {
		this.accTranMan = accTranMan;
	}
	/** @return ISurveyBO */
	public ISurveyBO getSurvey() {
		return survey;
	}
	/** @param survey */
	public void setSurvey(ISurveyBO survey) {
		this.survey = survey;
	}
	/** @return ProviderDao */
	public ProviderDao getProvider() {
		return provider;
	}
	/** @param provider */
	public void setProvider(ProviderDao provider) {
		this.provider = provider;
	}
	/** @return ServiceOrderDao */
	public ServiceOrderDao getSodao() {
		return sodao;
	}
	/** @param sodao */
	public void setSodao(ServiceOrderDao sodao) {
		this.sodao = sodao;
	}


	/**
	 * @return the vendorHdrDao
	 */
	public IVendorHdrDao getVendorHdrDao() {
		return vendorHdrDao;
	}


	/**
	 * @param vendorHdrDao the vendorHdrDao to set
	 */
	public void setVendorHdrDao(IVendorHdrDao vendorHdrDao) {
		this.vendorHdrDao = vendorHdrDao;
	}


	public String getStaleAfter() {
		return staleAfter;
	}


	public void setStaleAfter(String staleAfter) {
		this.staleAfter = staleAfter;
	}


	public String getGoal() {
		return goal;
	}


	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	
	
	
}

