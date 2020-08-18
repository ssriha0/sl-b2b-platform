package com.newco.marketplace.web.action.auditor.ajax;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID_NEW;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ICommonAuditorDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.ajax.AjaxRequestModelDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.security.SecuredAction;
import com.newco.marketplace.web.utils.AjaxHelperUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.CommonConstants;

public class CommonAuditorAjaxWidgetAction extends SLBaseAction implements
		ModelDriven<AjaxRequestModelDTO>, Preparable {

	private static final long serialVersionUID = 530343187692728846L;

	private static final Logger logger = Logger
			.getLogger("CommonAuditorAjaxWidgetAction");

	private ICommonAuditorDelegate auditorDelegateService = null;

	private ILookupDelegate lookupManager = null;

	private AjaxRequestModelDTO ajaxModel = new AjaxRequestModelDTO();
	
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;

	public void prepare() {
		createCommonServiceOrderCriteria();
	}

	public CommonAuditorAjaxWidgetAction(
			ICommonAuditorDelegate auditorDelegateService) {
		this.auditorDelegateService = auditorDelegateService;
	}

	public String handleAjaxCall() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		//ServletActionContext.getRequest().
		List<LookupVO> reasonCodeList = getLookupManager().loadReasonCodeList( convertModelToLookUpVo(getModel())); 
		AjaxResultsDTO actionResults = AjaxHelperUtil.convertLookVoToAJAXSubSelectList(reasonCodeList , true);
		// logger.debug("AJAX RESPONSE "+actionResults.toXmlDeepCopy());
		if (ajaxModel.getActionSubmitType()
				.equalsIgnoreCase("VendorCredential") ||
			ajaxModel.getActionSubmitType().equalsIgnoreCase("ServiceProCredential"))
		{
			actionResults.setAddtionalInfo1(getModel().getCredentialRequest().getSubSelectName());
		} else {
			actionResults.setAddtionalInfo1(getModel().getCompanyOrServicePro().getSubSelectName());
		}
		getResponse().getWriter().write(actionResults.toXmlDeepCopy());
		return NONE;
	}

	@SecuredAction(securityTokenEnabled = true)
	public String preformAdminAction() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		AjaxRequestModelDTO theModel = getModel();
		AjaxResultsDTO actionResults = null;
		if (theModel.getActionSubmitType() != null
				&& theModel.getActionSubmitType().equalsIgnoreCase(
						"ProviderCompany")) {
			actionResults = getAuditorDelegateService()
							.approveProviderServiceProWithAJAXResponse(
									createVOFromAJAXForVendor(getModel()), false);
		} else if (theModel.getActionSubmitType() != null
				&& theModel.getActionSubmitType().equalsIgnoreCase(
						"VendorCredential")) {
			actionResults = getAuditorDelegateService()
							.approveProviderCredentialWithAJAXResponse(
									createVOFromAJAXForVendorCredential(getModel()),false);
			
			 //Code added as part of Jira SL-20645 -To capture end time while auditing insurance/credentials
			 updateEndTimeCredentialStatus();
			
		} else if (theModel.getActionSubmitType() != null
				&& theModel.getActionSubmitType().equalsIgnoreCase(
						"ServiceProCompany")) {
			actionResults = getAuditorDelegateService()
							.approveProviderServiceProWithAJAXResponse(
									createVOFromAJAXForServicePro(getModel()),true);
		} else if (theModel.getActionSubmitType() != null
					&& theModel.getActionSubmitType().equalsIgnoreCase(
					"ServiceProCredential")) {
				actionResults = getAuditorDelegateService()
							.approveProviderCredentialWithAJAXResponse(
									createVOFromAJAXForTeamCredential(getModel()), true);
				
				 //Code added as part of Jira SL-20645 -To capture end time while auditing insurance/credentials
				 updateEndTimeCredentialStatus();
		}
		getResponse().getWriter().write(actionResults.toXml());
		return NONE;
	}

	
	/** @description Code added as part of Jira SL-20645 -To capture end time while auditing insurance /credentials
	 * @throws DelegateException
	 */
	private void updateEndTimeCredentialStatus() throws DelegateException {
		String auditTimeLogId = getModel().getCredentialRequest().getAuditTimeLoggingIdNew();
		ServletActionContext.getRequest().removeAttribute(AUDIT_TIME_LOGGING_ID);
		
		if(StringUtils.isBlank(auditTimeLogId))
		{
			auditTimeLogId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID_NEW);
		}
		
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
					auditVo.setEndAction(CommonConstants.UPDATE_CREDENTIAL_STATUS_END_ACTION);
					powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
				}
			}
	}

	public String addCommonNote() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		AjaxRequestModelDTO theModel = getModel();
		AjaxResultsDTO actionResults = null;
		if (theModel.getActionSubmitType() != null
				&& theModel.getActionSubmitType().equalsIgnoreCase(
						"AuditorNote")) {
			actionResults = getAuditorDelegateService()
					.addAuditorNoteWithAJAXResponse(createAuditorNote(getModel()));
			getResponse().getWriter().write(actionResults.toXml());
		}
		return NONE;
	}

	public AjaxRequestModelDTO getModel() {
		return this.ajaxModel;
	}

	private LookupVO convertModelToLookUpVo(AjaxRequestModelDTO ajaxModel) {
		LookupVO lookVo = new LookupVO();
		if (ajaxModel.getActionSubmitType().equalsIgnoreCase("VendorCredential") ||
			ajaxModel.getActionSubmitType().equalsIgnoreCase("ServiceProCredential")) {
			lookVo.setType(ajaxModel.getCredentialRequest().getCurrentVal());
			lookVo.setDescr(ajaxModel.getCredentialRequest().getSelectType());
		} else {
			lookVo.setType(ajaxModel.getCompanyOrServicePro().getCurrentVal());
			lookVo.setDescr(ajaxModel.getCompanyOrServicePro().getSelectType());
		}
		return lookVo;
	}

	private HashMap createAuditorNote(AjaxRequestModelDTO ajaxModel) {
		HashMap theMap = new HashMap();
		theMap.put(NOTE, ajaxModel.getNoteReq().getCommonMessageNote());
		theMap.put("VENDOR_ID", get_commonCriteria().getCompanyId());
		theMap.put("AUDITOR_ID", get_commonCriteria().getSecurityContext()
				.getSlAdminUName());
		return theMap;
	}

	private AuditVO createVOFromAJAXForVendor(AjaxRequestModelDTO ajaxModel) {
		AuditVO auVO = new AuditVO();
		auVO.setResourceId(new Integer(0));
		auVO.setVendorId(get_commonCriteria().getCompanyId());
		auVO.setAuditLinkId(1);

		auVO.setAuditKeyId(get_commonCriteria().getCompanyId());
		auVO.setReviewedBy(get_commonCriteria().getSecurityContext()
				.getSlAdminUName());
		auVO.setWfStatusId(new Integer(ajaxModel.getCompanyOrServicePro().getCurrentVal()));
		auVO.setWfStateId(new Integer(ajaxModel.getCompanyOrServicePro().getCurrentVal()));
		auVO.setWfState(ajaxModel.getCompanyOrServicePro().getCurrentKey());
		auVO.setReasonCodeIds((String[]) ajaxModel.getCompanyOrServicePro().getCommonSEL1SUB().toArray(
				new String[ajaxModel.getCompanyOrServicePro().getCommonSEL1SUB().size()]));
		boolean sendEmail = (ajaxModel.getCompanyOrServicePro().getSendEmail() != null
				&& ajaxModel.getCompanyOrServicePro().getSendEmail().equals("1") ? true : false);
		auVO.setSendEmailNotice(sendEmail);
		return auVO;
	}


	private AuditVO createVOFromAJAXForVendorCredential(
			AjaxRequestModelDTO ajaxModel) {
		AuditVO auVO = new AuditVO();
		auVO.setResourceId(new Integer(0));
		auVO.setVendorId(get_commonCriteria().getCompanyId());
		auVO.setAuditLinkId(3);
		Integer theId = -1;
		if(ajaxModel.getCredentialRequest().getCredentialKey() != null)
		{
			theId = Integer.parseInt(ajaxModel.getCredentialRequest().getCredentialKey());
		}
		else
		{
			logger.error("createVOFromAJAXForVendorCredential() - credentialKey was not initialized properly in front-end");
		}
		auVO.setAuditKeyId(theId);
		auVO.setVendorCredentialId(theId);
		auVO.setReviewedBy(get_commonCriteria().getSecurityContext()
				.getSlAdminUName());
		auVO.setWfStatusId(new Integer(ajaxModel.getCredentialRequest().getCurrentVal()));
		auVO.setWfStateId(new Integer(ajaxModel.getCredentialRequest().getCurrentVal()));
		auVO.setReviewComments( ajaxModel.getCredentialRequest().getCommonReviewNote());
		auVO.setWfState(ajaxModel.getCredentialRequest().getCurrentKey());
		
		Integer numReasonCodes = 0;
		String[] reasonCodeIds = null;
		
		if(ajaxModel != null && ajaxModel.getCredentialRequest() != null && ajaxModel.getCredentialRequest().getCommonSEL2SUB() != null)
		{
			numReasonCodes = ajaxModel.getCredentialRequest().getCommonSEL2SUB().size();
			reasonCodeIds = (String[])ajaxModel.getCredentialRequest().getCommonSEL2SUB().toArray(new String[numReasonCodes]);
		}
		else
		{
			// Can only select 1 reason code
			reasonCodeIds = new String[1];
			if(ajaxModel.getCredentialRequest() != null && ajaxModel.getCredentialRequest().getSubSelectName() != null)
			{
				reasonCodeIds[0] = ajaxModel.getCredentialRequest().getSubSelectName();
			}
			//logger.error("createVOFromAJAXForVendorCredential() reasonCodeIds creation failure");
		}
		
		
		
		
		auVO.setReasonCodeIds(reasonCodeIds);
		boolean sendEmail = (ajaxModel.getCredentialRequest().getSendEmail() != null
				&& ajaxModel.getCredentialRequest().getSendEmail().equals("1") ? true : false);
		auVO.setSendEmailNotice(sendEmail);
		return auVO;
	}

	private AuditVO createVOFromAJAXForServicePro(AjaxRequestModelDTO ajaxModel) {
		AuditVO auVO = new AuditVO();
		auVO.setResourceId(Integer.parseInt(ajaxModel.getCompanyOrServicePro().getTheResourceId()));
		auVO.setVendorId(get_commonCriteria().getCompanyId());
		auVO.setAuditLinkId(2);

		auVO.setAuditKeyId(Integer.parseInt(ajaxModel.getCompanyOrServicePro().getTheResourceId()));
		auVO.setReviewedBy(get_commonCriteria().getSecurityContext()
				.getSlAdminUName());
		auVO.setWfStatusId(new Integer(ajaxModel.getCompanyOrServicePro().getCurrentVal()));
		auVO.setWfStateId(new Integer(ajaxModel.getCompanyOrServicePro().getCurrentVal()));
		auVO.setWfState(ajaxModel.getCompanyOrServicePro().getCurrentKey());
		auVO.setReasonCodeIds((String[]) ajaxModel.getCompanyOrServicePro().getCommonSEL1SUB().toArray(
				new String[ajaxModel.getCompanyOrServicePro().getCommonSEL1SUB().size()]));
		boolean sendEmail = (ajaxModel.getCompanyOrServicePro().getSendEmail() != null
				&& ajaxModel.getCompanyOrServicePro().getSendEmail().equals("1") ? true : false);
		auVO.setSendEmailNotice(sendEmail);
		return auVO;
	}



	private AuditVO createVOFromAJAXForTeamCredential(
			AjaxRequestModelDTO ajaxModel) {
		AuditVO auVO = new AuditVO();
		auVO.setResourceId(Integer.parseInt(ajaxModel.getCredentialRequest().getTheResourceId()));
		auVO.setVendorId(get_commonCriteria().getCompanyId());
		auVO.setAuditLinkId(4);
		Integer theId = ajaxModel.getCredentialRequest().getCredentialKey() != null ? new Integer(
				ajaxModel.getCredentialRequest().getCredentialKey()) : new Integer(-1);
		auVO.setResourceCredentialId(theId);
		auVO.setAuditKeyId(theId);
		//auVO.setVendorCredentialId(theId);
		auVO.setReviewedBy(get_commonCriteria().getSecurityContext()
				.getSlAdminUName());
		auVO.setReviewComments( ajaxModel.getCredentialRequest().getCommonReviewNote());
		auVO.setWfStatusId(new Integer(ajaxModel.getCredentialRequest().getCurrentVal()));
		auVO.setWfStateId(new Integer(ajaxModel.getCredentialRequest().getCurrentVal()));
		auVO.setWfState(ajaxModel.getCredentialRequest().getCurrentKey());
		auVO.setReasonCodeIds((String[]) ajaxModel.getCredentialRequest().getCommonSEL2SUB().toArray(
				new String[ajaxModel.getCredentialRequest().getCommonSEL2SUB().size()]));
		boolean sendEmail = (ajaxModel.getCredentialRequest().getSendEmail() != null
				&& ajaxModel.getCredentialRequest().getSendEmail().equals("1") ? true : false);
		auVO.setSendEmailNotice(sendEmail);
		return auVO;
	}

	public void setModel(AjaxRequestModelDTO ajaxModel) {
		this.ajaxModel = ajaxModel;
	}

	public ILookupDelegate getLookupManager() {
		return lookupManager;
	}

	public void setLookupManager(ILookupDelegate lookupManager) {
		this.lookupManager = lookupManager;
	}

	public ICommonAuditorDelegate getAuditorDelegateService() {
		return auditorDelegateService;
	}

	public void setAuditorDelegateService(
			ICommonAuditorDelegate auditorDelegateService) {
		this.auditorDelegateService = auditorDelegateService;
	}

	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}
	
	
}
