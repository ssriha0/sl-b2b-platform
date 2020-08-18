/**
 *
 */
package com.newco.marketplace.web.action.provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.security.ActivityMapper;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.vo.provider.W9RegistrationVO;
import com.newco.marketplace.web.constants.W9Constants;
import com.newco.marketplace.web.dto.SOWError;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author hoza
 *
 */

// @Validation
public class W9RegistrationAction extends ActionSupport implements
ServletRequestAware {

	private static final long serialVersionUID = 123509443231332602L;
	private IW9RegistrationBO w9RegistrationBO;
	private W9RegistrationVO w9prefill = new W9RegistrationVO();
	private ILookupBO lookupBO;
	private SecurityContext securityContext;
	private HttpSession session;
	private HttpServletRequest request;
	private Map theSessionMap;
	private String w9Modal = "false";
	private String saveAction = "w9registrationAction_";
	private static final Logger logger = Logger
			.getLogger(W9RegistrationAction.class.getName());
	public static String NAME_OF_W9_ACTION = "w9registrationAction_";

	public static String DASHBOARD_ACTION = "dashboardAction";
	public static String SUCCESS_BODY = "successbody";
	public static String ALERT_MESSAGE_BODY = "alertMessage";
	public static String SUCCESS_MODAL = "successexternal";
	/**
	 *
	 */
	public W9RegistrationAction() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
	}
	public String cancel() {
		return DASHBOARD_ACTION;
	}

	
	private void loadW9Details() {
		List<LookupVO> stateCodes;
		List<LookupVO> businessTypes;
		List<LookupVO> taxPayerTypeIds;
		boolean isW9ExistsFlag = false;
		String vendorIdStr = (String) ActionContext.getContext().getSession()
				.get("vendorId");
		Integer vendorId = new Integer(vendorIdStr);
		try {
			isW9ExistsFlag = w9RegistrationBO.isW9Exists(vendorId);
			isW9Admin();
			if (isW9ExistsFlag) {
				this.w9prefill = w9RegistrationBO.get(vendorId);
			} else {
				request.setAttribute("w9prefillFlag", "true");
				this.w9prefill = w9RegistrationBO.getPrefill(vendorId);
			}
			this.w9prefill.setEditOrCancel("edit");
			this.w9prefill.setOrginalTaxPayerTypeId(this.w9prefill.getTaxPayerTypeId());
			if (this.w9prefill.getEin() != null && this.w9prefill.getEin().length()>= 9 && this.w9prefill.getTaxPayerTypeId()==0){
				this.w9prefill.setOrginalTaxPayerTypeId(3);
			}
			if(this.w9prefill!=null && this.w9prefill.getOriginalUnmaskedEin()!=null)
			{
			request.setAttribute("actualUnMaskedIn", this.w9prefill.getOriginalUnmaskedEin());
			}
			else
			{
			request.setAttribute("actualUnMaskedIn", "");	
			}
			stateCodes = lookupBO.getStateCodes();
			businessTypes = lookupBO.getBusinessTypes();
			taxPayerTypeIds = lookupBO.getTaxPayerTypeIdList();
			// this.model = w9prefill;

			// request.setAttribute("w9prefill", w9prefill);
			request.setAttribute("stateCodes", stateCodes);
			request.setAttribute("businessTypes", businessTypes);
			request.setAttribute("taxPayerTypeIds", taxPayerTypeIds);
			getEinData(isW9ExistsFlag);
			
		} catch (Exception e) {
			logger.error("Not able to get prefill information for w9. ", e);
		}
	}
	
	private void getEinData(boolean isW9ExistsFlag) throws Exception{
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		Integer vendorId = new Integer((String)sessionMap.get("vendorId"));
		sessionMap.put("w9isExist", "false");
		sessionMap.put("w9isExistWithSSNInd", "false");
		if (isW9ExistsFlag) {
			sessionMap.put("w9isExist", "true");
			if (w9RegistrationBO.isAvailableWithSSNInd(vendorId))
			{
				sessionMap.put("w9isExistWithSSNInd", "true");
			}
			else
			{
				if(!this.getActionErrors().contains("Please confirm if the Taxpayer ID Number type is EIN or SSN."))
					this.addActionError("Please confirm if the Taxpayer ID Number type is EIN or SSN.");
			}
		}
	}

	public String load() {
		loadW9Details();
		if ("true".equals(w9Modal)) {
			request.setAttribute("w9Modal", "true");
			return SUCCESS_MODAL;
		}
		return SUCCESS;
	}

	public String save() {
		W9RegistrationVO w9save = this.getModel();
		if ("Cancel".equals(w9save.getCancel()))
		{
			return this.cancel();
		}
		String vendorIdStr = (String) ActionContext.getContext().getSession().get("vendorId");
		w9save.setVendorId(new Integer(vendorIdStr));
		String username = getLoggedInUserName();
		w9save.setModifiedBy(username);
		String actualUnMaskedIn="";
		try {
			actualUnMaskedIn = w9RegistrationBO.getOrginalEin(new Integer(vendorIdStr));
			
		}  catch (Exception e) {
			logger.error("Not able to get Orginal Ein. ", e);
		}
		
		if (!this.validate_fields(w9save,actualUnMaskedIn)) {
			request.setAttribute("saveAction","w9registrationAction_save.action");

			try {
				if (!w9RegistrationBO.isW9Exists(new Integer(vendorIdStr)))
				{
					request.setAttribute("w9prefillFlag", "true");

				}
			} catch (Exception e) {
				logger.error("Not able to get prefill information for w9. ", e);
			}

			this.w9prefill = w9save;
			setAuxilaryRequiredSelectOptions();
			if ("true".equals(w9Modal)) {
				request.setAttribute("w9Modal", "true");
				return SUCCESS_MODAL;
			}
			return SUCCESS;
		}

		fixPrefillEIN(w9save);

		w9prefill = updateDB(w9save);
		//w9prefill.setEin2(w9prefill.getEin());
		setAuxilaryRequiredSelectOptions();

		if ("true".equals(w9Modal)) {
			request.setAttribute("w9Modal", "true");
			request.setAttribute("saveAction","w9registrationAction_saveAjax.action");
			return NONE;
		}
		else
		{
			request.setAttribute("saveAction","w9registrationAction_save.action");
		}
		return DASHBOARD_ACTION;
	}

	/**
	 * @return
	 */
	private String getLoggedInUserName() {
		String  userName = "UNKNOWN";
		if(getSecurityContext() != null ) {
		 userName = !StringUtils.isBlank(getSecurityContext().getSlAdminUName())? getSecurityContext().getSlAdminUName() : (String) getSecurityContext().getUsername();
		}
		return userName;
	}

	/**
	 *
	 */
	private void setAuxilaryRequiredSelectOptions() {
		try {
			List<LookupVO> stateCodes;
			List<LookupVO> businessTypes;
			List<LookupVO> taxPayerTypeIds;
			stateCodes = lookupBO.getStateCodes();
			businessTypes = lookupBO.getBusinessTypes();
			taxPayerTypeIds = lookupBO.getTaxPayerTypeIdList();
			request.setAttribute("stateCodes", stateCodes);
			request.setAttribute("businessTypes", businessTypes);
			request.setAttribute("taxPayerTypeIds", taxPayerTypeIds);
		} catch (Exception e) {
			logger.error("Error getting stateCodes ", e);
		}
	}

	/**
	 * @param stateCodes
	 * @param businessTypes
	 * @param w9save
	 */
	private W9RegistrationVO updateDB(W9RegistrationVO w9save) {
		try {
			String ein1 = w9save.getEin();
			if (!ein1.startsWith("XXX-XX-") || !ein1.startsWith("XX-XXXX-")) {
				ein1 = ein1.replace("-", "");
				w9save.setEin(ein1);
			}

			W9RegistrationVO w9saveResult = w9RegistrationBO.save(w9save);
			return w9saveResult;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public String saveAjax() throws Exception {
		this.setW9Modal("true");
		request.setAttribute("w9Modal", "true");
		return save();
		//return SUCCESS_BODY;
	}

	private String getErorListStr() {
		StringBuilder sb = new StringBuilder();
		if (hasActionErrors()) {
			// this.getActionErrors()
		}
		/*
		 * for()
		 *
		 * sb.append(b)
		 */
		return sb.toString();
	}

	public String execute() throws Exception {
		loadW9Details();
		request.setAttribute("saveAction", "w9registrationAction_save.action");
		return SUCCESS;
	}

	public String executeModal() {
		loadW9Details();
		// this could result in return
		request.setAttribute("w9Modal", "true");
		request.setAttribute("saveAction",
				"w9registrationAction_saveAjax.action");

		return SUCCESS_MODAL;
	}

	private void fixPrefillEIN(W9RegistrationVO w9save)
	{
		String vendorIdStr = (String) ActionContext.getContext().getSession().get("vendorId");
		String ein1 = w9save.getEin();
		String originalEin = w9save.getOriginalEin();
		

		try {
			if (w9RegistrationBO.isW9Exists(new Integer(vendorIdStr)))
			{
				W9RegistrationVO voFromDB = w9RegistrationBO.getPrefill(new Integer(vendorIdStr));
				String originalUnmaskedEin = null;
				if(voFromDB != null){
					 originalUnmaskedEin = voFromDB.getOriginalUnmaskedEin();
				}
				if (ein1.replace("-", "").equals(originalEin.replace("-", "")))
				{
					//this is when user did not update the XXX- stuff to numbers
					w9save.setEin(originalUnmaskedEin);
					w9save.setEin2(originalUnmaskedEin);
				}
			}
		} catch (Exception e) {
			logger.error("Not able to get prefill information for w9. ", e);
		}

	}

	public String isW9exist()
	{
		String vendorIdStr = (String) ActionContext.getContext().getSession().get("vendorId");
		Integer vendorId = new Integer(vendorIdStr);
		try
		{
			isW9Admin();
			if (w9RegistrationBO.isW9Exists(vendorId))
			{
				request.setAttribute("w9isExist", "true");
				if (!w9RegistrationBO.isAvailableWithSSNInd(vendorId))
					//this.addActionError("Please confirm if the 9 digit number is EIN/SSN");
					this.addActionError("Please confirm if the Taxpayer ID Number type is EIN or SSN.");
			}
			else
			{
				request.setAttribute("w9isExist", "false");
			}
		}
		catch (Exception e) {
			logger.error("Not able to figure out if w9 exists. ", e);
		}

		return executeModal();
	}
	
	private void isW9Admin()
	{
		SecurityContext securitycontext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		Integer role_id = securitycontext.getRoleId();
		boolean candoW9 = ActivityMapper.canDoAction(NAME_OF_W9_ACTION + "-" + role_id, securitycontext);
		if (!candoW9)
		{
			request.setAttribute("notw9Admin", "true");
		}
		else
		{
			request.setAttribute("notw9Admin", "false");
		}


	}

	public String w9History()
	{
		String vendorIdStr = (String) ActionContext.getContext().getSession().get("vendorId");
		Integer vendorId = new Integer(vendorIdStr);

		Map<Integer, List<W9RegistrationVO>> w9History;

		try
		{
			w9History = w9RegistrationBO.getW9History(vendorId);
			request.setAttribute("w9History", w9History);

		}
		catch (Exception e) {
			logger.error("Not able to get w9 history. ", e);
		}

		request.setAttribute("w9HistoryFlag", "true");
		return SUCCESS_MODAL;
	}

	/**
	 * @return the w9RegistrationBO
	 */
	public IW9RegistrationBO getW9RegistrationBO() {
		return w9RegistrationBO;
	}

	/**
	 * @param registrationBO
	 *            the w9RegistrationBO to set
	 */
	public void setW9RegistrationBO(IW9RegistrationBO registrationBO) {
		w9RegistrationBO = registrationBO;
	}

	public SecurityContext getSecurityContext() {
		if (securityContext == null) {
			setContextDetails();
		}
		return securityContext;
	}

	public void setContextDetails() {
		session = ServletActionContext.getRequest().getSession();
		theSessionMap = ActionContext.getContext().getSession();
		securityContext = (SecurityContext) session
				.getAttribute("SecurityContext");
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * @return the theSessionMap
	 */
	public Map getTheSessionMap() {
		return theSessionMap;
	}

	/**
	 * @param theSessionMap
	 *            the theSessionMap to set
	 */
	public void setTheSessionMap(Map theSessionMap) {
		this.theSessionMap = theSessionMap;
	}

	/**
	 * @param securityContext
	 *            the securityContext to set
	 */
	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	/**
	 * @return the lookupBO
	 */
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	/**
	 * @param lookupBO
	 *            the lookupBO to set
	 */
	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public Boolean validate_fields(W9RegistrationVO w9save,String unMaskedEin) {

		
	    String vendorIdStr = (String) ActionContext.getContext().getSession().get("vendorId");
		String legalBusinessName = w9save.getLegalBusinessName(); // (String)request.getParameter("legalBusinessName");
		String taxStatus = w9save.getTaxStatus().getId().toString(); // (String)request.getParameter("taxStatus");
		String street1 = w9save.getAddress().getStreet1().toString(); // (String)request.getParameter("street1");
		String ein1 = w9save.getEin(); // (String)request.getParameter("ein1");
		String ein2 = w9save.getEin2(); // (String)request.getParameter("ein2");
		String originalEin = w9save.getOriginalEin().replace("-", "");
		String city = !StringUtils.isBlank(w9save.getAddress().getCity()) ? w9save.getAddress().getCity().toString()	: "";
		String state = !StringUtils.isBlank(w9save.getAddress().getState()) ? w9save.getAddress().getState().toString()	: ""; // (String)request.getParameter("state");
		String zip = !StringUtils.isBlank(w9save.getAddress().getZip()) ? w9save	.getAddress().getZip().toString(): ""; // (String)request.getParameter("zip");
		Boolean certCheckBool = w9save.getIsPenaltyIndicatiorCertified(); // (String)request.getParameter("certCheck");
		int taxPayerId = w9save.getTaxPayerTypeId();
//		boolean false_ind = true,stateziperror = false;
		Boolean isFalse = Boolean.TRUE, isStateZipError = Boolean.FALSE;
		String dob=w9save.getDob();
		//String unMaskedEin=	w9save.getOriginalUnmaskedEin();
		
		
		boolean bizType = true,txPay = true,tidFlag = true; //Will set to false if the other data validations fails.
		
		boolean doValidation=true;
		if(w9save.getEditOrCancel()!=null && w9save.getEditOrCancel().equals("edit") && (w9save.getOrginalTaxPayerTypeId()== w9save.getTaxPayerTypeId()||w9save.getOrginalTaxPayerTypeId()==3 ))
		{
			if (taxPayerId==2)

			{ 
				if(unMaskedEin!=null && unMaskedEin.length()>=9)
				{
				ein1 = unMaskedEin.substring(0,3)+"-"+unMaskedEin.substring(3,5)+"-"+unMaskedEin.substring(5, 9);
				ein2=ein1;
				w9save.setEin(ein1);
				w9save.setEin2(ein1);
				doValidation=false;
				}
			
			}

			if (taxPayerId==1)

			{ 
				if(unMaskedEin!=null && unMaskedEin.length()>=9)
				{
				ein1 = unMaskedEin.substring(0,2)+"-"+unMaskedEin.substring(2,9);
				ein2=ein1;
				w9save.setEin(ein1);
				w9save.setEin2(ein1);
				doValidation=false;
				}

			}
			if (taxPayerId==0)
			{
				ein1="";
				ein2="";
				doValidation=false;
			}
		
		}
		
		
		if (vendorIdStr == null || "".equals(vendorIdStr)) {
			this.addActionError("No vendor id is associated with this update.");
			isFalse = Boolean.FALSE;
		}
		if (legalBusinessName == null || "".equals(legalBusinessName)) {
			this.addActionError("Legal Business Name is a required field.");
			isFalse = Boolean.FALSE;
		}
		if ("-1".equals(taxStatus) || taxStatus == null || "".equals(taxStatus)) {
			this.addActionError("Tax Status is a required field.");
			isFalse = Boolean.FALSE;
			bizType = false;
		}
		if (street1 == null || "".equals(street1)) {
			this.addActionError("Address is a required field.");
			isFalse = Boolean.FALSE;
		}
		if (city == null || "".equals(city)) {
			this.addActionError("City is a required field.");
			isFalse = Boolean.FALSE;
		}
		if (state == null || "".equals(state) || state.equals("-1")) {
			this.addActionError("State is a required field.");
			isFalse = Boolean.FALSE;
			isStateZipError = Boolean.TRUE;
		}
		
		if (zip == null || "".equals(zip)) {
			this.addActionError("Zip is a required field.");
			isFalse = Boolean.FALSE;
			isStateZipError = Boolean.TRUE;
		}
		
		if(taxPayerId==2)
		{
			if(dob==null || "".equals(dob))
			{
				this.addActionError("Please enter a valid Date of Birth in MM/DD/YYYY format.");
				isFalse = Boolean.FALSE;
			}
			else if(getDate(dob,isFalse)!=null)
			{
				w9save.setDateOfBirth(getDate(dob,isFalse));						
				Date date=new Date();
				Date dateOfBirth=w9save.getDateOfBirth();
				if(dateOfBirth.after(date))
				{
					this.addActionError("Please enter a valid Date of Birth in MM/DD/YYYY format.");
					isFalse = Boolean.FALSE;
				}
			}
			else 
			{
				isFalse=Boolean.FALSE;	
			}
		}
				
		
		// Check for valid State and Zipcode
		if(!isStateZipError.booleanValue()){
			int zipValidation = LocationUtils.checkIfZipAndStateValid(zip,state);
			switch (zipValidation) {
				case Constants.LocationConstants.ZIP_NOT_VALID:
					this.addActionError("Zip code validation failed.");
					isFalse = Boolean.FALSE;
					break;
	
				case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
					this.addActionError("The ZipCode and State does not match.");
					isFalse = Boolean.FALSE;
					break;
			}
		}
		
		String type = null;
		if(taxPayerId==1){
			type = " (EIN)";
		}else if(taxPayerId==2){
			type = " (SSN)";
		}
		else
		{
			type = "";
		}
		if (doValidation && (ein1.replace("-", "") == null || "".equals(ein1.replace("-", "")))) {
			String errorMessage = "Please enter a valid Taxpayer ID"+type+".";
			this.addActionError(errorMessage);
			isFalse = Boolean.FALSE;
			tidFlag = false;
		}else{
			if (ein1.replace("-", "").length() != 9 && type != null && type != "")
			{
				String errorMessage = "Taxpayer ID"+type+" should be 9 digits.";
				this.addActionError(errorMessage);
				isFalse = Boolean.FALSE;
				tidFlag = false;
			}
			if (!ein1.startsWith("XXX-XX-") && !ein1.startsWith("XX-XXX") && type != null && type != "")
			{
				if (!StringUtils.isNumeric(ein1.replace("-", "")))
				{
					String errorMessage = "Taxpayer ID"+type+" can only be numeric.";
					this.addActionError(errorMessage);
					isFalse = Boolean.FALSE;
					tidFlag = false;
				}else if(ein1.replace("-", "").length()== 9){
					String tinRestrPattern = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
					if(tinRestrPattern!= null && !"".equals(tinRestrPattern) && tinRestrPattern.contains(ein1.replace("-", ""))){
						this.addActionError(ein1+" is not a valid Taxpayer ID. Please enter a valid Taxpayer ID"+type+".");
						isFalse = Boolean.FALSE;
						tidFlag = false;
					}
				}
			}
		}
		
		if (!ein1.equals(ein2)) {
			String errorMessage = "The Taxpayer IDs"+type+" entered do not match.";
			this.addActionError(errorMessage);
			isFalse = Boolean.FALSE;
			tidFlag = false;
		}
		if (ein1.replace("-", "").startsWith("XXXXX") && !ein1.replace("-", "").equalsIgnoreCase(originalEin))
		{
			this.addActionError("If you wish to change the EIN number, please enter all the digits and remove XXX-XX-.");
			isFalse = Boolean.FALSE;
			tidFlag = false;
		}
		if (!certCheckBool.booleanValue()) {
			this.addActionError("Please re-enter any changes and check the box to certify your legal tax identification.");
			isFalse = Boolean.FALSE;
		}
		if (taxPayerId==0) {
			// this.addActionError("Please choose if the Taxpayer id number entered is EIN/SSN.");
			this.addActionError("Please confirm if the Taxpayer ID Number type is EIN or SSN.");
			isFalse = Boolean.FALSE;
			txPay = false;
		}
		
		if(bizType && txPay){
			// Check type of business matches the TIN
			switch(Integer.parseInt(taxStatus)){
				case W9Constants.PARTNERSHIP:
				case W9Constants.CORPORATION:
				case W9Constants.S_CORPORATION:					
					if(taxPayerId==W9Constants.SSN){
						this.addActionError("Taxpayer id for this business type should be EIN.");
						isFalse = Boolean.FALSE;
					}
					break;
				case W9Constants.INDIVIDUAL:
					if(taxPayerId==W9Constants.EIN){
						this.addActionError("Taxpayer id for this business type should be SSN.");
						isFalse = Boolean.FALSE;
					}
					break;
			}
			
			//Validate entered TIN conforms to standards.
			if(tidFlag && doValidation){
				boolean isNum = isNumber(ein1.replace("-", ""));
				if(isNum){
					/*int ein_value = Integer.valueOf(ein1.replace("-", "")).intValue();
					switch (ein_value){
						case 111111111:
						case 123456789:
						case 000000000:
							this.addActionError("Please enter a valid Taxpayer ID"+type+".");
							isFalse = Boolean.FALSE;
					}*/
					
					String ein_value = ein1.replace("-", "");
					String tinRestrPattern = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
					if(tinRestrPattern!= null && !"".equals(tinRestrPattern)&& ein_value.length()==9 && tinRestrPattern.contains(ein_value)){
						this.addActionError(ein1+" is not a valid Taxpayer ID. Please enter a valid Taxpayer ID"+type+".");
						isFalse = Boolean.FALSE;
					}
				}else{
					this.addActionError("Please enter a valid Taxpayer ID"+type+".");
					isFalse = Boolean.FALSE;					
				}
			}
		}
		return isFalse;
	}
	
	private Date getDate(String dob,Boolean isFalse)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date=null;
		boolean error=false;
		try
		{
			date=dateFormat.parse(dob);	
			
			if (!dateFormat.format(date).equals(dob))   

		    {  
				error=true;
				isFalse=Boolean.FALSE;
				date=null;
		    }
			}
		catch(Exception exception)
		{
			error=true;
			isFalse=Boolean.FALSE;
		}
		  
		
		if(error)
		{
			this.addActionError("Please enter a valid Date of Birth in MM/DD/YYYY format.");
	
		}
		return date;
	}

    public static boolean isNumber(String n) {
  	  try {
  	     int ein = Integer.valueOf(n).intValue();
  	     return true;
  	  }catch (NumberFormatException e) {
  		  return false;
  	  }
   }
    
	public W9RegistrationVO getModel() {
		return w9prefill;
	}

	/**
	 * @return the w9Modal
	 */
	public String getW9Modal() {
		return w9Modal;
	}

	/**
	 * @param modal
	 *            the w9Modal to set
	 */
	public void setW9Modal(String modal) {
		w9Modal = modal;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(W9RegistrationVO model) {
		this.w9prefill = model;
	}

	/**
	 * @return the saveAction
	 */
	public String getSaveAction() {
		return saveAction;
	}

	/**
	 * @param saveAction
	 *            the saveAction to set
	 */
	public void setSaveAction(String saveAction) {
		this.saveAction = saveAction;
	}

	/**
	 * @return the w9prefill
	 */
	public W9RegistrationVO getW9prefill() {
		return w9prefill;
	}

	/**
	 * @param w9prefill
	 *            the w9prefill to set
	 */
	public void setW9prefill(W9RegistrationVO w9prefill) {
		this.w9prefill = w9prefill;
	}
	
	public void getCompleteSOAmount(boolean isW9ExistsFlag)
	{
		
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		Integer vendorId = new Integer((String)sessionMap.get("vendorId"));
		sessionMap.put("w9isExist", "false");
		
		if (isW9ExistsFlag) {
			sessionMap.put("w9isExist", "true");
			Double amount = w9RegistrationBO.getCompleteSOAmount(vendorId);
	
			if (amount!=null)
			{
				sessionMap.put("completeSOAmount", amount);
			}			
		}		
	}
	
	public void getProviderThreshold(boolean isW9ExistsFlag)
	{
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		sessionMap.put("w9isExist", "false");
		
		if (isW9ExistsFlag) 
		{
			sessionMap.put("w9isExist", "true");
			Double threshold = w9RegistrationBO.getProviderThreshold();		
			sessionMap.put("providerThreshold", threshold);						
		}		
	}
	
	private void isDobNotAvailableWithSSN(boolean isW9ExistsFlag) throws Exception{
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		Integer vendorId = new Integer((String)sessionMap.get("vendorId"));
		sessionMap.put("w9isExist", "false");
		sessionMap.put("w9isDobNotAvailable", "false");
		if (isW9ExistsFlag) {
			sessionMap.put("w9isExist", "true");
			if (w9RegistrationBO.isDobNotAvailableWithSSN(vendorId))
			{
				sessionMap.put("w9isDobNotAvailable", "true");
			}
		}
	}
	
}
