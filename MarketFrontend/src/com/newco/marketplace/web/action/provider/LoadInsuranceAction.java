package com.newco.marketplace.web.action.provider;

/**
 * @author sahmad7
 */

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.opensymphony.xwork2.ActionContext;

public class LoadInsuranceAction extends 
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
	public LoadInsuranceAction(IInsuranceTypeDelegate iInsuranceDelegate,InsurancePolicyDto insurancePolicyDto) {
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
	
	public String doLoad() throws Exception {
		try{
			//SL-20645 : getting and setting the audit Logging id in request
			
			String auditTimeLoggingId  = (String)ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);		
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);				
			insurancePolicyDto.setAuditTimeLoggingIdNew(auditTimeLoggingId);
			
			determineSLAdminFeature();
			int tempCred=0;
			String category = null;
			if((String)getSessionMap().get("vendorId")!=null){
				insurancePolicyDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
			}
			insurancePolicyDto.setUserId((String)getSessionMap().get("username"));
			
			if(getSessionMap().get("VendorCredId")!=null&& !getSessionMap().get("VendorCredId").equals("0")){
				tempCred = new Integer((String)getSessionMap().get("VendorCredId")).intValue();
				insurancePolicyDto.setVendorCredentialId(tempCred);
			}else{
				insurancePolicyDto.setVendorCredentialId(0);	
			}
			if(getSessionMap().get("Category")!=null){
				category = (String)getSessionMap().get("Category");
			}
			InsurancePolicyDto insurancePolicyDto1=iInsuranceDelegate.loadInsuranceDetails(insurancePolicyDto);
			
			//SL-10809: Additional Insurance
			if (insurancePolicyDto1.getCredentialCategoryId() == OrderConstants.OTHER_CREDENTIAL_CATEGORY_ID) {
				insurancePolicyDto1.setDisplayOtherInsurance(true);
			} else {
				insurancePolicyDto1.setDisplayOtherInsurance(false);
			}
			
			category = insurancePolicyDto1.getCategory();
			if(StringUtils.isBlank(category)){
				if(category != null && category.equalsIgnoreCase("auto"))
					category = "Auto Liability";
				else if(category != null && category.equalsIgnoreCase("work"))
					category = "Workman's Compensation";
				else if(category != null && category.equalsIgnoreCase("gene"))
					category = "General Liability";
			}
			if (insurancePolicyDto1.getInsuranceTypeList() != null
					&& !insurancePolicyDto1.getInsuranceTypeList().isEmpty()) {
				for (int i = 0; i < insurancePolicyDto1.getInsuranceTypeList()
						.size(); i++) {
					CredentialProfile cProfile = (CredentialProfile) insurancePolicyDto1
							.getInsuranceTypeList().get(i);
					logger.debug("-----cProfile.getCredCategory()-------"
							+ cProfile.getCredCategory());
					if (cProfile.getCredCategory().equalsIgnoreCase(category)) {
						int typeId = (cProfile.getCredentialTypeId());
						int cateId = (cProfile.getCredentialCategoryId());
						String key = typeId + "-" + cateId;
						insurancePolicyDto1.setCombinedKey(key);
						logger.debug("------Key------" + key);
					}

				}
			}
			insurancePolicyDto1.setInsuranceType(category);
			if(!doInput())
			{	
				logger.debug("----Inside IF ----" + insurancePolicyDto1.getInsuranceTypeList());
				insurancePolicyDto.setInsuranceTypeList(insurancePolicyDto1.getInsuranceTypeList());
				logger.debug("----Inside IF ---111-" + insurancePolicyDto.getInsuranceTypeList());
				/**
				 * Retaining values for Document Upload
				 */
				insurancePolicyDto.setCredentialDocumentBytes(insurancePolicyDto1.getCredentialDocumentBytes());
				insurancePolicyDto.setCredentialDocumentExtention(insurancePolicyDto1.getCredentialDocumentExtention());
				insurancePolicyDto.setCredentialDocumentFileName(insurancePolicyDto1.getCredentialDocumentFileName());
				insurancePolicyDto.setCredentialDocumentFileSize(insurancePolicyDto1.getCredentialDocumentFileSize());
				insurancePolicyDto.setCredentialDocumentId(insurancePolicyDto1.getCredentialDocumentId());
				return SUCCESS;
			}else{
				insurancePolicyDto=insurancePolicyDto1;
				logger.debug("--------- Inside ELSE " + insurancePolicyDto.getInsuranceTypeList());
			}
			
			
			
			logger.debug("------tempCred------"+tempCred);
			logger.debug("-------------------------insurancePolicyDto------"+insurancePolicyDto.getVendorId());
			logger.debug("-------------------------insurancePolicyDto------11"+insurancePolicyDto.getUserId());
			logger.debug("------tempCred id------"+request.getParameter("tempCred"));
			insurancePolicyDto.setVendorCredentialId(tempCred);
		}catch(DelegateException ex){
			ex.printStackTrace();
			logger.info("Exception Occured while processing the request due to"+ex.getMessage());
			addActionError("Exception Occured while processing the request due to"+ex.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
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

}
