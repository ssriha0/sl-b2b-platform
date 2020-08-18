package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.newco.marketplace.web.security.SecuredAction;
import com.newco.marketplace.web.utils.FileUpload;
import com.newco.marketplace.web.utils.FileUtils;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.CommonConstants;

/**
 * @author sahmad7
 * $Revision: 1.22 $ $Author: glacy $ $Date: 2008/04/26 01:13:51 $
 */

public class SaveInsuranceTypeAction extends 
SLAuditableBaseAction implements SessionAware, 
ServletRequestAware,
ServletResponseAware,
Preparable{
	private static final long serialVersionUID = -701228751917043142L;
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private InsurancePolicyDto insurancePolicyDto;
	private Map sSessionMap;
	private FileUpload fileUpload = null;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private static final String FILE_FIELD_NAME = "insurancePolicyDto.file";
	private static final Logger logger = Logger.getLogger(SaveInsuranceTypeAction.class.getName());
	private String method;
	public SaveInsuranceTypeAction(IInsuranceTypeDelegate iInsuranceDelegate,InsurancePolicyDto insurancePolicyDto) {
		this.iInsuranceDelegate = iInsuranceDelegate;
		this.insurancePolicyDto = insurancePolicyDto;
	}
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;

	String auditTimeLoggingId;


	public String getAuditTimeLoggingId() {
		return auditTimeLoggingId;
	}

	public void setAuditTimeLoggingId(String auditTimeLoggingId) {
		this.auditTimeLoggingId = auditTimeLoggingId;
	}
	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}

	@Override
	public void validate() {
		if(StringUtils.isNotBlank(insurancePolicyDto.getAuditTimeLoggingIdNew()) && StringUtils.isNumeric(insurancePolicyDto.getAuditTimeLoggingIdNew())){
			auditTimeLoggingId = insurancePolicyDto.getAuditTimeLoggingIdNew();
		}
		if((getMethod() != null
				&&	!(getMethod().equals("cancel")) 
				&& 	!(getMethod().equals("remove"))
				&& 	!(getMethod().equals("view")))){
			super.validate();
			validateBeforeDate();
			validateAmount();
			if (hasFieldErrors()) {
				if(getMethod().equals("")){
					ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID,auditTimeLoggingId);
				}
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
			}
		}
	}
	//Sl-20645 : modified the method to capture the end time of auditing the the vendor licenses
	public String doSave() throws Exception {	

		String return_val = "next";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		logger.debug("-----------------doSave------------------loadInsuranceAction");
		try{
			//get the below value from session
			String retStr = saveInsuranceTypeDetails();
			if (retStr != null && retStr.trim().length() > 0 && retStr.equalsIgnoreCase(INPUT))
				return retStr;

			if(method!=null&&method.equals("next")){
				return return_val;
			}

		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}

		return_val = SUCCESS;
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}
		//Sl-20645 : capturing the end time when user clicks the Update Policy btton in Auditor WorkFLow page
			
			auditTimeLoggingId = insurancePolicyDto.getAuditTimeLoggingIdNew();

			if(StringUtils.isNotBlank(auditTimeLoggingId) && StringUtils.isNumeric(auditTimeLoggingId))
			{
				Integer auditTimeId=Integer.parseInt(auditTimeLoggingId);
				if(null!=auditTimeId)
				{
					//SL-20645:updating 
					AuditTimeVO auditVo=new AuditTimeVO();
					auditVo.setAuditTimeLoggingId(auditTimeId);
					auditVo.setEndTime(new Date());

					auditVo.setEndAction(CommonConstants.COMPANY_INSURANCE_END_ACTION);
					powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
				}
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID,auditTimeLoggingId);
			}
		
		return return_val;
	}


	public String doNext() throws Exception {
		logger.debug("-----------------doNext------------------doNext");
		try{
			String retStr = saveInsuranceTypeDetails();
			if (retStr != null && retStr.trim().length() > 0 && retStr.equalsIgnoreCase(INPUT))
				return retStr;

			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.getFieldsError().clear();
		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}
		return "next";
	}

	public String loadTermsAfterSave() throws Exception {
		logger.debug("-----------------loadTermsAfterSave------------------loadTermsAfterSave");
		try{
			String retStr = saveInsuranceTypeDetails();
			if (retStr != null && retStr.trim().length() > 0 && retStr.equalsIgnoreCase(INPUT))
				return retStr;

			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.getFieldsError().clear();
		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}
		return "next";
	}

	@SkipValidation
	public String doCancel() throws Exception {
		logger.debug("-----------------doCancel------------------doCalcel");
		return "cancel";
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

	private String saveInsuranceTypeDetails() throws Exception{
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

			if (multiPartRequestWrapper != null)
			{
				logger.debug("----------- Inside Document IF - No ERRORS ");
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

						String contentType = insurancePolicyDto.getCredentialDocumentExtention(); 

						if (contentType == null)
						{
							addFieldError("insurancePolicyDto.extension.error", "Preferred file types include: JPG | PDF | DOC | GIF ");
							if (hasFieldErrors()) {
								BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
								baseTabDto.setDtObject(insurancePolicyDto);
								baseTabDto.getFieldsError().putAll(getFieldErrors());
								baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
							}
							return INPUT;
						}
						insurancePolicyDto.setCredentialDocumentFileSize(fileUpload.getCredentialDocumentFileSize());
					}
				}
			}
			else
				logger.debug("---------- Inside Document ELSE - ERRORS ");

			logger.debug(" ------ End of Document Upload Part ");

			insurancePolicyDto=iInsuranceDelegate.saveInsuranceDetails(insurancePolicyDto);

			if (!hasFieldErrors()) {
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(insurancePolicyDto);
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"complete");
			}

			logger.debug("End------Inside------saveInsuranceTypeDetails-----");
		}catch(DelegateException ex){
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			throw ex;
		}
		return SUCCESS;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void prepare() throws Exception {

	}

	@SkipValidation
	public String removeDocument() throws Exception
	{
		String return_val = "insurancePage";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		int credId = 0;
		try
		{
			String venId = (String)getSessionMap().get("vendorId");
			Object obj = getSessionMap().get("VendorCredId");

			if (obj != null)
			{ 
				credId = new Integer((String)obj).intValue();

				if (venId != null)
					insurancePolicyDto.setVendorId(Integer.parseInt(venId));

				insurancePolicyDto.setVendorCredentialId(credId);

				iInsuranceDelegate.removeDocumentDetails(insurancePolicyDto);
			}
		}catch(Exception a_Ex)
		{
			logger.error(a_Ex.getMessage(),a_Ex);
			return ERROR;
		}
		return return_val;
	}

	public void validateBeforeDate() {

		logger.debug("Validating the Date differences");
		String policyIssueStr = insurancePolicyDto.getPolicyIssueDate();
		String policyExpireStr=  insurancePolicyDto.getPolicyExpirationDate();
		boolean isValidIssueDt=true;
		boolean isValidExpireDt=true;
		if ((policyIssueStr == null)|| (policyIssueStr.trim()=="") || !(policyIssueStr.trim().length()>0)){
			addFieldError("insurancePolicyDto.policyIssueDate", "Policy Issue Date is required");
			isValidIssueDt=false;
		}
		if ((policyExpireStr == null)|| (policyExpireStr.trim()=="") || !(policyExpireStr.trim().length()>0)){
			addFieldError("insurancePolicyDto.policyExpirationDate", "Policy Expiration Date is required.");
			isValidExpireDt=false;
		}
		if((!isValidIssueDt) || (!isValidExpireDt) ){
			return;
		}
		Date policyIssueDt= null;
		Date policyExpireDt= null;
		String contFlag=null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);    
			contFlag="ISSUE";
			policyIssueDt  = sdf.parse(policyIssueStr.trim());  
			contFlag="EXPIRE";
			policyExpireDt = sdf.parse(policyExpireStr.trim());  
		}
		catch(Exception e){
			if((contFlag !=null) || (contFlag.equalsIgnoreCase("ISSUE"))){
				addFieldError("insurancePolicyDto.policyIssueDate", "Please Enter the valid Policy Issue Date");

			}else if((contFlag !=null) || (contFlag.equalsIgnoreCase("EXPIRE")))
			{
				addFieldError("insurancePolicyDto.policyExpirationDate", "Please Enter the valid Policy Expiration date.");
			}
			logger.debug("Exception comdate"+e);	
			return;
		}

		if(policyIssueDt.compareTo(policyExpireDt) > 0)
			addFieldError("insurancePolicyDto.policyExpirationDate", "Policy Expiration Date should be greater than Policy Issue date.");

	}



	public void validateAmount() {
		String dAmount = getInsurancePolicyDto().getAmount();
		if((dAmount !=null) || (dAmount !="")){
			try{
				if(Double.parseDouble(dAmount) < 0 ) {
					addFieldError("insurancePolicyDto.amount", "Amount cannot be a negative number");
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
		} 

	}

	/**
	 * Method to display the selected document
	 * @return String
	 * @throws Exception
	 */
	@SkipValidation
	public String displayTheDocument() throws Exception {		
		String documentId = request.getParameter("docId");		
		if (StringUtils.isNotBlank(documentId)){
			insurancePolicyDto.setCredentialDocumentId(Integer.parseInt(documentId));
			insurancePolicyDto = iInsuranceDelegate.viewDocumentDetails(insurancePolicyDto);		
			String credentialDocumentFileName = insurancePolicyDto.getCredentialDocumentFileName();
			credentialDocumentFileName = StringUtils.replaceChars(credentialDocumentFileName, ' ', '_');
			SecurityChecker sc = new SecurityChecker();
			credentialDocumentFileName = sc.fileNameCheck(credentialDocumentFileName);
			byte[] imgData = (byte[]) insurancePolicyDto.getCredentialDocumentBytes();
			String ext = sc.securityCheck(insurancePolicyDto.getCredentialDocumentExtention());
			response.setContentType(ext);
			response.setContentLength(imgData.length);
			response.setHeader("Content-Disposition", "attachment; filename=\""+credentialDocumentFileName+"\"");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			getResponse().setHeader("Pragma", "public");
			OutputStream o = response.getOutputStream();
			o.write(imgData);
			o.flush();
			return "docDownload";		
		}		
		return null;
	}

	//	@SkipValidation
	@SecuredAction(securityTokenEnabled = true)
	public String attachDocument() throws Exception
	{	
		String return_val = "input";
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
					addFieldError("teamCredentialsDto.file", "File not found ");
				}
			}
			else
			{
				addFieldError("teamCredentialsDto.file", "File not found ");
			}
		}
		else
		{
			addFieldError("teamCredentialsDto.file", "File not found ");
		}

		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(insurancePolicyDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
			return return_val;
		}

		int vandorResId = 0;
		saveInsuranceTypeDetails();

		if (insurancePolicyDto != null)
		{
			vandorResId = insurancePolicyDto.getVendorCredentialId();
			if (vandorResId != 0)
			{
				ActionContext.getContext().getSession().put("VendorCredId",vandorResId + "");
			}
		}

		return return_val;
	}

	public void setServletResponse(HttpServletResponse arg0)
	{
		response=arg0;
	}

	public String attachAdditionalInsDocument() throws Exception
	{	
		String return_val = "input";
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
					addFieldError("teamCredentialsDto.file", "File not found ");
				}
			}
			else
			{
				addFieldError("teamCredentialsDto.file", "File not found ");
			}
		}
		else
		{
			addFieldError("teamCredentialsDto.file", "File not found ");
		}

		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(insurancePolicyDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
			return return_val;
		}

		int vandorResId = 0;
		saveInsuranceTypeDetails();

		if (insurancePolicyDto != null)
		{
			vandorResId = insurancePolicyDto.getVendorCredentialId();
			if (vandorResId != 0)
			{
				ActionContext.getContext().getSession().put("VendorCredId",vandorResId + "");
			}
		}

		return return_val;
	}
}
/*
 * Maintenance History
 * $Log: SaveInsuranceTypeAction.java,v $
 * Revision 1.22  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.19.6.1  2008/04/01 22:04:24  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.20  2008/03/27 18:58:06  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.19.10.1  2008/03/19 13:19:36  dmill03
 * dmill03 check-point check in
 *
 * Revision 1.19  2008/02/26 18:18:07  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.18.2.1  2008/02/25 19:10:01  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */