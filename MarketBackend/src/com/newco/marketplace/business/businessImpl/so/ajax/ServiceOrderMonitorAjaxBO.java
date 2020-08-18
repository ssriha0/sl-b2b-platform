package com.newco.marketplace.business.businessImpl.so.ajax;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.business.businessImpl.so.order.BaseOrderBO;
import com.newco.marketplace.business.iBusiness.cache.ICacheManagerBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOAjaxBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.ajax.SOToken;
import com.newco.marketplace.dto.vo.cache.CachedResultVO;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderSearchDAO;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;

public class ServiceOrderMonitorAjaxBO extends BaseOrderBO implements ISOAjaxBO {

	private ServiceOrderDao serviceOrderDao;
	private IServiceOrderSearchDAO soSearchDAO;
	private ICacheManagerBO cacheManagerBO;
	
	public ICacheManagerBO getCacheManagerBO() {
		return cacheManagerBO;
	}

	public void setCacheManagerBO(ICacheManagerBO cacheManagerBO) {
		this.cacheManagerBO = cacheManagerBO;
	}

	@Override
	public IServiceOrderSearchDAO getSoSearchDAO() {
		return soSearchDAO;
	}

	@Override
	public void setSoSearchDAO(IServiceOrderSearchDAO soSearchDAO) {
		this.soSearchDAO = soSearchDAO;
	}

	@Override
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	@Override
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	
	private String getBuyerAdminDetailedCounts(AjaxCacheVO ajaxCacheVO){
		String xmlString = "";
		
		try {
			if (ajaxCacheVO.getCompanyId()>-1){
				//Use the cache mgr impl
				CachedResultVO cachedResult = null;
				cachedResult =  cacheManagerBO.getDetailedCounts(ajaxCacheVO);

				ArrayList<SOToken> soTokens = new ArrayList<SOToken>();
				soTokens = ajaxCacheVO.getSoTokenList();
				
				AjaxCacheVO acVO = new AjaxCacheVO();
				acVO = compareSOTokensBuyer(soTokens, cachedResult);
				
				acVO.setSoTokenList(acVO.getSoTokenList());
				
				xmlString = createDetailedCountsXMLBuyer(acVO);
			}
		}catch (Throwable t3) {
			logger.error("SOMAjaxBO:getBuyerCounts:: error: " + t3.getMessage(), t3);
		}
		return xmlString;	
	}
	

	private String getProviderAdminDetailedCounts(AjaxCacheVO ajaxCacheVO){
		String xmlString = "";
		
		try {
			if (ajaxCacheVO.getCompanyId()>-1){
				//Use the cache mgr impl
				CachedResultVO cachedResult = null;
				cachedResult =  cacheManagerBO.getDetailedCounts(ajaxCacheVO);

				ArrayList<SOToken> soTokens = new ArrayList<SOToken>();
				soTokens = ajaxCacheVO.getSoTokenList();
				
				AjaxCacheVO acVO = new AjaxCacheVO();
				acVO = compareSOTokensProvider(soTokens, cachedResult);
				
				acVO.setSoTokenList(acVO.getSoTokenList());
				
				xmlString = createDetailedCountsXMLProvider(acVO);
			}
		}catch (Throwable t3) {
			logger.error("SOMAjaxBO:getBuyerCounts:: error: " + t3.getMessage(), t3);
		}
		return xmlString;	
	}

	
	public String getDetailedCounts(AjaxCacheVO ajaxCacheVO){
		String xmlString = "";

		if (ajaxCacheVO.getRoleType().equalsIgnoreCase("buyer")){
			xmlString = this.getBuyerAdminDetailedCounts(ajaxCacheVO);
		}else if (ajaxCacheVO.getRoleType().equalsIgnoreCase("provider")){
			xmlString = this.getProviderAdminDetailedCounts(ajaxCacheVO);
		}
		
		return xmlString;
	}
	
	public String getSummaryCounts(AjaxCacheVO ajaxCacheVO){
		String xmlString = "";
		
		if (ajaxCacheVO.getRoleType().equalsIgnoreCase("buyer")){
			xmlString = this.getBuyerAdminCounts(ajaxCacheVO);
		}else if (ajaxCacheVO.getRoleType().equalsIgnoreCase("provider")){
			xmlString = this.getProviderAdminCounts(ajaxCacheVO);
		}
		
		return xmlString; 
	}
	

	private String getBuyerAdminCounts(AjaxCacheVO ajaxCacheVO) {
		String xmlString = "";
		
		try {

			String roleType = "BUYER";
			//Setting this here b/c the cache compare for BUYER vs. PROVIDER is doing a String compare
			//against it 
			ajaxCacheVO.setRoleType(roleType);
			int previousRoutedCount = -1;
			
			//addTokenIds = ajaxCacheVO.getSetTokens() ;
			
			
			HashMap tabCountList = new HashMap();
			
			//Change this to the summary Counts buyer of the cacheimplementation
			//tabCountList = (HashMap) serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVO);
			CachedResultVO cachedResult = null;

			cachedResult =  cacheManagerBO.getSummaryCounts(ajaxCacheVO);
			
			if (ajaxCacheVO.getRoutedTabCount()!=null){
				previousRoutedCount = ajaxCacheVO.getRoutedTabCount();
			}
			if (cachedResult!=null){
				xmlString = createSummaryCountsXML(cachedResult,previousRoutedCount,roleType);
			}
			
		}catch (Throwable t3) {
			logger.error("SOMAjaxBO:getBuyerCounts:: error: " + t3.getMessage(), t3);
		}
		return xmlString;
	}
	
	private String createDetailedCountsXMLBuyer(AjaxCacheVO acVO){
		//String xmlString = "";

		StringBuilder sb = new StringBuilder();

		//If the resubmitGrid() is true bypass the for loop and add the else to just return the resubmit tag
		if (acVO.getResubmitGrid()){
			sb.append("<tab_resubmit>true</tab_resubmit>");
		}else{
			int i = 0;
		
			//If the hasDataChanged() true then add the details
			if (acVO.getDataChanged() == true){
				//Tell the call back function to clear the cache
					sb.append("<clear_cache>true</clear_cache>");
					
				for (java.util.Iterator it = acVO.getSoTokenList().iterator(); it.hasNext();){
					SOToken detailedCounts = (SOToken) it.next();
					
					sb.append("<pCount_" + detailedCounts.getSoId() + ">" + detailedCounts.getRoutedCount() + "</pCount_"+ detailedCounts.getSoId() + ">");
					sb.append("<cCount_" + detailedCounts.getSoId() + ">" + detailedCounts.getCondAcceptCount() + "</cCount_"+ detailedCounts.getSoId() + ">");
					sb.append("<rCount_" + detailedCounts.getSoId() + ">" + detailedCounts.getRejectedCount() + "</rCount_"+ detailedCounts.getSoId() + ">");
					sb.append("<sCount_" + detailedCounts.getSoId() + ">" + java.text.NumberFormat.getCurrencyInstance().format(detailedCounts.getSpendLimit()) + "</sCount_"+ detailedCounts.getSoId() + ">");
					sb.append("<soidno_" + detailedCounts.getSoId() + ">" + detailedCounts.getSoId() + "</soidno_" +detailedCounts.getSoId()+ ">");
					
					i = i+1;
				}
			}
		}

		//xmlString = sb.toString();
		
		return sb.toString();
	}
	
	private String createDetailedCountsXMLProvider(AjaxCacheVO acVO){
		//String xmlString = "";

		StringBuilder sb = new StringBuilder();

		//If the resubmitGrid() is true bypass the for loop and add the else to just return the resubmit tag
		if (acVO.getResubmitGrid()){
			sb.append("<tab_resubmit>true</tab_resubmit>");
		}else{
			int i = 0;
		
			//If the hasDataChanged() true then add the details
			if (acVO.getDataChanged() == true){
				//Tell the call back function to clear the cache
					sb.append("<clear_cache>true</clear_cache>");
					
				for (java.util.Iterator it = acVO.getSoTokenList().iterator(); it.hasNext();){
					SOToken detailedCounts = (SOToken) it.next();
					
					sb.append("<sCount_" + detailedCounts.getSoId() + ">" + java.text.NumberFormat.getCurrencyInstance().format(detailedCounts.getSpendLimit()) + "</sCount_"+ detailedCounts.getSoId() + ">");
					sb.append("<soidno_" + detailedCounts.getSoId() + ">" + detailedCounts.getSoId() + "</soidno_" +detailedCounts.getSoId()+ ">");
					
					i = i+1;
				}
			}
		}

		//xmlString = sb.toString();
		
		return sb.toString();
	}
	
	private String createSummaryCountsXML( CachedResultVO cachedResult, int previousRoutedCount, String roleType){
		String xmlString = "";
		int todayCount = 0;
		int draftCount = 0;
		int routedCount = 0;
		int acceptedCount = 0;
		int problemCount = 0;
		int inactiveCount = 0;
		
		if (roleType.equalsIgnoreCase("buyer")){
			todayCount = Integer.parseInt(cachedResult.getSummaryCount().get("TODAY").toString());
			draftCount = Integer.parseInt(cachedResult.getSummaryCount().get("DRAFT").toString());
			routedCount  = Integer.parseInt(cachedResult.getSummaryCount().get("SENT").toString());
			acceptedCount = Integer.parseInt(cachedResult.getSummaryCount().get("ACCEPTED").toString());
			problemCount = Integer.parseInt(cachedResult.getSummaryCount().get("PROBLEM").toString());
			inactiveCount = Integer.parseInt(cachedResult.getSummaryCount().get("INACTIVE").toString());
		}else if (roleType.equalsIgnoreCase("provider")){
			todayCount = Integer.parseInt(cachedResult.getSummaryCount().get("TODAY").toString());
			routedCount  = Integer.parseInt(cachedResult.getSummaryCount().get("RECEIVED").toString());
			acceptedCount = Integer.parseInt(cachedResult.getSummaryCount().get("ACCEPTED").toString());
			problemCount = Integer.parseInt(cachedResult.getSummaryCount().get("PROBLEM").toString());
			inactiveCount = Integer.parseInt(cachedResult.getSummaryCount().get("INACTIVE").toString());
		}
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("<tab_today>" + todayCount + "</tab_today>");
		if (roleType.equalsIgnoreCase("buyer")){
			sb.append("<tab_draft>" + draftCount + "</tab_draft>");
			sb.append("<tab_posted>" + routedCount + "</tab_posted>");
		}
		
		if(roleType.equalsIgnoreCase("provider")){
			sb.append("<tab_received>" + routedCount + "</tab_received>");
		}
		
		sb.append("<tab_accepted>" + acceptedCount + "</tab_accepted>");
		sb.append("<tab_problem>" + problemCount + "</tab_problem>");
		sb.append("<tab_inactive>" + inactiveCount + "</tab_inactive>");
		//Commented since sent_Changed flag is set to true and it always refreshes the grid 
		
		if (previousRoutedCount > -1){
			if (previousRoutedCount != routedCount){
				//System.out.println ("Previous Routed Count: " + previousRoutedCount + " RoutedCount: " + routedCount);
				//Add this element b/c we know that the counts changed on the
				//backend
				sb.append("<sent_changed>" + routedCount + "</sent_changed>");
			}
		}
		
		xmlString = sb.toString();
		
		return xmlString;
	}
	
	
	private String getProviderAdminCounts(AjaxCacheVO ajaxCacheVO) {
		String xmlString = "";
		
		try {

			String roleType = "PROVIDER";
			//Setting this here b/c the cache compare for BUYER vs. PROVIDER is doing a String compare
			//against it 
			ajaxCacheVO.setRoleType(roleType);
			int previousRoutedCount = -1;
			
			
			if (ajaxCacheVO.getRoutedTabCount()!=null){
				previousRoutedCount = ajaxCacheVO.getRoutedTabCount();
			}
			
			CachedResultVO cachedResult = null;

			cachedResult =  cacheManagerBO.getSummaryCounts(ajaxCacheVO);
			
			xmlString = createSummaryCountsXML(cachedResult,previousRoutedCount,roleType);
			
		}catch (Throwable t3) {
			logger.error("SOMAjaxBO:getVendorCounts:: error: " + t3.getMessage(), t3);
		}
		return xmlString;
	}

	private AjaxCacheVO compareSOTokensBuyer(List<SOToken> list, CachedResultVO cachedResult ){
		AjaxCacheVO ajaxVO = new AjaxCacheVO();
		ArrayList <SOToken> soTokens = new ArrayList<SOToken>();
		SOToken singleToken =null;
		
		int cachedCondAcceptCnt = 0;
		int cachedRejectCnt = 0;
		int cachedRoutedCnt = 0;
		double cachedSpendLmtCnt = 0;
		boolean hasChanged = false;
		
		if (list != null){
			for (int i = 0; i < list.size(); i++) {
				//1. get the soId from the SOToken list.get(i).getSoId();
				singleToken = new SOToken();
				String soId = list.get(i).getSoId().trim();	
				
				
				if (null != cachedResult && null != cachedResult.getDetailedCount() && null != cachedResult.getDetailedCount().get(soId)){
					//The soId exists in the cache move on
					cachedCondAcceptCnt = cachedResult.getDetailedCount().get(soId).getConditionalAccept();
					cachedRejectCnt = cachedResult.getDetailedCount().get(soId).getRejected();
					cachedRoutedCnt = cachedResult.getDetailedCount().get(soId).getSentProviders();
					cachedSpendLmtCnt = cachedResult.getDetailedCount().get(soId).getSpendLimit();
					
					//By setting hasChanged = true, we then tell the ajax callback function that
					//we need to update the document ... we will append the details xml in the document so that the
					//callback function knows to traverse through the xml document and update the records on the grid
					if (cachedCondAcceptCnt != list.get(i).getCondAcceptCount()){
						hasChanged = true;
					}
					if (cachedRoutedCnt != list.get(i).getRoutedCount()){
						hasChanged = true;
					}
					if (cachedRejectCnt != list.get(i).getRejectedCount()){
						hasChanged = true;
					}

					if (cachedSpendLmtCnt != list.get(i).getSpendLimit()){
						hasChanged = true;
					}

					singleToken.setSoId(soId);
					singleToken.setCondAcceptCount(cachedCondAcceptCnt);
					singleToken.setRejectedCount(cachedRejectCnt);
					singleToken.setRoutedCount(cachedRoutedCnt);
					singleToken.setSpendLimit(cachedSpendLmtCnt);
	
					//Add the token to the Tokens List
					soTokens.add(singleToken);
				}else{
					//The soId does not exist in the cache.  Exit out of this loop and method.  Put the <tab_resubmit></tab_resubmit> tag
					//on the xml document.  Do not add the details tag to the document
					ajaxVO.setResubmitGrid(true);
					return ajaxVO;
				}
			}
		}
		
		if (hasChanged){
			ajaxVO.setDataChanged(true);
		}else{
			ajaxVO.setDataChanged(false);
		}
		
		ajaxVO.setSoTokenList(soTokens);
		
		return ajaxVO;
	}

	
	private AjaxCacheVO compareSOTokensProvider(List<SOToken> list, CachedResultVO cachedResult ){
		AjaxCacheVO ajaxVO = new AjaxCacheVO();
		ArrayList <SOToken> soTokens = new ArrayList<SOToken>();
		SOToken singleToken =null;
		
		double cachedSpendLmtCnt = 0.0;
		boolean hasChanged = false;
		
		if (list != null){
			for (int i = 0; i < list.size(); i++) {
				//1. get the soId from the SOToken list.get(i).getSoId();
				singleToken = new SOToken();
				String soId = list.get(i).getSoId().trim();	
				
				
				if (cachedResult.getDetailedCount().get(soId) != null){
					//The soId exists in the cache move on
					
					
					cachedSpendLmtCnt = cachedResult.getDetailedCount().get(soId).getSpendLimit();
					
					
					//By setting hasChanged = true, we then tell the ajax callback function that
					//we need to update the document ... we will append the details xml in the document so that the
					//callback function knows to traverse through the xml document and update the records on the grid

					if (cachedSpendLmtCnt != list.get(i).getSpendLimit()){
						hasChanged = true;
					}

					singleToken.setSoId(soId);
					singleToken.setSpendLimit(cachedSpendLmtCnt);
	
					//Add the token to the Tokens List
					soTokens.add(singleToken);
				}else{
					//The soId does not exist in the cache.  Exit out of this loop and method.  Put the <tab_resubmit></tab_resubmit> tag
					//on the xml document.  Do not add the details tag to the document
					ajaxVO.setResubmitGrid(true);
					return ajaxVO;
				}
			}
		}
		
		if (hasChanged){
			ajaxVO.setDataChanged(true);
		}else{
			ajaxVO.setDataChanged(false);
		}
		
		ajaxVO.setSoTokenList(soTokens);
		
		return ajaxVO;
	}


}
