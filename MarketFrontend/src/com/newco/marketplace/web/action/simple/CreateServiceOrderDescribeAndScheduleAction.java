	package com.newco.marketplace.web.action.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchSkillTypeVO;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.web.action.base.ISimpleServiceOrderAction;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class CreateServiceOrderDescribeAndScheduleAction extends SLSimpleBaseAction
				implements Preparable, ModelDriven<CreateServiceOrderDescribeAndScheduleDTO>, ISimpleServiceOrderAction
{
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private ILookupDelegate lookupDelegate = null;
	private PromoBO PromoBO = null;
	private CreateServiceOrderDescribeAndScheduleDTO model = new CreateServiceOrderDescribeAndScheduleDTO();
	private static final Logger logger = Logger.getLogger("CreateServiceOrderDescribeAndScheduleAction");
	private CreateServiceOrderFindProvidersDTO findProvidersDTO = null;
	
	private static final long serialVersionUID = 0L;

	public CreateServiceOrderDescribeAndScheduleAction(ICreateServiceOrderDelegate delegate)
	{
		createServiceOrderDelegate = delegate;
	}
	
	public void prepare() throws Exception
	{
//		USED THIS METHOD TO DETERMINE IF THE USER
		// IS LOGGED INTO THE SITE

		createCommonServiceOrderCriteria();
		
	}
	
	public CreateServiceOrderDescribeAndScheduleDTO getModel()
	{
		model = 
				(CreateServiceOrderDescribeAndScheduleDTO)
							SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
		return model;
	}

	public void setModel(CreateServiceOrderDescribeAndScheduleDTO model)
	{
		this.model = model;
	}

	
	/**
	 * display describe & schedule page
	 * @return
	 */
	public String displayPage(){
		// get so from session.
		
		/** if SO is already posted take user to find providers page to start with new SO */
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(org.apache.commons.lang.StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}

		try {
			// SINCE THE SESSIONFACTILITY HAVE CREATE ALL DTO'S AND PLACED THEM INTO THE SESSION
			// WE JUST NEED TO RETRIEVE THEM AT THIS POINT. PLEASE NOTE THE getModel method below
			
			 model = 
				(CreateServiceOrderDescribeAndScheduleDTO)
							SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
			 findProvidersDTO = (CreateServiceOrderFindProvidersDTO)
				SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_FIND_PROVIDERS_DTO); 
			 
			 if (model.getZip() != null) {
				 getStateMatch();
				 model.setTimeZone(getTimeZone(model.getZip()));
			 }
			 
			 Boolean isLoggedIn = (Boolean)getSession().getAttribute(IS_LOGGED_IN);
				if (isLoggedIn.booleanValue()){
					model.setLoggedIn(true);
				}
			
			 setCost();	
			
			 
			String editOrCreateMode = 
						(String)SSoWSessionFacility.getInstance().getApplicationMode();
			populateTaskName();
			setModel(model);
			
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}

		
		getRequest().setAttribute("DESC_SCHE_PAGE", true);
		return SUCCESS;
		
		
	}

	/**
	 * Sets the labor limit, materials limit, posting fee and total cost
	 */
	private void setCost() {
		String postingFee = PropertiesUtils.getPropertyValue(BuyerConstants.SIMPLE_BUYER_POSTING_FEE);

		Double theFee = Double.parseDouble(postingFee);
		postingFee = ""+PromoBO.retrievePromoPostingFeeSimpleBuyer(theFee);
		
		if (model.getWfStateId() != null && model.getWfStateId() == OrderConstants.ROUTED_STATUS) {
			model.setPostingFee("0.00");
		 }
		 else{
			 
			 model.setPostingFee(postingFee);
		 }

		//unconditionally update the so_hdr rec with retrieved posting fee.
		ServiceOrder so = new ServiceOrder();
		so.setPostingFee(new Double(model.getPostingFee()));
		so.setSoId(findProvidersDTO.getSo_id());
		PromoBO.updatePostingFee(so);

		double laborAmt = model.getLaborLimit()!= null ? Double.parseDouble(model.getLaborLimit()) : 0.00;
		double partsAmt = model.getMaterialsLimit()!= null ? Double.parseDouble(model.getMaterialsLimit()) : 0.00;
		double posting = model.getPostingFee() != null ? Double.parseDouble(model.getPostingFee()) : 0.00;
		double totalAmt = laborAmt + partsAmt + posting;
		model.setTotalLimit(String.valueOf(totalAmt));
	}

	public String next() throws Exception {
		
		/** if SO is already posted take user to find providers page to start with new SO */
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(org.apache.commons.lang.StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		
//		 IF YOU HAVE SCREEN VALIDATIONS CALL THE evaluateSSOWBeanState METHOD
		// ON THE SSoWSessionFacility INSTANCE.
		 SSoWSessionFacility.getInstance().evaluateSSOWBeanState( getModel());
		//AFTER CALLING evaluateSSOWBeanState() THE DTO WILL HAVE A LIST OF 
		// IERROR MESSAGES POPULATED. IN ORDER TO GET THIS TO WORK YOU WILL NEED TO 
		// IMPLEMENT THE validate METHOD LOCATED IN YOUR DTO. PLEASE NOTE: YOUR DTO MUST
		// EXTEND THE SOWBaseTabDTO CLASS.
		model = getModel();
		/*		if(Boolean.getBoolean(getSession().getAttribute(IS_LOGGED_IN).toString())){
			validateZipCode(model);
			model.setLoggedIn(true);
		}*/
		
		// save into session
		//Map<String, Object> sessionMap = (Map<String, Object>)ActionContext.getContext().getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean)
		sessionMap.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		HashMap<String, Object> soDTOs = serviceOrderWizardBean.getTabDTOs();
		soDTOs.put(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO, model);
		
		sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
		
		
		model = 
			(CreateServiceOrderDescribeAndScheduleDTO)
						SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
		 
		
		


		List<IError> errorsList = getModel().getErrorsOnly();
		if(errorsList.size()== 0) {
			Boolean isLoggedIn = (Boolean)getSession().getAttribute(IS_LOGGED_IN);
			if (isLoggedIn.booleanValue()){
				return TO_SSO_SERVICE_ORDER_REVIEW_VIEW;
			} else {
				return TO_SSO_CREATE_ACCOUNT_VIEW;
			}
		} else {
			return TO_SSO_DESCRIBE_AND_SCHEDULE_VIEW;
		}
	}
	
	// get state for given zipCide
	private void getStateMatch() throws Exception{
		
		String zipCode = model.getZip();
		String stateDesc = "";
		// Get resource Bundle for validation error messages
		ServletContext servletContext = ServletActionContext.getServletContext();
		LocalizationContext localizationContext = (LocalizationContext)servletContext.getAttribute("serviceliveCopyBundle");
		String invalidZipCodeMsg = localizationContext.getResourceBundle().getString("wizard.invalidZipCode");
		
		// Validate zipcode number format
    	boolean validFormat = false;
    	String stateCode = null;
    	
    	try {
			int zipcodeNbr = Integer.parseInt(zipCode);
			validFormat = true;
		} catch (NumberFormatException nfEx) {
			logger.info("Invalid zipcode format - NumberFormatException");
			validFormat = false;
		}
		
		if (validFormat) {
			// Validate zipcode from database
			LocationVO locationVO = lookupDelegate.checkIfZipISValid(zipCode);
			
			// Prepare response output
			if (locationVO == null) {
				logger.info("Invalid zipcode - no state found in database");
			} else {
				stateCode = locationVO.getState();
				logger.info("State Code = " + stateCode);
				List<LookupVO> states = (List<LookupVO>)servletContext.getAttribute(Constants.SERVLETCONTEXT.STATES_LIST);
				
				for (LookupVO lookupVO : states) {
					if (lookupVO.getType().equalsIgnoreCase(stateCode)) {
						stateDesc = lookupVO.getDescr();
						break;
					}
				}
				
				if (stateDesc == null) {
					logger.info("Invalid zipcode - State description not found in servlet context");
				} 
				
			}
		}
	
		model.setStateCd(stateCode);
		model.setStateDesc(stateDesc);
		return;
	
	}
	
	/**
	 * Populates the DTO with task name
	 */
	private void populateTaskName() {
		StringBuilder taskName = new StringBuilder();
		String mainCatId = new String();
		
		if (findProvidersDTO != null) {
			PopularServicesVO servicesVO = findProvidersDTO.getSelectedCategorysVO();
			if (StringUtils.isNotEmpty(servicesVO.getName())) {
				taskName.append(servicesVO.getName() + " > ");
			}
			if (StringUtils.isNotEmpty(servicesVO.getCategoryName())) {
				taskName.append(servicesVO.getCategoryName() + " > ");
			}
			if (StringUtils.isNotEmpty(servicesVO.getSubCategoryName())) {
				taskName.append(servicesVO.getSubCategoryName() + " > ");
			}
			List serviceTypesList = findProvidersDTO.getCheckedJobs();
			if(serviceTypesList != null){
				for (int i = 0; i < serviceTypesList.size(); i++) {
					taskName.append(((SimpleProviderSearchSkillTypeVO)serviceTypesList.get(i)).getSkillTypeDescr() + ", ");
				}
			}
			if (taskName.length() > 2) {
				taskName.delete(taskName.length()-2, taskName.length()-1);
			}
			mainCatId = servicesVO.getMainCategoryId().toString();
		}
		model.setTaskName(taskName.toString());
		model.setMainCatId(mainCatId);
	}
	
	
	public String previous() throws Exception {
		
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();

		/** if SO is already posted take user to find providers page to start with new SO */
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(StringUtils.isBlank(isOrderPostedAlready)){
			return "go_to_dashboard";
		}
		

//		 IF YOU HAVE SCREEN VALIDATIONS CALL THE evaluateSSOWBeanState METHOD
		// ON THE SSoWSessionFacility INSTANCE.
		// SSoWSessionFacility.getInstance().evaluateSSOWBeanState( getModel());
		//AFTER CALLING evaluateSSOWBeanState() THE DTO WILL HAVE A LIST OF 
		// IERROR MESSAGES POPULATED. IN ORDER TO GET THIS TO WORK YOU WILL NEED TO 
		// IMPLEMENT THE validate METHOD LOCATED IN YOUR DTO. PLEASE NOTE: YOUR DTO MUST
		// EXTEND THE SOWBaseTabDTO CLASS.
		model = getModel();
		model.setTimeZone(getTimeZone(model.getZip()));
/*		if(Boolean.getBoolean(getSession().getAttribute(IS_LOGGED_IN).toString())){
			validateZipCode(model);
			model.setLoggedIn(true);
		}*/
		
		// save into session
		//Map<String, Object> sessionMap = (Map<String, Object>)ActionContext.getContext().getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean = (SimpleServiceOrderWizardBean)
		sessionMap.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		HashMap<String, Object> soDTOs = serviceOrderWizardBean.getTabDTOs();
		soDTOs.put(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO, model);
		
		sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
		
		
		model = 
			(CreateServiceOrderDescribeAndScheduleDTO)
						SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
	
		List<IError> errorsList = getModel().getErrorsOnly();
		
		return TO_SSO_FIND_PROVIDERS_VIEW;
	}

	public ILookupDelegate getLookupDelegate() {
		return lookupDelegate;
	}

	public void setLookupDelegate(ILookupDelegate lookupDelegate) {
		this.lookupDelegate = lookupDelegate;
	}

	public PromoBO getPromoBO() {
		return PromoBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		PromoBO = promoBO;
	}
}
