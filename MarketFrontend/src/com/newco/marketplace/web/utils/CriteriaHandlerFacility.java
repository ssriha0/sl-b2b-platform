package com.newco.marketplace.web.utils;

import java.util.Map;

import com.newco.marketplace.criteria.DisplayTabCriteria;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.ICriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SearchWordsCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.opensymphony.xwork2.ActionContext;

public class CriteriaHandlerFacility implements OrderConstants{
	
	private static CriteriaHandlerFacility _facility = new CriteriaHandlerFacility();
	
	public CriteriaHandlerFacility(){}
	
	public static CriteriaHandlerFacility getInstance(){
		if(_facility == null)
		{
			_facility = new CriteriaHandlerFacility();
		}
		return _facility;
	}
	
	public void	initFacility( FilterCriteria fCriteria, 
							  SortCriteria sCriteria, 
							  PagingCriteria pCriteria, 
							  OrderCriteria orderCriteria, 
							  SearchCriteria searchCriteria ) 
	{
		addCriteria(FILTER_CRITERIA_KEY, fCriteria);
		addCriteria(SORT_CRITERIA_KEY, sCriteria);
		addCriteria(PAGING_CRITERIA_KEY, pCriteria);
		addCriteria(ORDER_CRITERIA_KEY, orderCriteria);
		addCriteria(SEARCH_CRITERIA_KEY, searchCriteria);
	}
	
	public void	initFacility( FilterCriteria fCriteria, 
			  SortCriteria sCriteria, 
			  PagingCriteria pCriteria, 
			  OrderCriteria orderCriteria, 
			  SearchCriteria searchCriteria, SearchWordsCriteria swc , DisplayTabCriteria dtc) 
	{
		addCriteria(FILTER_CRITERIA_KEY, fCriteria);
		addCriteria(SORT_CRITERIA_KEY, sCriteria);
		addCriteria(PAGING_CRITERIA_KEY, pCriteria);
		addCriteria(ORDER_CRITERIA_KEY, orderCriteria);
		addCriteria(SEARCH_CRITERIA_KEY, searchCriteria);
		addCriteria(SEARCH_WORDS_CRITERIA_KEY, swc);
		addCriteria(DISPLAY_TAB_CRITERIA_KEY, dtc);
	}
	
	public void resetAllCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(FILTER_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(FILTER_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(SORT_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(SORT_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(PAGING_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(PAGING_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(PAGINATION_VO))
		{
			theMap.put(PAGINATION_VO, null);
//			 THE pagination.jsp uses this key
			theMap.put("paginationVO", null);
		}
		if(theMap.containsKey(SEARCH_WORDS_CRITERIA_KEY)){
			((ICriteria)theMap.get(SEARCH_WORDS_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(DISPLAY_TAB_CRITERIA_KEY)){
			((ICriteria)theMap.get(DISPLAY_TAB_CRITERIA_KEY)).reset();
		}
	}
	
	public CriteriaMap getCriteria(){
		CriteriaMap newMap = new CriteriaMap();
		newMap.put(FILTER_CRITERIA_KEY, getFilterCriteria());
		newMap.put(SORT_CRITERIA_KEY, getSortingCriteria());
		newMap.put(PAGING_CRITERIA_KEY, getPagingCriteria());
		newMap.put(ORDER_CRITERIA_KEY, getOrderCriteria());
		newMap.put(SERVICE_ORDER_CRITERIA_KEY, getServiceOrdersCriteria());
		newMap.put(SEARCH_CRITERIA_KEY, getSearchingCriteria());
		newMap.put(SEARCH_WORDS_CRITERIA_KEY, getSearchWordsCriteria());
		newMap.put(DISPLAY_TAB_CRITERIA_KEY, getDisplayTabCriteria());
		return newMap;

	}
	
	public ServiceOrdersCriteria getServiceOrdersCriteria(){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SERVICE_ORDER_CRITERIA_KEY))
		{
			return (ServiceOrdersCriteria)theMap.get(SERVICE_ORDER_CRITERIA_KEY);
		}
		return null;
		
	}
	
	public SearchCriteria getSearchingCriteria(){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SEARCH_CRITERIA_KEY))
		{
			return (SearchCriteria)theMap.get(SEARCH_CRITERIA_KEY);
		}
		return null;
		
	}
	
	
	public SearchWordsCriteria getSearchWordsCriteria(){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SEARCH_WORDS_CRITERIA_KEY))
		{
			return (SearchWordsCriteria)theMap.get(SEARCH_WORDS_CRITERIA_KEY);
		}
		return null;
		
	}
	
	public DisplayTabCriteria getDisplayTabCriteria(){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(DISPLAY_TAB_CRITERIA_KEY))
		{
			return (DisplayTabCriteria)theMap.get(DISPLAY_TAB_CRITERIA_KEY);
		}
		return null;
		
	}
	
	
	
	public OrderCriteria getOrderCriteria(){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ORDER_CRITERIA_KEY))
		{
			return (OrderCriteria)theMap.get(ORDER_CRITERIA_KEY);
		}
		return null;
		
	}
	public FilterCriteria getFilterCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(FILTER_CRITERIA_KEY))
		{
			return (FilterCriteria)theMap.get(FILTER_CRITERIA_KEY);
		}
		return null;
	}
	
	public PagingCriteria getPagingCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(PAGING_CRITERIA_KEY))
		{
			return (PagingCriteria)theMap.get(PAGING_CRITERIA_KEY);
		}
		return null;
	}
	
	public SortCriteria getSortingCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SORT_CRITERIA_KEY))
		{
			return (SortCriteria)theMap.get(SORT_CRITERIA_KEY);
		}
		return null;
	}
	

	public void resetCriteria(String criteraKey )
	{
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(criteraKey))
		{
			((ICriteria)theMap.get(criteraKey)).reset();
		}
	}
	
	
	public void updatePagingCriteria( String criteriaKey, ICriteria theCriteria, PaginationVO pagination )
	{
		Map theMap = ActionContext.getContext().getSession();
		resetCriteria(criteriaKey);
		PagingCriteria pagingCriteria = getPagingCriteria();
		if (pagination != null)
		{
			pagingCriteria.setStartIndex(pagination.getStartIndex());
			pagingCriteria.setEndIndex(pagination.getEndIndex());
			pagingCriteria.setPageSize( pagination.getPageSize());
			theMap.put(criteriaKey, pagingCriteria);
			theMap.put(PAGINATION_VO, pagination);
			// THE pagination.jsp uses this key
			theMap.put("paginationVO", pagination);
		}
	}
	
	public void addCriteria( String key, ICriteria criteria)
	{
		ActionContext.getContext().getSession().put(key, criteria);
	}
	
	public void removeCriteria( String key)
	{
		ActionContext.getContext().getSession().remove(key);
	}
	
	public PaginationVO getPaginationItem() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(PAGINATION_VO))
		{
			return (PaginationVO)theMap.get(PAGINATION_VO);
		}
		return null;
	}
	
	//R12_1
	//SL-20379 : The following methods are added for resolving SOM session issue
	public void	initFacility( FilterCriteria fCriteria, 
			  SortCriteria sCriteria, 
			  PagingCriteria pCriteria, 
			  OrderCriteria orderCriteria, 
			  SearchCriteria searchCriteria, 
			  SearchWordsCriteria swc , DisplayTabCriteria dtc, String tab) 
	{
		addCriteria(FILTER_CRITERIA_KEY+"_"+tab, fCriteria);
		addCriteria(SORT_CRITERIA_KEY+"_"+tab, sCriteria);
		addCriteria(PAGING_CRITERIA_KEY+"_"+tab, pCriteria);
		addCriteria(ORDER_CRITERIA_KEY+"_"+tab, orderCriteria);
		addCriteria(SEARCH_CRITERIA_KEY+"_"+tab, searchCriteria);
		addCriteria(SEARCH_WORDS_CRITERIA_KEY, swc);
		addCriteria(DISPLAY_TAB_CRITERIA_KEY, dtc);
	}
	
	public CriteriaMap getCriteria(String tab){
		CriteriaMap newMap = new CriteriaMap();
		newMap.put(FILTER_CRITERIA_KEY+"_"+tab, getFilterCriteria(tab));
		newMap.put(SORT_CRITERIA_KEY+"_"+tab, getSortingCriteria(tab));
		newMap.put(PAGING_CRITERIA_KEY+"_"+tab, getPagingCriteria(tab));
		newMap.put(ORDER_CRITERIA_KEY+"_"+tab, getOrderCriteria(tab));
		newMap.put(SERVICE_ORDER_CRITERIA_KEY, getServiceOrdersCriteria());
		newMap.put(SEARCH_CRITERIA_KEY+"_"+tab, getSearchingCriteria(tab));
		newMap.put(SEARCH_WORDS_CRITERIA_KEY, getSearchWordsCriteria());
		newMap.put(DISPLAY_TAB_CRITERIA_KEY, getDisplayTabCriteria());
		return newMap;

	}

	public OrderCriteria getOrderCriteria(String tab){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ORDER_CRITERIA_KEY+"_"+tab))
		{
			return (OrderCriteria)theMap.get(ORDER_CRITERIA_KEY+"_"+tab);
		}
		return null;
		
	}
	
	public FilterCriteria getFilterCriteria(String tab) {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(FILTER_CRITERIA_KEY+"_"+tab))
		{
			return (FilterCriteria)theMap.get(FILTER_CRITERIA_KEY+"_"+tab);
		}
		return null;
	}
	
	public PagingCriteria getPagingCriteria(String tab) {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(PAGING_CRITERIA_KEY+"_"+tab))
		{
			return (PagingCriteria)theMap.get(PAGING_CRITERIA_KEY+"_"+tab);
		}
		return null;
	}
	
	public SortCriteria getSortingCriteria(String tab) {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SORT_CRITERIA_KEY+"_"+tab))
		{
			return (SortCriteria)theMap.get(SORT_CRITERIA_KEY+"_"+tab);
		}
		return null;
	}
	
	public SearchCriteria getSearchingCriteria(String tab){
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SEARCH_CRITERIA_KEY+"_"+tab))
		{
			return (SearchCriteria)theMap.get(SEARCH_CRITERIA_KEY+"_"+tab);
		}
		return null;
		
	}
	
}
