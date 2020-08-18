package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_LOGGING_ID;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.newco.marketplace.business.iBusiness.provider.IAuditStates;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.IInsuranceDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.ajax.AjaxRequestModelDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.common.CommonConstants;


/**
 * 
 * $Revision: 1.9 $ $Author: glacy $ $Date: 2008/04/26 01:13:50 $
 *
 */
public class AllTabViewAction extends SLAuditableBaseAction 
							 implements SessionAware,
							 			ServletResponseAware,
							 			ModelDriven<AjaxRequestModelDTO> {

	private static final Logger logger = Logger.getLogger(AllTabViewAction.class.getName());
	private static final long serialVersionUID = -982300357609440834L;
	private AjaxRequestModelDTO theModel = new AjaxRequestModelDTO();
	private IActivityRegistryDelegate activityRegistryDelegate;
	private IInsuranceDelegate insuranceDelegate;
	private BaseTabDto tabDto;
	private String tabView;
	private HttpServletResponse response;
	private HttpSession session;
	private String tabType = null;
	private String nexturl = null;
	
	private Map sSessionMap;
	
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

	
	/**
	 * @param activityRegistryDelegate
	 * @param tabDto
	 */
	public AllTabViewAction(IActivityRegistryDelegate activityRegistryDelegate,
			BaseTabDto tabDto, IInsuranceDelegate insuranceDelegate) {
		this.activityRegistryDelegate = activityRegistryDelegate;
		this.tabDto = tabDto;
		this.insuranceDelegate = insuranceDelegate;
	}

	/**
	 * @param tabDto
	 */
	public AllTabViewAction(BaseTabDto tabDto) {
		super();
		this.tabDto = tabDto;
	}
	
	public String getTabType() {
		return tabType;
	}
	public void setTabType(String tabType) {
		this.tabType = tabType;
	}
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		
		//Code added as part of Jira SL-20645 -To capture time while auditing insurance
		auditTimeLoggingId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		
		//Code added as part of Jira SL-20645 -To capture time while auditing insurance
		String auditLoggingId =  (String) ServletActionContext.getRequest().getParameter(AUDIT_LOGGING_ID);
		
		if(StringUtils.isNotBlank(auditLoggingId) && StringUtils.isNumeric(auditLoggingId))
		{
			Integer auditTimeId=Integer.parseInt(auditLoggingId);

			Date date = new Date();

			if(null!=auditTimeId && null!=date)
			{
				//SL-20645:updating 
				AuditTimeVO auditVo=new AuditTimeVO();
				auditVo.setAuditTimeLoggingId(auditTimeId);
				auditVo.setEndTime(date);
				auditVo.setEndAction(CommonConstants.INSURANCE_CLOSE_END_ACTION);
				powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
			}
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditLoggingId);

		}

		getUpdateProviderStatus();
		
		// new service live admin feature
		// determine is SLAdmin features are enabled
		determineSLAdminFeature();
		
		if (ActionContext.getContext().getSession().get("tabDto") == null || tabView == null){
			ActionContext.getContext().getSession().put("tabDto", tabDto);
			System.out.println("inside here after setting tabdto"+ActionContext.getContext().getSession().get("tabDto"));
			String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
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
		// calling the method to refresh the sidewar status
		refreshProviderStatus();		
		ActionContext.getContext().getSession().put(OrderConstants.CURRENT_TAB , tabView);				
		tabDto = (BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Expires", "0"); 
		return SUCCESS;
	}
	
	private void getUpdateProviderStatus(){
		setContextDetails();
		
		String resourceId = getSecurityContext().getVendBuyerResId().toString();
		String vendorId = getSecurityContext().getCompanyId().toString();
		ActionContext.getContext().getSession().put("resourceId", resourceId);
		/**
		 * Added to include Provider Status.
		 */
		String status;
		try {
			ActionContext.getContext().getSession().put("providerStatus", activityRegistryDelegate.getProviderStatus(vendorId));
			ActionContext.getContext().getSession().put("resourceStatus", activityRegistryDelegate.getResourceStatus(resourceId));
			
			status = activityRegistryDelegate.getVendorResourceStateStatus(resourceId);
			ActionContext.getContext().getSession().put("resourceState", status);
			
			if ( status == null || status.trim().length() == 0 || IAuditStates.RESOURCE_INCOMPLETE.equals(status) || IAuditStates.RESOURCE_OUT_OF_COMPLIANCE.equals(status) ) {
				ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_NEW);
				logger.info("getUpdateProviderStatus - addUser loop");
			} else {
				ActionContext.getContext().getSession().put("userStatus", ActivityRegistryConstants.USER_STATUS_EDIT);
				logger.info("getUpdateProviderStatus - editUser loop");
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
	}
	/**
	 * @Desciption: We are checking the status of tab status and if all tab are complete then settign the side war indicator as complete 
	 */
	private void refreshProviderStatus() {		
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		
		boolean businessinfoStatus = false;
		boolean licenceStatus = false;
		boolean insuranceStatus = false;
		boolean warrantyStatus = false;
		boolean termsAndCondStatus = false;
		
		if (baseTabDto!= null) {
			businessinfoStatus = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.BUSINESSINFO));
			licenceStatus = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.LICENSE));
			insuranceStatus = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.INSURANCE));
			warrantyStatus = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.WARRANTY));
			termsAndCondStatus = ActivityRegistryConstants.COMPLETE.equals(baseTabDto.getTabStatus().get(ActivityRegistryConstants.TERMSANDCOND));			
		}
		String isInformationPopup="";
		try {
			isInformationPopup = insuranceDelegate.isFirstTimeVisit(getSecurityContext().getCompanyId());
		}catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		if("0".equals(isInformationPopup)){	
			ActionContext.getContext().getSession().put(OrderConstants.INSURANCE_STATUS,OrderConstants.INSURANCE_STATUS_INCOMPLETE);			
		}
		if (businessinfoStatus && licenceStatus && insuranceStatus && warrantyStatus && termsAndCondStatus)
			ActionContext.getContext().getSession().put("providerStatus",ActivityRegistryConstants.COMPLETE);
		else	
			ActionContext.getContext().getSession().put("providerStatus",ActivityRegistryConstants.INCOMPLETE);		
	}
	/**
	 * @return the tabView
	 */
	public String getTabView() {
		return tabView;
	}
	/**
	 * @param tabView the tabView to set
	 */
	public void setTabView(String tabView) {
		this.tabView = tabView;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	/**
	 * @return the tabDto
	 */
	public BaseTabDto getTabDto() {
		return tabDto;
	}
	
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the nexturl
	 */
	public String getNexturl() {
		return nexturl;
	}

	/**
	 * @param nexturl the nexturl to set
	 */
	public void setNexturl(String nexturl) {
		this.nexturl = nexturl;
	}

	
	public void setSession(Map ssessionMap) {
		// TODO Auto-generated method stub
		this.sSessionMap=ssessionMap;		
	}	

	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public AjaxRequestModelDTO getModel() {
		return this.theModel;
	}
	
	public void setModel(AjaxRequestModelDTO theModel) {
		this.theModel = theModel;
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
	
}