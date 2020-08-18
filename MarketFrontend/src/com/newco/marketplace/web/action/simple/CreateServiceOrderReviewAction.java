/*
 * @(#)CreateServiceOrderReviewAction.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.web.action.simple;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchProviderResultVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchSkillTypeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.DateDisplayUtil;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.action.base.ISimpleServiceOrderAction;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderReviewDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class CreateServiceOrderReviewAction extends SLSimpleBaseAction 
					implements Preparable, ModelDriven<CreateServiceOrderReviewDTO>, ISimpleServiceOrderAction
{
	
	private static final long serialVersionUID = 7040642367479178450L;
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private static final Logger logger = Logger.getLogger(CreateServiceOrderReviewAction.class.getName());
	private CreateServiceOrderReviewDTO model = new CreateServiceOrderReviewDTO();
	private CreateServiceOrderDescribeAndScheduleDTO describeDTO;
	CreateServiceOrderFindProvidersDTO findProvidersDTO;
	private ISOMonitorDelegate serviceOrderDelegate;
	
	

	public CreateServiceOrderReviewAction(ICreateServiceOrderDelegate delegate){
		createServiceOrderDelegate = delegate;
	}
	
	public void prepare() throws Exception
	{
		//Check IF THE USER IS LOGGED INTO THE SITE
		createCommonServiceOrderCriteria();
		
		if (getSession().getAttribute(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_INDICTATOR) == null)
		{
			throw new Exception("Invalid entry point into Service Order Wizard.");
		}//end if
		try {
			setDescribeDTO((CreateServiceOrderDescribeAndScheduleDTO)
				SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO));
			findProvidersDTO = (CreateServiceOrderFindProvidersDTO)
				SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_FIND_PROVIDERS_DTO);
			model = (CreateServiceOrderReviewDTO) SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_ORDER_REVIEW_DTO);
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
	
	/**
	 * 
	 * Populates the DTO required to display the page.
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String displayPage() throws Exception
	{
		/** if SO is already posted take user to find providers page to start with new SO */
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(org.apache.commons.lang.StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		populateLocation();
		poplutateDateAndTime();
		populateCost();
		populateBalance();
		getModel().clearAllWarnings();
		
		if (model.getPrimaryPhone() == null) {
			populateContact();
		}
		//Populate findProviders related Data
		if (findProvidersDTO != null) {
			if (findProvidersDTO.getSelectedProviders() != null) {
				setStarImage(findProvidersDTO.getSelectedProviders());
				model.setProviders(findProvidersDTO.getSelectedProviders());
				model.setNumProvidersSelected(findProvidersDTO.getSelectedProviders().size());
			}
			populateTaskName();			
		}
		getRequest().setAttribute("SB_REVIEW_PAGE", true);
		return SSO_ORDER_REVIEW_DTO;
	}
	
	
	
	/**
	 * Populate the DTO with Account balance information
	 */
	private void populateBalance() {
		Map<String, Object> balanceMap = loadBalance(serviceOrderDelegate);
		model.setAvailableBalance((Double)balanceMap.get("availBalance"));
		model.setCurrentBalance((Double)balanceMap.get("currentBalance"));
	}
	
	
	/**
	 * Sets the rating image for the providers
	 * 
	 * @param providers
	 */
	public void setStarImage(List<SimpleProviderSearchProviderResultVO> providers){
		if(providers != null && providers.size() > 0){
			for (SimpleProviderSearchProviderResultVO provider : providers) {
				if(provider.getProviderStarRating() != 0.0){
					provider.setProviderStarRatingImage(UIUtils.calculateScoreNumber(provider.getProviderStarRating()));
				}
			}
		}
	}

	/**
	 * Populate the DTO with location information
	 */
	private void populateLocation() {
		StringBuilder location = new StringBuilder();
		location.append(describeDTO.getLocationName() + "<br/>" + describeDTO.getStreet1());
		if (!SLStringUtils.isNullOrEmpty(describeDTO.getStreet2())) {
			location.append(", " + describeDTO.getStreet2());
		}
		if (!SLStringUtils.isNullOrEmpty(describeDTO.getAptNo())) {
			location.append(", " + describeDTO.getAptNo());
		}
		
		location.append("<br/>" + describeDTO.getCity() + ", " + describeDTO.getStateCd() + " " + describeDTO.getZip());
		model.setLocation(location.toString());
		
	}

	/**
	 * Populates the DTO with task name
	 */
	private void populateTaskName() {
		StringBuilder taskName = new StringBuilder();
		PopularServicesVO servicesVO = findProvidersDTO.getSelectedCategorysVO();
		if (servicesVO != null) {
			if (!SLStringUtils.isNullOrEmpty(servicesVO.getName())) {
				taskName.append(servicesVO.getName() + " > ");
			}
			if (!SLStringUtils.isNullOrEmpty(servicesVO.getCategoryName())) {
				taskName.append(servicesVO.getCategoryName() + " > ");
			}
			if (!SLStringUtils.isNullOrEmpty(servicesVO.getSubCategoryName())) {
				taskName.append(servicesVO.getSubCategoryName() + " > ");
			}
		}
		List serviceTypesList = findProvidersDTO.getCheckedJobs();
		if(serviceTypesList != null)
		{
			SimpleProviderSearchSkillTypeVO skillType=null;
			for (int i = 0; i < serviceTypesList.size(); i++)
			{
				if(serviceTypesList.get(i) != null)
				{
					skillType = (SimpleProviderSearchSkillTypeVO)serviceTypesList.get(i);
				}
				if(skillType != null && !SLStringUtils.isNullOrEmpty(skillType.getSkillTypeDescr()))
						taskName.append(skillType.getSkillTypeDescr() + ", ");
			}
		}
		if (taskName.length() > 2) {
			taskName.delete(taskName.length()-2, taskName.length()-1);
		}
		model.setTaskName(taskName.toString());
	}
	
	/**
	 * Populates the DTO with Date and Time
	 * @throws ParseException 
	 */
	private void poplutateDateAndTime(){
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMid = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat sdfDisplay = new SimpleDateFormat("MM/dd/yyyy hh:mm a (z)");
		SimpleDateFormat sdfTZ = new SimpleDateFormat("z");
		SimpleDateFormat sdfOut = new SimpleDateFormat("MMMM dd, yyyy");
		
		
		try {
			String serviceDateTimeStr = sdfMid.format(Calendar.getInstance().getTime());
			String displayDateTime = 
				DateDisplayUtil.getDisplayDateWithTimeZone(serviceDateTimeStr, describeDTO.getZip());
			
			if (describeDTO.getFixedServiceDate() != null) {
				model.setServiceDates(sdfOut.format(sdfIn.parse(describeDTO.getFixedServiceDate())));
				model.setServiceTime("Between " + describeDTO.getStartTime() + " - " + describeDTO.getEndTime()
						+ " " + sdfTZ.format(sdfDisplay.parse(displayDateTime)));
			} 
			else{
				model.setServiceDates(sdfOut.format(sdfIn.parse(describeDTO.getServiceDate1Text())) + " - " + 
						sdfOut.format(sdfIn.parse(describeDTO.getServiceDate2Text())));
				model.setServiceTime("Between 08:00 AM - 05:00 PM " + sdfTZ.format(sdfDisplay.parse(displayDateTime)));
			}
		}
		catch (ParseException e) {
			logger.error("Date from create servie order DTO cannot be parsed.");
		}
	}
	
	/**
	 * Populates the DTO with costs
	 */
	private void populateCost() {
		double laborCost = Double.parseDouble(describeDTO.getLaborLimit());
		double materialCost = 0.00;
		if (!SLStringUtils.isNullOrEmpty(describeDTO.getMaterialsLimit()) && 
				SLStringUtils.IsParsableNumber(describeDTO.getMaterialsLimit())){
			materialCost = Double.parseDouble(describeDTO.getMaterialsLimit());
		}
		model.setTotalCost(Double.valueOf(laborCost + materialCost).toString());
		model.setPostingFee(describeDTO.getPostingFee());
		model.setTotal(describeDTO.getTotalLimit());		
	}
	
	/**
	 * Load the primary contact from database and populate the primary phone number
	 */
	private void populateContact() {
		try {
			Integer buyerId = get_commonCriteria().getCompanyId();
			Integer buyerResourceId = get_commonCriteria().getVendBuyerResId();
			Contact contact = createServiceOrderDelegate.loadBuyerPrimaryContact(buyerId, buyerResourceId);
			model.setPrimaryPhone(contact.getPhoneNo());
		} catch (DelegateException e) {
			logger.error("Error loading buyer primary contact.");
		}	
	}

	/* 
	 * The method is called when user clicks the next button on the review page. After
	 * saving the bean, it goes to payment page.
	 */
	public String next() throws Exception {
		
		
		/** if SO is already posted take user to find providers page to start with new SO */
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(org.apache.commons.lang.StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		SSoWSessionFacility.getInstance().evaluateSSOWBeanState( getModel());
		List<IError> errorsList = getModel().getErrorsOnly();
		List<IWarning> warningsList = getModel().getWarningsOnly(); 
		
		if(errorsList.size() > 0 || warningsList.size() > 0) {
			return SSO_ORDER_REVIEW_DTO;
		}
		
		return SSO_ADD_FUNDS_DTO;
	}

	/* The method is called when user clicks the previous button on the review page. After
	 * saving the bean, it goes to create and describe page.
	 */
	public String previous() throws Exception {	
		/** if SO is already posted take user to find providers page to start with new SO */
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(org.apache.commons.lang.StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		return SSO_DESCRIBE_AND_SCHEDULE_DTO;
	}
	
	/**
	 * Save the service order as draft and go to dashboard when the user clicks 
	 * the save button on review page.
	 * 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public String save() throws BusinessServiceException{
		/** if SO is already posted take user to find providers page to start with new SO */
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(org.apache.commons.lang.StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
		String result = SSoWSessionFacility.getInstance().evaluateAndSaveSSO(isoWizardPersistDelegate);
		
		String simpleBuyerId = (String)getSession().getAttribute(Constants.SESSION.SIMPLE_BUYER_GUID);
		String soId;
		
		//Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
		.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		soId = serviceOrderWizardBean.getSoId();
		
		if(soId != null && simpleBuyerId != null)
		{
			createServiceOrderDelegate.saveDocuments(simpleBuyerId, soId, null);
		}
		
		if (result.equals(SUCCESS)) {
			SSoWSessionFacility.getInstance().invalidateSessionFacilityStates();
			return "go_to_dashboard";	
		}
		else{
			return result;
		}
	}
	
	public String viewProvidersList(){
		return "view_providers_list";
	}
	
	public CreateServiceOrderReviewDTO getModel()
	{
		return model;
	}

	
	public void setModel(CreateServiceOrderReviewDTO model){
		this.model = model;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	/**
	 * @return the describeDTO
	 */
	public CreateServiceOrderDescribeAndScheduleDTO getDescribeDTO() {
		return describeDTO;
	}

	/**
	 * @param describeDTO the describeDTO to set
	 */
	public void setDescribeDTO(CreateServiceOrderDescribeAndScheduleDTO describeDTO) {
		this.describeDTO = describeDTO;
	}

	/**
	 * @return the findProvidersDTO
	 */
	public CreateServiceOrderFindProvidersDTO getFindProvidersDTO() {
		return findProvidersDTO;
	}

	/**
	 * @param findProvidersDTO the findProvidersDTO to set
	 */
	public void setFindProvidersDTO(
			CreateServiceOrderFindProvidersDTO findProvidersDTO) {
		this.findProvidersDTO = findProvidersDTO;
	}

	/**
	 * @return the serviceOrderDelegate
	 */
	public ISOMonitorDelegate getServiceOrderDelegate() {
		return serviceOrderDelegate;
	}

	/**
	 * @param serviceOrderDelegate the serviceOrderDelegate to set
	 */
	public void setServiceOrderDelegate(ISOMonitorDelegate serviceOrderDelegate) {
		this.serviceOrderDelegate = serviceOrderDelegate;
	}
}//End of Class



