package com.newco.marketplace.web.action.base;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.ICommonAuditorDelegate;
import com.opensymphony.xwork2.ActionContext;

/**
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 01:13:54 $
 *
 */
public class SLAuditableBaseAction extends SLBaseAction {

	private static final long serialVersionUID = -2494051981294687379L;
	private static final Logger logger = Logger.getLogger(SLAuditableBaseAction.class.getName());
	private ICommonAuditorDelegate auditorDelegateService = null;
	private SecurityContext securityContext;
	private HttpSession session;
	private Map theSessionMap;

	public ICommonAuditorDelegate getAuditorDelegateService() {
		return auditorDelegateService;
	}

	public void setAuditorDelegateService(
			ICommonAuditorDelegate auditorDelegateService) {
		this.auditorDelegateService = auditorDelegateService;
	}

	protected void determineSLAdminFeature()  {
		String currentProviderStatus = "N/A";
		String currentServiceProStatus = "N/A";
		String currentProviderCredenitalStatus = "N/A";
		String currentResourceCredenitalStatus = "N/A";
		String currentResourceCredenitalReasonCd = "N/A";
		String currentProviderCredenitalReasonCode="N/A";
		if(getSecurityContext().isSlAdminInd())
		{
			ServletActionContext.getRequest().setAttribute("showAdminWidget", "showWidget");
			session.setAttribute("pre_selected_audit_selects", new ArrayList());
			Integer tempCredId=(Integer)ActionContext.getContext().getSession().get("resourceCredId");
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");

			String myCredId = "0";
			if(getRequest().getParameter("id") != null){
						ServletActionContext.getRequest().setAttribute("credId", getRequest().getParameter("id"));
						myCredId = (String)getRequest().getParameter("id");
				}
			
			else if(resourceId!=null && !resourceId.equals("0") && tempCredId != null){
				ServletActionContext.getRequest().setAttribute("credId", tempCredId.intValue());
				myCredId = String.valueOf(tempCredId.intValue());
			}
			else if(theSessionMap.get("VendorCredId")!=null&& !theSessionMap.get("VendorCredId").equals("0")){
				Integer theCred = new Integer((String)theSessionMap.get("VendorCredId")).intValue();
				myCredId = (String)theSessionMap.get("VendorCredId");
				ServletActionContext.getRequest().setAttribute("credId", theCred);
			}
			else{
				ServletActionContext.getRequest().setAttribute("credId", 0);
				myCredId = "0";
			}

			if(resourceId!=null){
				try {
					currentResourceCredenitalStatus =
						getAuditorDelegateService().getResourceCredentialStatus(Integer.parseInt(resourceId), Integer.parseInt(myCredId));
					currentResourceCredenitalReasonCd =
							getAuditorDelegateService().getResourceCredentialReasonCd(Integer.parseInt(resourceId), Integer.parseInt(myCredId));
				} catch (NumberFormatException e) {
					logger.info("Caught Exception and ignoring",e);
				} catch (BusinessServiceException e) {
					logger.info("Caught Exception and ignoring",e);
				}

				try {
					currentServiceProStatus =
						getAuditorDelegateService().getResourceCurrentStatus(Integer.parseInt(resourceId));
				} catch (NumberFormatException e) {
					logger.info("Caught Exception and ignoring",e);
				} catch (BusinessServiceException e) {
					logger.info("Caught Exception and ignoring",e);
				}

				ServletActionContext.getRequest().setAttribute("theResourceId", resourceId);
				ServletActionContext.getRequest().setAttribute("current_service_pro_status", currentServiceProStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_cred_status", currentResourceCredenitalStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_reason_cd", currentResourceCredenitalReasonCd);
			}
			else {
				ServletActionContext.getRequest().setAttribute("theResourceId", 0);
				ServletActionContext.getRequest().setAttribute("current_service_pro_status", currentServiceProStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_cred_status", currentResourceCredenitalStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_reason_cd", currentResourceCredenitalReasonCd);
			}

			String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
			if(getSecurityContext().isSlAdminInd()){
				if(!StringUtils.isBlank(vendorId)){
					try {
						currentProviderStatus =
							getAuditorDelegateService().getVendorCurrentStatus(Integer.parseInt(vendorId));
					} catch (NumberFormatException e) {
						logger.info("Caught Exception and ignoring",e);
					} catch (BusinessServiceException e) {
						logger.info("Caught Exception and ignoring",e);
					}
					try {
						currentProviderCredenitalStatus =
							getAuditorDelegateService().getVendorCredentialStatus(Integer.parseInt(vendorId), Integer.parseInt(myCredId));
						currentProviderCredenitalReasonCode=
								getAuditorDelegateService().getVendorCredentialReasonCd(Integer.parseInt(vendorId), Integer.parseInt(myCredId));
					} catch (NumberFormatException e) {
						logger.info("Caught Exception and ignoring",e);
					} catch (BusinessServiceException e) {
						logger.info("Caught Exception and ignoring",e);
					}
				}
				ServletActionContext.getRequest().setAttribute("current_provider_status", currentProviderStatus);
				ServletActionContext.getRequest().setAttribute("current_provider_credential_status", currentProviderCredenitalStatus);
				ServletActionContext.getRequest().setAttribute("current_provider_credential_reason_code", currentProviderCredenitalReasonCode);
			}
		}

	}

	protected void determineSLAdminFeatureForAdditionalInsurance()  {
		String currentProviderStatus = "N/A";
		String currentServiceProStatus = "N/A";
		String currentProviderCredenitalStatus = "N/A";
		String currentResourceCredenitalStatus = "N/A";
		String currentResourceCredenitalReasonCd ="N/A";
		String currentProviderCredenitalReasonCode="N/A";
		if(getSecurityContext().isSlAdminInd())
		{
			ServletActionContext.getRequest().setAttribute("showAdminWidget", "showWidget");
			session.setAttribute("pre_selected_audit_selects", new ArrayList());
			Integer tempCredId=(Integer)ActionContext.getContext().getSession().get("resourceCredId");
			String myCredId = "0";
			String credId = (String) ServletActionContext.getRequest()
			.getSession().getAttribute("cred_id");
			if(StringUtils.isNotBlank(credId)){
				myCredId = credId;
				ServletActionContext.getRequest().setAttribute("credId", Integer.parseInt(credId));
			}
			else if(getRequest().getParameter("id") != null){
						ServletActionContext.getRequest().setAttribute("credId", getRequest().getParameter("id"));
						myCredId = (String)getRequest().getParameter("id");
				}
			else if(theSessionMap.get("VendorCredId")!=null&& !theSessionMap.get("VendorCredId").equals("0")){
				Integer theCred = new Integer((String)theSessionMap.get("VendorCredId")).intValue();
				myCredId = (String)theSessionMap.get("VendorCredId");
				ServletActionContext.getRequest().setAttribute("credId", theCred);
			}
			else if(tempCredId != null){
				ServletActionContext.getRequest().setAttribute("credId", tempCredId.intValue());
				myCredId = String.valueOf(tempCredId.intValue());
			}
			else{
				ServletActionContext.getRequest().setAttribute("credId", 0);
				myCredId = "0";
			}

			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if(resourceId!=null){
				try {
					currentResourceCredenitalStatus =
						getAuditorDelegateService().getResourceCredentialStatus(Integer.parseInt(resourceId), Integer.parseInt(myCredId));
					currentResourceCredenitalReasonCd =
							getAuditorDelegateService().getResourceCredentialReasonCd(Integer.parseInt(resourceId), Integer.parseInt(myCredId));
				} catch (NumberFormatException e) {
					logger.info("Caught Exception and ignoring",e);
				} catch (BusinessServiceException e) {
					logger.info("Caught Exception and ignoring",e);
				}

				try {
					currentServiceProStatus =
						getAuditorDelegateService().getResourceCurrentStatus(Integer.parseInt(resourceId));
				} catch (NumberFormatException e) {
					logger.info("Caught Exception and ignoring",e);
				} catch (BusinessServiceException e) {
					logger.info("Caught Exception and ignoring",e);
				}

				ServletActionContext.getRequest().setAttribute("theResourceId", resourceId);
				ServletActionContext.getRequest().setAttribute("current_service_pro_status", currentServiceProStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_cred_status", currentResourceCredenitalStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_reascon_cd", currentResourceCredenitalReasonCd);
			}
			else {
				ServletActionContext.getRequest().setAttribute("theResourceId", 0);
				ServletActionContext.getRequest().setAttribute("current_service_pro_status", currentServiceProStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_cred_status", currentResourceCredenitalStatus);
				ServletActionContext.getRequest().setAttribute("current_service_pro_reascon_cd", currentResourceCredenitalReasonCd);
			}

			String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
			if(getSecurityContext().isSlAdminInd()){
				if(!StringUtils.isBlank(vendorId)){
					try {
						currentProviderStatus =
							getAuditorDelegateService().getVendorCurrentStatus(Integer.parseInt(vendorId));
					} catch (NumberFormatException e) {
						logger.info("Caught Exception and ignoring",e);
					} catch (BusinessServiceException e) {
						logger.info("Caught Exception and ignoring",e);
					}
					try {
						currentProviderCredenitalStatus =
							getAuditorDelegateService().getVendorCredentialStatus(Integer.parseInt(vendorId), Integer.parseInt(myCredId));
						currentProviderCredenitalReasonCode=
								getAuditorDelegateService().getVendorCredentialReasonCd(Integer.parseInt(vendorId), Integer.parseInt(myCredId));
					} catch (NumberFormatException e) {
						logger.info("Caught Exception and ignoring",e);
					} catch (BusinessServiceException e) {
						logger.info("Caught Exception and ignoring",e);
					}
				}
				ServletActionContext.getRequest().setAttribute("current_provider_status", currentProviderStatus);
				ServletActionContext.getRequest().setAttribute("current_provider_credential_status", currentProviderCredenitalStatus);
				ServletActionContext.getRequest().setAttribute("current_provider_credential_reason_code", currentProviderCredenitalReasonCode);
			}
		}

	}
	
	
	
	

	public SecurityContext getSecurityContext() {
		if(securityContext == null)
		{
			setContextDetails();
		}
		return securityContext;
	}

	public void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		theSessionMap = ActionContext.getContext().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}
}
