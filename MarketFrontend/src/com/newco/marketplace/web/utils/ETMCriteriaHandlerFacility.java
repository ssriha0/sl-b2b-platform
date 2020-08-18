package com.newco.marketplace.web.utils;

import java.util.Map;

import com.newco.marketplace.criteria.ICriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceFilterCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceSearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.opensymphony.xwork2.ActionContext;

public class ETMCriteriaHandlerFacility extends CriteriaHandlerFacility{
	
	private static ETMCriteriaHandlerFacility _facility = new ETMCriteriaHandlerFacility();
	private ETMCriteriaHandlerFacility(){};
	
	public static ETMCriteriaHandlerFacility getInstance(){
		if(_facility == null)
		{
			_facility = new ETMCriteriaHandlerFacility();
		}
		return _facility;
	}
	
	public void	initFilterFacility( ExploreMktPlaceFilterCriteria fCriteria, SortCriteria sCriteria, PagingCriteria pCriteria, ExploreMktPlaceSearchCriteria searchCriteria) 
	{
		addCriteria(ETM_FILTER_CRITERIA_KEY, fCriteria);
		addCriteria(ETM_SORT_CRITERIA_KEY, sCriteria);
		addCriteria(ETM_PAGING_CRITERIA_KEY, pCriteria);
		addCriteria(ETM_SEARCH_CRITERIA_KEY, searchCriteria);
	}
	
	public CriteriaMap getCriteria(){
		CriteriaMap newMap = new CriteriaMap();
		newMap.put(ETM_FILTER_CRITERIA_KEY, getETMFilterCriteria());
		newMap.put(ETM_SORT_CRITERIA_KEY, getSortingCriteria());
		newMap.put(ETM_PAGING_CRITERIA_KEY, getPagingCriteria());
		newMap.put(ETM_SEARCH_CRITERIA_KEY, getETMSearchCriteria());
		newMap.put(ETM_SEARCH_KEY, getSearchKey());
		return newMap;

	}
	
	public ExploreMktPlaceFilterCriteria getETMFilterCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ETM_FILTER_CRITERIA_KEY))
		{
			return (ExploreMktPlaceFilterCriteria)theMap.get(ETM_FILTER_CRITERIA_KEY);
		}
		return null;
	}
	
	
	public void setExploreMktPlaceFilterCriteria(ExploreMktPlaceFilterCriteria criteria) {
		Map theMap = ActionContext.getContext().getSession();
		theMap.put(ETM_FILTER_CRITERIA_KEY, criteria);
	}
	
	public void resetAllCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ETM_SEARCH_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(ETM_SEARCH_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(ETM_FILTER_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(ETM_FILTER_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(ETM_SORT_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(ETM_SORT_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(ETM_PAGING_CRITERIA_KEY))
		{
			((ICriteria)theMap.get(ETM_PAGING_CRITERIA_KEY)).reset();
		}
		if(theMap.containsKey(ETM_SEARCH_KEY))
		{
			((ICriteria)theMap.get(ETM_SEARCH_KEY)).reset();
		}
	}
	
	public PagingCriteria getPagingCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ETM_PAGING_CRITERIA_KEY))
		{
			return (PagingCriteria)theMap.get(ETM_PAGING_CRITERIA_KEY);
		}
		return null;
	}
	
	public void setPagingCriteria(PagingCriteria criteria) {
		Map theMap = ActionContext.getContext().getSession();
		theMap.put(ETM_PAGING_CRITERIA_KEY, criteria);
	}
	
	public SortCriteria getSortingCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ETM_SORT_CRITERIA_KEY))
		{
			return (SortCriteria)theMap.get(ETM_SORT_CRITERIA_KEY);
		}
		return null;
	}
	
	public void setSortCriteria(SortCriteria criteria){
		Map theMap = ActionContext.getContext().getSession();
		theMap.put(ETM_SORT_CRITERIA_KEY, criteria);
	}
	
	
	public ExploreMktPlaceSearchCriteria getETMSearchCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ETM_SEARCH_CRITERIA_KEY))
		{
			return (ExploreMktPlaceSearchCriteria)theMap.get(ETM_SEARCH_CRITERIA_KEY);
		}
		return null;
	}
	
	public void setExploreMktPlaceSearchCriteria(ExploreMktPlaceSearchCriteria searchCriteria)
	{
		Map theMap = ActionContext.getContext().getSession();
		theMap.put(ETM_SEARCH_CRITERIA_KEY,searchCriteria);
	}
	
	public void addSearchKey(String searchKey)
	{	
		ActionContext.getContext().getSession().put(ETM_SEARCH_KEY, searchKey);
	}
	
	public void addFilterZipCd(String zipCd)
	{	
		ActionContext.getContext().getSession().put(ETM_FILTER_ZIP, zipCd);
	}
	public String getSearchKey()
	{	
		Map theMap = ActionContext.getContext().getSession();
		if(theMap.containsKey(ETM_SEARCH_KEY))
		{
			return (String)theMap.get(ETM_SEARCH_KEY);
		}
		return null;
	}
		
}
