package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.util.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IInsuranceDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.web.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.servicelive.common.CommonConstants;
import com.thoughtworks.xstream.XStream;

public class AddInsuranceAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8794184008510627326L;
	private static final Logger logger = Logger
			.getLogger(AddInsuranceAction.class.getName());
	private IInsuranceDelegate insuranceDelegate;
	private IActivityRegistryDelegate iActivityRegistryDelegate;
	private InsuranceInfoDto insuranceInfoDto;
	private IAuditLogDelegate auditLogDelegates;
	private Map sSessionMap;
	private String method;
	private HashMap errorMap;
	
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;
	Integer auditTimeLoggingId;

	public AddInsuranceAction(InsuranceInfoDto insuranceInfoDto,
			IInsuranceDelegate insuranceDelegate,
			IActivityRegistryDelegate iActivityRegistryDelegate) {
		this.insuranceInfoDto = insuranceInfoDto;
		this.insuranceDelegate = insuranceDelegate;
		this.iActivityRegistryDelegate = iActivityRegistryDelegate;
	}
	
	public boolean validateAmount(String str){
		try {
			String VLIAmount=StringUtils.deleteAny(str, ",");
			Double d1 = Double.parseDouble(VLIAmount);
			int intStr=0;
			intStr=str.indexOf(".");
			if(intStr!=-1){
				str=str.substring(0,intStr);
			}
			else{
				str=str;
			}
			int comma=0;
			for(int index=0;index<str.length();index++){
				if((str.length() > 1) && (index > 0 )&& (index < str.length()) ){
					String prev= String.valueOf(str.charAt(index-1));
					if((prev.equals(",")) && prev.equals(String.valueOf(str.charAt(index)))){
						return false;
					}
			}
			if(!Character.isDigit(str.charAt(index))){
				comma++;
			}
			if(str.length()== comma || comma > 2){
				return false;
			}
			if( comma == 1 && index > 4 && str.length() < 4 ){
				return false;
			}
			if( comma == 1 &&  str.length() == 4 ){
				return false;
			}
		}
		
		}catch(Exception ex){
			return false;
		}
		return true;
		
	}
	
	@Override
	public void validate() {
		super.validate();
		
		
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			baseTabDto.setDtObject(insuranceInfoDto);

			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,
					"errorOn");
		}
	}

	// method to load the insurance policy lists residing into the datase to
	// show it to the user
	/**
	 * @return
	 * @throws Exception
	 */
	public String loadInsuranceList() throws Exception {
		getSessionMessages();
		logger.info("Entering into loadinsurance method !!!!!");
		String vendorId = (String) getSession().get("vendorId");
		if (doInput()) {
			insuranceInfoDto = new InsuranceInfoDto();
			insuranceInfoDto.setVendorId(new Integer(vendorId));
			try {
				insuranceInfoDto = insuranceDelegate.getInsuranceInfo(insuranceInfoDto);
				this.setInsuranceInfoForFirstTimeVisit(vendorId);	
				if(null!=insuranceInfoDto.getInsuranceList()){
					ActionContext.getContext().getSession().put(OrderConstants.INSURANCE_TYPELIST, insuranceInfoDto.getInsuranceList());
					ActionContext.getContext().getSession().put(OrderConstants.INSURANCE_INFORMATION, insuranceInfoDto);
				}
				//Fix for SL-20227: Additional insurance added is not displayed when an error message is displayed
				if(null!=insuranceInfoDto.getAdditionalInsuranceList()){
					ActionContext.getContext().getSession().put(OrderConstants.ADDITIONALINSURANCE_TYPELIST, insuranceInfoDto.getAdditionalInsuranceList());
					ActionContext.getContext().getSession().put(OrderConstants.ADDITIONALINSURANCE_TYPELISTSIZE, insuranceInfoDto.getAdditionalInsuranceListSize());
				}
			} catch (DelegateException ex) {
				ex.printStackTrace();
				logger
						.info("Exception Occured while processing the request due to"
								+ ex.getMessage());
				addActionError("Exception Occured while processing the request due to"
						+ ex.getMessage());
				return ERROR;
			}
		}
		return SUCCESS;
	}

	@SkipValidation
	public boolean doInput() throws Exception {
		int returnFlag=0;
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");
		if (baseTabDto != null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap());
			if (!getFieldErrors().isEmpty()){
				String fromPopupInd =(String)ActionContext.getContext().getSession().get(OrderConstants.FROM_POPUP_IND);				
				if(org.apache.commons.lang.StringUtils.isNotBlank(fromPopupInd)){
					if(fromPopupInd.equals(OrderConstants.FROM_POPUP_IND_TRUE)){
						returnFlag=1;
						if(null!=ActionContext.getContext().getSession().get(OrderConstants.INSURANCE_INFO)){
							insuranceInfoDto=(InsuranceInfoDto)ActionContext.getContext().getSession().get(OrderConstants.INSURANCE_INFO);						
						}
					}
				}
				else{
					insuranceInfoDto = (InsuranceInfoDto) baseTabDto.getDtObject();
					if(null!=ActionContext.getContext().getSession().get(OrderConstants.INSURANCE_TYPELIST)){
						insuranceInfoDto.setInsuranceList((ArrayList)ActionContext.getContext().getSession().get(OrderConstants.INSURANCE_TYPELIST));
					}
					//Fix for SL-20227: Additional insurance added is not displayed when an error message is displayed
					if((null!=ActionContext.getContext().getSession().get(OrderConstants.ADDITIONALINSURANCE_TYPELIST)) 
							&& (null!=ActionContext.getContext().getSession().get(OrderConstants.ADDITIONALINSURANCE_TYPELISTSIZE))){
						insuranceInfoDto.setAdditionalInsuranceList((ArrayList)ActionContext.getContext().getSession().get(OrderConstants.ADDITIONALINSURANCE_TYPELIST));
						insuranceInfoDto.setAdditionalInsuranceListSize((Integer)ActionContext.getContext().getSession().get(OrderConstants.ADDITIONALINSURANCE_TYPELISTSIZE));
					}
				}
			}		
		}
		
		if (getFieldErrors().isEmpty()){
			return true;
		}else{
			if(returnFlag == 1){
				return true;
			}else{
				return false;
			}
		}
	}
	
	private String save() throws Exception {
		validateInsurance();
		if (getFieldErrors().size() == 0) {
			//Get Vendor Id from Session 	
			String vendorId = (String) getSession().get("vendorId");			
			//Set Vendor Id in DTO
			insuranceInfoDto.setVendorId(new Integer(vendorId));

			try {
				//SAve insurance information
				boolean saveInsurance = insuranceDelegate
						.saveInsuranceInfo(insuranceInfoDto);
				auditUserProfileLog(insuranceInfoDto);			
				//SAVE Activity completed for Insurance			
				iActivityRegistryDelegate.updateActivityStatus(vendorId,
						ActivityRegistryConstants.INSURANCE);
				ActionContext.getContext().getSession().put(OrderConstants.INSURANCE_STATUS, "1");		
			} catch (DelegateException ex) {
				ex.printStackTrace();
				logger
						.info("Exception Occured while processing the request due to"
								+ ex.getMessage());
				addActionError("Exception Occured while processing the request due to"
						+ ex.getMessage());
				return ERROR;
			}
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");

			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,
					"complete");
			
			return "saveInsuranceList";
		} else {
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			baseTabDto.setDtObject(insuranceInfoDto);

			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,
					"errorOn");
			return INPUT;
		}
	}
	
	private void auditUserProfileLog(InsuranceInfoDto insuranceInfoDto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {InsuranceInfoDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(insuranceInfoDto);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_COMPANY_PROFILE_EDIT");
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		if(securityContext!=null)
		{
			auditUserProfileVO.setLoginCompanyId(securityContext.getCompanyId());
			auditUserProfileVO.setLoginResourceId(securityContext.getVendBuyerResId());
			auditUserProfileVO.setRoleId(securityContext.getRoleId());
			if(securityContext.isSlAdminInd())
				auditUserProfileVO.setIsSLAdminInd(1);
			auditUserProfileVO.setModifiedBy(securityContext.getUsername());
			auditUserProfileVO.setUserProfileData(xmlContent);
			auditLogDelegates.auditUserProfile(auditUserProfileVO);
		}
	}
	public String saveInsuranceList() throws Exception {
			
			if (save().equalsIgnoreCase(INPUT))
				return INPUT;
				//SAVE Activity completed for Insurance
			
			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			addActionMessage(getText("addInsurance.success.save"));
			baseTabDto.getActionMessages().addAll(getActionMessages());
			return "saveInsuranceList";
	
	}

	public String loadInsuranceTypeList() throws Exception {
		//SAVE Activity completed for Insurance
		String vendorId = (String) getSession().get("vendorId");
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");

		getActionMessages().clear();
		setActionMessages(baseTabDto.getActionMessages());
		baseTabDto.setActionMessages(new ArrayList<String>());
		if (save().equalsIgnoreCase(INPUT))
			return INPUT;

		return "loadInsuraceTypeList";
	}

	public String loadPrevious() throws Exception {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		getActionMessages().clear();
		setActionMessages(baseTabDto.getActionMessages());
		baseTabDto.setActionMessages(new ArrayList<String>());
		if (save().equalsIgnoreCase(INPUT))
			return INPUT;

		return "loadPrevious";
	}
	
	/**
	 * author: G.Ganapathy
	 * Method to updateProfile
	 * @return
	 * @throws Exception
	 */
	public String updateProfile() throws Exception {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		getActionMessages().clear();
		setActionMessages(baseTabDto.getActionMessages());
		baseTabDto.setActionMessages(new ArrayList<String>());
		if (save().equalsIgnoreCase(INPUT))
			return INPUT;

		return "updateProfile";
	}
	public String execute() {		
		return SUCCESS;
	}

	public InsuranceInfoDto getInsuranceInfoDto() {
		return insuranceInfoDto;
	}

	public void setInsuranceInfoDto(InsuranceInfoDto insuranceInfoDto) {
		this.insuranceInfoDto = insuranceInfoDto;
	}

	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap = ssessionMap;
	}

	public Map getSession() {
		// TODO Auto-generated method stub
		return this.sSessionMap;
	}

	/**
	 * Validates provider firms insurance details
	 */
	private void validateInsurance() {
		if (org.apache.commons.lang.StringUtils.isEmpty(insuranceInfoDto.getCBGLI())
				&& org.apache.commons.lang.StringUtils.isEmpty(insuranceInfoDto.getVLI())
				&& org.apache.commons.lang.StringUtils.isEmpty(insuranceInfoDto.getWCI())) {
			addFieldError("insuranceInfoDto.CBGLI", getTheResourceBundle()
					.getString("All_Insurance_Button_Notchecked"));
			insuranceInfoDto.setCBGLI(null);
			insuranceInfoDto.setVLI(null);
			insuranceInfoDto.setWCI(null);
			return;
		}
		this.validateGeneralLiabiltyInsurance();
		this.validateVehicleLiabilityInsurance();
		this.validateWorkersCompensationInsurance();		
	}

	/**
	 * Validates general liability insurance
	 */
	private void validateGeneralLiabiltyInsurance() {
		String CBGLIAmountPrev=insuranceInfoDto.getCBGLIAmount();
		String CBGLIAmount=StringUtils.deleteAny(insuranceInfoDto.getCBGLIAmount(), ",");
		insuranceInfoDto.setCBGLIAmount(CBGLIAmount);
		
		//CBGLI-Radio button validation
		if(org.apache.commons.lang.StringUtils.isEmpty(insuranceInfoDto.getCBGLI())){
			insuranceInfoDto.setCBGLI(null);
			addFieldError("insuranceInfoDto.CBGLI",
					getTheResourceBundle().getString("General_Liability_Insurance_Button_Notchecked"));
		}
		
		//CBGLI Amount  validation
		if("1" .equals(insuranceInfoDto.getCBGLI())){
			if ("".equals(insuranceInfoDto.getCBGLIAmount())) {
				addFieldError("insuranceInfoDto.CBGLIAmount",getTheResourceBundle().getString("General_Liability_Amount_Not_Entered"));
			} else {
				String privacyValue = insuranceInfoDto.getCBGLIAmount();
				try {			
					if (privacyValue != null && !privacyValue.trim().equals("")
							&& privacyValue.trim().length() > 0) {
						if (!greaterThanZero(privacyValue)) {
							addFieldError("insuranceInfoDto.CBGLIAmount",getTheResourceBundle().getString("General_Liability_Amount_Zero"));
						} else {
							if (!currencyValidate2(privacyValue))
								addFieldError("insuranceInfoDto.CBGLIAmount",getTheResourceBundle().getString("General_Liability_Amount_Max_Decimal_Allowed"));
							else if (!currencyValidate3(privacyValue))
								addFieldError("insuranceInfoDto.CBGLIAmount",getTheResourceBundle().getString("General_Liability_Insurance_Max_Value_Allowed"));
						}
					}
				} catch (Exception ex) {
					//CBGLI - Number format exception				
					addFieldError("insuranceInfoDto.CBGLIAmount",getTheResourceBundle().getString("General_Liability_Insurance_Not_Number"));
				}
			}
			if (getFieldErrors().size() == 0) {			
				if(insuranceInfoDto.getCBGLIAmount()!=null && !validateAmount(CBGLIAmountPrev)){
					addFieldError("insuranceInfoDto.CBGLIAmount", getTheResourceBundle().getString("General_Liability_Insurance_Invalid_Format"));
					insuranceInfoDto.setCBGLIAmount(CBGLIAmountPrev);
				}
			}
		}		
	}
	
	/**
	 * Validates vehicle liability insurance
	 */
	private void validateVehicleLiabilityInsurance() {
		String VLIAmountPrev=insuranceInfoDto.getVLIAmount();
		String VLIAmount=StringUtils.deleteAny(insuranceInfoDto.getVLIAmount(), ",");
		insuranceInfoDto.setVLIAmount(VLIAmount);
		
		//VLI-Radio button validation
		if(org.apache.commons.lang.StringUtils.isEmpty(insuranceInfoDto.getVLI())){
			insuranceInfoDto.setVLI(null);
			addFieldError("insuranceInfoDto.VLI",
					getTheResourceBundle().getString("Vehicle_Liability_Insurance_Button_Notchecked"));
		}

		//VLI Amount - isRequired
		if("1" .equals(insuranceInfoDto.getVLI())){
			if ("".equals(insuranceInfoDto.getVLIAmount())) {
				addFieldError("insuranceInfoDto.VLIAmount",getTheResourceBundle().getString("Vehicle_Liability_Amount_Not_Entered"));
			} else {
				String privacyValue = insuranceInfoDto.getVLIAmount();
				try {
					if (privacyValue != null && !privacyValue.trim().equals("")
							&& privacyValue.trim().length() > 0) {
						if (!greaterThanZero(privacyValue)) {
							addFieldError("insuranceInfoDto.VLIAmount",getTheResourceBundle().getString("Vehicle_Liability_Amount_Zero"));								
						} else {
							if (!currencyValidate2(privacyValue))
								addFieldError(
										"insuranceInfoDto.VLIAmount",getTheResourceBundle().getString("Vehicle_Liability_Amount_Max_Decimal_Allowed"));
							else if (!currencyValidate3(privacyValue))
								addFieldError(
										"insuranceInfoDto.VLIAmount",getTheResourceBundle().getString("Vehicle_Liability_Insurance_Max_Value_Allowed"));
						}
					}
				} catch (Exception ex) {
					//VLI - Number format exception
					addFieldError("insuranceInfoDto.VLIAmount",getTheResourceBundle().getString("Vehicle_Liability_Insurance_Not_Number"));
				}
			}
			
			if (getFieldErrors().size() == 0) {
				if(insuranceInfoDto.getVLIAmount()!=null && !validateAmount(VLIAmountPrev)){
					addFieldError("insuranceInfoDto.VLIAmount", getTheResourceBundle().getString("Vehicle_Liability_Insurance_Invalid_Format"));
					insuranceInfoDto.setVLIAmount(VLIAmountPrev);
				}
			}
		}		
	}
	
	/**
	 * Validates workers compensation insurance
	 */
	private void validateWorkersCompensationInsurance(){
		String WCIAmountPrev=insuranceInfoDto.getWCIAmount();
		String WCIAmount=StringUtils.deleteAny(insuranceInfoDto.getWCIAmount(), ",");
		insuranceInfoDto.setWCIAmount(WCIAmount);
		
		//WCI-Radio Button validation
		if(org.apache.commons.lang.StringUtils.isEmpty(insuranceInfoDto.getWCI())){
			insuranceInfoDto.setWCI(null);
			addFieldError("insuranceInfoDto.WCI",
					getTheResourceBundle().getString("Workers_Compensation_Insurance_Button_Notchecked"));
		}

		//WCI Amount - isRequired
		if("1" .equals(insuranceInfoDto.getWCI())){
			if ("".equals(insuranceInfoDto.getWCIAmount())) {
				addFieldError("insuranceInfoDto.WCIAmount",getTheResourceBundle().getString("Workers_Compensation_Amount_Not_Entered"));
			} else {
				String privacyValue = insuranceInfoDto.getWCIAmount();
				try {
					if (privacyValue != null && !privacyValue.trim().equals("")
							&& privacyValue.trim().length() > 0) {
						if (!greaterThanZero(privacyValue)) {
							addFieldError("insuranceInfoDto.WCIAmount",getTheResourceBundle().getString("Workers_Compensation_Amount_Zero"));
						} else {
							if (!currencyValidate2(privacyValue))
								addFieldError("insuranceInfoDto.WCIAmount",getTheResourceBundle().getString("Workers_Compensation_Amount_Max_Decimal_Allowed"));
							else if (!currencyValidate3(privacyValue))
								addFieldError("insuranceInfoDto.WCIAmount",getTheResourceBundle().getString("Workers_Compensation_Insurance_Max_Value_Allowed"));
						}
					}
				} catch (Exception ex) {
					//WCI - Number format exception
					addFieldError("insuranceInfoDto.WCIAmount",getTheResourceBundle().getString("Workers_Compensation_Insurance_Not_Number"));
				}
			}		
			if (getFieldErrors().size() == 0) {
				if(insuranceInfoDto.getWCIAmount()!=null && !validateAmount(WCIAmountPrev)){
					addFieldError("insuranceInfoDto.WCIAmount", getTheResourceBundle().getString("Workers_Compensation_Insurance_Invalid_Format"));
					insuranceInfoDto.setWCIAmount(WCIAmountPrev);
				}
			}
		}		
	}
	
	
	/**
	 * Sets default values for insurance radio buttons
	 * @param vendorId
	 * @throws NumberFormatException
	 * @throws DelegateException
	 */
	private void setInsuranceInfoForFirstTimeVisit(String vendorId) throws NumberFormatException, DelegateException{
		String isFirstTimeVisit = insuranceDelegate.isFirstTimeVisit(Integer.parseInt(vendorId));
		if(isFirstTimeVisit.equals(OrderConstants.INSURANCE_STATUS_INCOMPLETE)){
			insuranceInfoDto.setCBGLI(null);
			insuranceInfoDto.setVLI(null);
			insuranceInfoDto.setWCI(null);
		}
	}
	

	/**
	 * Two Decimal Point validation
	 * @param privacyValue
	 * @return
	 */
	private boolean currencyValidate2(String privacyValue) {

		if (privacyValue.indexOf(".") != -1) {
			if (privacyValue.trim().substring(privacyValue.indexOf("."),
					privacyValue.length()).length() > 3) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Equal to zero or greater than zero
	 * @param privacyValue
	 * @return
	 */
	private boolean eq2OrGtZero(String privacyValue) {

		if (privacyValue.indexOf(".") != -1) {
			double amount = Double.parseDouble(privacyValue);
			if (amount < 0)
				return false;
		} else {
			long amount = Long.parseLong(privacyValue);
			if (amount < 0)
				return false;
		}

		return true;
	}

	/**
	 * Exceeding the maximum value allowed, $9,999,999.99 - validation
	 * @param privacyValue
	 * @return
	 */
	private boolean currencyValidate3(String privacyValue) {

		if (privacyValue.indexOf(".") != -1) {
			if (privacyValue.substring(0, privacyValue.indexOf(".")).length() > 7) {
				return false;
			}
		} else if (privacyValue.length() > 7) {
			return false;
		}
		return true;
	}

	/**
	 * Greater than zero validation
	 * @param privacyValue
	 * @return
	 */
	private boolean greaterThanZero(String privacyValue) {

		if (privacyValue.indexOf(".") != -1) {
			double amount = Double.parseDouble(privacyValue);
			if (amount <= 0)
				return false;
		} else {
			long amount = Long.parseLong(privacyValue);
			if (amount <= 0)
				return false;
		}
		return true;
	}

	private void getSessionMessages() {
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
				.getSession().get("tabDto");

		// getting messages from session
		if (baseTabDto != null) {

			setActionMessages(baseTabDto.getActionMessages());
			baseTabDto.setActionMessages(new ArrayList<String>());
		}
	}

	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}

	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}

	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}
	
	
	//Modified method to capture the start time for adding new additional insurance 

	public String doAdd() throws Exception {
		
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		if(securityContext.isSlAdminInd())
		{
			AuditTimeVO auditTimeVo=new AuditTimeVO();
			auditTimeVo.setStartTime(new Date());
			auditTimeVo.setAgentName(securityContext.getSlAdminUName());
			auditTimeVo.setStartAction(CommonConstants.ADDITIONAL_INSURANCE_ADD_ACTION);

			AuditTimeVO auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);

			if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
			{	
				auditTimeLoggingId = auditTimeSaveVo.getAuditTimeLoggingId();
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			}
		}
		ServletActionContext.getRequest().getSession().setAttribute("button_type","ADD");
		ServletActionContext.getRequest().getSession().setAttribute("cred_id","");
		ServletActionContext.getRequest().getSession().setAttribute("policy_amount","");
		return "add";

	}
	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected String getParameter(String param) {
		return ServletActionContext.getRequest().getParameter(param);
	}
	protected boolean isButtonPressed(String buttonID) {
		return (getParameter(buttonID) != null);
	}

	protected void setAttribute(String name, Object obj) {
		ServletActionContext.getRequest().setAttribute(name, obj);
	}

	protected Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}

	public Integer getAuditTimeLoggingId() {
		return auditTimeLoggingId;
	}

	public void setAuditTimeLoggingId(Integer auditTimeLoggingId) {
		this.auditTimeLoggingId = auditTimeLoggingId;
	}


}
