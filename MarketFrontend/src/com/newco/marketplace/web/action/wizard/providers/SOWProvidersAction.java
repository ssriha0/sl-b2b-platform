package com.newco.marketplace.web.action.wizard.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.CheckboxDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.SOWProvidersTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.ObjectMapperWizard;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SOWProvidersAction extends SLWizardBaseAction implements Preparable, ModelDriven<SOWProvidersTabDTO>
{

	private static final long serialVersionUID = -1365958115369737174L;

	private SOWProviderSearchDTO providerSearchDto;
	private String previous;
	private String next;
	
	//SL-19820
	String soID;

	SOWProvidersTabDTO providersTabDTO = new SOWProvidersTabDTO();
		
	public void prepare() throws Exception
	{
		ArrayList<SOWProviderDTO> providersDto = null;
		ArrayList providersVo = null;
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		//SL-19820
		String soId = getParameter(OrderConstants.SO_ID);
		setAttribute(OrderConstants.SO_ID, soId);
		this.soID = soId;
		
		String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
        setAttribute(OrderConstants.GROUP_ID,groupId);
		
		String entryTab = (String)getSession().getAttribute("entryTab_"+soId);
		setAttribute("entryTab", entryTab);
		
		Integer status = (Integer) getSession().getAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId);
		setAttribute("status", status);
	    /*//SL-20527 Setting Spend Limit labor with out considering deleted task
      	setSpendLimitLabor();*/
		SOWScopeOfWorkTabDTO sowDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		
		if(sowDTO.getServiceLocationContact().getZip() != null)
		{
			providersTabDTO.setZip(sowDTO.getServiceLocationContact().getZip());
		}
		
		// Build performance level checkbox list
		
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

		createCommonServiceOrderCriteria();
		maps();
		if(null != sessionMap){
			// Set the default values
			//SL-19820
			/*providersVo = (ArrayList)sessionMap.get(ProviderConstants.ALL_PROVIDERS);
			if(sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA) == null){
				SOWProviderSearchDTO providerSearchDto = new SOWProviderSearchDTO();
				sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);	
			}
			if((Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG) == null){
				sessionMap.put(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG,Boolean.FALSE);
			}
			if(sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS) == null){
				sessionMap.put(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS,0);
			}*/
			providersVo = (ArrayList)sessionMap.get(ProviderConstants.ALL_PROVIDERS+"_"+soId);
			if(sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId) == null){
				SOWProviderSearchDTO providerSearchDto = new SOWProviderSearchDTO();
				sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId, providerSearchDto);
				setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);
			}
			if((Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId) == null){
				sessionMap.put(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId,Boolean.FALSE);
				setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG,Boolean.FALSE);
			}
			if(sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId) == null){
				sessionMap.put(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId,0);
				setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS,0);
			}
		}
		SOWProvidersTabDTO modelScopeDTO = 
			(SOWProvidersTabDTO)
						SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PROVIDERS_TAB);
		
		if(modelScopeDTO != null){
			providersTabDTO.setWarnings(modelScopeDTO.getWarnings());
		}
		// If we already have the providers list, we dnt make another call.
		//SL-19820
		if(providersVo != null && providersVo.size() > 0
				//&& sessionMap.get(ProviderConstants.SELECTED_MAIN_CATEGORY_ID) != null
				//&& ((Integer)sessionMap.get(ProviderConstants.SELECTED_MAIN_CATEGORY_ID)).intValue() 
					//== sowDTO.getMainServiceCategoryId().intValue()
				//&& sessionMap.get(ProviderConstants.SERVICE_LOCATION) != null
				&& sessionMap.get(ProviderConstants.SELECTED_MAIN_CATEGORY_ID+"_"+soId) != null
				&& ((Integer)sessionMap.get(ProviderConstants.SELECTED_MAIN_CATEGORY_ID+"_"+soId)).intValue() 
					== sowDTO.getMainServiceCategoryId().intValue()
				&& sessionMap.get(ProviderConstants.SERVICE_LOCATION+"_"+soId) != null
				&& !isServiceLocationChanged(sowDTO.getServiceLocationContact())){
			// If we have filtered providers then show only filtered list
			//if(sessionMap.get(ProviderConstants.FILTERED_PROVIDERS) != null
			if(sessionMap.get(ProviderConstants.FILTERED_PROVIDERS+"_"+soId) != null
					//&& !(Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG) 
					//&& ((Integer)sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS)).intValue() == 0){
					&& !(Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId) 
					&& ((Integer)sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId)).intValue() == 0){
				//providersDto = (ArrayList<SOWProviderDTO>)sessionMap.get(ProviderConstants.FILTERED_PROVIDERS);
				providersDto = (ArrayList<SOWProviderDTO>)sessionMap.get(ProviderConstants.FILTERED_PROVIDERS+"_"+soId);
				setCheckedProviders(providersDto);
				providersTabDTO.setProviders(providersDto);
				if(null==providersTabDTO.getRoutingPriorityAppliedInd()){
					//SL-19820
					//providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING));
					providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING+"_"+soId));
					//sessionMap.remove(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING);
				}
				providersTabDTO.validate();
				if(null != providersDto){
					providersTabDTO.clearWarnings();
				}
				return;
			} 
			// If user clicked show selected providers then, show only selected providers
			//SL-19820
			//if((Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG) != null 
					//&& ((Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG)).booleanValue()){
			if((Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId) != null 
					&& ((Boolean)sessionMap.get(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId)).booleanValue()){
				//providersDto = (ArrayList<SOWProviderDTO>)sessionMap.get(ProviderConstants.SELECTED_PROVIDERS);
				providersDto = (ArrayList<SOWProviderDTO>)sessionMap.get(ProviderConstants.SELECTED_PROVIDERS+"_"+soId);
				// Clear the session.
				sessionMap.put(ProviderConstants.SELECTED_PROVIDERS,null);
				sessionMap.put(ProviderConstants.SELECTED_PROVIDERS+"_"+soId,null);
				setAttribute(ProviderConstants.SELECTED_PROVIDERS, null);
				setCheckedProviders(providersDto);
				providersTabDTO.setProviders(providersDto);
				if(null == providersTabDTO.getRoutingPriorityAppliedInd()){
					//providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING));
					providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING+"_"+soId));
					//sessionMap.remove(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING);
				}
				providersTabDTO.setShowSelectedProviders(false);
				providersTabDTO.validate();
				//SL-19820
				//sessionMap.put(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG,Boolean.FALSE);
				sessionMap.put(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soId,Boolean.FALSE);
				setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG,Boolean.FALSE);
				return;
			}
			// To select the top 'x' number of providers
			//SL-19820
			//if(sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS) != null 
					//&& ((Integer)sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS)).intValue() > 0){
			if(sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId) != null 
					&& ((Integer)sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId)).intValue() > 0){
				//providersDto = (ArrayList<SOWProviderDTO>)sessionMap.get(ProviderConstants.SELECT_TOP_PROVIDERS_LIST);
				providersDto = (ArrayList<SOWProviderDTO>)sessionMap.get(ProviderConstants.SELECT_TOP_PROVIDERS_LIST+"_"+soId);
				// Clear the session.
				sessionMap.put(ProviderConstants.SELECT_TOP_PROVIDERS_LIST,null);
				sessionMap.put(ProviderConstants.SELECT_TOP_PROVIDERS_LIST+"_"+soId,null);
				setAttribute(ProviderConstants.SELECT_TOP_PROVIDERS_LIST,null);
				setCheckedProviders(providersDto);
				providersTabDTO.setProviders(providersDto);	
				if(null==providersTabDTO.getRoutingPriorityAppliedInd()){
					providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING));
					//sessionMap.remove(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING);
				}
				//SL-19820
				//providersTabDTO.setSelectTopProviders((Integer)sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS));
				providersTabDTO.setSelectTopProviders((Integer)sessionMap.get(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId));
				providersTabDTO.validate();
				sessionMap.put(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS,0);
				sessionMap.put(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soId,0);
				setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS,0);
				return;
			}
			//SL-19820
			//if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS) != null){
			if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId) != null){
				providersDto = ObjectMapperWizard.assingProviderResultVOToProvidersDto(resetProvidersList(providersVo));
				setCheckedProviders(providersDto);
				providersTabDTO.setProviders(providersDto);	
				return;
			}
			// Display the full list of providers.
			providersDto = ObjectMapperWizard.assingProviderResultVOToProvidersDto(providersVo);
			setCheckedProviders(providersDto);
			providersTabDTO.setProviders(providersDto);
			if(null==providersTabDTO.getRoutingPriorityAppliedInd()){
				//SL-19820
				//providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING));
				providersTabDTO.setRoutingPriorityAppliedInd((Boolean)sessionMap.get(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING+"_"+soId));
				//sessionMap.remove(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING);
			}
			providersTabDTO.validate();
			return;
		}else{		
			// If this is the first time, Make a call to the business object.
			if(sowDTO != null){
				//SL-19820
				//sessionMap.put(ProviderConstants.SELECTED_MAIN_CATEGORY_ID,sowDTO.getMainServiceCategoryId());
				sessionMap.put(ProviderConstants.SELECTED_MAIN_CATEGORY_ID+"_"+soId,sowDTO.getMainServiceCategoryId());
				setAttribute(ProviderConstants.SELECTED_MAIN_CATEGORY_ID,sowDTO.getMainServiceCategoryId());
				ProviderSearchCriteriaVO searchCriteria = new ProviderSearchCriteriaVO();
				try{
					if(sowDTO != null && sowDTO.getMainServiceCategoryId() != null){
						ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
						List<Integer> skillTypeIds = new ArrayList<Integer>();
						if(null != sowDTO.getTasks() && sowDTO.getTasks().size() > 0){
							List<SOTaskDTO> tasksDto = sowDTO.getTasks();
							// If we have tasks then assign the SubCategory/Category to the list
							// else select the main category
							for(int i = 0; i < tasksDto.size(); i++){
								SOTaskDTO taskDto = tasksDto.get(i);
								
								if(taskDto.getSubCategoryId() != null 
										&& taskDto.getSubCategoryId().intValue() != -1){
									skillNodeIds.add(taskDto.getSubCategoryId());
								}else if(taskDto.getCategoryId() != null
										&& taskDto.getCategoryId().intValue() != -1){
									skillNodeIds.add(taskDto.getCategoryId());
								}else{
									skillNodeIds.add(sowDTO.getMainServiceCategoryId());
								}
								
								if(taskDto.getSkillId() != null){
									skillTypeIds.add(taskDto.getSkillId());
								}else{
									// For a particular skill node id we need to get the corresponding skill type id
									// If he didnt select a skill type, then add -1 to that particular skill node and 
									// dont apply % decrement 
									skillTypeIds.add(-1);
								}
							}
							searchCriteria.setSkillNodeIds(skillNodeIds);
							searchCriteria.setSkillServiceTypeId(skillTypeIds);
						}
						else{
							skillNodeIds.add(sowDTO.getMainServiceCategoryId());
							skillTypeIds.add(-1);
							
							searchCriteria.setSkillNodeIds(skillNodeIds);
							searchCriteria.setSkillServiceTypeId(skillTypeIds);
						}
						// Get the service location address to display the distance from providers address
						// This address is passed to the GIS web service
						SOWContactLocationDTO contLoc = copyServiceLocation(sowDTO.getServiceLocationContact());
						//SL-19820
						//sessionMap.put(ProviderConstants.SERVICE_LOCATION,contLoc);
						sessionMap.put(ProviderConstants.SERVICE_LOCATION+"_"+soId,contLoc);
						setAttribute(ProviderConstants.SERVICE_LOCATION,contLoc);
						LocationVO location = new LocationVO();
						if(contLoc != null){
							location.setStreet1(contLoc.getStreetName1());
							location.setStreet2(contLoc.getStreetName2());
							location.setCity(contLoc.getCity());
							location.setState(contLoc.getState());
							location.setZip(contLoc.getZip());
						}
						searchCriteria.setServiceLocation(location);	
					}
					SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");
					searchCriteria.setBuyerID(context.getCompanyId());
					// Service order id is used for retrieve saved providers when coming from edit mode 
					//SL-19820
					/*if(sessionMap.get(OrderConstants.SO_ID) != null){
						searchCriteria.setServiceOrderID((String)sessionMap.get(OrderConstants.SO_ID));
					}*/
					if(getAttribute(OrderConstants.SO_ID) != null){
						searchCriteria.setServiceOrderID((String)getAttribute(OrderConstants.SO_ID));
					}
					
					// If we are dealing with a child order of a group, need to set the search criteria groupID.
					//SL-19820
					//String groupId = (String)getSession().getAttribute(GROUP_ID);
					groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
			        setAttribute(OrderConstants.GROUP_ID,groupId);
					if(!StringUtils.isBlank(groupId)){
						searchCriteria.setGroupID(groupId);
						//SL-19820 : TODO Not sure
						//searchCriteria.setCurrentChildOrderId((String)getSession().getAttribute(OrderConstants.SO_CHILD_ID));
						searchCriteria.setCurrentChildOrderId((String) getAttribute(OrderConstants.SO_ID));
					}
					else
						searchCriteria.setGroupID(null);
					
					//SL-19820
					//SOWProviderSearchDTO providerSearchDto = (SOWProviderSearchDTO) sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA);
					SOWProviderSearchDTO providerSearchDto = (SOWProviderSearchDTO) sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId);
					if (providerSearchDto.getSpn() != null && providerSearchDto.getSpn() > 0) {
						searchCriteria.setSpnID(providerSearchDto.getSpn());
						searchCriteria.setIsNewSpn(Boolean.TRUE);
						searchCriteria.setRoutingPriorityApplied(providerSearchDto.isRoutingPriorityApplied());
						searchCriteria.setPerfScore(providerSearchDto.getPerformanceScore());
					}
					providersDto = fetchDelegate.getProviderList(searchCriteria, soId);
	
					sessionMap.put(ProviderConstants.SEARCH_CRITERIA+"_"+soId, searchCriteria);
					providersTabDTO.setProviders(providersDto);	
					providersTabDTO.setRoutingPriorityAppliedInd(providerSearchDto.isRoutingPriorityApplied());
					providersTabDTO.validate();
					//SL-18914 Setting the spn id obtained from the so hdr to DTO in the provider action initially 
					//before providers being searched by spn from frontend
					providersTabDTO.setSpnId(modelScopeDTO.getSpnId());
					//SL-19820
					/*if(sessionMap.get(SOW_ACTION_TYPE) != null 
							&& (((String)sessionMap.get(SOW_ACTION_TYPE)).equalsIgnoreCase("create")
							|| ((String)sessionMap.get(SOW_ACTION_TYPE)).equalsIgnoreCase("copy"))){
						providersTabDTO.clearWarnings();
					}*/	
					if(sessionMap.get(SOW_ACTION_TYPE+"_"+soId) != null 
							&& (((String)sessionMap.get(SOW_ACTION_TYPE+"_"+soId)).equalsIgnoreCase("create")
							|| ((String)sessionMap.get(SOW_ACTION_TYPE+"_"+soId)).equalsIgnoreCase("copy"))){
						providersTabDTO.clearWarnings();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	private List<ProviderResultVO> resetProvidersList(List<ProviderResultVO> providersVo){
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		List<Integer> checkedProviders = null;
		boolean found = false;
		//SL-19820
		String soId = (String) getAttribute(SO_ID);
		//if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS) != null){		
			//checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS);
		
		if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId) != null){		
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId);
			
			for(int i=0;i<providersVo.size();i++){
				found = false;
				ProviderResultVO providerResultVo = providersVo.get(i);
				for(int j=0;j<checkedProviders.size();j++){					
					if(providerResultVo != null 
							&& providerResultVo.getResourceId() == checkedProviders.get(j).intValue()){
						providerResultVo.setSelected(Boolean.TRUE);
						found = true;
						break;
					}
				}
				if(!found && providerResultVo != null){
					providerResultVo.setSelected(Boolean.FALSE);
				}
			}
		}
		
		return providersVo;
	}
	
	public SOWProvidersAction(ISOWizardFetchDelegate fetchDelegate ) {
		this.fetchDelegate = fetchDelegate;
	}
	
	public String setDtoForTab() throws IOException, BusinessServiceException{
		String TabStatus="tabIcon ";
		SOWProvidersTabDTO sOWProvidersTabDTO = getModel();
		if(sOWProvidersTabDTO == null){
			sOWProvidersTabDTO =  new SOWProvidersTabDTO();
		}
		handleCheckboxes(sOWProvidersTabDTO);				
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_NEXT_ACTION,
				OrderConstants.SOW_PROVIDERS_TAB,
				OrderConstants.SOW_EDIT_MODE, "SOM");
        SOWSessionFacility sowSessionFacility  = SOWSessionFacility.getInstance();
        sowSessionFacility.evaluateSOWBean(sOWProvidersTabDTO, tabNav);

		sOWProvidersTabDTO.validate();
		if (sOWProvidersTabDTO.getErrors().size() > 0){
			TabStatus="tabIcon error";
		}
		else if (sOWProvidersTabDTO.getWarnings().size() > 0){
			TabStatus="tabIcon incomplete";
		}
		else{
			TabStatus="tabIcon complete";
		}
		//SL-19820
		//getRequest().getSession().setAttribute("ProvidersList", sOWProvidersTabDTO);
		getRequest().getSession().setAttribute("ProvidersList_"+soID, sOWProvidersTabDTO);
		setAttribute("ProvidersList", sOWProvidersTabDTO);
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(1);
		actionResults.setResultMessage(SUCCESS);
		actionResults.setAddtionalInfo1(TabStatus);
		
		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		response.getWriter().write(responseStr);

		return NONE; 
	}
	
	public String createEntryPoint() throws Exception {
		return SUCCESS;
	}
	
	private void handleCheckboxes(SOWProvidersTabDTO tabDTO)
	{
		String param, val;
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		List<Integer> checkedProviders = null;
		//SL-19820
		/*if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS) == null){		
			checkedProviders = new ArrayList<Integer>();
		}else{
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS);
		}*/
		String soId = (String) getAttribute(SO_ID);
		if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId) == null){		
			checkedProviders = new ArrayList<Integer>();
		}else{
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId);
		}
		for(SOWProviderDTO provider : tabDTO.getProviders())
		{
			param = "check_" + provider.getResourceId();
			val = getRequest().getParameter(param);
			if(val != null)
			{
				provider.setIsChecked(Boolean.TRUE);
				if(!checkedProviders.contains(provider.getResourceId())){
					checkedProviders.add(provider.getResourceId());
				}
			}else if(checkedProviders.contains(provider.getResourceId())){
				checkedProviders.remove(provider.getResourceId());
				provider.setIsChecked(Boolean.FALSE);
			}else{
				provider.setIsChecked(Boolean.FALSE);
			}
		}
		//SL-19820
		//sessionMap.put(ProviderConstants.CHECKED_PROVIDERS, checkedProviders);
		sessionMap.put(ProviderConstants.CHECKED_PROVIDERS+"_"+soId, checkedProviders);
		setAttribute(ProviderConstants.CHECKED_PROVIDERS, checkedProviders);
	}
	
	public String createAndRoute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public String editEntryPoint() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// Show only the providers selected by the user and remove other providers from the list.
	public String showSelectedProviders() throws Exception{
		SOWProvidersTabDTO sOWProvidersTabDTO = getModel();
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		
		if(sOWProvidersTabDTO == null){
			sOWProvidersTabDTO =  new SOWProvidersTabDTO();
		}
		
		// Get the selected providers.
		handleCheckboxes(sOWProvidersTabDTO);
		
		
		ArrayList<SOWProviderDTO> providers = sOWProvidersTabDTO.getProviders();
		ArrayList<SOWProviderDTO> selectedProviders = (ArrayList<SOWProviderDTO>)providers.clone();
		int size = selectedProviders.size();
		int j = 0;
		// Remove the un-selected providers
		for(int i=0;i<size;i++){
			if(selectedProviders.get(j).getIsChecked().booleanValue() != true){
				selectedProviders.remove(j);
				j--;
			}
			j++;
		}
		// Save it in session to retrieve in prepare method and set it in the model.
		//SL-19820
		/*sessionMap.put(ProviderConstants.SELECTED_PROVIDERS, selectedProviders);
		sessionMap.put(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG, Boolean.TRUE);*/		
		sessionMap.put(ProviderConstants.SELECTED_PROVIDERS+"_"+soID, selectedProviders);
		sessionMap.put(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG+"_"+soID, Boolean.TRUE);
		setAttribute(ProviderConstants.SELECTED_PROVIDERS, selectedProviders);
		setAttribute(ProviderConstants.SHOW_SELECTED_PROVIDERS_FLAG, Boolean.TRUE);
		this.setDefaultTab(OrderConstants.SOW_PROVIDERS_TAB);
		return GOTO_COMMON_WIZARD_CONTROLLER;		
	}
	
	// Show all providers: selected and un-selected
	public String showAllProviders() throws Exception {		
		SOWProvidersTabDTO sOWProvidersTabDTO = getModel();
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		
		if(sOWProvidersTabDTO == null){
			sOWProvidersTabDTO =  new SOWProvidersTabDTO();
		}
		
		// Get the selected providers.
		handleCheckboxes(sOWProvidersTabDTO);
		
		
		this.setDefaultTab(OrderConstants.SOW_PROVIDERS_TAB);
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	// Check the top 'x' providers check box.
	public String selectTopProviders()throws Exception {
		SOWProvidersTabDTO sOWProvidersTabDTO = getModel();
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		
		if(sOWProvidersTabDTO == null){
			sOWProvidersTabDTO =  new SOWProvidersTabDTO();
		}
		// Get the checked provider list. If not exist, create a new lsit.
		List<Integer> checkedProviders = null;
		//SL-19820
		/*if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS) == null){		
			checkedProviders = new ArrayList<Integer>();
		}else{
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS);
		}*/
		if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soID) == null){		
			checkedProviders = new ArrayList<Integer>();
		}else{
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soID);
		}
		
		ArrayList<SOWProviderDTO> providers = sOWProvidersTabDTO.getProviders();
		int selectCheck = sOWProvidersTabDTO.getSelectTopProviders().intValue();
		//sort the providers based on performance score
		if(null!= sOWProvidersTabDTO.getRoutingPriorityAppliedInd() && sOWProvidersTabDTO.getRoutingPriorityAppliedInd()){
			Comparator<SOWProviderDTO> compare = null;
			compare = SOWProviderDTO.getComparator(SOWProviderDTO.SortParameter.SCORE_DESCENDING);
			Collections.sort(providers, compare);
		}
		// Add to the checkedproviders list the top 'x' providers and remove the others if exists
		for(int i=0;i<providers.size();i++){
			if(i < selectCheck){
				providers.get(i).setIsChecked(true);
				
				if(!checkedProviders.contains(providers.get(i).getResourceId())){
					checkedProviders.add(providers.get(i).getResourceId());
				}
			}else{
				providers.get(i).setIsChecked(false);
				if(checkedProviders.contains(providers.get(i).getResourceId())){
					checkedProviders.remove(providers.get(i).getResourceId());
				}
			}			
		}
		//Save it in session to retrieve in prepare method and set it in the model.
		//SL-19820
		/*sessionMap.put(ProviderConstants.SELECT_TOP_PROVIDERS_LIST, providers);
		sessionMap.put(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS, selectCheck);
		sessionMap.put(ProviderConstants.CHECKED_PROVIDERS, checkedProviders);	*/
		sessionMap.put(ProviderConstants.SELECT_TOP_PROVIDERS_LIST+"_"+soID, providers);
		sessionMap.put(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS+"_"+soID, selectCheck);
		sessionMap.put(ProviderConstants.CHECKED_PROVIDERS+"_"+soID, checkedProviders);	
		setAttribute(ProviderConstants.SELECT_TOP_PROVIDERS_LIST, providers);
		setAttribute(ProviderConstants.SELECT_NUBER_OF_TOP_PROVIDERS, selectCheck);
		setAttribute(ProviderConstants.CHECKED_PROVIDERS, checkedProviders);
		
		this.setDefaultTab(OrderConstants.SOW_PROVIDERS_TAB);
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	public String next() throws Exception {
	
		// 1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		// 2. WHAT TAB OR YOU ON sow ETC..
		// 3. WHAT MODE ARE YOU IN CREATE OR EDIT
		// 4. WHERE DID YOU START FROM IN THE APPLICATION
		getRequest().setAttribute("previous","tab4");
		getRequest().setAttribute("next","tab5");
		TabNavigationDTO tabNav = _createNavPoint(
						OrderConstants.SOW_NEXT_ACTION,
						OrderConstants.SOW_PROVIDERS_TAB,
						OrderConstants.SOW_EDIT_MODE, "SOM");
		
		SOWProvidersTabDTO sOWProvidersTabDTO = getModel();
		if(sOWProvidersTabDTO == null){
			sOWProvidersTabDTO =  new SOWProvidersTabDTO();
		}
		handleCheckboxes(sOWProvidersTabDTO);				
		
		SOWSessionFacility sowSessionFacility  = SOWSessionFacility.getInstance();
		sowSessionFacility.evaluateSOWBean(sOWProvidersTabDTO, tabNav);
		
		String goingTotab = sowSessionFacility.getGoingToTab();
		setDefaultTab(goingTotab);
		if (goingTotab == "Providers"){
			this.setNext("tab4");
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	
	public String previous() throws Exception {
//		 1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		// 2. WHAT TAB OR YOU ON sow ETC..
		// 3. WHAT MODE ARE YOU IN CREATE OR EDIT
		// 4. WHERE DID YOU START FROM IN THE APPLICATION
		getRequest().setAttribute("previous","tab4");
		TabNavigationDTO tabNav = _createNavPoint(
						OrderConstants.SOW_PREVIOUS_ACTION,
						OrderConstants.SOW_PROVIDERS_TAB,
						OrderConstants.SOW_EDIT_MODE, "SOM");
		
		SOWProvidersTabDTO sOWProvidersTabDTO = getModel();
		if(sOWProvidersTabDTO == null){
			sOWProvidersTabDTO =  new SOWProvidersTabDTO();
		}
		handleCheckboxes(sOWProvidersTabDTO);			
		
		SOWSessionFacility sowSessionFacility  = SOWSessionFacility.getInstance();
		sowSessionFacility.evaluateSOWBean(sOWProvidersTabDTO, tabNav);
		
		String goingTotab = sowSessionFacility.getGoingToTab();
		this.setDefaultTab(goingTotab);
		if (goingTotab == "Providers"){
			this.setNext("tab4");
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	public String saveAsDraft() throws Exception {
		String returnValue = null;
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_SAVE_AS_DRAFT_ACTION,
				OrderConstants.SOW_PROVIDERS_TAB,
				OrderConstants.SOW_EDIT_MODE,
					"SOW" );
		
		SOWProvidersTabDTO provider = getModel();
		
		//Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		
		if(provider == null){
			provider =  new SOWProvidersTabDTO();
		}
		handleCheckboxes(provider);	
		//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();
		soPricePopulation();
		SOWSessionFacility.getInstance().evaluateAndSaveSOWBean(provider, tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);
		
		String str = SOWSessionFacility.getInstance().getGoingToTab();
		if (str!=null && str.equalsIgnoreCase(OrderConstants.SOW_EXIT_SAVE_AS_DRAFT))
		{
			//SL-19820
			//String currentSO = (String)getSession().getAttribute( OrderConstants.SO_ID);
			String soId = (String) getAttribute( OrderConstants.SO_ID);
			//SL-21355 : Saving logo from model to the service order.
			setBrandingLogo();
			invalidateAndReturn(fetchDelegate);
			Map sessionMap = ActionContext.getContext().getSession();
			if(new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, soId))
			{
			  returnValue = OrderConstants.WORKFLOW_STARTINGPOINT;
			}
			else
			{
			  returnValue = OrderConstants.SOW_STARTPOINT_SOM;
			}
		}
		else
		{   
			this.setDefaultTab(str);
			returnValue = GOTO_COMMON_WIZARD_CONTROLLER;
		}
		return returnValue;
		//this.setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
		//return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	public SOWProvidersTabDTO getModel() {
//		SOWProvidersTabDTO modelScopeDTO = 
//							(SOWProvidersTabDTO)
//										SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PROVIDERS_TAB);
//		if(modelScopeDTO !=null){
//			return modelScopeDTO;
//		}
		return providersTabDTO;
	}
	// Set the checked flag in prvoiders dto.
	private void setCheckedProviders(ArrayList<SOWProviderDTO> providers){
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		List<Integer> checkedProviders = null;
		//SL_19820
		/*if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS) == null){		
			checkedProviders = new ArrayList<Integer>();
		}else{
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS);
		}*/
		String soId = (String) getAttribute(SO_ID);
		if(sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId) == null){		
			checkedProviders = new ArrayList<Integer>();
		}else{
			checkedProviders = (List<Integer>)sessionMap.get(ProviderConstants.CHECKED_PROVIDERS+"_"+soId);
		}
		
		for(int i=0;i<providers.size();i++){
			for(int j=0;j<checkedProviders.size();j++){
				if(providers.get(i).getResourceId().intValue() 
						== checkedProviders.get(j).intValue()){
					providers.get(i).setIsChecked(Boolean.TRUE);
					break;
				}
			}
		}
	}
	
	private boolean isServiceLocationChanged(SOWContactLocationDTO contLoc){
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		SOWContactLocationDTO serviceLocation = null;
		if(sessionMap != null){
			//SL-19820
			//serviceLocation = (SOWContactLocationDTO)sessionMap.get(ProviderConstants.SERVICE_LOCATION);
			String soId = (String) getAttribute(SO_ID);
			serviceLocation = (SOWContactLocationDTO)sessionMap.get(ProviderConstants.SERVICE_LOCATION+"_"+soId);
			
			if(serviceLocation != null){				
				if(!StringUtils.equalsIgnoreCase(serviceLocation.getStreetName1(),contLoc.getStreetName1()))
					return true;
				else if(!StringUtils.equalsIgnoreCase(serviceLocation.getStreetName2(),contLoc.getStreetName2()))
					return true;
				else if(!StringUtils.equalsIgnoreCase(serviceLocation.getCity(),contLoc.getCity()))
					return true;
				else if(!StringUtils.equalsIgnoreCase(serviceLocation.getState(),contLoc.getState()))
					return true;
				else if(!StringUtils.equalsIgnoreCase(serviceLocation.getZip(),contLoc.getZip()))
					return true;
				else
					return false;
			}
		}
		return true;
	}
	
	private SOWContactLocationDTO copyServiceLocation(SOWContactLocationDTO serviceLocation){
		SOWContactLocationDTO copy = new SOWContactLocationDTO();
		
		copy.setStreetName1(serviceLocation.getStreetName1());
		copy.setStreetName2(serviceLocation.getStreetName2());
		copy.setCity(serviceLocation.getCity());
		copy.setState(serviceLocation.getState());
		copy.setZip(serviceLocation.getZip());
		return copy;
	}
	
	/**@Description : Update the logo document against the service order
	 * @throws Exception
	 */
	public void setBrandingLogo() throws Exception {
		Integer buyerDocumentLogo =null;
		SOWScopeOfWorkTabDTO sowScopeOfWorkDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		if(null!= sowScopeOfWorkDTO && null!=sowScopeOfWorkDTO.getSkus() 
				&& !sowScopeOfWorkDTO.getSkus().isEmpty() && null!= sowScopeOfWorkDTO.getSkus().get(0)){
			    buyerDocumentLogo = sowScopeOfWorkDTO.getSkus().get(0).getBuyerDocumentLogo();
		}
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		Integer existingLogoId = fetchDelegate.getLogoDocumentId(soId);
		if(null== existingLogoId && null!= buyerDocumentLogo){
			fetchDelegate.applyBrandingLogo(soId, buyerDocumentLogo);
		}
	}
	
	private String showProviderProfile()
	{
		
		return "";
	}
	
	public void setModel(SOWProvidersTabDTO dto){
		
		providersTabDTO = dto;
	}
	

	public SOWProvidersTabDTO getProvidersTabDTO() {
		return providersTabDTO;
	}

	public void setProvidersTabDTO(SOWProvidersTabDTO providersTabDTO) {
		this.providersTabDTO = providersTabDTO;
	}

	public SOWProviderSearchDTO getProviderSearchDto() {
		return providerSearchDto;
	}

	public void setProviderSearchDto(SOWProviderSearchDTO providerSearchDto) {
		this.providerSearchDto = providerSearchDto;
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

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}


}
