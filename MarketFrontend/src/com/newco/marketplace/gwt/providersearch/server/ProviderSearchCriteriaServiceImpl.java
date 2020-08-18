package com.newco.marketplace.gwt.providersearch.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLanguageResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.gwt.providersearch.client.GwtFindProvidersDTO;
import com.newco.marketplace.gwt.providersearch.client.GwtRemoteServiceResponse;
import com.newco.marketplace.gwt.providersearch.client.LanguageVO;
import com.newco.marketplace.gwt.providersearch.client.ProviderSearchCriteriaService;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchCriteraVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchProviderResultVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchSkillNodeVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchSkillTypeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;

public class ProviderSearchCriteriaServiceImpl extends RemoteServiceServlet implements ProviderSearchCriteriaService {
	private static final Logger logger = Logger.getLogger(ProviderSearchCriteriaServiceImpl.class.getName());
	//private static final String  STATE_SESSION_OBJECT = "currentState";
	private static final String DEFAULT_ANYNOMS_BUYER_ID = "-1";

		
	ILookupBO myLookupBo;
	IProviderSearchBO myProviderSearchBO;
	
	public ILookupBO getLookupBO(){
		if (myLookupBo == null){
			myLookupBo = (ILookupBO)MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
		}
		return myLookupBo;
	}
	
	public IProviderSearchBO getProviderSearchBO(){
		if (myProviderSearchBO == null){
			myProviderSearchBO = (IProviderSearchBO)MPSpringLoaderPlugIn.getCtx().getBean("providerSearchBO");
		}
		return myProviderSearchBO;
	}
	
	public GwtRemoteServiceResponse getNullSessionResponse() {
		GwtRemoteServiceResponse response = new GwtRemoteServiceResponse();
		response.setValidSession(false);
		return response;
	}
	
	public GwtRemoteServiceResponse getVerticals() {
		GwtRemoteServiceResponse response = null;
		String state = null;
		try {
			CreateServiceOrderFindProvidersDTO dto = getSessionDTO();
			if(dto == null){
				response = getNullSessionResponse();
				return response;
			}
			state = dto.getState();
		} catch (Exception e) {
			logger.info(" Exception getting the state from the session defaulting to il" + e.getMessage());
			state = "IL";
		}
		List<SkillNodeVO> myList = null;
		if(state == null || state == ""){
			state = "IL";
		}		
		
		try{
			myList = getLookupBO().getNotBlackedOutSkillTreeMainCategories(DEFAULT_ANYNOMS_BUYER_ID,state);
		}catch (Exception e) {
			logger.info(" Exception in getNotBlackedOutSkillTreeMainCategories" + e.getMessage());
		}

		List returnList  = new ArrayList();
		for(SkillNodeVO aSkillNodeVo:myList){
			SimpleProviderSearchSkillNodeVO aSimpleSkillNodeVO = new SimpleProviderSearchSkillNodeVO();
			aSimpleSkillNodeVO.setParentSkillNodeId(new Long(aSkillNodeVo.getParentNodeId()).intValue());
			aSimpleSkillNodeVO.setSkillNodeId(aSkillNodeVo.getNodeId());
			aSimpleSkillNodeVO.setSkillNodeDesc(aSkillNodeVo.getNodeName());
			aSimpleSkillNodeVO.setDecriptionContent(aSkillNodeVo.getDecriptionContent());
			returnList.add(aSimpleSkillNodeVO);
		}
		response = new GwtRemoteServiceResponse();
		response.setValidSession(true);
		response.setValueList(returnList);
		return response;
	}

	public GwtRemoteServiceResponse getCatagories(SimpleProviderSearchSkillNodeVO inSkillNodeVO) {
		GwtRemoteServiceResponse response = null;
		
		String state = null;
	
		try {
			CreateServiceOrderFindProvidersDTO dto = getSessionDTO();
			if(dto == null){
				response = getNullSessionResponse();
				return response;
			}
			state = dto.getState();
		} catch (Exception e) {
			logger.info(" Exception getting the state from the session defaulting to il" + e.getMessage());
			state = "IL";
		}
		if(state == null || state == ""){
			state = "IL";
		}
		List<SkillNodeVO> myList = new ArrayList();
		try{
			myList = getLookupBO().getNotBlackedOutSkillTreeCategoriesOrSubCategories(inSkillNodeVO.getSkillNodeId(),DEFAULT_ANYNOMS_BUYER_ID,state);
		}catch (Exception e) {
			logger.info(" Exception in getNotBlackedOutSkillTreeCategoriesOrSubCategories" + e.getMessage());
		}
		List returnList  = new ArrayList();
		for(SkillNodeVO aSkillNodeVo:myList){
			SimpleProviderSearchSkillNodeVO aSimpleSkillNodeVO = new SimpleProviderSearchSkillNodeVO();
			aSimpleSkillNodeVO.setParentSkillNodeId(new Long(aSkillNodeVo.getParentNodeId()).intValue());
			aSimpleSkillNodeVO.setSkillNodeId(aSkillNodeVo.getNodeId());
			aSimpleSkillNodeVO.setSkillNodeDesc(aSkillNodeVo.getNodeName());
			returnList.add(aSimpleSkillNodeVO);
		}
		response = new GwtRemoteServiceResponse();
		response.setValidSession(true);
		response.setValueList(returnList);
		return response;
	}

	public GwtRemoteServiceResponse getSubCatagories(SimpleProviderSearchSkillNodeVO inSkillNodeVO) {
		return getCatagories(inSkillNodeVO);
	}

	public GwtRemoteServiceResponse getSkillTypes(SimpleProviderSearchSkillNodeVO inSkillNodeVO) {
		GwtRemoteServiceResponse response = null;
		String state = null;
		try {
			CreateServiceOrderFindProvidersDTO dto = getSessionDTO();
			if(dto == null){
				response = getNullSessionResponse();
				return response;
			}
			state = dto.getState();
		} catch (Exception e) {
			logger.info(" Exception getting the state from the session defaulting to il" + e.getMessage());
			state = "IL";
		}
		if(state == null || state == ""){
			state = "IL";
		}
		List<ServiceTypesVO> myList = null;
		try{
			myList = getLookupBO().getNotBlackedOutServiceTypes(inSkillNodeVO.getSkillNodeId(),DEFAULT_ANYNOMS_BUYER_ID,state);
		}catch (Exception e) {
			logger.info(" Exception in getNotBlackedOutSkillTreeMainCategories" + e.getMessage());
			state = "IL";
		}
		List returnList  = new ArrayList();
		for(ServiceTypesVO aServiceTypesVO:myList){
			SimpleProviderSearchSkillTypeVO aSkillTypeVO = new SimpleProviderSearchSkillTypeVO();
			aSkillTypeVO.setSkillTypeDescr(aServiceTypesVO.getDescription());
			aSkillTypeVO.setSkillTypeId(aServiceTypesVO.getServiceTypeId());
			returnList.add(aSkillTypeVO);
		}
		response = new GwtRemoteServiceResponse();
		response.setValidSession(true);
		response.setValueList(returnList);
		return response;
	}

	 
	public GwtRemoteServiceResponse getProviderResults(SimpleProviderSearchCriteraVO inSearchVO) {
		GwtRemoteServiceResponse response = new GwtRemoteServiceResponse();
		response.setValidSession(true);
		List myList = new ArrayList();
		response.setValueList(myList);
		try {
		if(inSearchVO.getTheSkillNode().getSkillNodeId()== Integer.parseInt(DEFAULT_ANYNOMS_BUYER_ID)){
			response = new GwtRemoteServiceResponse();
			response.setValidSession(true);
			response.setValueList(myList);
			return response;
		}
		CreateServiceOrderFindProvidersDTO dto = getSessionDTO();
		if(dto == null){
			response = getNullSessionResponse();
			return response;
		}
		inSearchVO.setZip(dto.getZip());
		inSearchVO.setLockedResourceId(dto.getResourceID());
		ProviderSearchCriteriaVO searchCriteria = new ProviderSearchCriteriaVO(); 
		searchCriteria.setBuyerZipCode(Long.parseLong(inSearchVO.getZip()));
		
		// add in a dummylocation
		LocationVO aLocationVO = new LocationVO();
		aLocationVO.setZip(inSearchVO.getZip());
		searchCriteria.setServiceLocation(aLocationVO);
		
		ArrayList<Integer> skillNodeIds = new ArrayList();
		List<Integer> serviceTypeIds = new ArrayList();
		for (int i = 0; i < inSearchVO.getSkillTypes().size(); i++) {
			serviceTypeIds.add((Integer)inSearchVO.getSkillTypes().get(i));
			skillNodeIds.add((Integer)inSearchVO.getTheSkillNode().getSkillNodeId());
		}
		// if we didnt get any service types still search for the skill node
		
		if(skillNodeIds.size() == 0){
			skillNodeIds.add((Integer)inSearchVO.getTheSkillNode().getSkillNodeId());
		}
		searchCriteria.setSkillNodeIds(skillNodeIds);
		searchCriteria.setSkillServiceTypeId(serviceTypeIds);
		searchCriteria.setLockedResourceId(inSearchVO.getLockedResourceId());
		List<ProviderResultVO> myProviders = getProviderSearchBO().getProviderList(searchCriteria);
		
		for(ProviderResultVO myProviderResultVO:myProviders){
			SimpleProviderSearchProviderResultVO aProvVo= new SimpleProviderSearchProviderResultVO();
			aProvVo.setBackgroundCheckStatus(myProviderResultVO.getBackgroundCheckStatus()); // set this as well as following boolean 
			//this boolean is for easy access in  GWT without refereing OrdersConstant.TEAM_CLEAR..blah blah 
			boolean showBgrndCheckFlag = myProviderResultVO.getBackgroundCheckStatus() != null ? ( OrderConstants.TEAM_BACKGROUND_CLEAR ==  myProviderResultVO.getBackgroundCheckStatus().intValue()  ? true : false)   : false;
			aProvVo.setBackgroundCheckClear(showBgrndCheckFlag);
			aProvVo.setCity(myProviderResultVO.getCity());
			aProvVo.setDistance(myProviderResultVO.getDistance());
			aProvVo.setDistanceFromBuyer(myProviderResultVO.getDistanceFromBuyer());
			aProvVo.setResourceId(myProviderResultVO.getResourceId());
			aProvVo.setIsSLVerified(myProviderResultVO.getIsSLVerified());
			aProvVo.setPercentageMatch(myProviderResultVO.getPercentageMatch());
			double starRating = 0.0d;
			Integer totalSoCompleted = new Integer(0);
			//stupid null check
			if(myProviderResultVO.getProviderStarRating() != null ) {
				starRating  = myProviderResultVO.getProviderStarRating().getHistoricalRating() == null ? 0.0 :  myProviderResultVO.getProviderStarRating().getHistoricalRating().doubleValue();
				if(starRating > 0.0){
					aProvVo.setProviderStarRatingImage(UIUtils.calculateScoreNumber(starRating));
				}
				totalSoCompleted = myProviderResultVO.getProviderStarRating().getTotalOrdersComplete() == null ? new Integer(0) : myProviderResultVO.getProviderStarRating().getTotalOrdersComplete();
			}
			aProvVo.setProviderStarRating(starRating);
			aProvVo.setProviderRating(myProviderResultVO.getProviderRating());
			aProvVo.setProviderFirstName(myProviderResultVO.getProviderFirstName());
			aProvVo.setProviderLastName(myProviderResultVO.getProviderLastName());
			aProvVo.setSelected(false);
			aProvVo.setTotalSOCompleted(totalSoCompleted);
			aProvVo.setState(myProviderResultVO.getState());
			aProvVo.setVendorID(myProviderResultVO.getVendorID());
			aProvVo.setLanguagesKnownByMe(getGwtLanguagesList(myProviderResultVO.getLanguages()));
			
			if(inSearchVO.getLockedResourceId() == myProviderResultVO.getResourceId()){
				aProvVo.setLockedProvider(true);
			}
			myList.add(aProvVo);
			
			
			
		}
		
		
		response.setValueList(myList);
		if (logger.isDebugEnabled())
		  logger.debug(" End Building the List for Providers for " +inSearchVO.getZip() + " : End List :" + System.currentTimeMillis() );
		}catch(Exception e ){
			logger.error("Exception Occurred in fetching provideer for zip code : "+inSearchVO.getZip(), e);
		}
		return response;
		
	}

	private List getGwtLanguagesList(List<ProviderLanguageResultsVO> backendLangauageVOs){
		List<LanguageVO> langagues = new ArrayList<LanguageVO>();
		if(backendLangauageVOs != null){
			for(ProviderLanguageResultsVO lvo: backendLangauageVOs){
				LanguageVO vo = new LanguageVO();
				vo.setId(lvo.getProviderLanguageId());
				langagues.add(vo);
				//vo.setDescr(lvo.get);
			}
		}
		return langagues;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.gwt.providersearch.client.ProviderSearchCriteriaService#execute()
	 */
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.gwt.providersearch.client.ProviderSearchCriteriaService#getDTOFromSession()
	 */
	public GwtRemoteServiceResponse getDTOFromSession() {
		
		GwtRemoteServiceResponse response  = new GwtRemoteServiceResponse();
		
		//GwtFindProvidersDTO aGwtFindProvidersDTO =  getMockDTO();
		
		CreateServiceOrderFindProvidersDTO aDTO = getSessionDTO();
		
		if(aDTO == null){
			response = getNullSessionResponse();
			return response;
		}
		GwtFindProvidersDTO aGwtFindProvidersDTO = new GwtFindProvidersDTO();
		if(aDTO != null){
			moveStuffToGWTDTO(aDTO,aGwtFindProvidersDTO); 
		}
		
		response.setValidSession(true);
		response.setGwtDto(aGwtFindProvidersDTO);
		return response;
		
		
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.gwt.providersearch.client.ProviderSearchCriteriaService#setDTOinSession(com.newco.marketplace.gwt.providersearch.client.GwtFindProvidersDTO)
	 */
	public GwtRemoteServiceResponse setDTOinSession(GwtFindProvidersDTO avo) {
		
		HttpServletRequest request = this.getThreadLocalRequest();
		CreateServiceOrderFindProvidersDTO aCreateServiceOrderFindProvidersDTO = getSessionDTO();
		if(aCreateServiceOrderFindProvidersDTO == null){
			return getNullSessionResponse();
		}
		moveStuffToTabDTO(avo, aCreateServiceOrderFindProvidersDTO);
		SimpleServiceOrderWizardBean sBean = (SimpleServiceOrderWizardBean) request.getSession().getAttribute(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		Map<String , Object>  aMap =(Map<String , Object>) sBean.getTabDTOs();
		aMap.put(OrderConstants.SSO_FIND_PROVIDERS_DTO, aCreateServiceOrderFindProvidersDTO);
		request.getSession().setAttribute(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, sBean);
		GwtRemoteServiceResponse response  = new GwtRemoteServiceResponse();
		response.setValidSession(true);
		return response;
	}
	
	/*
	 * 	these are kinda stupid but WTH?
	 */
			
	public CreateServiceOrderFindProvidersDTO getSessionDTO(){
		HttpServletRequest request = this.getThreadLocalRequest();
		CreateServiceOrderFindProvidersDTO aDTO = null;
		//check if valid session is available
		//if(request.isRequestedSessionIdValid()) { 
			aDTO = new CreateServiceOrderFindProvidersDTO();
			SimpleServiceOrderWizardBean sBean = (SimpleServiceOrderWizardBean) request.getSession().getAttribute(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
			if(sBean != null){
				if(sBean.getTabDTOs() != null){
					Map<String , Object>  aMap =(Map<String , Object>) sBean.getTabDTOs();
					aDTO =  (CreateServiceOrderFindProvidersDTO) aMap.get(OrderConstants.SSO_FIND_PROVIDERS_DTO);
				}
			}
			else {
				//meaning we did not find sBean in session.. assuming session is timed out 
				logger.debug("Found session is timed out.. returning null ");
				return null;
				
			}
		/*}
		else {
			
		}*/
		
		return aDTO;
		
	}
	private void moveStuffToTabDTO(GwtFindProvidersDTO from , CreateServiceOrderFindProvidersDTO to){
		
		to.setCheckedJobs(from.getCheckedJobs());// SkillTypeVos
		PopularServicesVO aPopularServicesVO = new PopularServicesVO();
		
		SimpleProviderSearchSkillNodeVO anodeVO = new SimpleProviderSearchSkillNodeVO();
		
		anodeVO = (SimpleProviderSearchSkillNodeVO)from.getSelectedCategorys().get(GwtFindProvidersDTO.MAIN_VERTICLES);
		aPopularServicesVO.setMainCategoryId(anodeVO.getSkillNodeId());
		aPopularServicesVO.setName(anodeVO.getSkillNodeDesc());
		
		anodeVO = (SimpleProviderSearchSkillNodeVO)from.getSelectedCategorys().get(GwtFindProvidersDTO.CATEGORY);
		aPopularServicesVO.setCategoryId(anodeVO.getSkillNodeId());
		aPopularServicesVO.setCategoryName(anodeVO.getSkillNodeDesc());
		
		anodeVO = (SimpleProviderSearchSkillNodeVO)from.getSelectedCategorys().get(GwtFindProvidersDTO.SUB_SUB_CATEGORY);
		aPopularServicesVO.setSubCategoryId(anodeVO.getSkillNodeId());
		aPopularServicesVO.setSubCategoryName(anodeVO.getSkillNodeDesc());
		
			
		
		
		to.setSelectedCategorys(aPopularServicesVO);
		to.setSelectedProviders(from.getSelectedProviders());// 
		to.setRedirectUrl(from.getRedirectUrl());
		to.setState(from.getState());
		to.setZip(from.getZip());
		to.setSelectedFilterDistance(from.getSelectedFilterDistance());
		to.setSelectedFilterRating(from.getSelectedFilterRating());
		to.setSelectedSortLable(from.getSelectedSortLable());
		
		
	}
	public GwtRemoteServiceResponse getAllLanguages(){
		GwtRemoteServiceResponse response  = new GwtRemoteServiceResponse();
		List<LookupVO> languagesfromBackend = new ArrayList<LookupVO>();
		List<LanguageVO> languagestoGwt = new ArrayList<LanguageVO>();
			try {
				languagesfromBackend = getLookupBO().getLanguages();
				for (LookupVO vo:languagesfromBackend){
					LanguageVO gwtVo = new LanguageVO();
					gwtVo.setId(vo.getId());
					gwtVo.setDescr(vo.getDescr());
					languagestoGwt.add(gwtVo);
				}
				
			} catch (DataServiceException e) {
				
				logger.info(" Exception in get All Languages in GWT remote call" + e.getMessage());
			}
			
		response.setValidSession(true);
		response.setValueList(languagestoGwt);
		return response;
	
	}

	/*
	 * 	these are kinda stupid but WTH?
	 */

	private void moveStuffToGWTDTO(CreateServiceOrderFindProvidersDTO from ,GwtFindProvidersDTO to){
	
		if(from.getCheckedJobs() != null){
			to.setCheckedJobs(from.getCheckedJobs());// SkillTypeVos
		}
		else {
			to.setCheckedJobs(new ArrayList());// SkillTypeVos
		}
		
		if(from.getSelectedProviders() != null){
		to.setSelectedProviders(from.getSelectedProviders());//
		}
		else {
			to.setSelectedProviders(new ArrayList());
		}
		to.setRedirectUrl(from.getRedirectUrl());
		to.setState(from.getState());
		to.setZip(from.getZip());
		PopularServicesVO aPopularServicesVO = 	from.getSelectedCategorysVO();	
		Map selectedCatMap = new HashMap();
		
		SimpleProviderSearchSkillNodeVO anodeVO = new SimpleProviderSearchSkillNodeVO();
		if(aPopularServicesVO == null){
			aPopularServicesVO = new PopularServicesVO();
		}
			if(aPopularServicesVO.getMainCategoryId() == null ) {
			
				anodeVO.setSkillNodeId(-1);
			}
			else {
				anodeVO.setSkillNodeId(aPopularServicesVO.getMainCategoryId());
			}
			selectedCatMap.put(GwtFindProvidersDTO.MAIN_VERTICLES, anodeVO);
	
			SimpleProviderSearchSkillNodeVO anotherNodeVO = new SimpleProviderSearchSkillNodeVO();
			if(aPopularServicesVO.getCategoryId() == null ){
				anotherNodeVO.setSkillNodeId(-1);
			}
			else{
				anotherNodeVO.setSkillNodeId(aPopularServicesVO.getCategoryId());
				anotherNodeVO.setSkillNodeDesc(aPopularServicesVO.getCategoryName());
			}
			selectedCatMap.put(GwtFindProvidersDTO.CATEGORY, anotherNodeVO);
			
			SimpleProviderSearchSkillNodeVO yetAnotherNodeVO = new SimpleProviderSearchSkillNodeVO();
			if(aPopularServicesVO.getSubCategoryId() == null){
				yetAnotherNodeVO.setSkillNodeId(-1);
			}else{
				yetAnotherNodeVO.setSkillNodeId(aPopularServicesVO.getSubCategoryId());
				yetAnotherNodeVO.setSkillNodeDesc(aPopularServicesVO.getSubCategoryName());
			}
			selectedCatMap.put(GwtFindProvidersDTO.SUB_SUB_CATEGORY, yetAnotherNodeVO);
			List<ServiceTypesVO> myList = null;
			//use this in only case where we DONOT have checkedjob filled in and this would allow only one guy cheked
			if(aPopularServicesVO.getServiceTypeTemplateId()!= null && from.getCheckedJobs() == null){
				try{
					myList = getLookupBO().getNotBlackedOutServiceTypes(aPopularServicesVO.getMainCategoryId() 
								,DEFAULT_ANYNOMS_BUYER_ID,from.getState());
				}catch (Exception e) {
					logger.info(" Exception in MoveStuffto GWT" + e.getMessage());
				}
				
				SimpleProviderSearchSkillTypeVO aSimpleProviderSearchSkillTypeVO = new SimpleProviderSearchSkillTypeVO();
				aSimpleProviderSearchSkillTypeVO.setSkillTypeId(aPopularServicesVO.getServiceTypeTemplateId().intValue());
				
				for(ServiceTypesVO aServiceTypesVO:myList){
					if(aServiceTypesVO.getServiceTypeId().equals(aPopularServicesVO.getServiceTypeTemplateId())){
						aSimpleProviderSearchSkillTypeVO.setSkillTypeDescr(aServiceTypesVO.getDescription());
						break;
					}
				}
				List aList = new ArrayList();
				aList.add(aSimpleProviderSearchSkillTypeVO);
				to.setCheckedJobs(aList);
			}
		
	
		to.setSelectedCategorys(selectedCatMap);
		to.setSelectedFilterDistance(from.getSelectedFilterDistance());
		to.setSelectedFilterRating(from.getSelectedFilterRating());
		to.setSelectedSortLable(from.getSelectedSortLable());
		to.setLockedProviderList(from.isLockedProviderList());
		//TODO may be GWT spit out javascript error for the null values here... changes to -999 for resource id
		to.setResourceID(from.getResourceID());
		
	
	}
	
	public GwtFindProvidersDTO getMockDTO() {
		GwtFindProvidersDTO dto = new GwtFindProvidersDTO();
		dto.setState("IL");
		dto.setZip("60194");
		Map map = new HashMap();
		SimpleProviderSearchSkillNodeVO anodeVO = new SimpleProviderSearchSkillNodeVO();
		anodeVO.setSkillNodeId(1500);
		anodeVO.setSkillNodeDesc("Home Electronics");
		map.put(GwtFindProvidersDTO.MAIN_VERTICLES, anodeVO);
		//map.put(GwtFindProvidersDTO.CATEGORY, "1501");
		dto.setSelectedCategorys(map);
		
		SimpleProviderSearchSkillTypeVO skill = new SimpleProviderSearchSkillTypeVO();
		skill.setSkillTypeId(1);
		skill.setSkillTypeDescr("Delivery");
		ArrayList <SimpleProviderSearchSkillTypeVO> list = new ArrayList<SimpleProviderSearchSkillTypeVO>();
		list.add(skill);
		
		dto.setCheckedJobs(list);
		return dto;
	}
}
