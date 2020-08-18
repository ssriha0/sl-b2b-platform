package com.newco.marketplace.web.action.provider;

/**
 * @author sahmad7
 */

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.opensymphony.xwork2.ActionContext;

public class AddAdditionalInsuranceAction extends 
								 SLAuditableBaseAction 
								 implements SessionAware,ServletRequestAware{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5410000668792055670L;
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private InsurancePolicyDto insurancePolicyDto;
	private Map sSessionMap;
	private HttpServletRequest request;
	private static final Logger logger = Logger.getLogger(ProviderRegistrationAction.class.getName());
	public AddAdditionalInsuranceAction(IInsuranceTypeDelegate iInsuranceDelegate,InsurancePolicyDto insurancePolicyDto) {
		this.iInsuranceDelegate = iInsuranceDelegate;
		this.insurancePolicyDto = insurancePolicyDto;
	}

	@Override
	public void validate() {
		super.validate();
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(insurancePolicyDto);
			
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
		}
	}
	
	@SkipValidation
	public boolean doInput() throws Exception {
		Boolean returnString=false;
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap()); 
			if (!getFieldErrors().isEmpty())
				insurancePolicyDto = (InsurancePolicyDto)baseTabDto.getDtObject();
		}
		if (getFieldErrors().isEmpty())
			returnString= true;
		else
			returnString= false;
		
		return returnString;
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
	
	/**
	 * Done as part of SL-10809: Additional Insurance 
	 * This method loads the page to enter additional insurances
	 * @throws Exception
	 */
	public String doLoadAdditionalInsurance() throws Exception {
		
		//Code added as part of Jira SL-20645 -To capture time while auditing insurance
		String auditTimeLoggingId = (String)ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);		
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		
		// SL-10809 Additional Insurance
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(0);
		doInput();
		try {
				determineSLAdminFeatureForAdditionalInsurance();
				int vendorId = 0;
				if ((String) getSessionMap().get(OrderConstants.VENDOR_ID) != null) {
					vendorId = new Integer((String) getSessionMap().get(
							OrderConstants.VENDOR_ID)).intValue();
					insurancePolicyDto.setVendorId(new Integer(
							(String) getSessionMap().get(OrderConstants.VENDOR_ID))
							.intValue());
				}
				String buttonType = (String) ServletActionContext.getRequest()
						.getSession().getAttribute("button_type");
				String credId = (String) ServletActionContext.getRequest()
						.getSession().getAttribute("cred_id");
				if (buttonType == null
						|| !(buttonType.equals(OrderConstants.BUTTON_TYPE_EDIT))) {
					buttonType = OrderConstants.BUTTON_TYPE_ADD;
					credId = "";
					insurancePolicyDto
							.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
				}
				insurancePolicyDto.setUserId((String) getSessionMap().get(
						OrderConstants.USER_NAME));

				if (StringUtils.isNotBlank(credId)) {
					insurancePolicyDto.setVendorCredentialId(Integer
							.parseInt(credId));
				}
				insurancePolicyDto.setProviderAdmin(true);
				InsurancePolicyDto insurancePolicyDto1=new InsurancePolicyDto();
				if(buttonType.equals(OrderConstants.BUTTON_TYPE_EDIT))
				{
					insurancePolicyDto1=iInsuranceDelegate.loadInsuranceDetails(insurancePolicyDto);
				}
				else
				{
					insurancePolicyDto1 = iInsuranceDelegate
							.loadInsuranceDetailsWithMaxVendorDocument(
									insurancePolicyDto, buttonType);
				}	
				String category = getParameter(OrderConstants.CATEGORY_NAME);
				insurancePolicyDto1.setInsuranceType(category);
				String currentDocId = getParameter(OrderConstants.CURRENT_DOC_ID);
				String currentDocName = getParameter(OrderConstants.CURRENT_DOC_NAME);
				String policyAmount = (String) ServletActionContext.getRequest()
						.getSession().getAttribute("policy_amount");
				if (policyAmount != null) {
					policyAmount = policyAmount.replaceAll(",", "");
				}

				insurancePolicyDto1.setAmount(policyAmount);
				if (insurancePolicyDto1.getCredentialCategoryId() == OrderConstants.OTHER_CREDENTIAL_CATEGORY_ID) {
					insurancePolicyDto1.setDisplayOtherInsurance(true);
				} else {
					insurancePolicyDto1.setDisplayOtherInsurance(false);
				}

				insurancePolicyDto = insurancePolicyDto1;

				ServletActionContext
						.getRequest()
						.getSession()
						.setAttribute(OrderConstants.CREDENTIAL_FILE_NAME,
								insurancePolicyDto.getCredentialDocumentFileName());
				ServletActionContext
					.getRequest()
					.getSession()
					.setAttribute(OrderConstants.CREDENTIAL_DOC_ID,
							insurancePolicyDto.getLastUploadedDocumentId());
			
				getSession().setAttribute("credentialMap",
						insurancePolicyDto.getMapCredentialType());
				ServletActionContext.getRequest().getSession()
						.setAttribute(OrderConstants.BUTTON_TYPE, buttonType);
				actionResults.setActionState(1);
				actionResults.setResultMessage(SUCCESS);
				actionResults
						.setAddtionalInfo1(getHtmlFriendlyText(insurancePolicyDto
								.getAgencyCountry()));
				actionResults
						.setAddtionalInfo2(getHtmlFriendlyText(insurancePolicyDto
								.getAgencyName()));
				actionResults
						.setAddtionalInfo3(insurancePolicyDto.getAgencyState());
				actionResults.setAddtionalInfo4(insurancePolicyDto.getAmount());
				actionResults
						.setAddtionalInfo5(getHtmlFriendlyText(insurancePolicyDto
								.getCarrierName()));
				actionResults
						.setAddtionalInfo6(getHtmlFriendlyText(insurancePolicyDto
								.getPolicyNumber()));
				actionResults.setAddtionalInfo7(""
						+ insurancePolicyDto.getLastUploadedDocumentId());
				actionResults
						.setAddtionalInfo8(getHtmlFriendlyText(insurancePolicyDto
								.getCredentialDocumentFileName()));
				actionResults.setAddtionalInfo9(insurancePolicyDto
						.getPolicyIssueDate());
				actionResults.setAddtionalInfo10(insurancePolicyDto
						.getPolicyExpirationDate());
				actionResults.setAddtionalInfo11(currentDocId);
				actionResults
						.setAddtionalInfo12(getHtmlFriendlyText(currentDocName));
			} catch (DelegateException ex) {
				ex.printStackTrace();
				logger.info("Exception Occured while processing the request due to"
						+ ex.getMessage());
				addActionError("Exception Occured while processing the request due to"
						+ ex.getMessage());
				return ERROR;
			}


		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		logger.info(responseStr);
		response.getWriter().write(responseStr);
		return "showAdditionalInsuranceForm";
	}
	
	private String getHtmlFriendlyText(String fromDB) {
		String result = fromDB;
		if(fromDB != null){
			if( result.indexOf('&') > 0 ) {
				result = StringUtils.replace(result, "&", "&amp;");
			
			}
			if(result.indexOf("'") > 0){
				 result = StringUtils.replace(result, "'", "&#39;");
				
			}
			if(result.indexOf("<") > 0){
				result = StringUtils.replace(result, "<", "&lt;");
				
			}
			if(result.indexOf(">") > 0){
				 result = StringUtils.replace(result, ">", "&gt;");
				
			}
		}
		return result;
	}
	
	
}
