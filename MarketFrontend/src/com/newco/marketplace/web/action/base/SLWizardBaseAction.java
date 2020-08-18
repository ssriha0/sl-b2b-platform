package com.newco.marketplace.web.action.base;

import com.newco.marketplace.auth.SecurityContext; 
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.dto.DropdownOptionDTO;
import com.newco.marketplace.web.dto.SLTabDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.SOWTabStateVO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class SLWizardBaseAction extends SLBaseAction {

	protected ISOWizardFetchDelegate fetchDelegate;
	protected ISOWizardPersistDelegate isoWizardPersistDelegate;
	protected IOrderGroupDelegate orderGroupDelegate;
	private Logger logger = Logger.getLogger("SLWizardBaseAction");
	private String defaultTab;
	
	private HashMap yesNoRadio = new HashMap();
	private HashMap locationType = new HashMap();
	private HashMap buyerProviderMap = new HashMap();
	private HashMap measurement = new HashMap();
	private HashMap acceptTermsAndConditionsMap = new HashMap();
	public static final String OPTION_SELECTED = "selected='true'";
	private static final String DEFAULT_SOW_SELECTED_TAB = "Scope of Work";
	
	//SL-19820
	private static String soID;

	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String cancel() throws Exception {
		return invalidateAndReturn(fetchDelegate);
	}
	
	public String invalidateAndReturn(ISOWizardFetchDelegate isoFetchDeletgate){
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		getSession().setAttribute("displayTab",null);
		//SL-19820
		setAttribute("displayTab",null);
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		//String entryPoint = (String)sessionMap.get("entryTab");
		String entryPoint = (String)sessionMap.get("entryTab_"+soId);
		//In cases of grouped orders session value for SO_ID is null.So setting the value into Session.
		//Otherwise removing edit lock indicator in getStartPointAndInvalidate doesn't work.
		/*if(getSession().getAttribute(OrderConstants.SO_ID)==null){
			if(getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY)!=null){
				String soId = ((ServiceOrderWizardBean)getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY)).getSoId();
				getSession().setAttribute(OrderConstants.SO_ID,soId);
			}
		}*/
        SecurityContext securityContext = get_commonCriteria().getSecurityContext();
        logger.debug("Invoking getStartPointAndInvalidate() for cancel" );
        String origination = SOWSessionFacility.getInstance().getStartPointAndInvalidate(isoFetchDeletgate, securityContext);
		
        //SL-19820
		//if (new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap)) {
		if (new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, soId)) {	
			return OrderConstants.WORKFLOW_STARTINGPOINT;
		} else if(origination != null && origination.equalsIgnoreCase(OrderConstants.SOW_STARTPOINT_SOM)) {
			if (entryPoint != null && entryPoint.equalsIgnoreCase("posted")) {
				return OrderConstants.SOW_STARTPOINT_SOM+"Posted";
			} else {
				return OrderConstants.SOW_STARTPOINT_SOM;
			}
		} else {
			return OrderConstants.SOW_STARTPOINT_DASHBOARD;
		}
	}
	
	protected void maps() {
		yesNoRadio.put(1, "Yes");
		yesNoRadio.put(0, "No");
		
		locationType.put(1, "Commercial");
		locationType.put(2, "Residential");
		
		buyerProviderMap.put(OrderConstants.SOW_SOW_BUYER_PROVIDES_PART, "Buyer");
		buyerProviderMap.put(OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART, "Provider");
		buyerProviderMap.put(OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED, "No parts are required");
	
		measurement.put(OrderConstants.ENGLISH, OrderConstants.STANDARD_ENGLISH);
		measurement.put(OrderConstants.METRIC, OrderConstants.STANDARD_METRIC);

		acceptTermsAndConditionsMap.put(OrderConstants.SOW_REVIEW_ACCEPT_TERMS_AND_CONDITIONS, " I accept the Terms & Conditions. ");
		acceptTermsAndConditionsMap.put(OrderConstants.SOW_REVIEW_DONT_ACCEPT_TERMS_AND_CONDITIONS, "  I do not accept the Terms & Conditions. ");
		getSession().setAttribute("acceptTermsAndConditionsMap", acceptTermsAndConditionsMap); 
	}
	
	protected String getDisplayImage(String tabKey ) {
		LocalizationContext context = (LocalizationContext)ServletActionContext.getServletContext().getAttribute("serviceliveCopyBundle");
		getSession().setAttribute("displayImage", context.getResourceBundle().getString(tabKey +".image"));
		return context.getResourceBundle().getString(tabKey +".image");
	}
	
	protected void _setTabs(String role, String selected) {
		if("buyer".equalsIgnoreCase(role)) {
			setBuyerTabs(selected);
		}
	}
	
	protected TabNavigationDTO _createNavPoint(String actionPerformed, String fromTab, String currentMode, String startingPoint) {
		TabNavigationDTO tabNav = new TabNavigationDTO();
		tabNav.setActionPerformed(actionPerformed);
		tabNav.setComingFromTab(fromTab);
		tabNav.setSowMode(currentMode);
		tabNav.setStartingPoint(startingPoint);
		return tabNav;
	}
	
	public String review() throws Exception {
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_GO_TO_REVIEW_ACTION, OrderConstants.SOW_PRICING_TAB, OrderConstants.SOW_EDIT_MODE, "SOW");
		SOWPricingTabDTO dto = (SOWPricingTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		SOWSessionFacility.getInstance().evaluateSOWBean(dto, tabNav);
		this.setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	private void setBuyerTabs(String selected) {
		ArrayList<SLTabDTO> tabList = new ArrayList<SLTabDTO>();
		SLTabDTO tab;
		String icon = null;
		
		//Sl-19820
		soID = (String) getAttribute(OrderConstants.SO_ID);
		
		if (selected == null) {
			icon = getIconForTab(OrderConstants.SOW_SOW_TAB);
			tab = new SLTabDTO("tab1", "soWizardScopeOfWorkCreate_execute.action", "Scope of Work", icon, DEFAULT_SOW_SELECTED_TAB, null);		
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
			tab = new SLTabDTO("tab2", "soWizardAdditionalInfoCreate_createEntryPoint.action", "Additional Info", icon, selected, null);		
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_PARTS_TAB);
			tab = new SLTabDTO("tab3", "soWizardPartsCreate_createEntryPoint.action", "Parts", icon, selected, null);		
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_PROVIDERS_TAB);
			tab = new SLTabDTO("tab4", "soWizardProvidersCreate_createEntryPoint.action", "Providers", icon, selected, null);		
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_PRICING_TAB);
			tab = new SLTabDTO("tab5", "soWizardPricingCreate_createEntryPoint.action", "Pricing", icon, selected, null);		
			tabList.add(tab);
			//Don't show any green checkmark on Review tab
			//icon = getIconForTab(OrderConstants.SOW_REVIEW_TAB);
			//tab = new SLTabDTO("tab6", "soWizardReviewCreate_createEntryPoint.action", "Review", icon, selected);
			tab = new SLTabDTO("tab6", "soWizardReviewCreate_createEntryPoint.action", "Review", null, selected, null);
			tabList.add(tab);
			getDisplayImage(OrderConstants.SOW_SOW_TAB);
		} else {
			icon = getIconForTab(OrderConstants.SOW_SOW_TAB);
			tab = new SLTabDTO("tab1", "soWizardScopeOfWorkCreate_execute.action", "Scope of Work", icon, selected, null);	
			if("true".equals(tab.getSelected())) {
				getDisplayImage(OrderConstants.SOW_SOW_TAB);
			}
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
			tab = new SLTabDTO("tab2", "soWizardAdditionalInfoCreate_execute.action", "Additional Info", icon, selected, null);	
			if("true".equals(tab.getSelected())) {
				getDisplayImage(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
			}
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_PARTS_TAB);
			tab = new SLTabDTO("tab3", "soWizardPartsCreate_execute.action", "Parts", icon, selected, null);	
			if("true".equals(tab.getSelected())) {
				getDisplayImage(OrderConstants.SOW_PARTS_TAB);
			}
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_PROVIDERS_TAB);
			tab = new SLTabDTO("tab4", "soWizardProvidersCreate_execute.action", "Providers", icon, selected, null);	
			if("true".equals(tab.getSelected())) {
				getDisplayImage(OrderConstants.SOW_PROVIDERS_TAB);
			}
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_PRICING_TAB);
			tab = new SLTabDTO("tab5", "soWizardPricingCreate_execute.action", "Pricing", icon, selected, null);	
			if("true".equals(tab.getSelected())) {
				getDisplayImage(OrderConstants.SOW_PRICING_TAB);
			}
			tabList.add(tab);
			
			icon = getIconForTab(OrderConstants.SOW_REVIEW_TAB);
			//Don't show any green checkmark on Review tab
			//tab = new SLTabDTO("tab6", "soWizardReviewCreate_execute.action", "Review", icon, selected);
			tab = new SLTabDTO("tab6", "soWizardReviewCreate_execute.action", "Review", null, selected, null);
			if("true".equals(tab.getSelected())) {
				getDisplayImage(OrderConstants.SOW_REVIEW_TAB);
			}
			tabList.add(tab);			
		}
	
		setAttribute("tabList", tabList);		
	}
	
	
	private static String getIconForTab(String tabId) {
		//SL-19820
		//ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean();
		ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(soID);
		if(bean == null) {
			return null;
		}
		
		SOWTabStateVO vo;
		vo = (SOWTabStateVO)bean.getTabStateDTOs().get(tabId);
		
		// check if parts not supplied by anyone, dont display icon on parts
		/*SOWScopeOfWorkTabDTO tabDTO =(SOWScopeOfWorkTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		if (tabId.equalsIgnoreCase(OrderConstants.SOW_PARTS_TAB) && tabDTO.getPartsSuppliedBy().equals("3")){ 
			vo.setTabShowIcon(false);
		}*/
		if(vo == null) {
			return null;
		}
		
		if(!vo.isTabShowIcon()) {
			return OrderConstants.ICON_NON;
		}
		
		//Check for error state
		if(!SLStringUtils.isNullOrEmpty(vo.getTabErrorState())) {
			if(vo.getTabErrorState().equalsIgnoreCase(OrderConstants.SOW_TAB_ERROR)) {
				return OrderConstants.ICON_ERROR;
			}
		}
		// Check for warning state
		else if(!SLStringUtils.isNullOrEmpty(vo.getTabWarningState())) {
			if(vo.getTabWarningState().equalsIgnoreCase(OrderConstants.SOW_TAB_WARNING)) {
				return OrderConstants.ICON_INCOMPLETE;
			}
		}
		// Check for complete state
		else if(!SLStringUtils.isNullOrEmpty(vo.getTabCompleteState())) {
			if(vo.getTabCompleteState().equalsIgnoreCase(OrderConstants.SOW_TAB_COMPLETE)) {
				return OrderConstants.ICON_COMPLETE;
			}
		}
		
		return null;
	}
	
	public ArrayList<DropdownOptionDTO> getPhoneTypeOptions(Integer selected) {
		ArrayList<LookupVO> masterList = null;
		try {
			masterList = getFetchDelegate().getPhoneTypes();
		} catch(Exception e) {
			ArrayList<DropdownOptionDTO> list = new ArrayList<DropdownOptionDTO>();
			list.add(new DropdownOptionDTO("Home","3",null));
			list.add(new DropdownOptionDTO("Work","1",null));
			list.add(new DropdownOptionDTO("Mobile","2",null));
			list.add(new DropdownOptionDTO("Pager","4",null));
			list.add(new DropdownOptionDTO("Other","5",null));
			return list;
		}
		
		if(masterList == null) {
			return null;
		}
		
		ArrayList<DropdownOptionDTO> phoneDropdowns=null;
		try {
			phoneDropdowns = new ArrayList<DropdownOptionDTO>();
			DropdownOptionDTO option;
			int type;
			
			for(LookupVO vo : masterList) {
				option = new DropdownOptionDTO(vo.getDescr(), vo.getId() +"", null);
				if(selected != null) {
					type = Integer.parseInt(option.getValue());
					if(type == selected.intValue()) {
						option.setSelected(OPTION_SELECTED);
					} else {
						option.setSelected(null);
					}
				}
				phoneDropdowns.add(option);
			}
		} catch(Exception e) {
			return null;
		}
		
		return phoneDropdowns;
	}
	
	protected void createSessionAttributes() throws DataServiceException {
		ArrayList<LookupVO> phoneTypes = fetchDelegate.getPhoneTypes();
		getSession().setAttribute("phoneTypes", phoneTypes);
	}

	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public HashMap getLocationType() {
		return locationType;
	}

	public void setLocationType(HashMap locationType) {
		this.locationType = locationType;
	}

	public HashMap getYesNoRadio() {
		return yesNoRadio;
	}

	public void setYesNoRadio(HashMap yesNoRadio) {
		this.yesNoRadio = yesNoRadio;
	}

	public HashMap getBuyerProviderMap() {
		return buyerProviderMap;
	}

	public void setBuyerProviderMap(HashMap buyerProviderMap) {
		this.buyerProviderMap = buyerProviderMap;
	}

	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}

	public void setIsoWizardPersistDelegate(ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}

	public HashMap getMeasurement() {
		return measurement;
	}

	public void setMeasurement(HashMap measurement) {
		this.measurement = measurement;
	}

	public HashMap getAcceptTermsAndConditionsMap() {
		return acceptTermsAndConditionsMap;
	}

	public void setAcceptTermsAndConditionsMap(HashMap acceptTermsAndConditionsMap) {
		this.acceptTermsAndConditionsMap = acceptTermsAndConditionsMap;
	}

	public IOrderGroupDelegate getOrderGroupDelegate() {
		return orderGroupDelegate;
	}

	public void setOrderGroupDelegate(IOrderGroupDelegate orderGroupDelegate) {
		this.orderGroupDelegate = orderGroupDelegate;
	}
	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService service) {
		this.notificationService = service;
	}

	/**
	 * SO_tasks price population as it becomes null when the so gets manually edited.
	 * @throws BusinessServiceException
	 * @throws DataServiceException
	 */
	protected void soPricePopulation() throws BusinessServiceException,
			DataServiceException {
				//setting the price in the task dto
				//SL-19820
				String soId = (String) getAttribute(OrderConstants.SO_ID);
				//ServiceOrderWizardBean serviceOrderWizardBeans = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY);
				ServiceOrderWizardBean serviceOrderWizardBeans = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
				HashMap<String, Object> tabDTO = serviceOrderWizardBeans.getTabDTOs();
				SOWScopeOfWorkTabDTO scopeOfWorkDto = (SOWScopeOfWorkTabDTO) tabDTO
				   .get(OrderConstants.SOW_SOW_TAB);
				if (null != scopeOfWorkDto) {					
					Iterator<SOTaskDTO> taskDtoItr = scopeOfWorkDto.getTasks().iterator();
					while (taskDtoItr.hasNext()) {
						SOTaskDTO taskDto = (SOTaskDTO) taskDtoItr.next();
						Map sotaskMap = isoWizardPersistDelegate.getTaskPrice(taskDto.getEntityId());
						if (null!=sotaskMap) {
							if(null!=sotaskMap.get("price")){
							   Double price = Double.parseDouble(sotaskMap.get("price").toString());
					     	    taskDto.setPrice(price);
							}
						    Boolean autoinjected=(Boolean)sotaskMap.get("auto_injected_ind");
						    if(null!=autoinjected){
						        taskDto.setAutoInjected(autoinjected.equals(new Boolean(true))?1:0);
						    }
						    if(null!=sotaskMap.get("sort_order")){
						        Integer sortOrder = Integer.parseInt(sotaskMap.get("sort_order").toString());
						       	taskDto.setSortOrder(sortOrder);
						    }
						    Boolean primaryTask = (Boolean)sotaskMap.get("primary_task");
						    if(null!=primaryTask){
						        taskDto.setPrimaryTask(primaryTask.booleanValue());
						     }
						 }
				     }
				}
			}
	
	
	/**SL-20527 :Description: This method will set the spend limit labor and price in pricing DTO
	 * after iterating through Scope of work dto incase the user delete a task.
	 * 
	 */
	protected void setSpendLimitLabor(){
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		ServiceOrderWizardBean serviceOrderWizardBeans = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
		HashMap<String, Object> tabDTO = serviceOrderWizardBeans.getTabDTOs();
		SOWPricingTabDTO pricingdto = (SOWPricingTabDTO) tabDTO.get(OrderConstants.SOW_PRICING_TAB);
		SOWScopeOfWorkTabDTO modelScopeDTO =(SOWScopeOfWorkTabDTO)tabDTO.get(OrderConstants.SOW_SOW_TAB);
		List<SOTaskDTO> tasks =modelScopeDTO.getTasks();
        logger.info("Spend Limit Labor :"+ pricingdto.getLaborSpendLimit());
        logger.info("Pre Paid Price :"+ pricingdto.getPermitPrepaidPrice());
        logger.info("Task Level Pricing Indicator"+ modelScopeDTO.isTaskLevelPricingInd());
        Double totalLabourPrice=0.0;
        Double prePaidpermitPrice = 0.0;
        try{
		if(null != tasks && !(tasks.isEmpty()) &&(modelScopeDTO.isTaskLevelPricingInd())){
			for(SOTaskDTO task : tasks){
				if(null != task && null!= task.getFinalPrice() && (task.getTaskType()!= 2)){
					if(StringUtils.isNotBlank(task.getTaskStatus()) &&(OrderConstants.ACTIVE_TASK.equalsIgnoreCase(task.getTaskStatus()))){
					  totalLabourPrice = totalLabourPrice + task.getFinalPrice();
					}
				}
				// Handle newly added task
				if(null != task && null!= task.getFinalPrice() && task.getIsFreshTask() &(!task.getIsSaved())){
					 totalLabourPrice = totalLabourPrice + task.getFinalPrice();
				}
				if(null != task && null!= task.getFinalPrice() && (task.getTaskType()== 1)){
					if(StringUtils.isNotBlank(task.getTaskStatus()) &&(OrderConstants.ACTIVE_TASK.equalsIgnoreCase(task.getTaskStatus()))){
						prePaidpermitPrice = prePaidpermitPrice + task.getFinalPrice();
					}
				}
			}
			 logger.info("Spend Limit Labor excluding deleted tasks :"+ totalLabourPrice);
			 logger.info("Permit Price  excluding deleted tasks :"+ prePaidpermitPrice);
			 //setting spendLimit labor and permit price in DTO
			 //SL-20575 issue fix
			 totalLabourPrice=MoneyUtil.getRoundedMoney(totalLabourPrice);
			 if(null!= totalLabourPrice){
				 pricingdto.setLaborSpendLimit(totalLabourPrice.toString());
			 }
			 prePaidpermitPrice=MoneyUtil.getRoundedMoney(prePaidpermitPrice);
			 if(null!= prePaidpermitPrice){
				 pricingdto.setPermitPrepaidPrice(prePaidpermitPrice);
			 }
		  }
		
        }catch (Exception e) {
			logger.error("Exception in setting spend limit labor excluding deleted tasks:"+ e.getMessage());
		}
	}
	/**
	 * @param model
	 */
	
}
