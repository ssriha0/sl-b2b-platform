package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceDelegate;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.newco.marketplace.web.utils.FileUpload;
import com.newco.marketplace.web.utils.FileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.CommonConstants;

/**
 * @author  
 *
 */

public class InsurancePolicyDetailsAction extends 
SLAuditableBaseAction implements SessionAware, 
ServletRequestAware,
ServletResponseAware,
Preparable,ModelDriven<InsurancePolicyDto>{
	private static final long serialVersionUID = -701228751917043142L;
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private IInsuranceDelegate insuranceDelegate;
	private InsurancePolicyDto insurancePolicyDto;
	private Map sSessionMap;
	private FileUpload fileUpload = null;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final String FILE_FIELD_NAME = "insurancePolicyDto.file";
	private static final Logger logger = Logger.getLogger(InsurancePolicyDetailsAction.class.getName());
	private String method;
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;


	public InsurancePolicyDetailsAction(IInsuranceTypeDelegate iInsuranceDelegate) {
		this.iInsuranceDelegate = iInsuranceDelegate;		
	}

	@Override
	public void validate() {
		logger.info("Inside InsurancePolicyDetailsAction:: validate()");
		if((getMethod() != null
				&&	!(getMethod().equals("cancel")) 
				&& 	!(getMethod().equals("remove"))
				&& 	!(getMethod().equals("view")))){
			super.validate();
			//validateBeforeDate();
			validateAmount();
			if (hasFieldErrors()) {
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				ActionContext.getContext().getSession().put("fieldErrors",getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
				ActionContext.getContext().getSession().put(OrderConstants.FROM_POPUP_IND, "1");
			}
		} 
	}


	@SkipValidation
	public boolean doInput() throws Exception {
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap()); 
			if (!getFieldErrors().isEmpty())
				insurancePolicyDto = (InsurancePolicyDto)baseTabDto.getDtObject();
		}
		if (getFieldErrors().isEmpty())
			return true;
		else
			return false;
	}

	public String doSave() throws Exception {
		logger.info("Inside InsurancePolicyDetailsAction:: doSave()");
		InsurancePolicyDto insurancePolicyDto = getModel();
		boolean credIdPresent=false;

		validateAmount();
		validateDates();
		validateFields();
		String return_val = "next";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		String buttonType = (String) request.getSession().getAttribute("buttonType");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}
		try{
			if (hasFieldErrors()) {                 
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
				ActionContext.getContext().getSession().put(OrderConstants.FROM_POPUP_IND, "1");  
				getSession().setAttribute(OrderConstants.FROM_MODAL_IND, "1");
			}else{                    
				//get the below value from session
				ActionContext.getContext().getSession().put(OrderConstants.FROM_POPUP_IND, "1");
				//SL-21003 : checking whether new credential or existing credential
				if(buttonType.equals(OrderConstants.BUTTON_TYPE_EDIT)){
					credIdPresent=true;
				}
				String retStr = saveInsuranceTypeDetails();                         
				if (retStr != null && retStr.trim().length() > 0 && retStr.equalsIgnoreCase(INPUT))
					return retStr;                
			}   
		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			//	return NONE;
		}

		//Code added as part of Jira SL-20645 -To capture time while auditing insurance

		String auditTimeLogId =  insurancePolicyDto.getAuditTimeLoggingIdNew();

		if(StringUtils.isNotBlank(auditTimeLogId) && StringUtils.isNumeric(auditTimeLogId))
		{
			Integer auditTimeId=Integer.parseInt(auditTimeLogId);

			Date date = new Date();

			if(null!=auditTimeId && null!=date)
			{
				//SL-20645:updating 
				AuditTimeVO auditVo=new AuditTimeVO();
				auditVo.setAuditTimeLoggingId(auditTimeId);
				auditVo.setEndTime(date);
				if(credIdPresent){
					auditVo.setEndAction(CommonConstants.COMPANY_INSURANCE_SAVE_END_ACTION);
				}
				else{
					auditVo.setEndAction(CommonConstants.COMPANY_INSURANCE_SAVE_ACTION);
					auditVo.setCredId(insurancePolicyDto.getVendorCredentialId());
					Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(String.valueOf(insurancePolicyDto.getVendorId()),false,insurancePolicyDto.getVendorCredentialId(),AuditStatesInterface.COMPANY_AUDIT_LINK_ID);
					auditVo.setAuditTaskId(auditTaskId);
				}
				
				powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
			}
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLogId);

		}


		return_val = SUCCESS;
		if ("true".equals(isFromPA))
		{			
			return_val = "returnToPA";
		}

		return return_val;
	}


	private String saveInsuranceTypeDetails() throws Exception{
		logger.info("Inside InsurancePolicyDetailsAction:: doSave()");
		try{	
			BaseTabDto baseTabDto1 =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto1.getFieldsError().clear();
			logger.debug("Start ------Inide------saveInsuranceTypeDetails-----");

			if((String)getSessionMap().get("vendorId")!=null){
				insurancePolicyDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
			}
			insurancePolicyDto.setUserId((String)getSessionMap().get("username"));

			//Code for Document Upload
			logger.debug("\n\n\n\n ------ Inside Document Upload Part ");

			MultiPartRequestWrapper multiPartRequestWrapper  = 
					(MultiPartRequestWrapper) ServletActionContext.getRequest();
			String sameDoc = getParameter(OrderConstants.SAME_DOCUMENT_ID);
			String currentDoc = getParameter("docId");

			if(StringUtils.isEmpty(sameDoc)){
				if (multiPartRequestWrapper != null)
				{
					Collection<String> collection = multiPartRequestWrapper.getErrors();					
					fileUpload = FileUtils.updateDetailsforFile(multiPartRequestWrapper, FILE_FIELD_NAME);
					if (fileUpload != null) 
					{ 
						String fileName = fileUpload.getCredentialDocumentFileName();
						long fileSize = fileUpload.getCredentialDocumentFileSize();						
						if (fileName != null
								&&  fileName.trim().length() > 0
								&& 	fileSize > 0)
						{
							insurancePolicyDto.setCredentialDocumentBytes(fileUpload.getCredentialDocumentBytes());
							insurancePolicyDto.setCredentialDocumentExtention(fileUpload.getCredentialDocumentExtention());
							insurancePolicyDto.setCredentialDocumentFileName(fileUpload.getCredentialDocumentFileName());
							insurancePolicyDto.setCredentialDocumentFileSize(fileUpload.getCredentialDocumentFileSize());
							String contentType = insurancePolicyDto.getCredentialDocumentExtention(); 							
							if (contentType == null)
							{
								addFieldError("insurancePolicyDto.extension.error", "Preferred file types include: JPG | PDF | DOC | GIF ");
								if (hasFieldErrors()) {
									BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
									baseTabDto.setDtObject(insurancePolicyDto);
									baseTabDto.getFieldsError().putAll(getFieldErrors());
									baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
									ActionContext.getContext().getSession().put(OrderConstants.FROM_POPUP_IND, "1");									
									getSession().setAttribute(OrderConstants.FROM_MODAL_IND, "1");
								}
								return "";
							}
							else if (fileSize>2097152)
							{
								addFieldError("insurancePolicyDto.extension.error", "Please attach a file no larger than 2 MB");
								if (hasFieldErrors()) {
									BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
									baseTabDto.setDtObject(insurancePolicyDto);
									baseTabDto.getFieldsError().putAll(getFieldErrors());
									baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
									ActionContext.getContext().getSession().put(OrderConstants.FROM_POPUP_IND, "1");									
									getSession().setAttribute(OrderConstants.FROM_MODAL_IND, "1");
								}
								return "";
							}

						}else{
							if(!StringUtils.isEmpty(currentDoc)){
								sameDoc = currentDoc;
							}
						}
					}
				}
				else
					logger.debug("---------- Inside Document ELSE - ERRORS ");
			}
			insurancePolicyDto.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);			
			String category = getParameter(OrderConstants.CATEGORY_NAME);
			String credId = getParameter(OrderConstants.CREDENTIAL_ID );

			if (StringUtils.isNotBlank(credId)) {
				insurancePolicyDto.setVendorCredentialId(Integer.parseInt(credId));
			}
			if(category.equals(OrderConstants.GENERAL_LIABILTY)){
				insurancePolicyDto.setCredentialCategoryId(OrderConstants.GL_CREDENTIAL_CATEGORY_ID);
			}else if(category.equals(OrderConstants.AUTO_LIABILTY)){
				insurancePolicyDto.setCredentialCategoryId(OrderConstants.AL_CREDENTIAL_CATEGORY_ID);
			}else if(category.equals(OrderConstants.WORKMANS_COMPENSATION)){
				insurancePolicyDto.setCredentialCategoryId(OrderConstants.WC_CREDENTIAL_CATEGORY_ID);
			}	
			if(!StringUtils.isEmpty(sameDoc)){
				insurancePolicyDto.setLastUploadedDocumentId(Integer.parseInt(sameDoc));
			}
			insurancePolicyDto=iInsuranceDelegate.saveInsurancePolicyDetails(insurancePolicyDto); 

			if (!hasFieldErrors()) {
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				addActionMessage("Insurance Details entered have been successfully saved.");
				baseTabDto.getActionMessages().addAll(getActionMessages());
				ActionContext.getContext().getSession().put(OrderConstants.FROM_POPUP_IND, "1");
				ActionContext.getContext().getSession().put(OrderConstants.INSURANCE_STATUS,"1");
			}

		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			throw ex;
		}
		return SUCCESS;
	}

	private void validateFields(){
		logger.info("Inside validate fields");
		if(StringUtils.isBlank(insurancePolicyDto.getCarrierName())){             
			addFieldError("insurancePolicyDto.carrierName","Please provide the name of the insurance carrier.");
		}           
		if(StringUtils.isBlank(insurancePolicyDto.getAgencyName())){                  
			addFieldError("insurancePolicyDto.agencyName","Please provide the name of the insurance Agency");
		}     
		if(StringUtils.isBlank(insurancePolicyDto.getAgencyState())){           
			addFieldError("insurancePolicyDto.agencyState","Please select the state where your agency is located");
		}
		if(StringUtils.isBlank(insurancePolicyDto.getPolicyNumber())){                
			addFieldError("insurancePolicyDto.policyNumber","Please provide the policy number.");
		}     
	}


	private void validateDates(){      
		logger.info("Validating the Date differences");
		String policyIssueStr = insurancePolicyDto.getPolicyIssueDate();
		String policyExpireStr=  insurancePolicyDto.getPolicyExpirationDate();
		boolean isValidIssueDt=true;
		boolean isValidExpireDt=true;
		if (StringUtils.isBlank(policyIssueStr)){       
			addFieldError("insurancePolicyDto.policyIssueDate","Please select the issue date  for your current policy.");
			isValidIssueDt=false;
		}
		if (StringUtils.isBlank(policyExpireStr)){       
			addFieldError("insurancePolicyDto.policyExpirationDate","Please select the expiration date  for your current policy.");
			isValidExpireDt=false;
		}
		if((!isValidIssueDt) || (!isValidExpireDt) ){
			return;
		}
		Date policyIssueDt= null;
		Date policyExpireDt= null;
		String contFlag=null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
			sdf.setLenient(false);
			contFlag="ISSUE";                
			policyIssueDt = sdf.parse(policyIssueStr.trim());
			contFlag="EXPIRE";            
			policyExpireDt = sdf.parse(policyExpireStr.trim());
		}
		catch(Exception e){
			if((contFlag !=null) && (contFlag.equalsIgnoreCase("ISSUE"))){                
				addFieldError("insurancePolicyDto.policyIssueDate","Please enter a valid Policy Issue Date.");
			}else if((contFlag !=null) && (contFlag.equalsIgnoreCase("EXPIRE")))
			{                 
				addFieldError("insurancePolicyDto.policyExpirationDate","Please enter a valid Policy Expiration date.");
			}
			logger.debug("Exception comdate"+e);      
			return;
		}
		if(policyIssueDt.compareTo(policyExpireDt) > 0){      
			addFieldError("insurancePolicyDto.policyExpirationDate","Policy Expiration Date should be greater than Policy Issue date.");
		}
	}

	public void validateAmount() {
		logger.info("Inside InsurancePolicyDetailsAction:: validateAmount()");
		String dAmount = insurancePolicyDto.getAmount();        
		if((StringUtils.isNotBlank(dAmount))){
			try{
				if(Double.parseDouble(dAmount) < 0 ) {
					addFieldError("insurancePolicyDto.amount", "Amount cannot be a negative number");
				}
				if(Double.parseDouble(dAmount) == 0){
					addFieldError("insurancePolicyDto.amount","Please provide your current coverage amount for the insurance.");	
				}
			}
			catch (NumberFormatException nfe) {
				addFieldError("insurancePolicyDto.amount", "Amount should be number.");
			}
			if(dAmount.indexOf(".")!=-1){
				if(dAmount.trim().substring(dAmount.indexOf("."),dAmount.length()).length()>3){
					addFieldError("insurancePolicyDto.amount", "Amount should contain only two digits after decimal. ");
				}
			}
		} else{
			addFieldError("insurancePolicyDto.amount","Please provide your current coverage amount for the insurance.");
		}
	}


	public InsurancePolicyDto getInsurancePolicyDto() {
		return insurancePolicyDto;
	}

	public void setInsurancePolicyDto(InsurancePolicyDto insurancePolicyDto) {
		this.insurancePolicyDto = insurancePolicyDto;
	}

	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;		
	}	
	public Map getSessionMap() {
		return this.sSessionMap;		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;
	}
	public void setServletResponse(HttpServletResponse arg0)
	{
		response=arg0;
	}

	public InsurancePolicyDto getModel() {
		return insurancePolicyDto;
	}

	public  void setModel(InsurancePolicyDto insurancePolicyDto)
	{
		this.insurancePolicyDto = insurancePolicyDto;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void prepare() throws Exception {

	}

	/**
	 * Method to save additional insurance types.
	 * Done as part of SL-10809: Additional Insurance
	 * @return
	 * @throws Exception
	 */
	public String doSaveAdditionalInsurance() throws Exception {
		logger.info("Inside InsurancePolicyDetailsAction:: doSave()");
		InsurancePolicyDto insurancePolicyDto = getModel();

		boolean credIdPresent=false;

		/*Changes related to SL-20301: Details based on LastUploadedDocument*/
		String issueDate = getParameter("issueDate1");
		String expirationDate = getParameter("expirationDate1");
		if(null != FormatDateStringToMMDDYYYYString(issueDate))
		{
			insurancePolicyDto.setPolicyIssueDate(FormatDateStringToMMDDYYYYString(issueDate));
		}
		if(null != FormatDateStringToMMDDYYYYString(expirationDate))
		{
			insurancePolicyDto.setPolicyExpirationDate(FormatDateStringToMMDDYYYYString(expirationDate));
		}
		validateAmount();
		validateAdditionalInsuranceDates();
		validateAdditionalInsuranceFields();
		String return_val = "next";
		ActionContext.getContext().getSession().put("saveReturnString",SUCCESS);
		String isFromPA = (String) request.getSession()
				.getAttribute("isFromPA");
		if ("true".equals(isFromPA)) {
			return_val = "returnToPA";
		}
		try {
			if (hasFieldErrors()) {
				BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(
						ActivityRegistryConstants.INSURANCE, "errorOn");
				return "redirectError";
			} else {

				if(insurancePolicyDto.getVendorCredentialId()!=0){
					credIdPresent=true;
				}

				String returnString=saveAdditionalInsuranceTypeDetails();


				if(returnString.equals("err"))
				{
					return "redirectError";
				}
				BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				addActionMessage("Insurance Details entered have been successfully saved.");
				baseTabDto.getActionMessages().addAll(getActionMessages());
			}
		} catch (DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			// return NONE;
		}

		//Code added as part of Jira SL-20645 -To capture time while auditing insurance
		String auditTimeLogId =  insurancePolicyDto.getAuditTimeLoggingIdNew();		

		if(StringUtils.isNotBlank(auditTimeLogId) && StringUtils.isNumeric(auditTimeLogId))
		{
			Integer auditTimeId=Integer.parseInt(auditTimeLogId);

			Date date = new Date();

			if(null!=auditTimeId && null!=date)
			{
				//SL-20645:updating 
				AuditTimeVO auditVo=new AuditTimeVO();
				auditVo.setAuditTimeLoggingId(auditTimeId);
				auditVo.setEndTime(date);
				if(credIdPresent){
					auditVo.setEndAction(CommonConstants.COMPANY_UPDATE_ADDITIONAL_INSURANCE_END_ACTION);
				}
				else{
					auditVo.setEndAction(CommonConstants.ADDITIONAL_INSURANCE_SAVE_ACTION);
					auditVo.setCredId(insurancePolicyDto.getVendorCredentialId());
					Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(String.valueOf(insurancePolicyDto.getVendorId()),false,insurancePolicyDto.getVendorCredentialId(),AuditStatesInterface.COMPANY_AUDIT_LINK_ID);
					auditVo.setAuditTaskId(auditTaskId);
				}

				powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
			}
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLogId);

		}

		return_val = SUCCESS;
		if ("true".equals(isFromPA)) {
			return_val = "returnToPA";
		}

		return return_val;
	}


	private String saveAdditionalInsuranceTypeDetails() throws Exception {
		logger.info("Inside InsurancePolicyDetailsAction:: doSave()");
		try {
			BaseTabDto baseTabDto1 = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			baseTabDto1.getFieldsError().clear();
			logger.debug("Start ------Inide------saveInsuranceTypeDetails-----");

			if ((String) getSessionMap().get("vendorId") != null) {
				insurancePolicyDto.setVendorId(new Integer(
						(String) getSessionMap().get("vendorId")).intValue());
			}
			insurancePolicyDto.setUserId((String) getSessionMap().get(
					"username"));

			// Code for Document Upload
			logger.debug("\n\n\n\n ------ Inside Document Upload Part ");

			MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) ServletActionContext
					.getRequest();
			/*Changes related to SL-20301: Details based on LastUploadedDocument*/
			String newDoc = getParameter("newDocId");			
			String sameDoc = getParameter(OrderConstants.SAME_DOCUMENT_ID);
			String currentDoc = getParameter("docId");

			if (StringUtils.isEmpty(sameDoc)) {
				if (multiPartRequestWrapper != null) {
					Collection<String> collection = multiPartRequestWrapper
							.getErrors();
					fileUpload = FileUtils.updateDetailsforFile(
							multiPartRequestWrapper, FILE_FIELD_NAME);
					if (fileUpload != null) {
						String fileName = fileUpload
								.getCredentialDocumentFileName();
						long fileSize = fileUpload
								.getCredentialDocumentFileSize();
						if (fileName != null && fileName.trim().length() > 0
								&& fileSize > 0) {
							insurancePolicyDto
							.setCredentialDocumentBytes(fileUpload
									.getCredentialDocumentBytes());
							insurancePolicyDto
							.setCredentialDocumentExtention(fileUpload
									.getCredentialDocumentExtention());
							insurancePolicyDto
							.setCredentialDocumentFileName(fileUpload
									.getCredentialDocumentFileName());
							insurancePolicyDto
							.setCredentialDocumentFileSize(fileUpload
									.getCredentialDocumentFileSize());
							String contentType = insurancePolicyDto
									.getCredentialDocumentExtention();
							if (contentType == null) {
								addFieldError(
										"insurancePolicyDto.extension.error",
										"Preferred file types include: JPG | PDF | DOC | GIF ");
								if (hasFieldErrors()) {
									BaseTabDto baseTabDto = (BaseTabDto) ActionContext
											.getContext().getSession()
											.get("tabDto");
									baseTabDto.setDtObject(insurancePolicyDto);
									baseTabDto.getFieldsError().putAll(
											getFieldErrors());
									baseTabDto
									.getTabStatus()
									.put(ActivityRegistryConstants.INSURANCE,
											"errorOn");
									ActionContext
									.getContext()
									.getSession()
									.put(OrderConstants.FROM_POPUP_IND,
											"1");
									getSession().setAttribute(
											OrderConstants.FROM_MODAL_IND, "1");
								}
								return "err";
							} else if (fileSize > 2097152) {
								addFieldError(
										"insurancePolicyDto.extension.error",
										"Please attach a file no larger than 2 MB");
								if (hasFieldErrors()) {
									BaseTabDto baseTabDto = (BaseTabDto) ActionContext
											.getContext().getSession()
											.get("tabDto");
									baseTabDto.setDtObject(insurancePolicyDto);
									baseTabDto.getFieldsError().putAll(
											getFieldErrors());
									baseTabDto
									.getTabStatus()
									.put(ActivityRegistryConstants.INSURANCE,
											"errorOn");
									ActionContext
									.getContext()
									.getSession()
									.put(OrderConstants.FROM_POPUP_IND,
											"1");
									getSession().setAttribute(
											OrderConstants.FROM_MODAL_IND, "1");
								}
								return "err";
							}

						} else {
							if (!StringUtils.isEmpty(currentDoc)) {
								sameDoc = currentDoc;
							}
						}
					}
				} else
					logger.debug("---------- Inside Document ELSE - ERRORS ");
			}
			insurancePolicyDto
			.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
			String category = getParameter(OrderConstants.CATEGORY_NAME);
			String credId = getParameter(OrderConstants.CREDENTIAL_ID);

			if (StringUtils.isNotBlank(credId)) {
				insurancePolicyDto.setVendorCredentialId(Integer
						.parseInt(credId));
			}
			if(StringUtils.isEmpty(sameDoc)){
				sameDoc=newDoc;
			}

			if(getAttribute("attach_flag")!= null)
			{
				String attach_flag = (String) getAttribute("attach_flag");
				if (!StringUtils.isEmpty(sameDoc) && !(attach_flag.equals("1"))) {
					insurancePolicyDto.setLastUploadedDocumentId(Integer
							.parseInt(sameDoc));
				}
			} 
			else
			{
				if (!StringUtils.isEmpty(sameDoc)) {
					insurancePolicyDto.setLastUploadedDocumentId(Integer
							.parseInt(sameDoc));
				}
			}
			insurancePolicyDto = iInsuranceDelegate
					.saveInsurancePolicyDetails(insurancePolicyDto);

			if (!hasFieldErrors()) {
				BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				baseTabDto.getActionMessages().addAll(getActionMessages());
				ActionContext.getContext().getSession()
				.put(OrderConstants.INSURANCE_STATUS, "1");
			}

		} catch (DelegateException ex) {
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			throw ex;
		}

		return SUCCESS;
	}


	private void validateAdditionalInsuranceDates() {
		logger.info("Validating the Date differences");
		String policyIssueStr = insurancePolicyDto.getPolicyIssueDate();
		String policyExpireStr = insurancePolicyDto.getPolicyExpirationDate();
		boolean isValidIssueDt = true;
		boolean isValidExpireDt = true;
		if (StringUtils.isBlank(policyIssueStr)) {
			addFieldError("insurancePolicyDto.policyIssueDate",
					"Please select the issue date  for your current policy.");
			isValidIssueDt = false;
		}
		if (StringUtils.isBlank(policyExpireStr)) {
			addFieldError("insurancePolicyDto.policyExpirationDate",
					"Please select the expiration date  for your current policy.");
			isValidExpireDt = false;
		}
		if ((!isValidIssueDt) || (!isValidExpireDt)) {
			return;
		}

		if (insurancePolicyDto.getPolicyIssueDate().compareTo(
				insurancePolicyDto.getPolicyExpirationDate()) > 0) {
			addFieldError("insurancePolicyDto.policyExpirationDate",
					"Policy Expiration Date should be greater than Policy Issue date.");
		}
	}

	private void validateAdditionalInsuranceFields() {
		logger.info("Inside validate fields");
		if (StringUtils.isBlank(insurancePolicyDto.getCarrierName())) {
			addFieldError("insurancePolicyDto.carrierName",
					"Please provide the name of the insurance carrier.");
		}
		if (StringUtils.isBlank(insurancePolicyDto.getAgencyName())) {
			addFieldError("insurancePolicyDto.agencyName",
					"Please provide the name of the insurance Agency");
		}
		if (StringUtils.isBlank(insurancePolicyDto.getAgencyState())) {
			addFieldError("insurancePolicyDto.agencyState",
					"Please select the state where your agency is located");
		}
		if (StringUtils.isBlank(insurancePolicyDto.getPolicyNumber())) {
			addFieldError("insurancePolicyDto.policyNumber",
					"Please provide the policy number.");
		}
		// SL-10809 Additional Insurance
		if (insurancePolicyDto.getCredentialCategoryId() == 150) {
			if (StringUtils.isBlank(insurancePolicyDto
					.getCredentialCategoryDesc())) {
				addFieldError("insurancePolicyDto.credentialCategoryDesc",
						"Please provide the description.");
			}
		}

	}

	/**
	 * Method to remove additional insurance.
	 * SL-10809 Additional insurance
	 * @throws Exception
	 */
	public String doDelete() throws Exception {
		String return_val = SUCCESS;

		InsurancePolicyDto insurancePolicyDto = getModel();
		try {
			BaseTabDto baseTabDto1 = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			baseTabDto1.getFieldsError().clear();
			// get the below value from session
			if ((String) getSessionMap().get("vendorId") != null) {
				insurancePolicyDto.setVendorId(new Integer(
						(String) getSessionMap().get("vendorId")).intValue());
			}

			insurancePolicyDto.setUserId((String) getSessionMap().get(
					"username"));

			insurancePolicyDto = iInsuranceDelegate
					.deleteInsuranceDetails(insurancePolicyDto);

			if (!hasFieldErrors()) {
				BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				addActionMessage("Insurance Details entered have been successfully removed.");
				baseTabDto.getActionMessages().addAll(getActionMessages());
				ActionContext.getContext().getSession()
				.put(OrderConstants.INSURANCE_STATUS, "1");
			}

		} catch (DelegateException ex) {
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
		return return_val;
	}

	/**
	 * Method to remove worker's compensation.
	 * Done as part of SL-10809: Additional Insurance
	 * @return
	 * @throws Exception
	 */
	public String doDeleteWC() throws Exception {
		String return_val = SUCCESS;
		InsurancePolicyDto insurancePolicyDto = new InsurancePolicyDto();
		InsuranceInfoDto insuranceInfoDto = new InsuranceInfoDto();
		String credId = getParameter(OrderConstants.CREDENTIAL_ID);
		int credentialId = 0;
		if (StringUtils.isNotBlank(credId)) {
			credentialId = Integer.parseInt(credId);
		}

		try {
			BaseTabDto baseTabDto1 = (BaseTabDto) ActionContext.getContext()
					.getSession().get("tabDto");
			baseTabDto1.getFieldsError().clear();
			// get the below value from session
			if ((String) getSessionMap().get("vendorId") != null) {
				insurancePolicyDto.setVendorId(new Integer(
						(String) getSessionMap().get("vendorId")).intValue());
				insuranceInfoDto.setVendorId(insurancePolicyDto.getVendorId());
			}

			insurancePolicyDto.setUserId((String) getSessionMap().get(
					"username"));
			insurancePolicyDto.setVendorCredentialId(credentialId);
			insurancePolicyDto
			.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
			insurancePolicyDto = iInsuranceDelegate
					.deleteInsuranceDetails(insurancePolicyDto);

			// reusing existing code in IInsuranceDelegate to set indicator in
			// vendor header
			insuranceInfoDto.setWCI("false");
			insuranceInfoDto.setWCIAmount("");

			boolean saveInsurance = insuranceDelegate
					.saveInsuranceInfo(insuranceInfoDto);

			if (!hasFieldErrors()) {
				BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
						.getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				addActionMessage("Insurance Details entered have been successfully removed.");
				baseTabDto.getActionMessages().addAll(getActionMessages());
				ActionContext.getContext().getSession()
				.put(OrderConstants.INSURANCE_STATUS, "1");
			}

		} catch (DelegateException ex) {
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"
					+ ex.getMessage());
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
		return return_val;
	}

	public String attachDocument() throws Exception
	{	
		String attachFlag = "0";
		if(getParameter("attachFlag") != null)
		{
			attachFlag = getParameter("attachFlag").toString();
		}
		setAttribute("attach_flag", attachFlag);

		String return_val = "attachDoc";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		MultiPartRequestWrapper multiPartRequestWrapper  = 
				(MultiPartRequestWrapper) ServletActionContext.getRequest();

		if (multiPartRequestWrapper != null)
		{
			fileUpload = FileUtils.updateDetails(multiPartRequestWrapper, FILE_FIELD_NAME);
			if (fileUpload != null)
			{
				String fileName = fileUpload.getCredentialDocumentFileName();
				if (fileName == null
						||	fileName.trim().length() <= 0)
				{
					addFieldError("insurancePolicyDto.file", "File not found ");
				}
			}
			else
			{
				addFieldError("insurancePolicyDto.file", "File not found ");
			}
		}
		else
		{
			addFieldError("insurancePolicyDto.file", "File not found ");
		}

		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(insurancePolicyDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
			return "redirectError";
		}

		int vandorResId = 0;
		String return_string=saveAdditionalInsuranceTypeDetails();
		if (insurancePolicyDto != null)
		{ 
			vandorResId = insurancePolicyDto.getVendorCredentialId();
			Integer cred_id=vandorResId;
			if (vandorResId != 0)
			{
				ServletActionContext.getRequest().getSession().setAttribute("button_type","EDIT");
				ServletActionContext.getRequest().getSession().setAttribute("cred_id",cred_id.toString());
				ServletActionContext.getRequest().getSession().setAttribute("policy_amount",insurancePolicyDto.getAmount());
				ActionContext.getContext().getSession().put("VendorCredId",vandorResId + "");
			}
		}

		return return_val;
	}

	public IInsuranceDelegate getInsuranceDelegate() {
		return insuranceDelegate;
	}

	public void setInsuranceDelegate(IInsuranceDelegate insuranceDelegate) {
		this.insuranceDelegate = insuranceDelegate;
	}
	/*Changes related to SL-20301: Details based on LastUploadedDocument*/
	public static String FormatDateStringToMMDDYYYYString(String source)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = "" ;
		Date dt = defaultFormatStringToDate(source);
		formattedDate = sdf.format(dt);

		return formattedDate;
	}

	public static Date defaultFormatStringToDate(String source)
	{
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		try {
			return defaultDateFormat.parse(source);
		} catch (ParseException e) {
			//logger.info("Caught Exception and ignoring",e);
		}
		return null;
	}

	private Date covertTimeStringToDate(String formatted) throws Exception {
		if (null!=formatted) {
			SimpleDateFormat timeStringformater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return timeStringformater.parse(formatted);
			}catch (Exception e) {
				logger.error("Exception in covertTimeStringToDate failed to process for input :"+e.getMessage());
				throw e;
			}
		}
		return null;
	}

	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}

}
