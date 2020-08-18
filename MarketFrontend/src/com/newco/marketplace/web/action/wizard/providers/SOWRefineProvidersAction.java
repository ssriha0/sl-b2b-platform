package com.newco.marketplace.web.action.wizard.providers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.action.widgets.AjaxCredentialCategoryActionHelper;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.CheckboxDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.SOWProvidersTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SOWRefineProvidersAction extends SLWizardBaseAction implements Preparable, ModelDriven<SOWProviderSearchDTO> {

	private static final long serialVersionUID = 5040842141244790009L;
	
	private static final Logger logger = Logger.getLogger(SOWRefineProvidersAction.class);
	
	SOWProviderSearchDTO providerSearchDto = new SOWProviderSearchDTO();
	SOWProvidersTabDTO providersTabDTO = new SOWProvidersTabDTO();

	private String credentialId;
	private String checkedProvidersList;
	private String previous;
	private String next;
	//Sl-19820
	String soID;

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public void prepare() throws Exception {
		//Sl-19820
		String soId=getParameter(OrderConstants.SO_ID);
		setAttribute(OrderConstants.SO_ID,soId);
		this.soID = soId;
	}

	public SOWProviderSearchDTO getModel() {
		return providerSearchDto;
	}

	public SOWRefineProvidersAction(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

	public String refineSearch() throws Exception {
				
		SOWProviderSearchDTO providerSearchDto = getModel();
		
		
		
		setPerformanceLevels(providerSearchDto);
		
		providerSearchDto.validate();
		if (providerSearchDto.getErrors().size() != 0) {
			logger.debug("Errors in validate()");
			this.setDefaultTab(OrderConstants.SOW_PROVIDERS_TAB);
			return GOTO_COMMON_WIZARD_CONTROLLER;
		}
		// Languages select box handling "--- Select One ---"
		//Integer selectedLangs = providerSearchDto.getSelectedLang();
		//if (null != selectedLang && selectedLangs == 0) {
		//	selectedLangs.remove(0);
		//}

		getSession().getServletContext().setAttribute(ProviderConstants.SELECTED_ADDITIONAL_INSURANCE_LIST,providerSearchDto.getAdditionalSelectedInsurances());
		
		
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		if(null!=providerSearchDto.getPerformanceScore()){
			//SL-19820
			//sessionMap.put(ProviderConstants.PERFORMANCE_SCORE, providerSearchDto.getPerformanceScore().intValue());
			sessionMap.put(ProviderConstants.PERFORMANCE_SCORE+"_"+this.soID, providerSearchDto.getPerformanceScore().intValue());
			setAttribute(ProviderConstants.PERFORMANCE_SCORE, providerSearchDto.getPerformanceScore().intValue());
		}
		/*sessionMap.put(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING, providerSearchDto.isRoutingPriorityApplied());
		sessionMap.put(ProviderConstants.PERF_CRITERIA_LEVEL, providerSearchDto.getPerfCriteriaLevel());*/
		sessionMap.put(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING+"_"+this.soID, providerSearchDto.isRoutingPriorityApplied());
		sessionMap.put(ProviderConstants.PERF_CRITERIA_LEVEL+"_"+this.soID, providerSearchDto.getPerfCriteriaLevel());
		setAttribute(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING, providerSearchDto.isRoutingPriorityApplied());
		setAttribute(ProviderConstants.PERF_CRITERIA_LEVEL, providerSearchDto.getPerfCriteriaLevel());
		SOWProviderSearchDTO providerSDto = null;
		String providerList = getCheckedProvidersList();
		updatedCheckedProviderList(providerList);
		if (null != sessionMap) {
			//SL-19820
			/*if (sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA) == null) {
				providerSDto = new SOWProviderSearchDTO();
			} else {
				providerSDto = (SOWProviderSearchDTO) sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA);
			}*/
			if (sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+this.soID) == null) {
				providerSDto = new SOWProviderSearchDTO();
			} else {
				providerSDto = (SOWProviderSearchDTO) sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+this.soID);
			}

			if (providerSearchDto.getInsuranceFlag() == null) {
				providerSearchDto.setInsuranceFlag(providerSDto.getInsuranceFlag());
			}
			if (providerSearchDto.getBackgroundCheck() == null) {
				providerSearchDto.setBackgroundCheck(providerSDto.getBackgroundCheck());
			}
			if(providerSearchDto.getPerformanceScore() == null){
				providerSearchDto.setPerformanceScore(providerSDto.getPerformanceScore());
			}
			//SL-19820
			//sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);
			sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+this.soID, providerSearchDto);
			setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);
		}
		SOWScopeOfWorkTabDTO sowDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		LocationVO location = null;
		if (sowDTO != null) {
			SOWContactLocationDTO contLoc = sowDTO.getServiceLocationContact();
			if (contLoc != null) {
				location = new LocationVO();
				location.setStreet1(contLoc.getStreetName1());
				location.setStreet2(contLoc.getStreetName2());
				location.setCity(contLoc.getCity());
				location.setState(contLoc.getState());
				location.setZip(contLoc.getZip());
			}
		}
		//Getting the insurance rating list from session
		providerSearchDto.setGeneralLiabilityRatingList((ArrayList<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.GENERAL_LIABILITY_RATING_LIST));
		providerSearchDto.setVehicleLiabilityRatingList((ArrayList<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.VEHICLE_LIABILITY_RATING_LIST));
		providerSearchDto.setWorkersCompensationRatingList((ArrayList<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.WORKERS_COMPENSATION_RATING_LIST));
		providerSearchDto.setAdditionalInsuranceList((ArrayList<LookupVO>)getSession().getServletContext().getAttribute(ProviderConstants.ADDITIONAL_INSURANCE_LIST));
		
		
		List<Integer> selectedList= providerSearchDto.getSelectedAddnInsurances();
		List<Integer> newList = new ArrayList<Integer>();
		for(Integer sel:selectedList )
		{
			if(null!=sel)
			{
				newList.add(sel);
			}
		}
		providerSearchDto.setSelectedAddnInsurances(newList);
		ArrayList<SOWProviderDTO> providersDto = new ArrayList<SOWProviderDTO>();
		try {
			if (providerSearchDto != null) {
				providersDto = fetchDelegate.getRefinedProviderList(providerSearchDto, location, this.soID);
 				providersTabDTO.setProviders(providersDto);
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring", e);
		}

		if (null != sessionMap) {
			/*
			List<Integer> selectedLanguage = new ArrayList<Integer>();
			if(providerSearchDto.getSelectedLang() != null && providerSearchDto.getSelectedLang() > 0)
				selectedLanguage.add(providerSearchDto.getSelectedLang());
				
			List<LookupVO> languages = (List<LookupVO>) getSession().getServletContext().getAttribute("languages");
			List<Integer> addedLanguageList = new ArrayList<Integer>(languages.size());
			boolean flag = false;
			if (selectedLanguage != null && selectedLanguage.size() > 0) {
				for (int i = 0; i < languages.size(); i++) {
					flag = false;
					for (int j = 0; j < selectedLanguage.size(); j++) {
						if (languages.get(i).getId().intValue() == selectedLanguage.get(j).intValue()) {
							flag = true;
						}
					}
					if (flag) {
						addedLanguageList.add(languages.get(i).getId());
					} else {
						addedLanguageList.add(0);
					}
				}
				sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);
			}
			*/
		}
		this.setDefaultTab(OrderConstants.SOW_PROVIDERS_TAB);
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	private void setPerformanceLevels(SOWProviderSearchDTO providerSearchDto)
	{
		if(providerSearchDto.getSpn() == null || providerSearchDto.getSpn() <= 0)
			return;
		
		
		String key;
		
		List<LookupVO> perfLevels = (List<LookupVO>)ActionContext.getContext().getSession().get(ProviderConstants.PERFORMANCE_LEVEL_DROPDOWN_LIST);

		List<CheckboxDTO> perfLevelCheckboxList = new ArrayList<CheckboxDTO>();
		
		CheckboxDTO checkbox=null;
		for(LookupVO vo : perfLevels)
		{
			key = "perfLevel_" + vo.getId();
			String values[] = getRequest().getParameterValues(key);
			checkbox = new CheckboxDTO();
			checkbox.setLabel(vo.getDescr());
			checkbox.setValue(vo.getId());			
			if(values != null && values.length > 0)
			{
				providerSearchDto.getPerfLevelSelections().add(vo.getId());
				checkbox.setSelected(true);
			}
			perfLevelCheckboxList.add(checkbox);
		}
		
		getRequest().getSession().setAttribute("perfLevelCheckboxes", perfLevelCheckboxList);		
	}
	
	private void updatedCheckedProviderList(String listOfProviders) {
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		List<Integer> checkedProviders = null;
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> removeList = new ArrayList<Integer>();
		//SL-19820
		/*if (sessionMap.get(ProviderConstants.CHECKED_PROVIDERS) == null) {
			checkedProviders = new ArrayList<Integer>();
		} else {
			checkedProviders = (List<Integer>) sessionMap.get(ProviderConstants.CHECKED_PROVIDERS);
		}*/
		if (sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+this.soID) == null) {
			checkedProviders = new ArrayList<Integer>();
		} else {
			checkedProviders = (List<Integer>) sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+this.soID);
		}
		
		if(listOfProviders!=null){
			try {
				listOfProviders= URLDecoder.decode(listOfProviders , "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(listOfProviders != null){
			StringTokenizer tokenizer = new StringTokenizer(listOfProviders, ",");
	
			while (tokenizer.hasMoreTokens()) {
				String str = tokenizer.nextToken();
				int start = str.indexOf("_") + 1;
				int end = str.indexOf("$");
				if (str.endsWith("true"))
					list.add(new Integer(str.substring(start, end)));
				else
					removeList.add(new Integer(str.substring(start, end)));
			}
		}
		for (int i = 0; i < list.size(); i++) {
			if (!checkedProviders.contains(list.get(i))) {
				checkedProviders.add(list.get(i));
			}
		}
		int size = checkedProviders.size();
		int j = 0;
		for (int i = 0; i < size; i++) {
			if (removeList.contains(checkedProviders.get(j))) {
				checkedProviders.remove(checkedProviders.get(j));
				j--;
			}
			j++;
		}
		//SL-19820
		//sessionMap.put(ProviderConstants.CHECKED_PROVIDERS, checkedProviders);
		sessionMap.put(ProviderConstants.CHECKED_PROVIDERS+"_"+this.soID, checkedProviders);
	}
	
	/**
	 * This method is called using AJAX from right side filter widget on SOW->Providers Tab.
	 * This delegates to helper class; since same AJAX is used under ETM as well (by ExploreTheMarketPlaceSearchAction class)
	 * @return
	 */
	public String credentialCategory() {
		createCommonServiceOrderCriteria();
		AjaxCredentialCategoryActionHelper credentialHelper = new AjaxCredentialCategoryActionHelper();
		return credentialHelper.credentialCategory(get_commonCriteria(), getLookupManager(), getRequest(), getResponse());
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getCheckedProvidersList() {
		return checkedProvidersList;
	}

	public void setCheckedProvidersList(String checkedProvidersList) {
		this.checkedProvidersList = checkedProvidersList;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

}
