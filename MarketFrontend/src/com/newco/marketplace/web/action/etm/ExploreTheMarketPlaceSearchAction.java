package com.newco.marketplace.web.action.etm;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceFilterCriteria;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderRecord;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.widgets.AjaxCredentialCategoryActionHelper;
import com.newco.marketplace.web.delegates.IExploreTheMarketplaceDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.CheckboxDTO;
import com.newco.marketplace.web.dto.ETMSearchDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.security.NonSecurePage;
import com.newco.marketplace.web.utils.ETMCriteriaHandlerFacility;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class ExploreTheMarketPlaceSearchAction extends SLBaseAction implements Preparable, OrderConstants, ModelDriven<ETMSearchDTO> {
	
	private static final long serialVersionUID = -4416540784274128832L;

	private static final Logger logger = Logger.getLogger(ExploreTheMarketPlaceSearchAction.class);
	
	protected ISOWizardFetchDelegate wizardFetchDelegate;
	protected ILookupDelegate lookupManager;
	private ETMSearchDTO etmSearchDTO = new ETMSearchDTO();
	protected IExploreTheMarketplaceDelegate exploreTheMarketplaceDelegate;
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		populateLookup();
		maps();
		//Added for Incident 4148358(Sl-19820)
		// Clearing the session objects of Service Order Wizard
		logger.debug("Invoking getStartPointAndInvalidate()" );
		String soId = getParameter("soId");
		setAttribute(OrderConstants.SO_ID, soId);
		SOWSessionFacility.getInstance().getStartPointAndInvalidate(wizardFetchDelegate, get_commonCriteria().getSecurityContext());
	}
	
	public String execute() throws Exception {
		getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "true");
		getSession().removeAttribute("validationErrors");	
		getSession().removeAttribute("soProviderSearchList");
		return "success";
	}

	private String getUserName() {
		String userName = "";
		ServiceOrdersCriteria context = get_commonCriteria();
		if(context != null) {
			userName = context.getTheUserName();
		}
		return userName;
	}
	
	public String search() throws Exception {
		logger.debug("----Start of ExploreTheMarketPlaceSearchAction.search----");
		String searchQueryKey = "";
		String userName = "";
		//PagingCriteria criteriaNew=(PagingCriteria)getSession().getAttribute("ETM_PAGING_CRITERIA_KEY");
		try {
			userName = getUserName();
				
			//Clean all search criteria previously entered
			setDefaultETMCriteria();
			
			etmSearchDTO = (ETMSearchDTO)getModel();
			etmSearchDTO.validate("search");						
			if (etmSearchDTO.getErrors().isEmpty()) {
				ETMCriteriaHandlerFacility etmCriteriaHandler = loadETMCriteria();
				logger.debug("SearchKey: " + etmCriteriaHandler.getSearchKey());
				handleETMSearchCriteria(etmCriteriaHandler,etmSearchDTO);
				getSession().removeAttribute("soProviderSearchList");
				searchQueryKey = getCompleteSearchList(etmCriteriaHandler, userName);
				etmCriteriaHandler.addSearchKey(searchQueryKey);
				etmCriteriaHandler.addFilterZipCd(etmSearchDTO.getZipCd());
				//Clear sorting session parameters
				getSession().removeAttribute("sortColumnName");
				getSession().removeAttribute("sortOrder");
				getSession().removeAttribute("validationErrors");
				//SL-21281
			   // max distance available in the search
				Integer searchDistance=exploreTheMarketplaceDelegate.getDefaultDistance();
				getSession().setAttribute("ETMSearchDistance", searchDistance);
				
			} else {
				logger.debug("Errors in validate");			
				getSession().setAttribute("validationErrors", etmSearchDTO.getErrors());
				getSession().removeAttribute("soProviderSearchList");
				getSession().removeAttribute("paginationVO");
				getSession().removeAttribute(ETM_FILTER_ZIP);
				getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "true");
				getSession().removeAttribute("ETMSearchDistance");
			}
		} catch (BusinessServiceException e) {
			logger.info("Exception in searching the providers: ", e);
		}
		
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.search----");
		return "iframe_results";
	}
	
	protected String getCompleteSearchList(ETMCriteriaHandlerFacility criteriaHandler, String userName) throws BusinessServiceException {
		logger.debug("----Start of ExploreTheMarketPlaceSearchAction.getCompleteSearchList----");
		PaginationVO paginationVO = null;
		List<ETMProviderRecord> soProviderSearchList = new ArrayList<ETMProviderRecord>();
		String searchQueryKey = ""; 
		
		//Call the get Provider list method with CriteriaMap as input and returns provider search results
		ETMProviderSearchResults resultVO = exploreTheMarketplaceDelegate.getCompleteProviderList(criteriaHandler.getCriteria(), userName);
			
		if (null != resultVO) {
			soProviderSearchList = resultVO.getSearchResults();
			paginationVO = resultVO.getPaginationVo();
			getSession().removeAttribute("paginationVO");
			searchQueryKey = resultVO.getSearchQueryKey();
		}
		
		setAttribute("soProviderSearchList", soProviderSearchList);
		getSession().setAttribute("soProviderSearchList", soProviderSearchList);
		if (soProviderSearchList != null) {
			getSession().setAttribute("isProvidersLoaded","1");
		}

		// This attribute enables or disables all the widget filters
		if(soProviderSearchList != null && !soProviderSearchList.isEmpty()) {
			getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "false");
		} else if(SLStringUtils.isNullOrEmpty(searchQueryKey)) {
			getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "true");
		} else {
			getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "true");
		}
		
		setAttribute("paginationVO", paginationVO);
		getSession().setAttribute("paginationVO", paginationVO);
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.getCompleteSearchList----");
		return searchQueryKey;
	}
	
	protected String getCompleteSearchListPaginateResult(ETMCriteriaHandlerFacility criteriaHandler, String userName) throws BusinessServiceException {
		logger.debug("----Start of ExploreTheMarketPlaceSearchAction.getCompleteSearchList----");
		PaginationVO paginationVO = null;
		List<ETMProviderRecord> soProviderSearchList = new ArrayList<ETMProviderRecord>();
		String searchQueryKey = ""; 
		
		//Call the get Provider list method with CriteriaMap as input and returns provider search results
		ETMProviderSearchResults resultVO = exploreTheMarketplaceDelegate.getCompleteProviderListPaginateResult(criteriaHandler.getCriteria(), userName);
			
		if (null != resultVO) {
			soProviderSearchList = resultVO.getSearchResults();
			paginationVO = resultVO.getPaginationVo();
			getSession().removeAttribute("paginationVO");
			searchQueryKey = resultVO.getSearchQueryKey();
		}
		
		setAttribute("soProviderSearchList", soProviderSearchList);
		getSession().setAttribute("soProviderSearchList", soProviderSearchList);
		if (soProviderSearchList != null) {
			getSession().setAttribute("isProvidersLoaded","1");
		}

		// This attribute enables or disables all the widget filters
		if(soProviderSearchList != null && !soProviderSearchList.isEmpty()) {
			getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "false");
		} else if(SLStringUtils.isNullOrEmpty(searchQueryKey)) {
			getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "true");
		} else {
			getSession().setAttribute(OrderConstants.ETM_FILTERS_DISABLED, "true");
		}
		
		setAttribute("paginationVO", paginationVO);
		getSession().setAttribute("paginationVO", paginationVO);
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.getCompleteSearchList----");
		return searchQueryKey;
	}

	
	protected String getCompleteSearchListApplyFilter(CriteriaMap criteriaMap, String userName) throws BusinessServiceException {
		logger.info("----Start of ExploreTheMarketPlaceSearchAction.getCompleteSearchListApplyFilter----");
		PaginationVO paginationVO = null;
		List<ETMProviderRecord> soProviderSearchList = new ArrayList<ETMProviderRecord>();
		String searchQueryKey = ""; 
		ETMProviderSearchResults resultVO = exploreTheMarketplaceDelegate.getCompleteProviderListApplyFilter(criteriaMap, userName);
		if (null != resultVO) {
			searchQueryKey = resultVO.getSearchQueryKey();
		}
		logger.info("----End of ExploreTheMarketPlaceSearchAction.getCompleteSearchListApplyFilter----");
		return searchQueryKey;
	}
	/**
	 * This method is called using AJAX from right side filter widget on ETM.
	 * This delegates to helper class; since same AJAX is used under SOW->Providers Tab as well (by SOWRefineProvidersAction class)
	 * @return
	 */
	public String credentialCategory() {
		AjaxCredentialCategoryActionHelper credentialHelper = new AjaxCredentialCategoryActionHelper();
		return credentialHelper.credentialCategory(get_commonCriteria(), getLookupManager(), getRequest(), getResponse());
	}
	
	public String sortResultSet() throws Exception {
		logger.debug("----Start of ExploreTheMarketPlaceSearchAction.sortResultSet----");
		String searchQueryKey = "";
		String userName = "";
		try {
			userName = getUserName();
			etmSearchDTO = (ETMSearchDTO)getModel();
			ETMCriteriaHandlerFacility etmCriteriaHandler = loadETMCriteria();
			handleETMSortCriteria(etmCriteriaHandler);
			searchQueryKey = getCriteriaSearchList(etmCriteriaHandler, userName,false);
			etmCriteriaHandler.addSearchKey(searchQueryKey);
			reversehandleETMFilterCriteria(etmCriteriaHandler,etmSearchDTO);
		} catch (BusinessServiceException e) {
			logger.info("Exception in searching the providers: ", e);
		}
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.sortResultSet----");
		return "iframe_results";
	}
	
	public String paginateResultSet() throws Exception {
		logger.debug("----Start of ExploreTheMarketPlaceSearchAction.paginateResultSet----");
		String searchQueryKey = "";
		String userName = "";
		try {
			userName = getUserName();
			etmSearchDTO = (ETMSearchDTO)getModel();
			ETMCriteriaHandlerFacility etmCriteriaHandler = loadETMCriteria();
			handleETMPagingCriteria(etmCriteriaHandler);
			searchQueryKey = getCompleteSearchListPaginateResult(etmCriteriaHandler, userName);
			reversehandleETMFilterCriteria(etmCriteriaHandler,etmSearchDTO);
			etmCriteriaHandler.addSearchKey(searchQueryKey);
		} catch (BusinessServiceException e) {
			logger.info("Exception in searching the providers: ", e);
		}
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.paginateResultSet----");
		return "iframe_results";
	}
	
	public String applyFilter() throws Exception {
      logger.debug("----Start of ExploreTheMarketPlaceSearchAction.applyFilter----");
		String userName = "";
		String searchkey="";
		Boolean filterInd=true;
		try {
			userName = getUserName();
			etmSearchDTO = (ETMSearchDTO)getModel();
			etmSearchDTO.validate("applyfilter");
			if (etmSearchDTO.getErrors().isEmpty()) {
				//getSession().setAttribute(OrderConstants.ETM_PAGING_CRITERIA_KEY, null);
				ETMCriteriaHandlerFacility etmCriteriaHandler = loadETMCriteria();
				handleETMFilterCriteria(etmCriteriaHandler,etmSearchDTO);
				//SL-21281
				if(null!=getSession().getAttribute("ETMSearchDistance")){
				Integer searchDistance=(Integer)getSession().getAttribute("ETMSearchDistance");
				// max distance available in the search
				Integer searchDistanceApplyFilter=null;
				if(null!=etmSearchDTO.getDistance()){
					searchDistanceApplyFilter=Integer.parseInt(etmSearchDTO.getDistance());
				}
                if(null!=searchDistance && null!=searchDistanceApplyFilter && searchDistanceApplyFilter.intValue()>searchDistance.intValue()){
                	searchkey=getCompleteSearchListApplyFilter(etmCriteriaHandler.getCriteria(),userName);
                	etmCriteriaHandler.addSearchKey(searchkey);
                	getSession().setAttribute("ETMSearchDistance",searchDistanceApplyFilter);
				}
				}
				getCriteriaSearchList(etmCriteriaHandler, userName,filterInd);
				getSession().removeAttribute("validationErrors");
				
			} else {
				logger.debug("Errors in validate");
			}
		} catch (BusinessServiceException e) {
			logger.info("Exception in searching the providers: ", e);
		}
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.applyFilter----");
		return "iframe_results";
	}

	protected String getCriteriaSearchList(ETMCriteriaHandlerFacility criteriaHandler, String userName,Boolean filterInd) throws BusinessServiceException {
		logger.debug("----Start of ExploreTheMarketPlaceSearchAction.getFilteredSearchList----");
		PaginationVO paginationVO = null;
		List<ETMProviderRecord> soProviderSearchList = new ArrayList<ETMProviderRecord>();
		String searchQueryKey = null; 
		
		//Call the get filtered Provider list method with CriteriaMap as input and returns provider search results
		ETMProviderSearchResults resultVO = exploreTheMarketplaceDelegate.getCriteriaProviderList(criteriaHandler.getCriteria(), userName,filterInd);
		if (null != resultVO) {
			soProviderSearchList = resultVO.getSearchResults();
			paginationVO = resultVO.getPaginationVo();
			getSession().removeAttribute("paginationVO");
			searchQueryKey = resultVO.getSearchQueryKey();
		}
	
		setAttribute("soProviderSearchList", soProviderSearchList);
		getSession().setAttribute("soProviderSearchList", soProviderSearchList);
		getSession().setAttribute("searchQueryKey", searchQueryKey);
		
		if (soProviderSearchList != null) {
			getSession().setAttribute("isProvidersLoaded","1");
		}
		
		setAttribute("paginationVO", paginationVO);
		getSession().setAttribute("paginationVO", paginationVO);
		logger.debug("----End of ExploreTheMarketPlaceSearchAction.getFilteredSearchList----");
		return searchQueryKey;
	}
	
	public String showResultsIframe() throws Exception {
		if (org.apache.commons.lang.StringUtils.isNotEmpty((String)getSession().getAttribute("isProvidersLoaded"))) {	
			List<ETMProviderRecord> list = (List<ETMProviderRecord>)getSession().getAttribute("soProviderSearchList");
			getRequest().setAttribute("soProviderSearchList", list);
			getSession().removeAttribute("soProviderSearchList");
			getSession().removeAttribute("isProvidersLoaded");
		}
		return "iframe_results";
	}
	
	public ETMSearchDTO getModel() {
		return etmSearchDTO;
	}
	
	public void setModel(ETMSearchDTO etmSearchDTO) {
		this.etmSearchDTO = etmSearchDTO;
	}
 
	private void populateLookup() {
		etmSearchDTO = getModel();
		populateMainCategories();

		populateCredentials();

		populateLanguages();
		
		populateSPNListForBuyer();
		// get list of select provider network in COMPLETE status for given buyerID
		// populateSPNListForBuyer();
		// get minimum ratings list
		populateMinimumRatings();
		// get provider distance list
		populateProviderDistance();
		populateYesNoList();
	}
	
	private void populateYesNoList() {
		List<LookupVO> list = new ArrayList<LookupVO>();
		LookupVO vo = new LookupVO();
		vo.setId(Integer.valueOf(1));
		vo.setDescr("Yes");
		list.add(vo);
		LookupVO vo1 = new LookupVO();
		vo1.setId(Integer.valueOf(0));
		vo1.setDescr("No");
		list.add(vo1);
		etmSearchDTO.setYesNoList(list);
	}

	private void populateLanguages() {
		if (getSession().getServletContext().getAttribute(ProviderConstants.LANGUAGES_LIST) == null) {
			
			List<LookupVO> languages = null;			
			try {
				languages = getLookupManager().getLanguages();
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}			
			getSession().getServletContext().setAttribute(ProviderConstants.LANGUAGES_LIST, languages);				
		}
		etmSearchDTO.setLanguagesList((List<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.LANGUAGES_LIST));
	}

	private void populateCredentials() {
		if (getSession().getServletContext().getAttribute(ProviderConstants.CREDENTAILS_LIST) == null) {
			
			List<LookupVO> credentials = null;
			try {
				credentials = getLookupManager().getCredentials();
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
			getSession().getServletContext().setAttribute(ProviderConstants.CREDENTAILS_LIST, credentials);				
		}
		etmSearchDTO.setCredentialsList((List<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.CREDENTAILS_LIST));
	}

	private void populateMainCategories() {
		try {
			etmSearchDTO.setSkillTreeMainCatList(wizardFetchDelegate.getSkillTreeMainCategories());
		} catch (BusinessServiceException e) {
			logger.error("Unable to get SkillTreeMainCat from ExploreTheMarketPlaceSearchAction --> populateLookup()", e);
		}
	}

	

	
	

	private void populateProviderDistance() {
		List<LookupVO> providerDistanceList = new ArrayList<LookupVO>();
		if(getSession().getServletContext().getAttribute(ProviderConstants.DISTANCE_LIST) == null){
			
			try{
				providerDistanceList = getLookupManager().getProviderDistanceList();
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception while getting distance list and ignoring",e);
			}
			getSession().getServletContext().setAttribute(ProviderConstants.DISTANCE_LIST, providerDistanceList);
		}
		providerDistanceList = (List<LookupVO>) getSession().getServletContext().getAttribute(ProviderConstants.DISTANCE_LIST);
		etmSearchDTO.setDistanceList(providerDistanceList);
	}
	private void populateMinimumRatings() {
		List<LookupVO> minRatingsList = new ArrayList<LookupVO>();
		if(getSession().getServletContext().getAttribute(ProviderConstants.RATING_LIST) == null){
		
			try{
				minRatingsList = getLookupManager().getMinimumRatings();
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception while getting min ratings and ignoring",e);
			}
			getSession().getServletContext().setAttribute(ProviderConstants.RATING_LIST, minRatingsList);
		}
		etmSearchDTO.setRatingsList((List<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.RATING_LIST));
		
	}

	private void populateSPNListForBuyer() {
		if (ActionContext.getContext().getSession().get(ProviderConstants.SPN_LIST) != null) {
			ActionContext.getContext().getSession().remove(ProviderConstants.SPN_LIST);
		}
			
			List<SPNMonitorVO> spnlist = new ArrayList<SPNMonitorVO>();
			List<LookupVO> performanceLevelDropdownList = new ArrayList<LookupVO>();
			try {
				Integer buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
				spnlist = getLookupManager().getSPNetList(buyerId);
				performanceLevelDropdownList = getLookupManager().getPerformanceLevelList();	
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception while getting spn list and ignoring",e);
			}			
			ActionContext.getContext().getSession().put(ProviderConstants.SPN_LIST, spnlist);				
			ActionContext.getContext().getSession().put(ProviderConstants.PERFORMANCE_LEVEL_DROPDOWN_LIST, performanceLevelDropdownList);
			etmSearchDTO.setSpnList(spnlist);
			etmSearchDTO.setPerformanceLevelList(performanceLevelDropdownList);
			//populatePerformanceLevelOptions();
			
	}
	
	private void populatePerformanceLevelOptions()
	{
		if(getRequest().getSession().getAttribute("perfLevelCheckboxes") == null)
		{
			List<CheckboxDTO> checkboxList = new ArrayList<CheckboxDTO>();
			List<LookupVO> perfLevels = (List<LookupVO>)ActionContext.getContext().getSession().get(ProviderConstants.PERFORMANCE_LEVEL_DROPDOWN_LIST);
			CheckboxDTO dto;
			for(LookupVO vo : perfLevels)
			{
				dto = new CheckboxDTO();
				dto.setLabel(vo.getDescr());
				dto.setValue(vo.getId());			
				dto.setSelected(false);
				checkboxList.add(dto);
			}
			getRequest().getSession().setAttribute("perfLevelCheckboxes", checkboxList);
		}
		
		
		
	}

	public ISOWizardFetchDelegate getWizardFetchDelegate() {
		return wizardFetchDelegate;
	}

	public void setWizardFetchDelegate(ISOWizardFetchDelegate wizardFetchDelegate) {
		this.wizardFetchDelegate = wizardFetchDelegate;
	}

	public ILookupDelegate getLookupManager() {
		return lookupManager;
	}

	public void setLookupManager(ILookupDelegate lookupManager) {
		this.lookupManager = lookupManager;
	}
	
	public IExploreTheMarketplaceDelegate getExploreTheMarketplaceDelegate() {
		return exploreTheMarketplaceDelegate;
	}

	public void setExploreTheMarketplaceDelegate(IExploreTheMarketplaceDelegate exploreTheMarketplaceDelegate) {
		this.exploreTheMarketplaceDelegate = exploreTheMarketplaceDelegate;
	}
}
