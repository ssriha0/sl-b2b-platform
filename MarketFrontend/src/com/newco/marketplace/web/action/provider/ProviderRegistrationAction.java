package com.newco.marketplace.web.action.provider;

/**
 * @author sahmad7
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.jcraft.jsch.Session;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.web.delegates.provider.IRegistrationDelegate;
import com.newco.marketplace.web.dto.provider.ProviderRegistrationDto;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ProviderRegistrationAction extends ActionSupport implements
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4182435396546182313L;
	/**
	 * Variables for the Radio button in the Provider Registration Page
	 */
	private static List serviceCallList;

	/**
	 * load the radio buttons statically so new arrays are not created each time
	 */
	static {
		// List for Service Call radio
		serviceCallList = new ArrayList();
		serviceCallList.add("Yes");
		serviceCallList.add("No");
	}

	private IRegistrationDelegate iRegistrationDelegate;
	private ProviderRegistrationDto registrationDto;

	/**
	 * Variables for the Combo box in the Provider Registration Page.
	 */
	private List primaryIndList = new ArrayList();
	private List statesList = new ArrayList();
	private List roleWithinCompany = new ArrayList();
	private List howDidYouHearList = new ArrayList();
	// see XW-371 -code from: http://svn.opensymphony.com/svn/xwork/trunk/src/java/com/opensymphony/xwork2/validator/validators/EmailValidator.java
    public static final String emailAddressPattern =
    	"\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
	Pattern pattern;
	Matcher matcher;

	private LookupVO lookupVo;
	private static final Logger logger = Logger
			.getLogger(ProviderRegistrationAction.class.getName());

	public ProviderRegistrationAction(
			IRegistrationDelegate iRegistrationDelegate,
			ProviderRegistrationDto registrationDto) {
		this.iRegistrationDelegate = iRegistrationDelegate;
		this.registrationDto = registrationDto;
	}

	private boolean validateBusinessStateZip() throws Exception {
		try {
			registrationDto.setStateType("business");
			registrationDto = iRegistrationDelegate.loadZipSet(registrationDto);
			List stateTypeList = registrationDto.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					registrationDto.getBusinessZip())) {
				addFieldError("registrationDto.businessZip",
						"Business zip doesn't match selected state");
				return false;
			}
		} catch (Exception a_Ex) {
			logger.log(Level.ERROR,
					"--------- Exception inside validateBusinessStateZip ---",
					a_Ex);
			throw a_Ex;
		}
		return true;
	}

	private boolean validateMailingStateZip() throws Exception {
		try {
			registrationDto.setStateType("mail");
			registrationDto = iRegistrationDelegate.loadZipSet(registrationDto);
			List stateTypeList = registrationDto.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					registrationDto.getMailingZip())) {
				addFieldError("registrationDto.mailingZip",
						"Mailing zip doesn't match selected state");
				return false;
			}
		} catch (Exception a_Ex) {
			logger.log(Level.ERROR,
					"--------- Exception inside validateMailingStateZip ---",
					a_Ex);
			throw a_Ex;
		}
		return true;
	}

	private boolean validateStateZip(List list, String zip) {
		Iterator itr = list.iterator();
		boolean valid = false;
		while (itr.hasNext()) {
			String validZip = ((LocationVO) itr.next()).getZip();

			if (zip.equals(validZip)) {
				return true;
			}
		}
		return false;
	}
	
	private void firstNameValidation(){
		if (null != registrationDto.getFirstName() && registrationDto.getFirstName().equals("Enter your LEGAL first name")){
			addFieldError("registrationDto.firstName", "Please enter a Primary Contact First Name");
		}
	}
	
	private void lastNameValidation(){
		if (null != registrationDto.getLastName() && registrationDto.getLastName().equals("Enter your LEGAL last name")){
			addFieldError("registrationDto.lastName", "Please enter a Primary Contact Last Name");
		}
	}
	
	private void validateUserName(){
		boolean isDulplicate = iRegistrationDelegate.validateUserName(registrationDto.getUserName());
		if(isDulplicate){
			addFieldError("registrationDto.userName", "User Name already exists");
		}
	}

	public void prepare() throws Exception {
		validate();
		doLoad();
	}
	
	@Override
	public void validate() {
		logger.info("ProviderRegistrationAction.validate()");		
		super.validate();
		firstNameValidation();
		lastNameValidation();
		validateUserName();
		
	}

	@SkipValidation
	public String doLoad() throws Exception {

		try {
			// Load the DTO from DB
			registrationDto = iRegistrationDelegate
					.loadRegistration(registrationDto);

			// Loads the Primary Industry Combo from DTO
			setPrimaryIndList(registrationDto.getPrimaryIndList());

			// Loads the Role within Company Combo from DTO
			setRoleWithinCompany(registrationDto.getRoleWithinCompany());

			// Loads the How did you hear about ServiceLive? Combo from DTO
			setHowDidYouHearList(registrationDto.getHowDidYouHearList());

		} catch (DelegateException ex) {

			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			throw ex;
		}
		return SUCCESS;
	}

	/**
	 * Used to prevent validation errors on first hit
	 */
	@SkipValidation
	public String execute() throws Exception {
		return SUCCESS;
	}

	/*
	 * author: G.Ganapathy This method is used to validate business phone number
	 */
	private void validateBusinessPhone() {
		String busPhone1 = null;
		String busPhone2 = null;
		String busPhone3 = null;

		List list1 = new ArrayList();

		try {
			busPhone1 = (registrationDto.getMainBusiPhoneNo1() != null) ? registrationDto
					.getMainBusiPhoneNo1().trim() : "";
			busPhone2 = (registrationDto.getMainBusiPhoneNo2() != null) ? registrationDto
					.getMainBusiPhoneNo2().trim() : "";
			busPhone3 = (registrationDto.getMainBusiPhoneNo3() != null) ? registrationDto
					.getMainBusiPhoneNo3().trim() : "";

			busPhone1 = busPhone1 + busPhone2 + busPhone3;
			list1.add(busPhone1);

			isValidNumber(list1, "Business Phone ",
					"registrationDto.mainBusiPhoneNo1");

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			addFieldError("registrationDto.businessPhone1",
					"Error while validating the Business Phone Number.");
		}
	}

	/*
	 * author: G.Ganapathy This method is used to validate business fax number
	 */
	private void validateFaxNumber() {
		String busFax1 = null;
		String busFax2 = null;
		String busFax3 = null;

		List list1 = new ArrayList();

		try {
			busFax1 = (registrationDto.getBusinessFax1() != null) ? registrationDto
					.getBusinessFax1().trim() : "";
			busFax2 = (registrationDto.getBusinessFax2() != null) ? registrationDto
					.getBusinessFax2().trim() : "";
			busFax3 = (registrationDto.getBusinessFax3() != null) ? registrationDto
					.getBusinessFax3().trim() : "";

			busFax1 = busFax1 + busFax2 + busFax3;

			if (null != busFax1 && busFax1.trim().length() > 0) {
				list1.add(busFax1);
				isValidNumber(list1, "Business Fax ",
						"registrationDto.businessFax1");
			}
		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			addFieldError("registrationDto.businessFax1",
					"Error while validating the Business Fax Number.");
		}
	}

	private boolean isValidNumber(List numberList, String errorMsg,
			String fieldName) {
		String num1 = null;
		String num2 = null;
		String num3 = null;
		boolean flag = true;

		try {
			if (numberList == null || numberList.size() <= 0) {
				addFieldError(fieldName, errorMsg + " is incomplete");
				return false;
			}

			num1 = (numberList.get(0) != null) ? (String) numberList.get(0)
					: "";

			if (num1.trim().length() <= 0 || num1.trim().equals(""))
				addFieldError(fieldName, errorMsg + " is incomplete");

			// Validating for the NUMBER type
			try {
				long numInt1 = Long.parseLong(num1.trim());

			} catch (NumberFormatException a_Ex) {
				addFieldError(fieldName, errorMsg + " must be a number");
				return false;
			} catch (Exception a_Ex) {
				addFieldError(fieldName, errorMsg + " must be a number");
				return false;
			}

			// validation for length
			if (num1.trim().length() < 10) {
				addFieldError(fieldName, errorMsg
						+ "  can not be less than 10 characters");
				flag = false;
			}

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			return false;
		}

		return flag;
	}

	private void validateLegalBusinessName() {
		if (StringUtils.isBlank(registrationDto.getLegalBusinessName())) {
			addFieldError("registrationDto.legalBusinessName",
					"Please enter Legal Business Name");
		}
	}

	private void validateFields() {
		validateLegalBusinessName();
		validateBusinessPhone();
		validateFaxNumber();

		Integer nameLength = Integer.parseInt(Config.getResouceBundle()
				.getString("accountDTO.username.length"));

		if (StringUtils.isBlank(registrationDto.getPrimaryIndustry())) {
			addFieldError("registrationDto.primaryIndustry",
					"Please select a Primary Industry");
		}
		if (StringUtils.isBlank(registrationDto.getBusinessStreet1())) {
			addFieldError("registrationDto.businessStreet1",
					"Please enter a Business Street");
		}
		if (StringUtils.isBlank(registrationDto.getBusinessCity())) {
			addFieldError("registrationDto.businessCity",
					"Please enter a Business City");
		}
		if (StringUtils.isBlank(registrationDto.getRoleWithinCom())) {
			addFieldError("registrationDto.roleWithinCom",
					"Please select a Role within Company");
		}
		if (StringUtils.isBlank(registrationDto.getFirstName()) || registrationDto.getFirstName().equals("Enter your LEGAL first name")) {
			addFieldError("registrationDto.firstName",
					"Please enter a Primary Contact First Name");
		}
		if (StringUtils.isBlank(registrationDto.getLastName()) || registrationDto.getLastName().equals("Enter your LEGAL last name")) {
			addFieldError("registrationDto.lastName",
					"Please enter a Primary Contact Last Name");
		}
		if (StringUtils.isBlank(registrationDto.getEmail())) {
			addFieldError("registrationDto.email",
					"Please enter a Primary Contact Email");
		} else if (!registrationDto.getEmail().equals(
				registrationDto.getConfirmEmail())) {
			addFieldError("registrationDto.confirmEmail",
					"Primary Contact Confirm Email doesn't match");
		}
		if (StringUtils.isBlank(registrationDto.getConfirmEmail())) {
			addFieldError("registrationDto.confirmEmail",
					"Please enter a Primary Contact Confirm Email");
		}		
		if(registrationDto.getEmail()!=null && StringUtils.isNotBlank(registrationDto.getEmail())){
			pattern = Pattern.compile(emailAddressPattern);
            matcher = pattern.matcher(registrationDto.getEmail());
            if (!matcher.matches()) {            	
            	addFieldError("registrationDto.email", "Primary email not valid");            	
            }
		}
		
		if (StringUtils.isBlank(registrationDto.getAltEmail())) {
			addFieldError("registrationDto.altEmail",
					"Please enter a Alternate Contact Email");
		} else if (!registrationDto.getAltEmail().equals(
				registrationDto.getConfAltEmail())) {
			addFieldError("registrationDto.confAltEmail",
					"Alternate Contact Confirm  Email doesn't match");
		}
		if (StringUtils.isBlank(registrationDto.getConfAltEmail())) {
			addFieldError("registrationDto.confAltEmail",
					"Please enter a Alternate Contact Confirm Email");
		}		
		if(registrationDto.getAltEmail()!=null && StringUtils.isNotBlank(registrationDto.getAltEmail())){
			pattern = Pattern.compile(emailAddressPattern);
            matcher = pattern.matcher(registrationDto.getAltEmail());
            if (!matcher.matches()) {            	
            	addFieldError("registrationDto.altEmail", "Alternate email not valid");            	
            }
		}
		if (StringUtils.isBlank(registrationDto.getUserName())) {
			addFieldError("registrationDto.userName",
					"Please enter a Primary Contact Username");
		} else if (StringUtils.trim(registrationDto.getUserName()).length() < nameLength) {
			addFieldError("registrationDto.userName", Config.getResouceBundle()
					.getString("accountDTO.username.error.length"));
		}
		if (StringUtils.isBlank(registrationDto.getHowDidYouHear())) {
			addFieldError("registrationDto.howDidYouHear",
					"Please select how you heard about ServiceLive");
		}
		validateOtherPrimaryServices();
	}	
	
	public String doInsert() throws Exception {
		try {

			validateFields();
			boolean businessFlag = validateBusinessStateZip();
			boolean mailFlag = validateMailingStateZip();
		
				if (hasFieldErrors() || hasActionErrors()) {
					
					return INPUT;
				}
			
		
				if (businessFlag && mailFlag) {
					ServletActionContext.getRequest().getSession().setAttribute("registrationDto",this.registrationDto);
					ServletActionContext.getRequest().setAttribute("popUp",1);
					logger.info("State Status in Action-----------------"
							+ registrationDto.isValidateState());
					return INPUT; 
				} 

			}
		
		catch (DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		} catch (DuplicateUserException dpe) {
			dpe.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ dpe.getMessage());

			if (dpe.getMessage().substring(0, 1).equals("2")) {
				addFieldError("registrationDto.email", dpe.getMessage()
						.substring(1));
			} else {
				addFieldError("registrationDto.userName", Config
						.getResouceBundle().getString("Duplicate_User_Name"));
			}
			return "duplicate";
		}
		
		return SUCCESS;
	}
	
	public String insert() throws Exception {
		try {

			validateFields();
			boolean businessFlag = validateBusinessStateZip();
			boolean mailFlag = validateMailingStateZip();
		
				if (hasFieldErrors() || hasActionErrors()) {
					
					return INPUT;
				}
			
		
				if (businessFlag && mailFlag) {
					ServletActionContext.getRequest().getSession().setAttribute("registrationDto",this.registrationDto);
					ServletActionContext.getRequest().setAttribute("popUp",1);
					logger.info("State Status in Action-----------------"
							+ registrationDto.isValidateState());
					return INPUT; 
				} 

			}
		
		catch (DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		} catch (DuplicateUserException dpe) {
			dpe.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ dpe.getMessage());

			if (dpe.getMessage().substring(0, 1).equals("2")) {
				addFieldError("registrationDto.email", dpe.getMessage()
						.substring(1));
			} else {
				addFieldError("registrationDto.userName", Config
						.getResouceBundle().getString("Duplicate_User_Name"));
			}
			return "duplicate";
		}
		
		return SUCCESS;
	}

	@SkipValidation
	public String saveProviderDetails()throws Exception{
		try
		{		
				ProviderRegistrationDto registrationDto=(ProviderRegistrationDto) ServletActionContext.getRequest().getSession().getAttribute("registrationDto");
				if(null!=registrationDto){
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(registrationDto.getFirstName())){
						registrationDto.setFirstName(registrationDto.getFirstName().trim());
					}
					if(StringUtils.isNotBlank(registrationDto.getLastName())){
						registrationDto.setLastName(registrationDto.getLastName().trim());
					}
					if(StringUtils.isNotBlank(registrationDto.getMiddleName())){
						registrationDto.setMiddleName(registrationDto.getMiddleName().trim());
					}
					
					//R11_2
					//Trimming username before saving in the database
					if(StringUtils.isNotBlank(registrationDto.getUserName())){
						registrationDto.setUserName(registrationDto.getUserName().trim());
					}
					
					iRegistrationDelegate.saveRegistration(registrationDto);
					ServletActionContext.getRequest().getSession().removeAttribute("registrationDto");
					return SUCCESS;
				 }
		}
		catch (DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		} catch (DuplicateUserException dpe) {
			dpe.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ dpe.getMessage());

			if (dpe.getMessage().substring(0, 1).equals("2")) {
				addFieldError("registrationDto.email", dpe.getMessage()
						.substring(1));
			} else {
				addFieldError("registrationDto.userName", Config
						.getResouceBundle().getString("Duplicate_User_Name"));
			}
			return "duplicate";
		}
		return SUCCESS;
	}
	
		
	

	/**
	 * Method to validate other primary services
	 * 
	 */
	private void validateOtherPrimaryServices() {
		if (StringUtils.isNotBlank(registrationDto.getPrimaryIndustry())
				&& ProviderConstants.OTHER_PRIMARY_SERVICES
						.equals(registrationDto.getPrimaryIndustry())
				&& (StringUtils.isBlank(registrationDto
						.getOtherPrimaryService()))) {
			addFieldError(
					"registrationDto.otherPrimaryService",
					Config.getResouceBundle().getString(
							"Other_Primary_Service_Not_Entered"));
		}
	}

	public ProviderRegistrationDto getRegistrationDto() {
		return registrationDto;
	}

	public void setRegistrationDto(ProviderRegistrationDto registrationDto) {
		this.registrationDto = registrationDto;
	}

	public LookupVO getLookupVo() {
		return lookupVo;
	}

	public void setLookupVo(LookupVO lookupVo) {
		this.lookupVo = lookupVo;
	}

	public List getServiceCallList() {
		return serviceCallList;
	}

	public void setServiceCallList(List serviceCallList) {
		this.serviceCallList = serviceCallList;
	}

	public List getPrimaryIndList() {
		return primaryIndList;
	}

	public void setPrimaryIndList(List primaryIndList) {
		this.primaryIndList = primaryIndList;
	}

	public List getStatesList() {
		return statesList;
	}

	public void setStatesList(List statesList) {
		this.statesList = statesList;
	}

	public List getRoleWithinCompany() {
		return roleWithinCompany;
	}

	public void setRoleWithinCompany(List roleWithinCompany) {
		this.roleWithinCompany = roleWithinCompany;
	}

	public List getHowDidYouHearList() {
		return howDidYouHearList;
	}

	public void setHowDidYouHearList(List howDidYouHearList) {
		this.howDidYouHearList = howDidYouHearList;
	}

	
}