package com.newco.marketplace.web.delegatesImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.cache.ICacheManagerBO;
import com.newco.marketplace.business.iBusiness.dashboard.IDashboardDisplayBO;
import com.newco.marketplace.business.iBusiness.provider.ILoginBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.cache.BuyerCachedResult;
import com.newco.marketplace.dto.vo.cache.ProviderCachedResult;
import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.web.delegates.ISODashBoardDelegate;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;


/**
 * @author z
 *
 */ 
public class SODashBoardDelegateImpl implements ISODashBoardDelegate {
	
	private static final Logger logger = Logger.getLogger(SODashBoardDelegateImpl.class.getName());
	
	private IDashboardDisplayBO dashboardDisplay;
	private ICacheManagerBO cacheManager;
	private ILoginBO loginBO;

	
	public ILoginBO getLoginBO() {
		return loginBO;
	}

	public void setLoginBO(ILoginBO loginBO) {
		this.loginBO = loginBO;
	}

	/** (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODashBoardDelegate#getDashBoardWidgetDetails(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	
	public boolean isIncompleteProvider(Integer vendorId) {
		
		try {
			System.out.println("getting vendor details");
			VendorHdr aVendorHdr =      loginBO.getVendorDetails(vendorId + "");
			//TODO  Make this one thing a constant please!
			//logger.info("I think the verndor is like this:"+ aVendorHdr.getVendorStatusId() +":"+aVendorHdr.getVendorId());
			//System.out.println("I am really calling th isIncompleteProvider method");
			
			if(aVendorHdr != null && aVendorHdr.getVendorStatusId().intValue() == 1){
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			System.out.println("exception vendor details");
			e.printStackTrace();
			logger.debug(" Was Unable to retrieve vendor data from isIncompleteProvider defaulting to incomplete",e);
			return true;
		}
		
	}
	
	public SODashboardVO getDashBoardWidgetDetails(String entityId,
			String resourceId, Integer entityTypeId, SurveyRatingsVO vo, boolean manageSoFlag,boolean leadFlag) {
		SODashboardVO dashData = null;
		try{
			dashData = dashboardDisplay.getDashBoardWidgetDetails(entityId, resourceId, entityTypeId, vo, manageSoFlag,leadFlag);
		}catch (BusinessServiceException bse){
			logger.info("Problem retrieving dashboard data. ",  bse);
			bse.printStackTrace();
			// throw appropriate exception here ...  which one??
		}
		return dashData;
	}
 
	/** (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ISODashBoardDelegate#getDashBoardWidgetDetailsCache(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public SODashboardVO getDashBoardWidgetDetailsCache(String entityId,
			String resourceId, Integer entityTypeId){
		SODashboardVO dto = new SODashboardVO();
		
		// set up ajaxCacheVO
		AjaxCacheVO ajaxCacheVO = new AjaxCacheVO();
		ajaxCacheVO.setCompanyId((entityId != null)? new Integer(Integer.parseInt(entityId)) : new Integer(-1));
		if (entityTypeId.intValue() == 3){
			ajaxCacheVO.setRoleType(OrderConstants.BUYER_ROLE);
		}else if (entityTypeId.intValue() == 1){
			ajaxCacheVO.setRoleType(OrderConstants.PROVIDER_ROLE);
		}
		ajaxCacheVO.setVendBuyerResId((resourceId != null)? new Integer(Integer.parseInt(resourceId)) : new Integer(-1));
		
		if (entityTypeId.intValue() == OrderConstants.PROVIDER_ROLEID){ //provider
			ProviderCachedResult providerResult = null;
			try{
				providerResult = (ProviderCachedResult)cacheManager.getDashboardData(ajaxCacheVO);
			} 
			catch (BusinessServiceException bse){
				logger.error("getDashBoardWidgetDetailsCache()-->EXCEPTION-->", bse);
			}
			
			if(providerResult != null){
				dto.setAvailableBalance(providerResult.getAvailableBalance());
				dto.setTotalDollars(providerResult.getTotalReceivedAmount());
				//HashMap<String, Integer> summaryCounts = providerResult.getSummaryCount();
				
				if(providerResult.getSummaryCount() != null){
					dto.setAccepted(providerResult.getSummaryCount().get(OrderConstants.TAB_ACCEPTED));
					dto.setProblem(providerResult.getSummaryCount().get(OrderConstants.TAB_PROBLEM));
					dto.setReceived(providerResult.getSummaryCount().get("RECEIVED"));
					dto.setTodays(providerResult.getSummaryCount().get("TODAY"));
					dto.setTotalOrders(providerResult.getSummaryCount().get("RECEIVED"));
				}
				providerResult = null;
				//summaryCounts = null;
			}


		}
		if (entityTypeId.intValue() == OrderConstants.BUYER_ROLEID){ //buyer
			
			BuyerCachedResult buyerResult = null;
			try{
				buyerResult = (BuyerCachedResult)cacheManager.getDashboardData(ajaxCacheVO);
			} 
			catch (BusinessServiceException bse){
				logger.error("getDashBoardWidgetDetailsCache()-->EXCEPTION-->", bse);
			}
			if(buyerResult != null){
				dto.setAvailableBalance(buyerResult.getAvailableBalance());
				dto.setCurrentBalance(buyerResult.getCurrentBalance());
				logger.debug("Available Balance: " + buyerResult.getAvailableBalance());
				logger.debug("Current Balance: " + buyerResult.getCurrentBalance());
				//HashMap<String, Integer> summaryCounts = buyerResult.getSummaryCount();
				if(buyerResult.getSummaryCount() != null){
					dto.setAccepted(buyerResult.getSummaryCount().get(OrderConstants.TAB_ACCEPTED));
					dto.setDrafted(buyerResult.getSummaryCount().get("DRAFT"));
					dto.setPosted(buyerResult.getSummaryCount().get("SENT"));
					dto.setProblem(buyerResult.getSummaryCount().get(OrderConstants.TAB_PROBLEM));
					dto.setTodays(buyerResult.getSummaryCount().get("TODAY"));
				}
				buyerResult = null;
				//summaryCounts = null;
			}
		}
		ajaxCacheVO = null;		//null out to clean object
		// Num Issues needed for both buyer and provider
		dto.setNumIssues(0);
		
		return dto;
	}
	
	public SODashboardVO getApprovedUnapprovedCountsAndFirmStatus(Integer vendorId){
		SODashboardVO soDashboardVO = null; 
		try {
			soDashboardVO = getDashboardDisplay().getApprovedUnapprovedCountsAndFirmStatus(vendorId);
		} catch (BusinessServiceException e) {
			logger.error("Error in SODashboardDelegateImpl --> getApprovedAndUnapprovedResourcesCount()"
					+" retreving the counts");
		}
		return soDashboardVO;
	}
	
	//SL-17698: method to fetch show_member_offers_ind from vendor_resource
	public Integer showMemberOffers(Integer vendorId){
		Integer indicator = loginBO.showMemberOffers(vendorId);
		return indicator;
	}
	
	public boolean isNonFundedBuyer(String companyId) {
		Integer buyerId = null;
		if(StringUtils.isNotBlank(companyId)&& StringUtils.isNumeric(companyId)){
			buyerId =Integer.parseInt(companyId);
		}
		boolean isNonFunded =loginBO.isNonFundedBuyer(buyerId);
		return isNonFunded;
	}
	//SL-19293: method to fetch new lead T&C indicator
	public Integer showLeadsTCIndicator(Integer vendorId){
		Integer indicator = loginBO.showLeadsTCIndicator(vendorId);
		return indicator;
	}
	//Added for D2C provider permission
	public String buyerSkuPrimaryIndustry(Integer vendorId){
		return loginBO.buyerSkuPrimaryIndustry(vendorId);
	}
	
	//checks whether provider has Manage Business Profile Permission
	public int getPermission(Integer resourceId){
		return loginBO.getPermission(resourceId);
	}
	
	//checks whether provider has any un-archived CAR rules
	public int getUnarchivedCARRulesCount(Integer vendorId){
		return loginBO.getUnarchivedCARRulesCount(vendorId);
	}
	
	//checks whether provider has any active pending CAR rules
	public int getActivePendingCARRulesCount(Integer vendorId){
		return loginBO.getActivePendingCARRulesCount(vendorId);
	}
	
	//checks whether vendor has Leads accnt	
	public Integer showLeadsSignUp(Integer vendorId){
		return loginBO.showLeadsSignUp(vendorId);
	}
	
	//SL-19293 New T&C- START
	//SL 19293 update new T&C indicator
	public void updateNewTandC(Integer vendorId, String userName){
		loginBO.updateNewTandC(vendorId, userName);
	}
	//SL-19293 New T&C- END
	/** @return IDashboardDisplayBO */
	public IDashboardDisplayBO getDashboardDisplay() {
		return dashboardDisplay;
	}
	/** @param dashboardDisplay */
	public void setDashboardDisplay(IDashboardDisplayBO dashboardDisplay) {
		this.dashboardDisplay = dashboardDisplay;
	}
	/** @return ICacheManagerBO */
	public ICacheManagerBO getCacheManager() {
		return cacheManager;
	}
	/** @param cacheManager */
	public void setCacheManager(ICacheManagerBO cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	//SLT-2235 starts
	public boolean isLegalNoticeChecked(Integer vendorId) throws BusinessServiceException{
		try {
			return dashboardDisplay.isLegalNoticeChecked(vendorId);
		} catch (BusinessServiceException e) {
			logger.error("Exception in isLegalNoticeChecked() method: "+e.getMessage());
			throw new BusinessServiceException("[SODashboardDelegateImpl--> isLegalNoticeChecked() --> Exception]",e);

		}
	}

}
