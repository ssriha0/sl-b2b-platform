/**
 *
 */
package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.COULD_NOT_DISPLAY_RESULT;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.POWER_AUDITOR_SESSION_FILTER;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.WORKFLOW_QNAME;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.security.AdminPageAction;
import com.opensymphony.xwork2.ActionContext;
import com.servicelive.common.CommonConstants;

/**
 * @author ccarle5
 *
 */
@AdminPageAction
//Curretnlty this class is not model driven but in future when in need create this Action Model drive using the ProviderInfoPagesDto
public class PowerAuditorWorkflowControllerAction extends SLAuditableBaseAction implements
ServletRequestAware {

	private HttpServletRequest request;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PowerAuditorWorkflowControllerAction.class.getName());
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;
	private BaseTabDto tabDto;
	private IActivityRegistryDelegate activityRegistryDelegate;

	String auditTimeLoggingId;

	public String execute() throws Exception{
		PowerAuditorVendorInfoVO vendorInfo = new PowerAuditorVendorInfoVO();
		PowerAuditorSearchVO filtercriteria =  new PowerAuditorSearchVO();
		String qname =  (String) ServletActionContext.getRequest().getParameter(WORKFLOW_QNAME);;
		if(ServletActionContext.getRequest().getSession().getAttribute(POWER_AUDITOR_SESSION_FILTER) != null){
			filtercriteria = (PowerAuditorSearchVO)ServletActionContext.getRequest().getSession().getAttribute(POWER_AUDITOR_SESSION_FILTER);
		}else {
			filtercriteria.setPrimaryIndustry(-1);
			filtercriteria.setCategoryOfCredential(-1);
			filtercriteria.setCredentialType(-1);

		}
		if( StringUtils.isBlank(qname)) {
			if(filtercriteria.getWorkFlowQueue() != null ) {
				qname = filtercriteria.getWorkFlowQueue();
			}
			else {
				return credentialNotFound();
			}

		}
		else
		{
			filtercriteria.setWorkFlowQueue(qname);
		}


		AuditVO auditTask = powerAuditorWorkflowDelegate.getNextAuditFromQueue(filtercriteria);
		//SL-20645 : capturing the end time
		if(getSecurityContext().isSlAdminInd()){
			String auditTimeLoggingId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);		
			if(StringUtils.isNotBlank(auditTimeLoggingId) && StringUtils.isNumeric(auditTimeLoggingId))
			{
				Integer auditTimeId=Integer.parseInt(auditTimeLoggingId);
				if(null!=auditTimeId)
				{
					//SL-20645:updating 
					AuditTimeVO auditVo=new AuditTimeVO();
					auditVo.setAuditTimeLoggingId(auditTimeId);
					auditVo.setEndTime(new Date());

					auditVo.setEndAction(CommonConstants.AUDITOR_WORK_FLOW_NEXT_CREDENTIAL_BUTTON_END_ACTION);
					powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
				}
			}
		}
		if(auditTask == null) {
			return credentialNotFound();
		}
		/*auditTask.setVendorId(5579);
		auditTask.setResourceId(2276);*/

		vendorInfo = powerAuditorWorkflowDelegate.getVendorInfo(auditTask.getVendorId());
		ActionContext.getContext().getSession().put("vendorId",auditTask.getVendorId().toString());
		ActionContext.getContext().getSession().put("resourceId",auditTask.getResourceId().toString());
		ActionContext.getContext().getSession().put("auditTask",auditTask);
		ActionContext.getContext().getSession().put("isFromPA","true");
		ServletActionContext.getRequest().getSession().setAttribute(POWER_AUDITOR_SESSION_FILTER,filtercriteria);
		ServletActionContext.getRequest().getSession().setAttribute("powerAuditorVendorInfo",vendorInfo);

		assumeProviderRole(auditTask);

		buildTabDto(auditTask.getVendorId().toString());

		//SL-20645 : modified the code to capture the start time and end time for auditing the task		
		if(getSecurityContext().isSlAdminInd()){
			Integer adminId	= getSecurityContext().getRoles().getVendBuyerResId();
			AuditTimeVO auditTimeVo=new AuditTimeVO();
			auditTimeVo.setAuditTaskId(auditTask.getAuditTaskId());
			auditTimeVo.setStartTime(new Date());
			auditTimeVo.setAgentName(getSecurityContext().getSlAdminUName());
			//auditTimeVo.setAgentId(adminId);
			//SL-20645:capturing the start time		
			Integer credId = null;
			if(null!= auditTask.getResourceId() && auditTask.getResourceId()!= 0){
				Integer id = auditTask.getResourceId();
				Integer credTypeId = auditTask.getResourceCredentialTypeId();
				Integer credCategoryId = auditTask.getResourceCredentialCategoryTypeId();
				credId = powerAuditorWorkflowDelegate.getResourceCredentialId(id,credTypeId,credCategoryId,auditTask.getAuditTaskId());
			}else{
				Integer id = auditTask.getVendorId();
				Integer credTypeId = auditTask.getVendorCredentialTypeId();
				Integer credCategoryId = auditTask.getVendorCredentialCategoryTypeId();
				credId = powerAuditorWorkflowDelegate.getVendorCredentialId(id,credTypeId,credCategoryId,auditTask.getAuditTaskId());
			}
			auditTimeVo.setCredId(credId);
			String auditTimeLoggingId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
			if(StringUtils.isNotBlank(auditTimeLoggingId) && StringUtils.isNumeric(auditTimeLoggingId)){
				auditTimeVo.setStartAction(CommonConstants.AUDIT_NEXT_CREDENTIAL_START_ACTION);
			}
			else{
				auditTimeVo.setStartAction(CommonConstants.AUDITOR_WORK_FLOW_START_ACTION);
			}
			AuditTimeVO auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);

			
			if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
			{
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeSaveVo.getAuditTimeLoggingId());
			}
		}
		return SUCCESS;
	}

	//Sl-20645 : method to capture the end time when user clicks on 'Close Audit window'
	public String closeWindowAudit() throws Exception {		

		String auditTimeLoggingId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
		try{
			if(getSecurityContext().isSlAdminInd()){
				if(StringUtils.isNotBlank(auditTimeLoggingId) && StringUtils.isNumeric(auditTimeLoggingId))
				{
					Integer auditTimeId=Integer.parseInt(auditTimeLoggingId);
					if(null!=auditTimeId)
					{
						//SL-20645:updating the table with end time
						AuditTimeVO auditVo=new AuditTimeVO();
						auditVo.setAuditTimeLoggingId(auditTimeId);
						auditVo.setEndTime(new Date());
						auditVo.setEndAction(CommonConstants.AUDITOR_WORK_FLOW_CLOSE_AUDIT_BUTTON_END_ACTION);
						powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
					}
				}
			}
		}
		catch(Exception e){
			logger.error("Error occured in the method closeWindowAudit()"+e.getMessage());
			logger.error("Error occured in the method closeWindowAudit() for the auditTimeLoggingId :"+auditTimeLoggingId);
		}
		return COULD_NOT_DISPLAY_RESULT;
	}

	//SL-20645 : modified the method to capture and set the auditTimeLoggingId  on page reload
	public String refreshPage()

	{
		if(getSecurityContext().isSlAdminInd()){
			String auditTimeLogId = ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);

			if(StringUtils.isNotBlank(auditTimeLogId) && StringUtils.isNumeric(auditTimeLogId)){
				auditTimeLoggingId = auditTimeLogId;
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			}
		}
		String nextUrl = null;
		if(ActionContext.getContext().getSession().get("auditTask")== null) {
			return credentialNotFound();
		}

		return SUCCESS;
	}

	public String doLoad() {

		//getting the auditLoggingId from request for passing to the next action
		String auditTimeLogId =  (String) ServletActionContext.getRequest().getAttribute(AUDIT_TIME_LOGGING_ID);

		if(StringUtils.isNotBlank(auditTimeLogId) && StringUtils.isNumeric(auditTimeLogId))
		{
			auditTimeLoggingId = auditTimeLogId;
		}

		String nextUrl = null;
		if(ActionContext.getContext().getSession().get("auditTask")== null) {
			return credentialNotFound();
		}
		AuditVO auditTask = (AuditVO)ActionContext.getContext().getSession().get("auditTask");
		if(auditTask.getAuditKeyId() != null && auditTask.getAuditKeyId() > 0 ) {
			int audittype = getTypeOfAudit(auditTask);         
			switch(audittype) {
			case 1:
				ActionContext.getContext().getSession().put("VendorCredId",auditTask.getAuditKeyId().toString());
				// nextUrl = "licensesAndCertActiondoLoad.action";
				return "loadLicenceandcert";
				//break;


			case 2: ActionContext.getContext().getSession().put("resourceCredId",auditTask.getAuditKeyId());
			//nextUrl = "teamCredentialActionloadCredentialDetails.action";
			return "loadTeamLicenceandcert";
			//break;
			case 3:
				ActionContext.getContext().getSession().put("VendorCredId",auditTask.getAuditKeyId().toString());
				//nextUrl = "loadInsuranceActiondoLoad.action";
				return "loadVendorInsurance";
				//break;
			default  : return credentialNotFound();
			}
		}

		else return credentialNotFound();

		//?id=" + auditTask.getAuditKeyId()
		//teamCredentialActionloadCredentialDetails
		//request.setAttribute("nextURL",nextUrl);

	}

	private int getTypeOfAudit(AuditVO audit) {
		//TODO do it right way using DB
		if(3 == audit.getAuditLinkId()) {
			if(audit.getVendorCredentialTypeId() != null && audit.getVendorCredentialTypeId() == 6) return 3;
			else return 1;
		}
		else if ( 4 == audit.getAuditLinkId()){
			return 2;
		}
		return -1;
	}

	private String credentialNotFound() {
		return COULD_NOT_DISPLAY_RESULT;
	}
	private  void buildTabDto(String vendorId) throws Exception {
		if (ActionContext.getContext().getSession().get("tabDto") == null ){
			ActionContext.getContext().getSession().put("tabDto", tabDto);

		}
		fetchProviderStatuses(vendorId);
	}
	private  void fetchProviderStatuses(String vendorId) throws Exception{

		Map activityStatus = activityRegistryDelegate.getProviderActivityStatus(vendorId);

		if (activityStatus != null) {
			for (Object key : activityStatus.keySet()) {
				boolean value = (Boolean)activityStatus.get(key);
				if (value)
					tabDto.getTabStatus().put((String)key, ActivityRegistryConstants.COMPLETE);
				else
					tabDto.getTabStatus().put((String)key, ActivityRegistryConstants.INCOMPLETE);
			}
		}

	}

	private void assumeProviderRole(AuditVO auditTask){
		//TODO

		//getSecurityContext().setAdminRoleId(getSecurityContext().getRoleId());
		getSecurityContext().setCompanyId(auditTask.getVendorId());
		getSecurityContext().setVendBuyerResId(auditTask.getResourceId());

		//getSecurityContext().setSlAdminUName(getSecurityContext().getUsername());
		//need buyer id to be null for provider

		getSecurityContext().setRoleId(OrderConstants.PROVIDER_ROLEID);
		//getSecurityContext().setUsername(adminVO.getUsername());
		//getSecurityContext().setPrimaryInd(true);

		getSecurityContext().setRole(MPConstants.ROLE_PROVIDER);
		getSession().setAttribute(SOConstants.SECURITY_KEY, getSecurityContext());

	}

	/*
	 * This will get  called to replace the stuff back to normal
	 */
	public String dismissProviderRole()
	{
		getSecurityContext().setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
		getSecurityContext().setUsername(getSecurityContext().getSlAdminUName());
		getSecurityContext().setRole(OrderConstants.NEWCO_ADMIN);
		//Refer to LoginAction where companyId is set to 99 by default
		getSecurityContext().setCompanyId(99);
		//Set by default
		getSecurityContext().setVendBuyerResId(-1);

		ActionContext.getContext().getSession().remove("vendorId");
		ActionContext.getContext().getSession().remove("resourceId");
		ActionContext.getContext().getSession().remove("auditTask");
		ActionContext.getContext().getSession().remove("isFromPA");
		return NONE;

	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	/**
	 * @return the powerAuditorWorkflowDelegate
	 */
	public IPowerAuditorWorkflowDelegate getPowerAuditorWorkflowDelegate() {
		return powerAuditorWorkflowDelegate;
	}

	/**
	 * @param powerAuditorWorkflowDelegate the powerAuditorWorkflowDelegate to set
	 */
	public void setPowerAuditorWorkflowDelegate(
			IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate) {
		this.powerAuditorWorkflowDelegate = powerAuditorWorkflowDelegate;
	}

	/**
	 * @return the tabDto
	 */
	public BaseTabDto getTabDto() {
		return tabDto;
	}

	/**
	 * @param tabDto the tabDto to set
	 */
	public void setTabDto(BaseTabDto tabDto) {
		this.tabDto = tabDto;
	}

	/**
	 * @return the activityRegistryDelegate
	 */
	public IActivityRegistryDelegate getActivityRegistryDelegate() {
		return activityRegistryDelegate;
	}

	/**
	 * @param activityRegistryDelegate the activityRegistryDelegate to set
	 */
	public void setActivityRegistryDelegate(
			IActivityRegistryDelegate activityRegistryDelegate) {
		this.activityRegistryDelegate = activityRegistryDelegate;
	}


	public String getAuditTimeLoggingId() {
		return auditTimeLoggingId;
	}

	public void setAuditTimeLoggingId(String auditTimeLoggingId) {
		this.auditTimeLoggingId = auditTimeLoggingId;
	}

}
