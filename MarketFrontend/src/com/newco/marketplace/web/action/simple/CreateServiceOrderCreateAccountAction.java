package com.newco.marketplace.web.action.simple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderCreateAccountDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.utils.Config;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.constants.Constants;


@Validation
public class CreateServiceOrderCreateAccountAction extends SLBaseAction 
				implements Preparable, ModelDriven<CreateServiceOrderCreateAccountDTO> //,ISimpleServiceOrderAction
{
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private ILookupDelegate lookupDelegate ;
	private static final Logger logger = Logger.getLogger("CreateServiceOrderCreateAccountAction");
	private CreateServiceOrderCreateAccountDTO accountDTO;
	private List<String> blackoutStates;
	
	private static final long serialVersionUID = 123456L;

	public CreateServiceOrderCreateAccountAction(ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}
	
	public void prepare() throws Exception {
		// USED THIS METHOD TO DETERMINE IF THE USER
		// IS LOGGED INTO THE SITE
		createCommonServiceOrderCriteria();
		try {
			// SINCE THE SESSIONFACTILITY HAVE CREATE ALL DTO'S AND PLACED THEM INTO THE SESSION
			// WE JUST NEED TO RETRIEVE THEM AT THIS POINT. PLEASE NOTE THE getModel method below
			SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_CREATE_ACCOUNT_DTO);
			
			// NEEDED TO DETERMINE IF YOU ARE IN EDIT OR CREATE MODE
			SSoWSessionFacility.getInstance().getApplicationMode();
			
		} catch (Exception e) {
			logger.info("In CreateServiceOrderCreateAccountAction.prepare():Caught Exception and ignoring",e);
		}
	}
	@SkipValidation
	public String displayPage() throws Exception {
		try {
			
			Map<String,Object> sessionMap = ActionContext.getContext().getSession();

			/** if SO is already posted take user to find providers page to start with new SO */
			String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
			if(StringUtils.isBlank(isOrderPostedAlready) && (getRequest().getParameter("fromHome")== null )){
				return "go_to_dashboard";
			}
			
			CreateServiceOrderCreateAccountDTO defaultAccountDTO = new CreateServiceOrderCreateAccountDTO();
			createServiceOrderDelegate.loadAccount(defaultAccountDTO);
			getRequest().setAttribute("accountDTO", defaultAccountDTO);		
			getStateZipCode();
			
			blackoutStates = createServiceOrderDelegate.getBlackoutStates();
			
			return SUCCESS;
		} catch(DelegateException delegateEx) {
			addActionError("Exception Occured while processing the request due to " + delegateEx.getMessage());
			return ERROR;
		}
	}
	@SkipValidation
	private void getStateZipCode(){
		ServletContext servletContext = ServletActionContext.getServletContext();
		String zipCode = (String)getSession().getAttribute("caZipCode");
		getSession().removeAttribute("caZipCode");
		accountDTO = new CreateServiceOrderCreateAccountDTO();
		
		// Validate zipcode number format
    	boolean validFormat = false;
    	String stateCode = "";
    	if(StringUtils.isNotBlank(zipCode)){
    	try {
			int zipcodeNbr = Integer.parseInt(zipCode);
			validFormat = true;
		} catch (NumberFormatException nfEx) {
			logger.info("Invalid zipcode format - NumberFormatException");
			validFormat = false;
		}
    }
		if (validFormat) {
			// Validate zipcode from database
			if(StringUtils.isNotBlank(zipCode)){
			try{	
				stateCode = lookupDelegate.getStateForZip(zipCode);
				accountDTO.setZip(zipCode);		
				accountDTO.setState(stateCode);	
			}catch (Exception e){
				logger.info("In getStateZipCode():Caught Exception and ignoring",e);
			}				
		}				
	}			
		return;
	}



	public String previous() throws Exception {
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();

		/** if SO is already posted take user to find providers page to start with new SO */
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(StringUtils.isBlank(isOrderPostedAlready) && (getRequest().getParameter("fromHome")== null )){
			return "go_to_dashboard";
		}
		// Identify which path buyer came from:
		//    Path-1: Came from Sign up link present on "Find Providers" page
		//	  Path-2: Came from Describe and Schedule page
		String fromFindPro = getRequest().getParameter("fromFindPro");
		String fromDS = getRequest().getParameter("fromDS");
		if (MPConstants.FLAG_YES.equalsIgnoreCase(fromFindPro)) {
			return "findProvidersPage";
		} else if (MPConstants.FLAG_YES.equalsIgnoreCase(fromDS)) {
			return "describeSchedulePage";
		} else {
			throw new Exception("Invalid method call! Must pass a valid request parameter: fromHome, fromFindPro or fromDS to identify the workflow!");
		}
		
	}
	
	public String next() throws Exception {
		
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();

		/** if SO is already posted take user to find providers page to start with new SO */
		String isOrderPostedAlready = (String)sessionMap.get(OrderConstants.SSO_WIZARD_IN_PROGRESS);
		if(StringUtils.isBlank(isOrderPostedAlready) && (getRequest().getParameter("fromHome")== null )){
			return "go_to_dashboard";
		}
		
		// Get necessary request parameters to identify which path buyer came from:
		//    Path-1: Came from home page, where "Submit" was clicked on Join Now Page
		//    Path-2: Came from Sign up link present on "Find Providers" page
		//	  Path-3: Came from Describe and Schedule page
		String fromHome = getRequest().getParameter("fromHome");
		String fromFindPro = getRequest().getParameter("fromFindPro");
		String fromDS = getRequest().getParameter("fromDS");

		// Check if user came from Describe & Schedule page then session should be validated
		if (MPConstants.FLAG_YES.equalsIgnoreCase(fromDS) && getSession().getAttribute(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY) == null)
		{
			return "homepage";
		}
		
		// Perform validations
		CreateServiceOrderCreateAccountDTO accountDTO = getModel();
		boolean valid = checkValidations(accountDTO);
		if (!valid) {
			 // Load agreement texts
			CreateServiceOrderCreateAccountDTO tmpDTO = new CreateServiceOrderCreateAccountDTO();
			createServiceOrderDelegate.loadAccount(tmpDTO);
			accountDTO.setBuyerTermsAndConditionText(tmpDTO.getBuyerTermsAndConditionText());
			accountDTO.setSlBucksText(tmpDTO.getSlBucksText());
			
			blackoutStates = createServiceOrderDelegate.getBlackoutStates();
			
			return SUCCESS;
		}
		
		// Validations are through; now persist info in database
		CreateServiceOrderCreateAccountDTO updatedAccountDTO = null;
 		try {
			// if blackout state, save buyer and forward to registration error page
			if (createServiceOrderDelegate.isBlackoutState(accountDTO.getState())) {
				createServiceOrderDelegate.saveBuyerLead(accountDTO);
				SSoWSessionFacility.getInstance().invalidateSessionFacilityStates();
				logger.info( getClass().getName() + " :: state is blackoutState" );
				return "registrationErrorPage";
			} else {
				updatedAccountDTO = createServiceOrderDelegate.saveSimpleBuyerRegistration(accountDTO);
			}
		} catch (DuplicateUserException dupUserEx) {
			addFieldError("accountDTO.username",Config.getResouceBundle().getString("Duplicate_User_Name"));
			logger.info( "Duplicate user exists" );
			 // Load agreement texts
			CreateServiceOrderCreateAccountDTO tmpDTO = new CreateServiceOrderCreateAccountDTO();
			createServiceOrderDelegate.loadAccount(tmpDTO);
			accountDTO.setBuyerTermsAndConditionText(tmpDTO.getBuyerTermsAndConditionText());
			accountDTO.setSlBucksText(tmpDTO.getSlBucksText());
			return SUCCESS;
		} catch (DelegateException delegateEx) {
			return ERROR;
		}
		
		if (MPConstants.FLAG_YES.equalsIgnoreCase(fromHome) ||
				MPConstants.FLAG_YES.equalsIgnoreCase(fromFindPro)) { // Path-1 or Path-2
			// SET REQUEST ATTRIBUTES TO BE USED TO IN MMH URL
			setAttribute( "mmhURL", PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MMH_REGISTRATION_URL) );
			setAttribute( "messageType", "Signup Confirmation" );
			setAttribute( "email", updatedAccountDTO.getEmail() );
			setAttribute( "firstName", updatedAccountDTO.getFirstName() );
			setAttribute( "lastName", updatedAccountDTO.getLastName() );
			setAttribute( "zipCode", updatedAccountDTO.getZip() );
			return "emailSent";
		} else if (MPConstants.FLAG_YES.equalsIgnoreCase(fromDS)) { // Path-3
			getSession().setAttribute(SOConstants.USERNAME_WHILE_CREATING_SERVICE_ORDER, updatedAccountDTO.getUsername());
			getSession().setAttribute("firstorder", "true");
			return "autoLoginAndRedirectToReviewPage";
		} else {
			throw new Exception("Invalid method call! Must pass a valid request parameter: fromHome, fromFindPro or fromDS to identify the workflow!");
		}
	}
	
	private boolean checkValidations(CreateServiceOrderCreateAccountDTO accountDTO) {
		
		if ("true".equals(accountDTO.getAccountAddressInd())) {
			updateAccountAddressFromWorkAddress(accountDTO);
		}
		accountDTO.validate();
		
		List<IError> errors = accountDTO.getErrors();
		if (errors != null && !errors.isEmpty()) {
			// Go back to "Join Now" screen with highlighted errors
			for (IError errorObj : errors) {
				addFieldError(errorObj.getFieldId(), errorObj.getMsg());
			}
			return false;
		}
		
		boolean validStateZip = validateStateZip(accountDTO.getState(), accountDTO.getZip());
		if (!validStateZip) {
			// Go back to "Join Now" screen with highlighted errors
			return false;
		}
				
		return true;
	}
	@SkipValidation
	private void updateAccountAddressFromWorkAddress(CreateServiceOrderCreateAccountDTO accountDTO) {
		CreateServiceOrderDescribeAndScheduleDTO createSODTO = getDescribeAndScheduleDTO();
		accountDTO.setLocName(createSODTO.getLocationName());
		accountDTO.setStreet1(createSODTO.getStreet1());
		accountDTO.setAptNo(createSODTO.getAptNo());
		accountDTO.setStreet2(createSODTO.getStreet2());
		accountDTO.setCity(createSODTO.getCity());
		accountDTO.setState(createSODTO.getStateCd());
		accountDTO.setZip(createSODTO.getZip());
	}
	@SkipValidation
	private CreateServiceOrderDescribeAndScheduleDTO getDescribeAndScheduleDTO() {
		
		Map<String, Object> sessionMap = (Map<String, Object>)ActionContext.getContext().getSession();
		
		SimpleServiceOrderWizardBean serviceOrderWizardBean =
			(SimpleServiceOrderWizardBean)sessionMap.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		
		if (null == serviceOrderWizardBean) {
			return null;
		}
		
		HashMap<String, Object> soDTOs = serviceOrderWizardBean.getTabDTOs();
		CreateServiceOrderDescribeAndScheduleDTO createSODTO =
			(CreateServiceOrderDescribeAndScheduleDTO)soDTOs.get(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
		
		return createSODTO;
	}
	@SkipValidation
	private boolean validateStateZip(String stateCode, String zipcode) {
		try {
			List<LocationVO> locationVOList = createServiceOrderDelegate.loadLocations(stateCode);
			if(!validateStateZip(locationVOList, zipcode))
			{
				addFieldError("accountDTO.zip", "Zip code doesn't match with selected state");
				return false;
			}
		} catch (DelegateException delegateEx) {
			return false;
		}
		return true;
	}
	@SkipValidation
	private boolean validateStateZip(List<LocationVO> list, String zip) {	
		Iterator<LocationVO> itr = list.iterator();
		boolean valid = false;
		while (itr.hasNext()) {
			String validZip = ((LocationVO) itr.next()).getZip();
			
			if (zip.equals(validZip)) {
				valid = true;
			}
		}
		return valid;
	}
	
	public CreateServiceOrderCreateAccountDTO getModel() {
		return accountDTO;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	public CreateServiceOrderCreateAccountDTO getAccountDTO() {
		return accountDTO;
	}

	public void setAccountDTO(CreateServiceOrderCreateAccountDTO accountDTO) {
		this.accountDTO = accountDTO;
	}

	public List<String> getBlackoutStates() {
		return blackoutStates;
	}

	public void setBlackoutStates(List<String> blackoutStates) {
		this.blackoutStates = blackoutStates;
	}
	public ILookupDelegate getLookupDelegate() {
		return lookupDelegate;
	}

	public void setLookupDelegate(ILookupDelegate lookupDelegate) {
		this.lookupDelegate = lookupDelegate;
	}
}
