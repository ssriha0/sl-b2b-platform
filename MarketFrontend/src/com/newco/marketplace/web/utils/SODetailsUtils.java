package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.SODetailsTabDTO;

public class SODetailsUtils  {

	//TODO, eventually change status parameter to an int
	public static  ArrayList<SODetailsTabDTO> setTabs(String role, Integer status, String selectedID, String priceModel, boolean isAdmin,boolean viewOrderPricing,String estimationId,String isEstimationRequest)
	{
		
		if("provider".equalsIgnoreCase(role))
		{
			return getProviderTabs(status, selectedID, priceModel, isAdmin,viewOrderPricing,estimationId,isEstimationRequest);
		}
		else if("buyer".equalsIgnoreCase(role) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(role))
		{
			return getBuyerTabs(status, selectedID,estimationId,isEstimationRequest);
		}
		return null;
	}
	
	public static  ArrayList<SODetailsTabDTO> setTabs(String role, Integer status, String selectedID, HashMap hm, String priceModel,boolean viewOrderPricing,String estimateId,String isEstimateRequest)
	{
		
		if("provider".equalsIgnoreCase(role))
		{
			return getProviderTabs(status, selectedID, hm, priceModel,viewOrderPricing,estimateId,isEstimateRequest);
		}
		else if("buyer".equalsIgnoreCase(role) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(role))
		{
			return getBuyerTabs(status, selectedID, hm,estimateId,isEstimateRequest);
		}
		
		//TODO, handle error/exception
		return null;
	}

	private static  ArrayList<SODetailsTabDTO> getProviderTabs(Integer status, String selectedID, HashMap hm, String priceModel,boolean viewOrderPricing,String estimateId,String isEstimateRequest)
	{
		ArrayList<SODetailsTabDTO> tabList = new ArrayList<SODetailsTabDTO>();
		SODetailsTabDTO tab;

		if(Constants.PriceModel.NAME_PRICE.equals(priceModel))
		{
			if(status != null && status.intValue() == OrderConstants.CLOSED_STATUS)
			{
				tab = new SODetailsTabDTO(ID_SUMMARY,  selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
				tabList.add(tab);
				tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES + generateUniqueURLPart());
				tabList.add(tab);
				if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
				
				if(hm.get(ID_VIEW_RATINGS_NEW) != null) {
					if((Boolean)hm.get(ID_VIEW_RATINGS_NEW)) {
						tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS_NEW);
						tabList.add(tab);
					}
				} 
				else if(hm.get(ID_VIEW_RATINGS) != null) 
				{
					if((Boolean)hm.get(ID_VIEW_RATINGS)) {
						tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS);
						tabList.add(tab);
					}
				}
				else
				{
					tab = new SODetailsTabDTO(ID_RATE_BUYER,  selectedID, TAB_RATE_BUYER);
					tabList.add(tab);
				}
				
				
				
				tab = new SODetailsTabDTO(ID_COMPLETION_RECORD,  selectedID, TAB_PROV_COMP_RECORD);
				tabList.add(tab);
				
				if(status != null && status.intValue() != OrderConstants.DRAFT_STATUS && status.intValue() != OrderConstants.ROUTED_STATUS)
				{
					if(viewOrderPricing){
						tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
						tabList.add(tab);
					}	
					
				}
				
				tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
				tabList.add(tab);
				
				tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
				tabList.add(tab);
			}
		}
		else if(Constants.PriceModel.ZERO_PRICE_BID.equals(priceModel))
		{
			tab = new SODetailsTabDTO(ID_SUMMARY,  selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES + generateUniqueURLPart());
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			if(hm.get(ID_VIEW_RATINGS_NEW) != null) {
				if((Boolean)hm.get(ID_VIEW_RATINGS_NEW)) {
					tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS_NEW);
					tabList.add(tab);
				}
			}
			else if(hm.get(ID_VIEW_RATINGS) != null)
			{
				if((Boolean)hm.get(ID_VIEW_RATINGS)) {
					tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS);
					tabList.add(tab);
				}
			}
			else
			{
				tab = new SODetailsTabDTO(ID_RATE_BUYER,  selectedID, TAB_RATE_BUYER);
				tabList.add(tab);
			}
			
			tab = new SODetailsTabDTO(ID_COMPLETION_RECORD,  selectedID, TAB_PROV_COMP_RECORD);
			tabList.add(tab);
			
			if(status != null && status.intValue() != OrderConstants.DRAFT_STATUS && status.intValue() != OrderConstants.ROUTED_STATUS)
			{
				if(viewOrderPricing){
					tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
					tabList.add(tab);
				}
				
			}
			
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		return tabList;
	}	
	
	private static  ArrayList<SODetailsTabDTO> getBuyerTabs(Integer status, String selectedID, HashMap hm,String estimateId,String isEstimateRequest)
	{
		ArrayList<SODetailsTabDTO> tabList = new ArrayList<SODetailsTabDTO>();
		SODetailsTabDTO tab;

		if(status != null && status.intValue() == OrderConstants.CLOSED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES + generateUniqueURLPart());
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			if(hm.get(ID_VIEW_RATINGS_NEW) != null) {
				if((Boolean)hm.get(ID_VIEW_RATINGS_NEW)) {
					tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS_NEW);
					tabList.add(tab);
				}
			}
			
			else if(hm.get(ID_VIEW_RATINGS) != null) {
				if((Boolean)hm.get(ID_VIEW_RATINGS)) {
					tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS);
					tabList.add(tab);
				}
			}
			else
			{	
				if(selectedID != null && selectedID.equalsIgnoreCase("Close and Pay"))  // Made for Rate provider
					selectedID="Rate Provider";			         	// Made for Rate provider

				tab = new SODetailsTabDTO(ID_RATE_PROVIDER,  selectedID, TAB_RATE_PROVIDER);
				tabList.add(tab);
			}
			

			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_COMPLETION_RECORD,  selectedID, TAB_BUYER_COMP_RECORD);
			tabList.add(tab);			
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
			
		} else if(status != null && status.intValue() == OrderConstants.COMPLETED_STATUS) {
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES+ generateUniqueURLPart());
			tabList.add(tab);		
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
			}
			
			if(hm.get(ID_VIEW_RATINGS_NEW) != null) {
				if((Boolean)hm.get(ID_VIEW_RATINGS_NEW)) {
					tab = new SODetailsTabDTO(ID_VIEW_RATINGS,  selectedID, TAB_VIEW_RATINGS_NEW);
					tabList.add(tab);
				}
			}
			
			tab = new SODetailsTabDTO(ID_CLOSE_AND_PAY,  selectedID, TAB_CLOSE_AND_PAY);
			tabList.add(tab);			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM,  selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		return tabList;
	}	
	
	
	private static  ArrayList<SODetailsTabDTO> getBuyerTabs(Integer status, String selectedID,String estimateId,String isEstimateRequest)
	{
		ArrayList<SODetailsTabDTO> tabList = new ArrayList<SODetailsTabDTO>();
		SODetailsTabDTO tab;
		
		if(status != null && status.intValue() == OrderConstants.ACCEPTED_STATUS)
		{
		   	
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES + generateUniqueURLPart());
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
					if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);		
		}
		else if(status != null && status.intValue() == OrderConstants.ACTIVE_STATUS)
		{
			
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES + generateUniqueURLPart());
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM,  selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);	
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if(status != null && status.intValue() == OrderConstants.CANCELLED_STATUS)
		{
			
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES + generateUniqueURLPart());
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);			
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if(status != null && status.intValue() == OrderConstants.COMPLETED_STATUS)
		{
			
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES+ generateUniqueURLPart());
			tabList.add(tab);		
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_CLOSE_AND_PAY,  selectedID, TAB_CLOSE_AND_PAY);
			tabList.add(tab);			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM,  selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if(status != null && status.intValue() == OrderConstants.EXPIRED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);	
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);			
		}
		else if(status != null && status.intValue() == OrderConstants.PROBLEM_STATUS)
		{
		
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);	
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);						
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ISSUE_RESOLUTION,  selectedID, TAB_ISSUE_RESOLUTION);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if(status != null && status.intValue() == OrderConstants.ROUTED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_STATUS,  selectedID, TAB_RESPONSE_STATUS,true);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);			
		}
		else if(status != null && status.intValue() == OrderConstants.DRAFT_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);			
		}
		else if(status != null && status.intValue() == OrderConstants.VOIDED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);	
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);					
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);			
		}else if(status != null && status.intValue() == OrderConstants.DELETED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW); 
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);			
		}
		else if(status != null && status.intValue() == OrderConstants.PENDING_CANCEL_STATUS)
		{		
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_DEFAULT_SUMMARY_VIEW);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES,  selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			tab = new SODetailsTabDTO(ID_RESPONSE_HISTORY,  selectedID, TAB_RESPONSE_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_PRICE_HISTORY,  selectedID, TAB_PRICE_HISTORY);
			tabList.add(tab);						
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM,  selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);	
			tab = new SODetailsTabDTO(ID_SUPPORT,  selectedID, TAB_SUPPORT);
			tabList.add(tab);
		}
	
		return tabList;
	}
	
	
	private static  ArrayList<SODetailsTabDTO> getProviderTabs(Integer status, String selectedID, String priceModel, boolean isAdmin,boolean viewOrderPricing,String estimateId,String isEstimateRequest)
	{
		ArrayList<SODetailsTabDTO> tabList = new ArrayList<SODetailsTabDTO>();
		SODetailsTabDTO tab = null;
		
		
		
		if (status != null && status.intValue() == OrderConstants.ACCEPTED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES, selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			if(viewOrderPricing==true)
			{
				tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
				tabList.add(tab);
			}
			tab = new SODetailsTabDTO(ID_SUPPORT, selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if (status != null && status.intValue() == OrderConstants.ACTIVE_STATUS)
		{

			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES, selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			///SL-18869 Display order history and complete for payment tab only when user has permission to view price
			if(viewOrderPricing==true)
			{
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			
			tab = new SODetailsTabDTO(ID_COMPLETE_FOR_PAYMENT, selectedID, TAB_COMPLETE_FOR_PAYMENT);
			tabList.add(tab);
			}
			//SL-15642 End  Adding complete for payment tab only when user has permission to view price
		
			tab = new SODetailsTabDTO(ID_SUPPORT, selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM, selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if (status != null && status.intValue() == OrderConstants.COMPLETED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES, selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			
			tab = new SODetailsTabDTO(ID_COMPLETION_RECORD, selectedID, TAB_COMPLETION_RECORD);
			tabList.add(tab);
			if(viewOrderPricing==true)
			{
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			}
			tab = new SODetailsTabDTO(ID_SUPPORT, selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM, selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if (status != null && status.intValue() == OrderConstants.PROBLEM_STATUS)
		{

			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES, selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			
			if(viewOrderPricing==true)
			{
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			}
			tab = new SODetailsTabDTO(ID_SUPPORT, selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_ISSUE_RESOLUTION, selectedID, TAB_ISSUE_RESOLUTION);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if (status != null && status.intValue() == OrderConstants.ROUTED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			
			if(Constants.PriceModel.ZERO_PRICE_BID.equals(priceModel) || Constants.PriceModel.BULLETIN.equals(priceModel))
			{
				if(isAdmin)
				{
					if(viewOrderPricing==true)
					{
					tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
					tabList.add(tab);		
					}
				}
			}
			else
			{
				if(viewOrderPricing==true)
				{
				tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
				tabList.add(tab);
				}
			}
		}
		else if (status != null && status.intValue() == OrderConstants.CANCELLED_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES, selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			
			if(viewOrderPricing==true)
			{
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			}
			tab = new SODetailsTabDTO(ID_SUPPORT, selectedID, TAB_SUPPORT);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_TIME_ON_SITE, selectedID, TAB_TIME_ON_SITE);
			tabList.add(tab);
		}
		else if (status != null && status.intValue() == OrderConstants.PENDING_CANCEL_STATUS)
		{
			tab = new SODetailsTabDTO(ID_SUMMARY, selectedID, TAB_SUMMARY_PROV_POSTED_RECD);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_NOTES, selectedID, TAB_SO_NOTES);
			tabList.add(tab);
			
			if(null  != isEstimateRequest && isEstimateRequest.equals("true")){
				tab = new SODetailsTabDTO(ID_ESTIMATE_DETAILS,  selectedID, TAB_ESTIMATE_DETAILS);
				if(null != estimateId){
					tab.setEstimationId(estimateId);
					}
				tabList.add(tab);
				}
			
			
			if(viewOrderPricing==true)
			{
			tab = new SODetailsTabDTO(ID_ORDER_HISTORY, selectedID, TAB_SO_HISTORY);
			tabList.add(tab);
			}
			tab = new SODetailsTabDTO(ID_REPORT_A_PROBLEM, selectedID, TAB_REPORT_A_PROBLEM);
			tabList.add(tab);
			tab = new SODetailsTabDTO(ID_SUPPORT, selectedID, TAB_SUPPORT);
			tabList.add(tab);
		}
		
		return tabList;
		
	}
	
	public static ArrayList<SODetailsTabDTO>  setOrderGroupTabs(String groupId, Integer status, String roleType, String defaultTab)
	{
		ArrayList<SODetailsTabDTO> tabList = new ArrayList<SODetailsTabDTO>();
		SODetailsTabDTO tab;
		
		StringBuffer addGroupIdParam = new StringBuffer("?" + OrderConstants.GROUP_ID + "=" + groupId);
		StringBuffer appendGroupIdParam = new StringBuffer("&" + OrderConstants.GROUP_ID + "=" + groupId);
		
		tab = new SODetailsTabDTO(ID_SUMMARY, defaultTab, TAB_DEFAULT_SUMMARY_VIEW + addGroupIdParam);
		tabList.add(tab);
		tab = new SODetailsTabDTO(ID_NOTES,  defaultTab, TAB_SO_NOTES +  appendGroupIdParam + generateUniqueURLPart());
		tabList.add(tab);
		
		if(OrderConstants.PROVIDER.equalsIgnoreCase(roleType) == false) {
			if(status != null && status.intValue() == OrderConstants.ROUTED_STATUS) {
				tab = new SODetailsTabDTO(ID_RESPONSE_STATUS,  defaultTab, TAB_RESPONSE_STATUS + addGroupIdParam, true);
				tabList.add(tab);
			}
		}
		
		//tab = new SODetailsTabDTO(ID_ORDER_HISTORY,  defaultTab, TAB_SO_HISTORY + additionalParameters);
		//tabList.add(tab);
		tab = new SODetailsTabDTO(ID_SUPPORT,  defaultTab, TAB_SUPPORT + appendGroupIdParam);
		tabList.add(tab);
		
		
		return tabList;		
	}
	
	
	public static String TAB_DEFAULT_SUMMARY_VIEW= "soDetailsSummary.action";
	public static String TAB_BUYER_COMP_DOC = "soDetailsCompletionRecord.action";
	public static String TAB_BUYER_COMP_DOCS = "soDetailsCompletionRecord.action";
	public static String TAB_BUYER_COMP_RECORD = "soDetailsBuyerCompletionRecord.action";
	public static String TAB_ISSUE_RESOLUTION = "soDetailsIssueResolution.action";
	public static String TAB_COMPLETE_FOR_PAYMENT = "soDetailsCompleteForPayment_displayPage.action";
	public static String TAB_COMPLETION_RECORD = "soDetailsProvCompletionRecord.action";
	public static String TAB_PROV_COMP_DOCS = "soDetailsSummary.action";
	public static String TAB_PROV_COMP_RECORD = "soDetailsProvCompletionRecord.action";
	public static String TAB_RATE_BUYER = "soDetailsRateProvider.action";
	public static String TAB_RATE_PROVIDER = "soDetailsRateProvider.action";
	public static String TAB_REPORT_A_PROBLEM = "soDetailsReportAProblem.action";
	public static String TAB_RESPONSE_HISTORY = "soDetailsResponseHistory.action";
	public static String TAB_RESPONSE_STATUS = "soDetailsResponseStatus.action";
	public static String TAB_SO_HISTORY = "soDetailsOrderHistory.action";
	public static String TAB_PRICE_HISTORY = "soDetailsPriceHistory.action";
	public static String TAB_SO_NOTES = "soDetailsNotes.action?noteType=general";
	public static String TAB_CLOSE_AND_PAY = "soDetailsDisplayCloseAndPay.action"; //Yes, I know this is wrong.  We don't seem to have a proper jsp page for this.
	
	public static String TAB_SUMMARY_ACCEPTED_RESCHED = "soDetailsSummary.action";	
	public static String TAB_SUMMARY_ACCEPTED_POSTED_SENT = "soDetailsSummary.action";	
	public static String TAB_SUMMARY_PROV_POSTED_RECD = "soDetailsSummary.action";	
	public static String TAB_PROV_PS_1 = "soDetailsSummary.action";
	public static String TAB_SUPPORT = "soDetailsNotes.action?noteType=support";
	public static String TAB_TIME_ON_SITE = "soDetailsTimeOnSite.action";
	public static String TAB_VIEW_RATINGS = "soDetailsViewRatings.action";
	public static String TAB_VIEW_RATINGS_NEW = "soDetailsViewRatingsNew.action";
	
	//estimate
	public static String TAB_ESTIMATE_DETAILS = "estimateDetails.action";
	
	public static String ID_SUMMARY = "Summary";
	public static String ID_NOTES = "Notes";
	//estimate
	public static String ID_ESTIMATE_DETAILS = "Estimate Details";
	public static String ID_TIME_ON_SITE = "Time On Site";
	public static String ID_ORDER_HISTORY = "Order History";
	public static String ID_PRICE_HISTORY= "Price History";
	public static String ID_RESPONSE_STATUS = "Response Status";
	public static String ID_RESPONSE_HISTORY = "Response History";
	public static String ID_SUPPORT = "Support";
	public static String ID_REPORT_A_PROBLEM = "Report A Problem";
	public static String ID_COMPLETE_FOR_PAYMENT = "Complete for Payment";
	public static String ID_COMPLETION_RECORD = "Completion Record";
	public static String ID_EDIT_TIME_ON_SITE = "EdittimeonSite";
	public static String ID_ISSUE_RESOLUTION = "Issue Resolution";
	public static String ID_RATE_BUYER = "Rate Buyer";
	public static String ID_RATE_PROVIDER = "Rate Provider";
	public static String ID_VIEW_RATINGS = "View Ratings";
	public static String ID_VIEW_RATINGS_NEW = "View New Ratings";
	public static String ID_CLOSE_AND_PAY = "Close and Pay";
	public static String UNIQUE_URL = "uniqUrl";
	public static final String SODetail_Controller_Action = "soDetailsController.action";
	


	
	public static String generateUniqueURLPart(){
		Random generator = new Random();
		String url = "&" +UNIQUE_URL+"="+new Integer(generator.nextInt(99999999));
		return url;
	}
	

}

