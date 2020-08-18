package com.newco.marketplace.web.action.provider;


import java.text.SimpleDateFormat;
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

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.newco.marketplace.web.utils.FileUpload;
import com.newco.marketplace.web.utils.FileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
 
public class PolicyDetailsAjaxAction extends SLAuditableBaseAction implements SessionAware, 
	ServletRequestAware,ServletResponseAware,Preparable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PolicyDetailsAjaxAction.class.getName());
	private Map<?,?> sSessionMap;
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private InsurancePolicyDto insurancePolicyDto;
	private FileUpload fileUpload = null;
	private static final String FILE_FIELD_NAME = "insurancePolicyDto.file";	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public PolicyDetailsAjaxAction(IInsuranceTypeDelegate iInsuranceDelegate,InsurancePolicyDto insurancePolicyDto) {
		this.iInsuranceDelegate = iInsuranceDelegate;
		this.insurancePolicyDto = insurancePolicyDto;
	}
	public void prepare() throws Exception {
		
	}
		
	public String doSave() throws Exception {
		logger.info("####Inside doSave() of PolicyDetailsAjaxAction####"); 
		String amount = getParameter(OrderConstants.AMOUNT);	
		String returnValue = SUCCESS;
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		
		AjaxResultsDTO actionResults=new AjaxResultsDTO();
		actionResults.setActionState(0);
		validateAmount(actionResults,amount);
		validateFields(actionResults);
		validateDates(actionResults);
		try{
			insurancePolicyDto.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);			
			String category = getParameter(OrderConstants.CATEGORY_NAME);
			if(category.equals(OrderConstants.GENERAL_LIABILTY)){
				insurancePolicyDto.setCredentialCategoryId(OrderConstants.GL_CREDENTIAL_CATEGORY_ID);
			}else if(category.equals(OrderConstants.AUTO_LIABILTY)){
				insurancePolicyDto.setCredentialCategoryId(OrderConstants.AL_CREDENTIAL_CATEGORY_ID);
			}else if(category.equals(OrderConstants.WORKMANS_COMPENSATION)){
				insurancePolicyDto.setCredentialCategoryId(OrderConstants.WC_CREDENTIAL_CATEGORY_ID);
			}			
			insurancePolicyDto.setAmount(amount);
			insurancePolicyDto.setVendorCredentialId(Integer.parseInt(getParameter(OrderConstants.CREDENTIAL_ID)));
			insurancePolicyDto.setPolicyNumber(getParameter(OrderConstants.POLICY_NUMBER));
			insurancePolicyDto.setCarrierName(getParameter(OrderConstants.CARRIER_NAME));
			insurancePolicyDto.setAgencyName(getParameter(OrderConstants.AGENCY_NAME));
			insurancePolicyDto.setAgencyState(getParameter(OrderConstants.AGENCY_STATE));
			insurancePolicyDto.setAgencyCountry(getParameter(OrderConstants.AGENCY_COUNTRY));
			insurancePolicyDto.setIssueDate(getParameter(OrderConstants.POLICY_ISSUE_DATE));
			insurancePolicyDto.setExpirationDate(getParameter(OrderConstants.POLICY_EXPIRATION_DATE));
			returnValue = saveInsuranceTypeDetails(securityContext);			
		}catch(Exception ex){
			logger.warn("Exception Occured while processing the request due to",ex);
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());	
			
		}
		InsuranceInfoDto insuranceInfoDto=(InsuranceInfoDto)ServletActionContext.getRequest().getSession().getAttribute("InsuranceTabDto");
		insuranceInfoDto.setCBGLIAmount(amount);
		actionResults.setActionState(1);
		actionResults.setResultMessage(SUCCESS);	
		actionResults.setAddtionalInfo1(amount);
		logger.info("####Success -- doSave() of PolicyDetailsAjaxAction####");
		
		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		logger.info(responseStr);
		response.getWriter().write(responseStr);
		return NONE;
		}
	
	private String saveInsuranceTypeDetails(SecurityContext securityContext) throws Exception{
		try{	
			BaseTabDto baseTabDto1 =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto1.getFieldsError().clear();
			logger.info("Start ------Inside------saveInsuranceTypeDetails-----");
			if(null!=securityContext.getCompanyId()){
				insurancePolicyDto.setVendorId(securityContext.getCompanyId());
			}
			insurancePolicyDto.setUserId(securityContext.getUsername());			
			 //Code for Document Upload
			logger.debug("\n\n\n\n ------ Inside Document Upload Part ");
			 
			MultiPartRequestWrapper multiPartRequestWrapper  = null;
							//	(MultiPartRequestWrapper) ServletActionContext.getRequest();
			 
			if (multiPartRequestWrapper != null)
			{	
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
			
			insurancePolicyDto=iInsuranceDelegate.saveInsurancePolicyDetails(insurancePolicyDto);
			
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
	
	private void validateAmount(AjaxResultsDTO actionResults, String dAmount){	
			if((dAmount !=null) || (dAmount =="")){
				try{
					if(Double.parseDouble(dAmount) < 0 ) {				
						actionResults.setResultMessage("Amount cannot be a negative number");
					}
				}
				catch (NumberFormatException nfe) {					
					actionResults.setResultMessage("Amount should be number.");
				}
				if(dAmount.indexOf(".")!=-1){
					if(dAmount.trim().substring(dAmount.indexOf("."),dAmount.length()).length()>3){					
						actionResults.setResultMessage("Amount should contain only two digits after decimal.");
					}
				}
			} 		
			actionResults.setResultMessage("Please provide your current coverage amount for the insurance");
		}
		

	private void validateFields(AjaxResultsDTO actionResults){		
		if(StringUtils.isBlank(getParameter(OrderConstants.CARRIER_NAME))){			
			actionResults.setResultMessage("Please provide the name of the insurance carrier.");
		}		
		if(StringUtils.isBlank(getParameter(OrderConstants.AGENCY_NAME))){			
			actionResults.setResultMessage("Please provide the name of the insurance Agency");
		}	
		if(StringUtils.isBlank(getParameter(OrderConstants.AGENCY_STATE))){		
			actionResults.setResultMessage("Please select the state where your agency is located");
		}
		if(StringUtils.isBlank(getParameter(OrderConstants.POLICY_NUMBER))){			
			actionResults.setResultMessage("Please provide the policy number.");
		}
	
	}
	private void validateDates(AjaxResultsDTO actionResults){		 
		logger.debug("Validating the Date differences");
		String policyIssueStr = getParameter(OrderConstants.POLICY_ISSUE_DATE);
		String policyExpireStr=  getParameter(OrderConstants.POLICY_EXPIRATION_DATE);;
		boolean isValidIssueDt=true;
		boolean isValidExpireDt=true;
		if (StringUtils.isBlank(policyIssueStr)){		
			actionResults.setResultMessage("Please provide the policy number.");
			isValidIssueDt=false;
		}
		if (StringUtils.isBlank(policyIssueStr)){		
			actionResults.setResultMessage("Please select the expiration date date for your current policy.");
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
				actionResults.setResultMessage("Please enter avalid Policy Issue Date");
			}else if((contFlag !=null) || (contFlag.equalsIgnoreCase("EXPIRE")))
{			
				actionResults.setResultMessage("Please enter a valid Policy Expiration date.");
			}
			logger.debug("Exception comdate"+e);	
			return;
		}

		if(policyIssueDt.compareTo(policyExpireDt) > 0){	
			actionResults.setResultMessage("Policy Expiration Date should be greater than Policy Issue date.");
		}
	}
		

	
	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;		
	}		
	
	public Map<?,?> getSessionMap() {
		return this.sSessionMap;		
	}
	
	public void setServletResponse(HttpServletResponse arg0)
	{
		response=arg0;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;
	}
	public InsurancePolicyDto getInsurancePolicyDto() {
		return insurancePolicyDto;
	}

	public void setInsurancePolicyDto(InsurancePolicyDto insurancePolicyDto) {
		this.insurancePolicyDto = insurancePolicyDto;
	}



}
