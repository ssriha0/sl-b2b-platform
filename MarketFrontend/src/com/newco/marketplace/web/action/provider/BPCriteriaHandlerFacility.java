package com.newco.marketplace.web.action.provider;

import java.util.Map;

import com.newco.marketplace.criteria.ICriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.BPSearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.opensymphony.xwork2.ActionContext;

public class BPCriteriaHandlerFacility extends CriteriaHandlerFacility
{
	

	
	private static BPCriteriaHandlerFacility _facility = new BPCriteriaHandlerFacility();
	private BPCriteriaHandlerFacility()
	{
		
	}
	
	public static BPCriteriaHandlerFacility getInstance()
	{
		if(_facility == null) {
			_facility = new BPCriteriaHandlerFacility();
		}
		return _facility;
	}
	
	public void	initFilterFacility(SortCriteria sCriteria, PagingCriteria pCriteria, BPSearchCriteria searchCriteria) 
	{
		addCriteria(SORT_CRITERIA_KEY, sCriteria);
		addCriteria(PAGING_CRITERIA_KEY, pCriteria);
		addCriteria(SEARCH_CRITERIA_KEY, searchCriteria);
	}
	
	public CriteriaMap getCriteria(){
		CriteriaMap newMap = new CriteriaMap();
		newMap.put(SORT_CRITERIA_KEY, getSortingCriteria());
		newMap.put(PAGING_CRITERIA_KEY, getPagingCriteria());
		newMap.put(SEARCH_CRITERIA_KEY, getBPSearchCriteria());
		return newMap;
	}
	
	@SuppressWarnings("unchecked")
	public void resetAllCriteria() {
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SEARCH_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(SEARCH_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(SORT_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(SORT_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(PAGING_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(PAGING_CRITERIA_KEY)).reset();
		}
	}
	
	@SuppressWarnings("unchecked")
	public PagingCriteria getPagingCriteria() {
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(PAGING_CRITERIA_KEY))
		{
			return (PagingCriteria)theMap.get(PAGING_CRITERIA_KEY);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void setPagingCriteria(PagingCriteria criteria) {
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		theMap.put(PAGING_CRITERIA_KEY, criteria);
	}
	
	@SuppressWarnings("unchecked")
	public SortCriteria getSortCriteria() {
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SORT_CRITERIA_KEY))
		{
			return (SortCriteria)theMap.get(SORT_CRITERIA_KEY);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void setSortCriteria(SortCriteria criteria){
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		theMap.put(SORT_CRITERIA_KEY, criteria);
	}

	@SuppressWarnings("unchecked")
	public BPSearchCriteria getBPSearchCriteria() {
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(SEARCH_CRITERIA_KEY))
		{
			return (BPSearchCriteria)theMap.get(SEARCH_CRITERIA_KEY);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void setBPSearchCriteria(BPSearchCriteria searchCriteria)
	{
		Map<String, Object> theMap = ActionContext.getContext().getSession();
		theMap.put(SEARCH_CRITERIA_KEY,searchCriteria);
	}
}
