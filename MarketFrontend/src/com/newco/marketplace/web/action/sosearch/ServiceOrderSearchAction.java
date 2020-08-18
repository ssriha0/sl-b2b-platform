package com.newco.marketplace.web.action.sosearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.TabConstants;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

import org.apache.commons.lang.StringUtils;

/**
 * $Revision: 1.24 $ $Author: glacy $ $Date: 2008/05/02 21:23:23 $
 */


/*
 * Maintenance History
 * $Log: ServiceOrderSearchAction.java,v $
 * Revision 1.24  2008/05/02 21:23:23  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.23  2008/04/26 01:13:54  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.21.12.1  2008/04/23 11:41:49  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.22  2008/04/23 05:19:37  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.21  2008/02/18 21:46:43  bgangaj
 *  fix for SOM Search tab getting no results when coming thru other tabs
 *
 * Revision 1.20  2008/02/11 20:05:42  dmill03
 * added workflow checks
 *
 * Revision 1.19  2008/01/31 15:48:22  mhaye05
 * changed name of method from PBWorkflowSearch to pbWorkflowSearch
 *
 * Revision 1.18  2008/01/29 23:37:56  mhaye05
 * made sure the search filter is set for PB filtering
 *
 * Revision 1.17  2008/01/29 22:01:19  mhaye05
 * fixed the sorting of power buyer filter search results
 *
 * Revision 1.16  2008/01/25 02:37:33  dmill03
 * updated workflow nav
 *
 * Revision 1.15  2008/01/24 21:32:22  pkoppis
 * updated the Pbsearch
 *
 * Revision 1.14  2008/01/21 19:51:09  pkoppis
 * updated with PBWorkflowSearch
 *
 * Revision 1.13  2007/12/04 03:17:11  mhaye05
 * added logic for SOM data grid sorting
 *
 * Revision 1.12  2007/12/03 17:35:08  iullah2
 * fix for merge build - delete package com.newco.marketplace.business.Utils and organize imports on src
 *
 * Revision 1.11  2007/12/01 16:15:28  mhaye05
 * removed some extra white space
 *
 * Revision 1.10  2007/11/28 23:53:33  mhaye05
 * updated for so search  status filtering
 *
 * Revision 1.9  2007/11/27 22:59:43  mhaye05
 * updated for pagination
 *
 * Revision 1.8  2007/11/27 00:24:45  mhaye05
 * updated for search paging
 *
 */
public class ServiceOrderSearchAction extends SLBaseAction implements Preparable,OrderConstants {


	private static final long serialVersionUID = 1208422399414519108L;
	private String searchType;
	private String searchValue;	
	private String pageIndicator;
	
	public ServiceOrderSearchAction(ISOMonitorDelegate delegate) {
		super.soMonitorDelegate = delegate;
	}
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue.trim();
	}
	
	public String getPageIndicator() {
		return pageIndicator;
	}
	 	 
		public void setPageIndicator(String pageIndicator) {
			this.pageIndicator = pageIndicator;
		}
		
	public ServiceOrderSearchAction(SOMonitorDelegateImpl soMonitorDelegate) 
	{
		super.soMonitorDelegate = soMonitorDelegate;
	}

	public String execute() throws Exception
	{
		return SUCCESS;
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		//SL-20379
		setAttribute("tab", "Search");		
		//loadDataGridFilters(true);
		this.loadCriteria(true);
		
		HttpSession session  = getSession();
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		Map<String, UserActivityVO> activities = soContxt.getRoleActivityIdList();
		boolean incSpendLimit = false;
		if(activities != null && activities.containsKey("56")|| soContxt.isAdopted()){
			incSpendLimit = true;
		}
		session.setAttribute("incSpendLimit", incSpendLimit);
		//SL-19820
		String fromWFM = getParameter("fromWFM");
		setAttribute("fromWFM", fromWFM);
		
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String pbWorkflowSearch() throws Exception {
		
		//R12_1
		//SL-20362
		String pendingReschedule=getRequest().getParameter("pendingReschedule");
		getRequest().setAttribute("pendingReschedule", pendingReschedule);
		
		String wfFlag = getRequest().getParameter("wfFlag");
		Integer pbFilterId = new Integer("0");
		//sl-13522
		
		String wfSodFlag=getRequest().getParameter("wfmSodFlag");
		getRequest().setAttribute("wfmSodFlag", wfSodFlag);
		
		String soId=getRequest().getParameter("soId");
		getRequest().setAttribute("soId", soId);
		
		//SL-20379
		String tab = (String) getAttribute("tab");
		
		boolean doSearch = true;
		if(getRequest().getParameter("pbFilterId") != null && 
			!getRequest().getParameter("pbFilterId").equals("")){
			try
			{
				pbFilterId=  new Integer((getRequest().getParameter("pbFilterId")));
				
			}catch(NumberFormatException nfe){
				// handled
				doSearch = false;
			}
		}
		else
		{
			doSearch = false;
		}
		String pbFilterName= getRequest().getParameter("pbFilterName");
		String pbFilterOpt= getRequest().getParameter("pbFilterOpt");
		if(null == pbFilterOpt){
			pbFilterOpt=(String)getSession().getAttribute("pbFilterOpt");
		}
		// params for filtering
		Integer buyerRefTypeId;
		String buyerRefValue;
		String searchByBuyerId;
		try {
			buyerRefTypeId = Integer.valueOf(getRequest().getParameter("refType"));
			searchByBuyerId = (String) getSession().getAttribute("searchByBuyerId");
			buyerRefValue = getRequest().getParameter("refVal");
		} catch (NumberFormatException e) {
			buyerRefTypeId = null;
			buyerRefValue = null;
			searchByBuyerId=null;
		}

		if(wfSodFlag!=null && (wfSodFlag.equals("Provider")|| wfSodFlag.equals("Firm")))
	  	{
			pbFilterId = 1;
	  	}

		getRequest().setAttribute("wfFlag", wfFlag);
		getRequest().setAttribute("pbFilterId", pbFilterId.toString());
		getRequest().setAttribute("pbFilterName", pbFilterName);
		getRequest().setAttribute("pbFilterOpt", pbFilterOpt);
		LoginCredentialVO lvo = get_commonCriteria().getSecurityContext().getRoles();
		
		if(null == lvo) {
			return "out";
		}
		//For retaining the search Criteria in session
		CriteriaMap cMap = (CriteriaMap)getSession().getAttribute("selectedSearchCriteriaSession");
		if(null != cMap && pbFilterId.equals(0) ){
			CriteriaHandlerFacility cHandler = CriteriaHandlerFacility.getInstance();
			//SL-20379
			/*SortCriteria sortCriteria = (SortCriteria) cMap.get(OrderConstants.SORT_CRITERIA_KEY);
			OrderCriteria orderCriteria = (OrderCriteria) cMap.get(OrderConstants.ORDER_CRITERIA_KEY);
			SearchCriteria searchCriteria = (SearchCriteria) cMap.get(OrderConstants.SEARCH_CRITERIA_KEY);
			FilterCriteria filterCriteria = (FilterCriteria) cMap.get(OrderConstants.FILTER_CRITERIA_KEY);
			PagingCriteria pagingCriteria = (PagingCriteria) cMap.get(OrderConstants.PAGING_CRITERIA_KEY);
			
			cHandler.addCriteria(FILTER_CRITERIA_KEY, filterCriteria);
			cHandler.addCriteria(SORT_CRITERIA_KEY, sortCriteria);
			cHandler.addCriteria(PAGING_CRITERIA_KEY, pagingCriteria);
			cHandler.addCriteria(ORDER_CRITERIA_KEY, orderCriteria);
			cHandler.addCriteria(SEARCH_CRITERIA_KEY, searchCriteria);
			
			getSearchList(cHandler, soMonitorDelegate.getSoSearchIdsList(cMap));*/
			SortCriteria sortCriteria = (SortCriteria) cMap.get(OrderConstants.SORT_CRITERIA_KEY+"_"+tab);
			OrderCriteria orderCriteria = (OrderCriteria) cMap.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
			SearchCriteria searchCriteria = (SearchCriteria) cMap.get(OrderConstants.SEARCH_CRITERIA_KEY+"_"+tab);
			FilterCriteria filterCriteria = (FilterCriteria) cMap.get(OrderConstants.FILTER_CRITERIA_KEY+"_"+tab);
			PagingCriteria pagingCriteria = (PagingCriteria) cMap.get(OrderConstants.PAGING_CRITERIA_KEY+"_"+tab);
			
			cHandler.addCriteria(FILTER_CRITERIA_KEY+"_"+tab, filterCriteria);
			cHandler.addCriteria(SORT_CRITERIA_KEY+"_"+tab, sortCriteria);
			cHandler.addCriteria(PAGING_CRITERIA_KEY+"_"+tab, pagingCriteria);
			cHandler.addCriteria(ORDER_CRITERIA_KEY+"_"+tab, orderCriteria);
			cHandler.addCriteria(SEARCH_CRITERIA_KEY+"_"+tab, searchCriteria);
			
			CriteriaMap newMap = new CriteriaMap();
		    newMap.put(FILTER_CRITERIA_KEY, filterCriteria);
			newMap.put(SORT_CRITERIA_KEY, sortCriteria);
			newMap.put(PAGING_CRITERIA_KEY, pagingCriteria);
			newMap.put(ORDER_CRITERIA_KEY, orderCriteria);
			newMap.put(SEARCH_CRITERIA_KEY, searchCriteria);
			boolean isBuyer = get_commonCriteria().getSecurityContext().isBuyer();
			newMap.put("IS_BUYER", isBuyer);
			
			getSearchList(cHandler, soMonitorDelegate.getSoSearchIdsList(newMap));
			return SUCCESS;
		}
		CriteriaHandlerFacility criteriaHandler = CriteriaHandlerFacility.getInstance();
		ServiceOrdersCriteria serviceOrderCriteria = criteriaHandler.getServiceOrdersCriteria();
        
		//SL-20379
        //OrderCriteria orderCriteria = criteriaHandler.getOrderCriteria();
		OrderCriteria orderCriteria = criteriaHandler.getOrderCriteria(tab);
        orderCriteria.setRoleId(serviceOrderCriteria.getRoleId());
        orderCriteria.setRoleType(serviceOrderCriteria.getRoleType());
        orderCriteria.setCompanyId(serviceOrderCriteria.getCompanyId());
        orderCriteria.setVendBuyerResId(serviceOrderCriteria.getVendBuyerResId());
        // If admin is masquerading as buyer, get back into admin identity
        if (get_commonCriteria().getSecurityContext().isSlAdminInd() && !get_commonCriteria().getSecurityContext().isAdopted()){
            orderCriteria.setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
            orderCriteria.setRoleType(OrderConstants.NEWCO_ADMIN);
            orderCriteria.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
            orderCriteria.setVendBuyerResId(get_commonCriteria().getSecurityContext().getVendBuyerResId());
            
            
            Map session = ActionContext.getContext().getSession();
            if (session.get("buyerId") != null) {
            	orderCriteria.setAdoptedRoleId(OrderConstants.BUYER_ROLEID);
            	orderCriteria.setCompanyId(get_commonCriteria().getSecurityContext().getCompanyId());
            } else if (session.get("vendorId") != null) {
            	orderCriteria.setAdoptedRoleId(OrderConstants.PROVIDER_ROLEID);
            	orderCriteria.setCompanyId(get_commonCriteria().getSecurityContext().getCompanyId());
            } else {
            	orderCriteria.setAdoptedRoleId(null);
            }
            
        }
		
        //SL-20379
		//FilterCriteria filterCriteria = handleFilterCriteria(criteriaHandler);
        FilterCriteria filterCriteria = handleFilterCriteria(criteriaHandler, tab);
		filterCriteria.setPbfilterId(pbFilterId);
		filterCriteria.setPbfilterOpt(pbFilterOpt);
		filterCriteria.setBuyerRefTypeId(buyerRefTypeId);
		filterCriteria.setBuyerRefValue(buyerRefValue);
		filterCriteria.setSearchByBuyerId(searchByBuyerId);
		//SL-20379
		//PagingCriteria pagingCriteria = handlePagingCriteria( criteriaHandler);
		//SortCriteria sortCriteria = handleSortCriteria(criteriaHandler);
		PagingCriteria pagingCriteria = handlePagingCriteria( criteriaHandler, tab);
		SortCriteria sortCriteria = handleSortCriteria(criteriaHandler, tab);
		
		SearchCriteria searchCriteria = new SearchCriteria();
	  	searchCriteria.setSearchType(SEARCH_BY_PB_FILTER_ID.toString());
	 	if(wfSodFlag!=null && (wfSodFlag.equals("Provider")|| wfSodFlag.equals("Firm")))
		{
		  	searchCriteria.setSearchType(SEARCH_ACTIVESO_BY_PROVIDER.toString());
			sortCriteria.setSortColumnName("SERVICEDATE_TZ");
			sortCriteria.setSortOrder(SORT_ORDER_ASC);
		  	filterCriteria.setPbfilterId(null);
		  	getRequest().setAttribute("pbFilterId", "1");
		  	//Reset admin credentials if they have been masquerading as another buyer.
			if (get_commonCriteria().getSecurityContext().isSlAdminInd() && !get_commonCriteria().getSecurityContext().isAdopted()) {
				get_commonCriteria().setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
				get_commonCriteria().getSecurityContext().setRoleId(2);
				get_commonCriteria().setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
				get_commonCriteria().getSecurityContext().setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
			}
		  	
		}
	  	searchCriteria.setSearchValue("WORKFLOWFILTER_SEARCH");
	  	
	    CriteriaMap newMap = new CriteriaMap();
	    newMap.put(FILTER_CRITERIA_KEY, filterCriteria);
		newMap.put(SORT_CRITERIA_KEY, sortCriteria);
		newMap.put(PAGING_CRITERIA_KEY, pagingCriteria);
		newMap.put(ORDER_CRITERIA_KEY, orderCriteria);
		newMap.put(SEARCH_CRITERIA_KEY, searchCriteria);
	    
		String selectedSearchCriteria = (String)getSession().getAttribute("selectedSearchForSession");
		getSession().setAttribute("selectedSearchForSession", selectedSearchCriteria);
		boolean isBuyer = get_commonCriteria().getSecurityContext().isBuyer();
		newMap.put("IS_BUYER", isBuyer);
		
		if(doSearch||wfSodFlag!=null){
			//SL-20379
			//getSearchList(criteriaHandler, soMonitorDelegate.getSoSearchIdsList(newMap));
			getSearchList(criteriaHandler, soMonitorDelegate.getSoSearchIdsList(newMap));
		}
		
		return SUCCESS;	
	}
	
	
	
	
	/**
	 * soSearch performs the actual search and returns the first page
	 * @return
	 * @throws Exception
	 */
	
	
	public String soSearch() throws Exception {

		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
				if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
				{
					if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
					{
						getSession().setAttribute("viewOrderPricing",true);
					}
				}
				//SL 15642 
		String wfFlag = getRequest().getParameter("wfFlag");
		String sortColumnName;
		String sortOrder;
		
		getRequest().removeAttribute("pbFilterId");
		getRequest().removeAttribute("pbFilterName");
		getRequest().removeAttribute("pbFilterOpt");
		
		getSession().removeAttribute(Constants.SESSION.SO_SEARCH_RESULT_IDS);
		getSession().removeAttribute(SESSION_SORT_COLUMN + TabConstants.BUYER_SEARCH);
		getSession().removeAttribute(SESSION_SORT_ORDER + TabConstants.BUYER_SEARCH);
		
		LoginCredentialVO lvo = get_commonCriteria().getSecurityContext().getRoles();
		
		if(null == lvo) {
			return "out";
		}
		wfFlag = getRequest().getParameter("wfFlag");
		getRequest().setAttribute("wfFlag", wfFlag);
		
		// If the session has other than search in tab attribute, then we are coming in to 
		// search tab. Change the attribute to search and don't submit any thing
		//SL-20379
		/*if (getSession().getAttribute("tab") != null 
				&& !((String)getSession().getAttribute("tab")).equalsIgnoreCase("Search")) 
		{
			getSession().setAttribute("tab","Search");
			//return SUCCESS;
		}*/
		// HACK - bindia
		// THIS IS A SEARCH, THEREFORE NO SERVICEORDER STATUS IS NEEDED
		// WE THE STATUS NULL BECAUSE SOMETIMES IT IS SET TO 9000 (TODAY)
		//JIRA : SL-7661 : Commeting to filter search result based on the status.
		//else
		//{
		//	criteriaHandler.getFilterCriteria().setStatus( null );
		//}
		//For retaining the search Criteria in session
		
		//SL-20379
		String tab = (String) getAttribute("tab");
		String isSearchClicked = getRequest().getParameter("isSearchClicked");
		CriteriaMap cMapSession = (CriteriaMap)getSession().getAttribute("selectedSearchCriteriaSession");
		if(null != cMapSession  &&(
				isSearchClicked.equalsIgnoreCase("0")  || isSearchClicked == null )){
			CriteriaHandlerFacility cHandler = CriteriaHandlerFacility.getInstance();
			//SL-20379
			//SortCriteria sortCriteria = (SortCriteria) cMapSession.get(OrderConstants.SORT_CRITERIA_KEY);
			SortCriteria sortCriteria = (SortCriteria) cMapSession.get(OrderConstants.SORT_CRITERIA_KEY+"_"+tab);
			sortColumnName=sortCriteria.getSortColumnName();
			sortOrder=sortCriteria.getSortOrder();
		 	if(wfFlag!=null && (wfFlag.equals("Provider")|| wfFlag.equals("Firm"))) {
		 		sortCriteria.setSortColumnName("SERVICEDATE_TZ");
		 		sortCriteria.setSortOrder(SORT_ORDER_ASC);
			}
		 	//SL-20379
			/*OrderCriteria orderCriteria = (OrderCriteria) cMapSession.get(OrderConstants.ORDER_CRITERIA_KEY);
			SearchCriteria searchCriteria = (SearchCriteria) cMapSession.get(OrderConstants.SEARCH_CRITERIA_KEY);
			FilterCriteria filterCriteria = (FilterCriteria) cMapSession.get(OrderConstants.FILTER_CRITERIA_KEY);
			PagingCriteria pagingCriteria = (PagingCriteria) cMapSession.get(OrderConstants.PAGING_CRITERIA_KEY);

			cHandler.addCriteria(FILTER_CRITERIA_KEY, filterCriteria);
			cHandler.addCriteria(SORT_CRITERIA_KEY, sortCriteria);
			cHandler.addCriteria(PAGING_CRITERIA_KEY, pagingCriteria);
			cHandler.addCriteria(ORDER_CRITERIA_KEY, orderCriteria);
			cHandler.addCriteria(SEARCH_CRITERIA_KEY, searchCriteria);
			
			getSearchList(cHandler, soMonitorDelegate.getSoSearchIdsList(cMapSession));*/
		 	
		 	OrderCriteria orderCriteria = (OrderCriteria) cMapSession.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
			SearchCriteria searchCriteria = (SearchCriteria) cMapSession.get(OrderConstants.SEARCH_CRITERIA_KEY+"_"+tab);
			FilterCriteria filterCriteria = (FilterCriteria) cMapSession.get(OrderConstants.FILTER_CRITERIA_KEY+"_"+tab);
			PagingCriteria pagingCriteria = (PagingCriteria) cMapSession.get(OrderConstants.PAGING_CRITERIA_KEY+"_"+tab);

			cHandler.addCriteria(FILTER_CRITERIA_KEY+"_"+tab, filterCriteria);
			cHandler.addCriteria(SORT_CRITERIA_KEY+"_"+tab, sortCriteria);
			cHandler.addCriteria(PAGING_CRITERIA_KEY+"_"+tab, pagingCriteria);
			cHandler.addCriteria(ORDER_CRITERIA_KEY+"_"+tab, orderCriteria);
			cHandler.addCriteria(SEARCH_CRITERIA_KEY+"_"+tab, searchCriteria);
			
			CriteriaMap newMap = new CriteriaMap();
		    newMap.put(FILTER_CRITERIA_KEY, filterCriteria);
			newMap.put(SORT_CRITERIA_KEY, sortCriteria);
			newMap.put(PAGING_CRITERIA_KEY, pagingCriteria);
			newMap.put(ORDER_CRITERIA_KEY, orderCriteria);
			newMap.put(SEARCH_CRITERIA_KEY, searchCriteria);
			boolean isBuyer = get_commonCriteria().getSecurityContext().isBuyer();
			newMap.put("IS_BUYER", isBuyer);
			
			getSearchList(cHandler, soMonitorDelegate.getSoSearchIdsList(newMap));
			getSession().setAttribute(SESSION_SORT_COLUMN + TabConstants.BUYER_SEARCH,sortColumnName);
			getSession().setAttribute(SESSION_SORT_ORDER + TabConstants.BUYER_SEARCH,sortOrder);
			return SUCCESS;
		}else{
			CriteriaHandlerFacility criteriaHandler = loadCriteria(false);
			//SL-20379
			//_handleSearchCriteria(criteriaHandler);			
			//handleFilterCriteria(criteriaHandler);
			//handleSortCriteria(criteriaHandler);*/
			//CriteriaMap cMap = criteriaHandler.getCriteria();
			//OrderCriteria orderCriteria = (OrderCriteria)cMap.get(ORDER_CRITERIA_KEY);
			_handleSearchCriteria(criteriaHandler, tab);
			handleFilterCriteria(criteriaHandler, tab);
			handleSortCriteria(criteriaHandler, tab);
			
			CriteriaMap cMap = criteriaHandler.getCriteria(tab);			
			OrderCriteria orderCriteria = (OrderCriteria)cMap.get(ORDER_CRITERIA_KEY+"_"+tab);
	        // If admin is masquerading as buyer, get back into admin identity
	        if (get_commonCriteria().getSecurityContext().isSlAdminInd()){
	            orderCriteria.setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
	            orderCriteria.setRoleType(OrderConstants.NEWCO_ADMIN);
		            //setting the companyId as adopted user's company id since the search is 
		            //failing for SLA
		            //orderCriteria.setCompanyId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
		            orderCriteria.setCompanyId(get_commonCriteria().getSecurityContext().getCompanyId());
	            orderCriteria.setVendBuyerResId(get_commonCriteria().getSecurityContext().getVendBuyerResId());
	            Map session = ActionContext.getContext().getSession();
	            if (session.get("buyerId") != null) {
	            	orderCriteria.setAdoptedRoleId(OrderConstants.BUYER_ROLEID);
	            	orderCriteria.setCompanyId(get_commonCriteria().getSecurityContext().getCompanyId());
	            } else if (session.get("vendorId") != null) {
	            	orderCriteria.setAdoptedRoleId(OrderConstants.PROVIDER_ROLEID);
	            	orderCriteria.setCompanyId(get_commonCriteria().getSecurityContext().getCompanyId());
	            } else {
	            	orderCriteria.setAdoptedRoleId(null);
	            }
	            //SL-20379
	            //cMap.put(ORDER_CRITERIA_KEY, orderCriteria);
	            cMap.put(ORDER_CRITERIA_KEY+"_"+tab, orderCriteria);
	        }
        
	        if( pageIndicator != null && pageIndicator.equals("serviceOrderMonitor") )
	        	 cMap.put( "buyerID", ((SecurityContext) getSession().getAttribute("SecurityContext")).getCompanyId() );
		        //SL-20379
				//getSearchList(criteriaHandler, soMonitorDelegate.getSoSearchIdsList(cMap));
	        	CriteriaMap newMap = new CriteriaMap();
			    newMap.put(FILTER_CRITERIA_KEY, cMap.get(FILTER_CRITERIA_KEY+"_"+tab));
				newMap.put(SORT_CRITERIA_KEY, cMap.get(SORT_CRITERIA_KEY+"_"+tab));
				newMap.put(PAGING_CRITERIA_KEY, cMap.get(PAGING_CRITERIA_KEY+"_"+tab));
				newMap.put(ORDER_CRITERIA_KEY, orderCriteria);
				newMap.put(SEARCH_CRITERIA_KEY, cMap.get(SEARCH_CRITERIA_KEY+"_"+tab));
				newMap.put( "buyerID", ((SecurityContext) getSession().getAttribute("SecurityContext")).getCompanyId() );
				boolean isBuyer = get_commonCriteria().getSecurityContext().isBuyer();
				newMap.put("IS_BUYER", isBuyer);
		        getSearchList(criteriaHandler, soMonitorDelegate.getSoSearchIdsList(newMap));
				//For retaining the search Criteria in session
				String selectedSearchCriteria = getRequest().getParameter("selectedSearchCriteria");		
				//SearchCriteria searchCriteria = (SearchCriteria) cMap.get(OrderConstants.SEARCH_CRITERIA_KEY);
				SearchCriteria searchCriteria = (SearchCriteria) cMap.get(OrderConstants.SEARCH_CRITERIA_KEY+"_"+tab);
				if(searchCriteria.isFilterNameSearch()){
					selectedSearchCriteria = searchCriteria.getFilterTemplate();
				}
				getSession().setAttribute("selectedSearchForSession", selectedSearchCriteria);
				getSession().setAttribute("selectedSearchCriteriaSession", cMap);
				return SUCCESS;
			}
	}


	/**
	 * soSearchPaginationLoad returns the next page requested
	 * @return
	 * @throws Exception
	 */
	public String soSearchPaginationLoad() throws Exception {

		getRequest().setAttribute("pbFilterId", getRequest().getParameter("pbFilterId"));

		LoginCredentialVO lvo = get_commonCriteria().getSecurityContext().getRoles();
	
		if(null == lvo) {
			return "out";
		}
				
		CriteriaHandlerFacility criteriaHandler = loadCriteria(false);
		//SL-20379
		String tab = (String) getAttribute("tab");
		/*_handleSearchCriteria(criteriaHandler);
		handleSortCriteria(criteriaHandler);
		handlePagingCriteria(criteriaHandler);*/
		_handleSearchCriteria(criteriaHandler, tab);
		handleSortCriteria(criteriaHandler, tab);
		handlePagingCriteria(criteriaHandler, tab);

		getSearchList(criteriaHandler, (List<String>) getSession().getAttribute(Constants.SESSION.SO_SEARCH_RESULT_IDS));
		String wfFlag = getRequest().getParameter("wfFlag");
		if(wfFlag != null) {
			this.wfFlag =  wfFlag;
		}
	 	
		return SUCCESS;
	}
	
	protected void getSearchList(CriteriaHandlerFacility criteriaHandler, List<String> ids) {
		
		PaginationVO paginationVO = null;
		List<ServiceOrderDTO> soSearchList = new ArrayList<ServiceOrderDTO>();
		
		if (null != ids && !ids.isEmpty()){
			
			//SL-20379 : need to check again
			getSession().setAttribute(Constants.SESSION.SO_SEARCH_RESULT_IDS,ids);
			
			//SL-20379
			//criteriaHandler.getSearchingCriteria().setSoIds(ids);
			//ServiceOrderMonitorResultVO resultVO = soMonitorDelegate.getSoSearchList(criteriaHandler.getCriteria());
			String tab = (String) getAttribute("tab");
			criteriaHandler.getSearchingCriteria(tab).setSoIds(ids);
			CriteriaMap cMap = criteriaHandler.getCriteria(tab);
			CriteriaMap newMap = new CriteriaMap();
		    newMap.put(FILTER_CRITERIA_KEY, cMap.get(FILTER_CRITERIA_KEY+"_"+tab));
			newMap.put(SORT_CRITERIA_KEY, cMap.get(SORT_CRITERIA_KEY+"_"+tab));
			newMap.put(PAGING_CRITERIA_KEY, cMap.get(PAGING_CRITERIA_KEY+"_"+tab));
			newMap.put(ORDER_CRITERIA_KEY, cMap.get(ORDER_CRITERIA_KEY+"_"+tab));
			newMap.put(SEARCH_CRITERIA_KEY, cMap.get(SEARCH_CRITERIA_KEY+"_"+tab));
			ServiceOrderMonitorResultVO resultVO = soMonitorDelegate.getSoSearchList(newMap);
			
			if (null != resultVO){
				soSearchList = resultVO.getServiceOrderResults();
				if(null!= soSearchList && 0 != soSearchList.size()) {
					setAttribute("searchCount", ids.size());
				}
				paginationVO = resultVO.getPaginationVO();
			}
		} else {
			getSession().removeAttribute(Constants.SESSION.SO_SEARCH_RESULT_IDS);
			//Setting the search count to zero by default
			setAttribute("searchCount", "0"); 
		}
		//SL 15642 Start-Changes for permission based price display in Service Order Monitor
		if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.PROVIDER))
		{
			if(get_commonCriteria().getSecurityContext().getRoleActivityIdList().containsKey("59"))
			{
				getSession().setAttribute("viewOrderPricing",true);
			}
			else
			{
				getSession().setAttribute("viewOrderPricing",false);
			}
			
			//SL-21645 Code below to show max price			
			if(null!= soSearchList && 0 != soSearchList.size()) {
				for (ServiceOrderDTO serviceOrderDTO : soSearchList) {
					if("Accepted".equals(serviceOrderDTO.getStatusString())){
						EstimateVO estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(serviceOrderDTO.getId(),get_commonCriteria().getCompanyId(),get_commonCriteria().getVendBuyerResId());						
						if(null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")){
							serviceOrderDTO.setSpendLimitTotalCurrencyFormat("$"+estimateVo.getTotalPrice());
						}
					}
				}
			}			
		}
		
		//SL-21801: get latest max price
				if(get_commonCriteria().getRoleType().equalsIgnoreCase(OrderConstants.BUYER)){
					if(null!= soSearchList && 0 != soSearchList.size() && get_commonCriteria().getCompanyId().equals(3333)) {
						for (ServiceOrderDTO serviceOrderDTO : soSearchList) {
							if("Accepted".equals(serviceOrderDTO.getStatusString())){
								EstimateVO estimateVo = soMonitorDelegate.getServiceOrderEstimationDetails(serviceOrderDTO.getId());						
								if(null != estimateVo && null != estimateVo.getBuyerRefValue() && estimateVo.getBuyerRefValue().equals("ESTIMATION")){
									serviceOrderDTO.setSpendLimitTotalCurrencyFormat("$"+estimateVo.getTotalPrice());
								}
							}
						}
					}	
				}
				
		//SL 15642 
		setAttribute("soSearchList", soSearchList);
		setAttribute("paginationVO", paginationVO);
		setAttribute("searchString", getSearchValue());
		setAttribute("searchType", getSearchType());
		//SL-20379
		//resetStartingIndexPagingCriteria(criteriaHandler);
		resetStartingIndexPagingCriteria(criteriaHandler, tab);
		
		return;
	}
	/**
	 * This ajax call populates all State value for Advance Search
	 * @return String
	 */
	public String getStatesAjax()
	{
		try {			
			
			List<LookupVO> stateList = new ArrayList<LookupVO>();
			stateList = soMonitorDelegate.getNotBlackedOutStateCodes();
			getRequest().setAttribute("stateList", stateList);
			return SUCCESS;
		} catch (Exception e) {
			//logger.error("Error finding SPN for the spn ID" + model.getSpnHeader().getSpnId(), e);
			return ERROR;
		}
	}
	/**
	 * This ajax call populates all State value for Advance Search
	 * @return String
	 */
	public String getSkillsAjax()
	{
		try {			
			
			List<LookupVO> skillList = new ArrayList<LookupVO>();
			skillList = soMonitorDelegate.getSkills();
			getRequest().setAttribute("skillList", skillList);
			return SUCCESS;
		} catch (Exception e) {
			//logger.error("Error finding SPN for the spn ID" + model.getSpnHeader().getSpnId(), e);
			return ERROR;
		}
	}
	/**
	 * getMarketList returns the next page requested
	 * @return
	 * @throws Exception
	 */
	public String getMarketList() throws Exception {
		try{
			String startIndex = getRequest().getParameter("sIndex");
			String endIndex = getRequest().getParameter("eIndex");
			// to be removed after correcting the market
			if(null==startIndex){
				startIndex = "A";
				endIndex = "E";
			}
			List<LookupVO> marketList= soMonitorDelegate.getMarketsByIndex(startIndex,endIndex);
			getRequest().setAttribute("marketList", marketList);
			return SUCCESS;
		}catch (Exception e) {
			//logger.error("Error finding markets"+ e);
			return ERROR;
		}
	}
	/**
	 * This method is used to fetch all the main Service Categories 
	 */
	public String loadMainCategories(){
		ArrayList<SkillNodeVO> arrMainCategoryList = null;
		try {
			arrMainCategoryList = soMonitorDelegate.loadMainCategories();
			setAttribute("MainCategoriesList", arrMainCategoryList);
			return SUCCESS;
		} catch (Exception e) {
			//logger.error("Error in loadMainCategories" +  e);
			return ERROR;
		}
	}
	/**
	 * This method is used to fetch all the Service Categories and Sub Categories
	 */
	public String loadCategoryAndSubCategories(){
		ArrayList<SkillNodeVO> arrCategoryList = null;
		Integer selectedId = new Integer(getRequest().getParameter("selectedId"));
		try {
			arrCategoryList = soMonitorDelegate.loadCategoryAndSubCategories(selectedId);
			setAttribute("CategoriesList", arrCategoryList);
			return SUCCESS;
		} catch (Exception e) {
			//logger.error("Error in loadCategoryAndSubCategories" +  e);
			return ERROR;
		}
	}
	/**
	 * This method is used to fetch all the Substatuses for a ststus id
	 */
	public String loadSubStatuses(){
		try {
			Integer statusId = new Integer(getRequest().getParameter("subStatId"));
			List<LookupVO> serviceOrderStatusVOList = null;
			serviceOrderStatusVOList = soMonitorDelegate.getSubStatusList(statusId);
			setAttribute("subStatusList", serviceOrderStatusVOList);
			return SUCCESS;
		} catch (Exception e) {
			//logger.error("Error in loadCategoryAndSubCategories" +  e);
			return ERROR;
		}
		
	}
	public String saveSearchFilter() throws Exception{
		
		String filterName = getRequest().getParameter("searchFilterName");
		String selectedSearchCriteria = getRequest().getParameter("selectedSearchCriteria");
		SearchFilterVO searchFilterVO =	new SearchFilterVO();		
		SecurityContext sContext = get_commonCriteria().getSecurityContext();
		Map<String, String> searchFilterMap = mapSearchFilter();
		
		searchFilterVO.setFilterName(filterName.trim());
		searchFilterVO.setTemplateHtmlContent(selectedSearchCriteria);
		searchFilterVO.setEntityId(sContext.getCompanyId());
		searchFilterVO.setRoleId(sContext.getRoleId());		
		searchFilterVO.setTemplateValue(searchFilterMap.toString());
		
		searchFilterVO = soMonitorDelegate.saveSearchFilter(searchFilterVO);
		
		setAttribute("FromFilterSave", "1"); 
		return SUCCESS;
	}
	private Map<String,String> mapSearchFilter(){
		Map<String,String> searchFilterMap = new HashMap<String,String>();
				
		//Mapping States
		String stateCodes = getRequest().getParameter("stateVals");
		searchFilterMap.put(SEARCH_FILTER_STATE_LIST, stateCodes);
		
		//Mapping Skills
		String skills = getRequest().getParameter("skillVals");
		searchFilterMap.put(SEARCH_FILTER_SKILL_LIST, skills);
		
		//Mapping Markets
		String markets = getRequest().getParameter("marketVals");
		searchFilterMap.put(SEARCH_FILTER_MARKET_LIST, markets);
		
		//mapping Custom reference
		String customRefs = getRequest().getParameter("custRefVals");
		searchFilterMap.put(SEARCH_FILTER_CUST_REF_LIST, customRefs );
				
		//mapping checkNumber
		String checkNumbers = getRequest().getParameter("checkNumberVals");
		searchFilterMap.put(SEARCH_FILTER_CHECKNUMBER_LIST, checkNumbers);
				
		//mapping customerName
		String customerNames = getRequest().getParameter("customerNameVals");
		searchFilterMap.put(SEARCH_FILTER_CUSTOMER_NAME_LIST, customerNames);
				
		//mapping phone
		String phones = getRequest().getParameter("phoneVals");
		searchFilterMap.put(SEARCH_FILTER_PHONE_LIST, phones);
				
		//mapping providerFirmId
		String providerFirmIds = getRequest().getParameter("providerFirmIdVals");
		searchFilterMap.put(SEARCH_FILTER_PROVIDER_FIRM_ID_LIST, providerFirmIds);
						
		//mapping serviceOrderId
		String serviceOrderIds = getRequest().getParameter("serviceOrderIdVals");
		searchFilterMap.put(SEARCH_FILTER_SO_ID_LIST, serviceOrderIds);
		
		//mapping serviceProId
		String serviceProIds = getRequest().getParameter("serviceProIdVals");
		searchFilterMap.put(SEARCH_FILTER_SERVICEPRO_ID_LIST, serviceProIds);
						
		//mapping serviceProName
		String serviceProNames = getRequest().getParameter("serviceProNameVals");
		searchFilterMap.put(SEARCH_FILTER_SERVICEPRO_NAME_LIST, serviceProNames);
		
		//mapping zipCode
		String zipCodes = getRequest().getParameter("zipCodeVals");
		searchFilterMap.put(SEARCH_FILTER_ZIP_CODE_LIST, zipCodes);
		
		//mapping startdate
		String startDate = getRequest().getParameter("startDate");
		searchFilterMap.put(SEARCH_FILTER_START_DATE_LIST, startDate);
		
		//mapping enddate
		String endDate = getRequest().getParameter("endDate");
		searchFilterMap.put(SEARCH_FILTER_END_DATE_LIST, endDate);
		
		//mapping Main Category 
		String mainCategoryId = getRequest().getParameter("mainCatId");
		searchFilterMap.put(SEARCH_FILTER_MAIN_CATEGORY_LIST, mainCategoryId);
				
		//mapping Category and SubCategory
		String categoryId = getRequest().getParameter("catAndSubCatId");
		searchFilterMap.put(SEARCH_FILTER_CATEGORY_LIST, categoryId);
		
		//mapping Status and SubStatus
		String statuses = getRequest().getParameter("statusVals");
		searchFilterMap.put(SEARCH_FILTER_STATUS_LIST, statuses);
		
		//mapping Autoclose Rules
		String autocloseRules = getRequest().getParameter("autocloseRuleVals");
		searchFilterMap.put(SEARCH_FILTER_AUTOCLOSE_RULES_LIST, autocloseRules);
				
		return searchFilterMap;
	
	}
	public String deleteSearchFilter() throws Exception{
		
		String filterName = getRequest().getParameter("selectedFilterName");
		
		SearchFilterVO searchFilterVO =	new SearchFilterVO();		
		SecurityContext sContext = get_commonCriteria().getSecurityContext();		
		
		searchFilterVO.setFilterName(filterName);		
		searchFilterVO.setEntityId(sContext.getCompanyId());
		searchFilterVO.setRoleId(sContext.getRoleId());		
		searchFilterVO = soMonitorDelegate.deleteSearchFilter(searchFilterVO);
		
		setAttribute("FromFilterSave", "1"); 
		return SUCCESS;
	}
}
