package com.newco.marketplace.web.action.spn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.owasp.esapi.ESAPI;

import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SPNFilterCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.newco.marketplace.web.utils.CriteriaHandlerFacility;
import com.newco.marketplace.web.utils.TreeMapSortingComparator;

/**
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:23 $
 */

public abstract class SPNBaseAction extends SLWizardBaseAction{
	
	public static String TO_BUILDER = "to_builder";
	public static String SPN_MEMBERS_MANAGER_GRID = "grid";
	public static String SPN_GRID_IFRAME_CONTAINER_VIEW = "grid_iframe_container_view";
	public static String SPN_GRID_INNER_IFRAME_VIEW = "grid_iframe_grid_view";
	
	protected ISPNBuyerDelegate buyerSpnDelegate;
	protected List<SkillNodeVO> skillTreeMainCat;
	protected List<ServiceTypesVO> skillSelection = new ArrayList<ServiceTypesVO>();
	protected List<SkillNodeVO> categorySelection = new ArrayList<SkillNodeVO>();
	protected ArrayList<SkillNodeVO> subCategorySelection = new ArrayList<SkillNodeVO>();
	protected Map<Integer, List<ServiceTypesVO>> skillSelectionMap;
	protected Integer spnID;
	protected List commonResourceList = new ArrayList();
	protected List commonCompanyCredList = new ArrayList();
	private List<SPNHeaderVO> serviceProviderNetworks;
	private List<LookupVO> credentialsList;
	private List<LookupVO> languagesList;
	private Map<String,Object> ratingsList;
	private Map<String,Object> distanceList;
	private Map<String,Object> selectTopProviderList;
	private static final Logger logger = Logger.getLogger("SPNBaseAction");
	private Integer taskIndex = -1;
	private Integer delIndex = -1;
	private Integer mainServiceCategoryId = -1;
	private String credentialId;
	private static final long serialVersionUID = 806832717842681326L;
	
	
	protected void _setDefaultCriteria(Integer searchType, Integer aSpn) {
		CriteriaHandlerFacility facility 	= CriteriaHandlerFacility.getInstance();
		facility.resetAllCriteria();
		PagingCriteria pc 					= new PagingCriteria();
		SortCriteria   sc 					= new SortCriteria();
		
		SPNFilterCriteria fc 				= new SPNFilterCriteria();
		OrderCriteria  oc 					= new OrderCriteria();
		SearchCriteria sCriteria 			= new SearchCriteria();
		sCriteria.setSearchType(searchType.toString());
		sCriteria.setSearchValue(aSpn.toString());
		pc.configureForZeroBased();
		oc.setVendBuyerResId(get_commonCriteria().getVendBuyerResId());
		   facility.initFacility(fc,
				   				 sc,
				   				 pc,
				   				 oc,
				   				sCriteria);
		}
	
	
	protected PagingCriteria _handlePagingCriteria(CriteriaHandlerFacility facility) {
		PagingCriteria pagingCriteria = facility.getPagingCriteria();
		
		if (facility != null) {
			if (pagingCriteria != null) {
				if (getRequest().getParameter("startIndex") != null) {
					if (!getRequest().getParameter("startIndex").equals("")) {
						String l = getRequest().getParameter("startIndex");
						pagingCriteria.setStartIndex(new Integer(getRequest()
								.getParameter("startIndex")).intValue());
					}
				}
				if (getRequest().getParameter("endIndex") != null) {
					if (!getRequest().getParameter("endIndex").equals("")) {
						String k = getRequest().getParameter("endIndex");
						pagingCriteria.setEndIndex(new Integer(getRequest()
								.getParameter("endIndex")).intValue());
					}
				}
				if (getRequest().getParameter("pageSize") != null) {
					if (!getRequest().getParameter("pageSize").equals("")) {
						String k = getRequest().getParameter("pageSize");
						pagingCriteria.setPageSize(new Integer(getRequest()
								.getParameter("pageSize")).intValue());
					}
				}
				facility.addCriteria(OrderConstants.PAGING_CRITERIA_KEY, pagingCriteria);
				//facility.
				//facility.u
			}
		}
		return pagingCriteria;
	}
	
	
	protected SPNFilterCriteria _handleFilterCriteria(CriteriaHandlerFacility facility, Integer filterCriteriaId) {
		SPNFilterCriteria filterCriteria = null;
		if (null != facility) {
			filterCriteria =(SPNFilterCriteria) facility.getFilterCriteria();
			if (null != filterCriteria) {
				if(filterCriteriaId.intValue() == -1) {
					filterCriteria.setAllMarkets(true);
					filterCriteria.setMarketId(null);
				}
				else
				{
					filterCriteria.setAllMarkets(false);
					filterCriteria.setMarketId(filterCriteriaId);
				}
			}
			facility.addCriteria(OrderConstants.FILTER_CRITERIA_KEY, filterCriteria);
		}
		return filterCriteria;
	}
	
	
	protected void loadMarkets() {
		if(getSession().getAttribute("all_markets") == null){
			getSession().setAttribute("all_markets", getLookupManager().getMarkets());
		}
	}
	
	protected void loadAllActiveSPNetwork() {
		try {
			getSession().setAttribute("all_serviceProviderNetworks", buyerSpnDelegate.loadAllActiveSPNetwork());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void populateLookup() {
		List<LookupVO> credentials = null;
		try {
			if (getSession().getServletContext().getAttribute(ProviderConstants.CREDENTAILS_LIST) != null) {
				credentials = (List<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.CREDENTAILS_LIST);
			}else{
				credentials = getLookupManager().getCredentials();
				getSession().setAttribute(ProviderConstants.CREDENTIAL_CATEGORY_LIST, credentials);
			}
			setCredentialsList(credentials);
		} catch (BusinessServiceException e) {
			logger.error("Unable to get Credentials from ExploreTheMarketPlaceSearchAction " +
					"--> populateLookup()",e);
		}

		List<LookupVO> languages = null;
		try {
			if (getSession().getServletContext().getAttribute(ProviderConstants.LANGUAGES_LIST) != null) {
				languages = (List<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.LANGUAGES_LIST);
			}else{
				languages = getLookupManager().getLanguages();
			}
			setLanguagesList(languages);
		} catch (BusinessServiceException e) {
			logger.error("Unable to get languages from ExploreTheMarketPlaceSearchAction " +
					"--> populateLookup()",e);
		}

		TreeMap<String, Object> ratingsList = new TreeMap<String, Object>();
		ratingsList.put("1.0", "Greater than 1 Star");
		ratingsList.put("2.0", "Greater than 2 Star");
		ratingsList.put("3.0", "Greater than 3 Star");
		ratingsList.put("3.5", "Greater than 3.5 Star");
		ratingsList.put("4.0", "Greater than 4 Star");
		ratingsList.put("4.5", "Greater than 4.5 Star");
		setRatingsList(ratingsList);

		Map<String, Object> distanceList = new TreeMap<String, Object>(
				new TreeMapSortingComparator());
		distanceList.put("05", "Less than 5 miles");
		distanceList.put("10", "Less than 10 miles");
		distanceList.put("15", "Less than 15 miles");
		distanceList.put("20", "Less than 20 miles");
		distanceList.put("25", "Less than 25 miles");
		distanceList.put("30", "Less than 30 miles");
		distanceList.put("35", "Less than 35 miles");
		distanceList.put("40", "Less than 40 miles");
		distanceList.put("50", "Less than 50 miles");
		distanceList.put("75", "Less than 75 miles");
		distanceList.put("100", "Less than 100 miles");
		distanceList.put("125", "Less than 125 miles");
		setDistanceList(distanceList);

		TreeMap<String, Object> selectTopProviderList = new TreeMap<String, Object>();
		selectTopProviderList.put("0", "Please Select");
		selectTopProviderList.put("05", "5");
		selectTopProviderList.put("10", "10");
		selectTopProviderList.put("15", "15");
		selectTopProviderList.put("20", "20");
		selectTopProviderList.put("25", "25");
		selectTopProviderList.put("30", "30");
		setSelectTopProviderList(selectTopProviderList);
	}
	
	
	public String credentialCategory(){
		String id = "";
		String idVal = "";
		String desc = "";
		Integer credentialType = Integer.parseInt(getCredentialId());
		getSession().setAttribute(ProviderConstants.SELECTED_CREDENTIAL_ID,credentialType );
		
		List<LookupVO> credentialCategory = null;
		try {
			credentialCategory = getLookupManager().getCredentialCategory(credentialType);
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		getSession().setAttribute(ProviderConstants.CREDENTIAL_CATEGORY_LIST, credentialCategory);				

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");	
		StringBuffer xmlString = new StringBuffer("<message_output>");//------------Start XML String
		

		for (LookupVO lookupVo : credentialCategory){
			if(lookupVo.getId()!=null){
			id = lookupVo.getId().toString();
			idVal = ESAPI.encoder().encodeForXML(id);
			}
			desc = ESAPI.encoder().encodeForXML(lookupVo.getDescr());
			xmlString.append("<credCategory>");
			xmlString.append("<categoryId>" + idVal + "</categoryId>");
			xmlString.append("<categoryName>" + desc+ "</categoryName>");
			xmlString.append("</credCategory>");
		}
		xmlString.append("</message_output>");//------------End XML String
		try {
			response.getWriter().write(xmlString.toString());			
		} catch (IOException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return NONE;
	}
	
	protected List<LookupVO> loadSubCatData( Integer keyId) {
		
		List<LookupVO> credentialCategory = null;
		try {
			credentialCategory = getLookupManager().getCredentialCategory(keyId);
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return credentialCategory;
	}
	
	public List<ServiceTypesVO> getSkillSelection() {
		List<ServiceTypesVO> skillSelectionFromSession = 
				(List<ServiceTypesVO>) getSession().getAttribute("skillSelection");
		if(skillSelectionFromSession != null && skillSelectionFromSession.get(0) != null){
			return skillSelectionFromSession;
		}
		return skillSelection;
	}


	
	public List<SkillNodeVO> getCategorySelection() {

		List<SkillNodeVO> categorySelectionFromSession = 
			(List<SkillNodeVO>) getSession().getAttribute("categorySelection");
		if(categorySelectionFromSession != null && categorySelectionFromSession.get(0) != null){
			return categorySelectionFromSession;
		}
	
		return categorySelection;
	}

		
	public List<SkillNodeVO> getSubCategorySelection() {
		List<SkillNodeVO> subCategorySelectionFromSession = 
			(List<SkillNodeVO>) getSession().getAttribute("subCategorySelection");
		if(subCategorySelectionFromSession != null && subCategorySelectionFromSession.get(0) != null){
			return subCategorySelectionFromSession;
		}
		
		return subCategorySelection;	
	}
	
	public Integer getDelIndex() {
		return delIndex;
	}
	public void setDelIndex(Integer delIndex) {
		this.delIndex = delIndex;
	}
	public Integer getMainServiceCategoryId() {
		return mainServiceCategoryId;
	}
	public void setMainServiceCategoryId(Integer mainServiceCategoryId) {
		this.mainServiceCategoryId = mainServiceCategoryId;
	}
	public Integer getTaskIndex() {
		return taskIndex;
	}
	public void setTaskIndex(Integer taskIndex) {
		this.taskIndex = taskIndex;
	}
	
	public void setCategorySelection(ArrayList<SkillNodeVO> categorySelection) {
		this.categorySelection = categorySelection;
	}
	
	public void setSkillSelection(ArrayList<ServiceTypesVO> skillSelection) {
		this.skillSelection = skillSelection;
	}
	
	public Map<Integer, List<ServiceTypesVO>> getSkillSelectionMap() {
		return (Map<Integer, List<ServiceTypesVO>>)getSession().getAttribute("skillSelectionMap");
	}

	public void setSkillSelectionMap(
			Map<Integer, List<ServiceTypesVO>> skillSelectionMap) {
		this.skillSelectionMap = skillSelectionMap;
	}
	public List<SkillNodeVO> getSkillTreeMainCat() {
		return (ArrayList<SkillNodeVO>)getSession().getAttribute("mainServiceCategory");
	}
	public void setSkillTreeMainCat(ArrayList<SkillNodeVO> skillTreeMainCat) {
		this.skillTreeMainCat = skillTreeMainCat;
	}
	
	public void setSubCategorySelection(ArrayList<SkillNodeVO> subCategorySelection) {
		this.subCategorySelection = subCategorySelection;
	}

	public List<LookupVO> getCredentialsList() {
		return credentialsList;
	}

	public void setCredentialsList(List<LookupVO> credentialsList) {
		this.credentialsList = credentialsList;
	}

	public Map<String, Object> getDistanceList() {
		return distanceList;
	}

	public void setDistanceList(Map<String, Object> distanceList) {
		this.distanceList = distanceList;
	}

	public List<LookupVO> getLanguagesList() {
		return languagesList;
	}

	public void setLanguagesList(List<LookupVO> languagesList) {
		this.languagesList = languagesList;
	}

	public Map<String, Object> getRatingsList() {
		return ratingsList;
	}

	public void setRatingsList(Map<String, Object> ratingsList) {
		this.ratingsList = ratingsList;
	}

	public Map<String, Object> getSelectTopProviderList() {
		return selectTopProviderList;
	}

	public void setSelectTopProviderList(Map<String, Object> selectTopProviderList) {
		this.selectTopProviderList = selectTopProviderList;
	}


	public String getCredentialId() {
		return credentialId;
	}


	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}


	public Integer getSpnID() {
		return spnID;
	}


	public void setSpnID(Integer spnID) {
		this.spnID = spnID;
	}

	public List getServiceProviderNetworks() {
		return serviceProviderNetworks;
	}

	public void setServiceProviderNetworks(List<SPNHeaderVO> serviceProviderNetworks) {
		this.serviceProviderNetworks = serviceProviderNetworks;
	}


	protected Map loadCatNames() throws Exception {
		skillTreeMainCat = fetchDelegate.getSkillTreeMainCategories();
		Iterator<SkillNodeVO> i = skillTreeMainCat.iterator();
		SkillNodeVO singleSkillNode;
		Map<Integer, String> mainServiceCategoryNamesMap = new HashMap<Integer, String>();
		skillSelectionMap = new HashMap();
		while (i.hasNext()) {
			singleSkillNode = i.next();
			mainServiceCategoryNamesMap.put(singleSkillNode.getNodeId(),
					singleSkillNode.getNodeName());
			if (singleSkillNode.getServiceTypes() != null) {
				skillSelectionMap.put(singleSkillNode.getNodeId(),
						new ArrayList(singleSkillNode.getServiceTypes()));
			}
		}

		return mainServiceCategoryNamesMap;
	}

	protected SPNBuilderFormDTO setMainCategoryName(SPNBuilderFormDTO formDTO) throws Exception{
		
		Map categoryIdNameMap = loadCatNames();
		if (categoryIdNameMap != null) {
			
			String name = (String)categoryIdNameMap.get(formDTO.getTheCriteria()
					.getMainServiceCategoryId());
			
			formDTO.getTheCriteria().setMainServiceCategoryName(name);
		}
		return formDTO;

	}
	
	/**
	 * first get the info with Id s & name into a map, then set names into respective DTO fields
	 * @param mainCategoryId
	 * @param spnBuilderFormDTO
	 * @return
	 * @throws Exception
	 */
	protected SPNBuilderFormDTO loadCriteriaSkillInfo(Integer mainCategoryId, SPNBuilderFormDTO spnBuilderFormDTO) throws Exception{
		
		List<SkillNodeVO> categorySelectionFromSession = (List<SkillNodeVO>) getSession()
																	.getAttribute("categorySelection");
		
		if (categorySelectionFromSession == null) {
			setCategorySelection(fetchDelegate
					.getSkillTreeCategoriesOrSubCategories(mainCategoryId));
			setAttribute("categorySelection", getCategorySelection());
		}
		
		Iterator<SkillNodeVO> i = getCategorySelection().iterator();
		
		SkillNodeVO singleSkillNode;
		Map<Integer, String> mainSkillNamesMap = new HashMap<Integer, String>();
		
		while (i.hasNext()) {
			singleSkillNode = i.next();
			mainSkillNamesMap.put(singleSkillNode.getNodeId(),
					singleSkillNode.getNodeName());
		}
		
		// set the name retrieved from Map given id
		spnBuilderFormDTO = setCategorySkillsName(mainSkillNamesMap, spnBuilderFormDTO, mainCategoryId);
		
		
/*		SOTaskDTO taskDTO = (SOTaskDTO)spnBuilderFormDTO.getTheCriteria().getTasks().get(0);
		Integer categoryId = taskDTO.getCategoryId();
		
		ServiceTypesVO serviceTypeVONode;
		Map<Integer, String> serviceTypeIdNameMap = new HashMap<Integer, String>();
		
		if(!skillSelectionMap.isEmpty() && skillSelectionMap.get(mainCategoryId)!= null){
			Iterator<ServiceTypesVO> serviceTypeIt = skillSelectionMap.get(mainCategoryId).iterator();
						
			while (serviceTypeIt.hasNext()) {
				serviceTypeVONode = serviceTypeIt.next();
				serviceTypeIdNameMap.put(serviceTypeVONode.getServiceTypeId(), serviceTypeVONode.getDescription());

			}
			
		}

		String name = mainSkillNamesMap.get(categoryId);
		spnBuilderFormDTO.getTheCriteria().getTasks().get(0).setCategory(name);
		
		List<SOTaskDTO> taskList = spnBuilderFormDTO.getTheCriteria().getTasks();
		List<SOTaskDTO> tempTaskList = new ArrayList<SOTaskDTO>();
		
		for(SOTaskDTO taskDto : taskList){
			taskDto.setCategory(name);
			if(serviceTypeIdNameMap.get(taskDto.getSkillId())!= null){
				String skill = (String)serviceTypeIdNameMap.get(taskDto.getSkillId());
				taskDto.setSkill(skill);				
			}

			tempTaskList.add(taskDto);
		}
		spnBuilderFormDTO.getTheCriteria().addTaskList(tempTaskList);*/
		return spnBuilderFormDTO;
		
	}
	
	protected SPNBuilderFormDTO setCategorySkillsName(Map<Integer, String> mainSkillNamesMapRef,SPNBuilderFormDTO spnBuilderFormDTO, Integer mainCategoryId){
		if(spnBuilderFormDTO.getTheCriteria().getTasks() != null &&
				spnBuilderFormDTO.getTheCriteria().getTasks().size() == 0 )
		{
			return spnBuilderFormDTO;
		}
		SOTaskDTO taskDTO = (SOTaskDTO)spnBuilderFormDTO.getTheCriteria().getTasks().get(0);
		Integer categoryId = taskDTO.getCategoryId();
		
		ServiceTypesVO serviceTypeVONode;
		Map<Integer, String> serviceTypeIdNameMap = new HashMap<Integer, String>();
		
		if(!skillSelectionMap.isEmpty() && skillSelectionMap.get(mainCategoryId)!= null){
			Iterator<ServiceTypesVO> serviceTypeIt = skillSelectionMap.get(mainCategoryId).iterator();
						
			while (serviceTypeIt.hasNext()) {
				serviceTypeVONode = serviceTypeIt.next();
				serviceTypeIdNameMap.put(serviceTypeVONode.getServiceTypeId(), serviceTypeVONode.getDescription());

			}
			
		}

		String name = mainSkillNamesMapRef.get(categoryId);
		spnBuilderFormDTO.getTheCriteria().getTasks().get(0).setCategory(name);
		
		List<SOTaskDTO> taskList = spnBuilderFormDTO.getTheCriteria().getTasks();
		List<SOTaskDTO> tempTaskList = new ArrayList<SOTaskDTO>();
		
		for(SOTaskDTO taskDto : taskList){
			taskDto.setCategory(name);
			if(serviceTypeIdNameMap.get(taskDto.getSkillId())!= null){
				String skill = (String)serviceTypeIdNameMap.get(taskDto.getSkillId());
				taskDto.setSkill(skill);				
			}

			tempTaskList.add(taskDto);
		}
		spnBuilderFormDTO.getTheCriteria().addTaskList(tempTaskList);
		
		return spnBuilderFormDTO;
				
		
	}
	/**
	 * set language description
	 * @param spnBuilderFormDTO
	 * @return
	 */
	protected SPNBuilderFormDTO setLanguageDesc(SPNBuilderFormDTO spnBuilderFormDTO){
		
		/** set Language name */
		List<LookupVO> languageList = getLanguagesList();
		for(LookupVO langVO : languageList ){
			if(langVO!= null && spnBuilderFormDTO.getTheCriteria().getLanguages()!= null ){
				if((spnBuilderFormDTO.getTheCriteria().getLanguages().intValue()) == (langVO.getId().intValue())){
					spnBuilderFormDTO.getTheCriteria().setLanguageDescr(langVO.getDescr());
				}
			}
			
		}
		return spnBuilderFormDTO;
		
	}


	public ISPNBuyerDelegate getBuyerSpnDelegate() {
		return buyerSpnDelegate;
	}


	public void setBuyerSpnDelegate(ISPNBuyerDelegate buyerSpnDelegate) {
		this.buyerSpnDelegate = buyerSpnDelegate;
	}

	
}
