package com.newco.marketplace.web.action.provider;

import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.delegates.provider.ITeamCredentialDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.TeamCredentialsDto;
import com.newco.marketplace.web.utils.FileUpload;
import com.newco.marketplace.web.utils.FileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.common.CommonConstants;
import com.thoughtworks.xstream.XStream;

/** 
 * $Revision: 1.36 $ $Author: glacy $ $Date: 2008/04/26 01:13:50 $
 */
public class TeamCredentialAction extends SLAuditableBaseAction implements SessionAware,
ServletRequestAware,
ServletResponseAware,
Preparable {

	private static final long serialVersionUID = -6490452868982970060L;
	private ITeamCredentialDelegate iTeamCredentialDelegate;
	private IAuditLogDelegate auditLogDelegates;
	private TeamCredentialsDto teamCredentialsDto;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private static final Logger logger = Logger
			.getLogger(TeamCredentialAction.class.getName());
	private String method;
	private SecurityContext securityContext;
	private HttpSession session;
	private int resourceCredId = 0;
	private boolean editFlag = false;
	private Map sSessionMap;

	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;	
	Integer auditTimeLoggingId;


	/*changes for file upload -Starts */
	private FileUpload fileUpload = null;
	private static final String FILE_FIELD_NAME = "teamCredentialsDto.file";
	/*changes for file upload -Ends */

	public TeamCredentialAction(TeamCredentialsDto teamCredentialsDto,ITeamCredentialDelegate iTeamCredentialDelegate){
		this.teamCredentialsDto=teamCredentialsDto;
		this.iTeamCredentialDelegate=iTeamCredentialDelegate;
	}


	/***************************************************************************
	 * Loads the Credentials Lists
	 * 
	 * @return
	 * @throws Exception
	 */

	@Override
	public void validate() {
		super.validate();
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");

			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"errorOn");
		}
	}


	@SkipValidation
	public String loadCredentialList() throws Exception {
		try {
			if((String)getSessionMap().get("vendorId")!=null){
				teamCredentialsDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
			}
			getSessionMessages();
			setContextDetails();
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");

			// General Info Action has to set this into session
			if(resourceId!=null){
				teamCredentialsDto.setResourceId((new Integer(resourceId)).intValue());
			}
			teamCredentialsDto = iTeamCredentialDelegate
					.getCredentialList(teamCredentialsDto);
			if (hasFieldErrors()) {
				teamCredentialsDto.setPassCredentials(false);
			}

			if (teamCredentialsDto != null
					&&	teamCredentialsDto.getCredentialsList() != null)
			{
				teamCredentialsDto.setSize(teamCredentialsDto.getCredentialsList().size());
			}
			else
			{
				teamCredentialsDto.setSize(0);
			}

		} catch (DelegateException dle) {
			logger
			.info("Exception Occurred at TeamCredentialAction.loadCredentialList "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.loadCredentialList "
					+ dle.getMessage());
			return ERROR;
		}

		return "ShowCredentialList";
	}
	@SkipValidation
	public String addCredentilDetails() throws Exception{
		teamCredentialsDto.setResourceCredId(0);
		ActionContext.getContext().getSession().put("resourceCredId",resourceCredId);
		ActionContext.getContext().getSession().put("teamCredentialsDto",new TeamCredentialsDto());
		logger.debug("------ActionContext.getContext().getSession().put('teamCredentialsDto'-----"+((TeamCredentialsDto)ActionContext.getContext().getSession().get("teamCredentialsDto")).getResourceCredId());
		teamCredentialsDto=new TeamCredentialsDto();
		resourceCredId=0;
		editFlag=false;
		return "TabShowCredentialList";
	}
	@SkipValidation
	//sL-20645 : modified the method to capture the start time of audit the task of provider credential
	public String editCredentilDetails() throws Exception{

		ActionContext.getContext().getSession().put("resourceCredId",resourceCredId);
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		Integer tempId =(Integer)ActionContext.getContext().getSession().get("resourceCredId");
		//checks whether admin has logged in
		if(securityContext.isSlAdminInd() && null !=tempId){			
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			Integer auditLinkId = AuditStatesInterface.PROVIDER_AUDIT_LINK_ID;
			boolean userInd = true;
			Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(resourceId,userInd,tempId,auditLinkId);		

			AuditTimeVO auditTimeVo=new AuditTimeVO();
			auditTimeVo.setAuditTaskId(auditTaskId);
			auditTimeVo.setStartTime(new Date());
			auditTimeVo.setAgentName(securityContext.getSlAdminUName());
			auditTimeVo.setStartAction(CommonConstants.TEAM_CREDENTIAL_START_ACTION);
			//Inserting the start time in the table
			auditTimeVo.setCredId(tempId);
			AuditTimeVO auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);

			if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
			{
				//setting the generated auditLoggingId in request
				auditTimeLoggingId = auditTimeSaveVo.getAuditTimeLoggingId();
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			}
		}
		//setContextDetails();
		//determineSLAdminFeature();
		return "TabShowCredentialList";
	}
	/***************************************************************************
	 * Loads the Credential Details From DB
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	//SL-20645 : modified the method to capture the end time of the audit task of provider credentail
	public String loadCredentialDetails() throws Exception {

		try {
			String auditTimeLogId = (String)ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);		
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLogId);

			//SL-21003 : code added to capture the start time while adding a new team credential
			if(getSecurityContext().isSlAdminInd() && StringUtils.isBlank(auditTimeLogId) && StringUtils.isEmpty(auditTimeLogId) ){
				
				AuditTimeVO auditTimeVo=new AuditTimeVO();

				auditTimeVo.setStartTime(new Date());
				auditTimeVo.setAgentName(getSecurityContext().getSlAdminUName());
				//SL-20645:capturing the start time				
				auditTimeVo.setStartAction(CommonConstants.TEAM_MEMBER_ADD_CREDENTIAL);			
				AuditTimeVO auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);

				if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
				{
					auditTimeLoggingId = auditTimeSaveVo.getAuditTimeLoggingId();
					ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID,auditTimeLoggingId);
				}
			}



			if((String)getSessionMap().get("vendorId")!=null){
				teamCredentialsDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
			}

			if(resourceCredId==0){
				Integer tempCredId=(Integer)ActionContext.getContext().getSession().get("resourceCredId");
				if(tempCredId!=null){
					resourceCredId= tempCredId.intValue();
				}
			}
			if (resourceCredId != 0) {
				editFlag = true;
			}else{
				teamCredentialsDto=new TeamCredentialsDto();
			}
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if(resourceId!=null){
				teamCredentialsDto.setResourceId((new Integer(resourceId)).intValue());
			}
			teamCredentialsDto.setResourceCredId(resourceCredId);
			//If you don't have Field error get all the details else 
			// get only the drop down details
			if(doInput()){	
				teamCredentialsDto = iTeamCredentialDelegate.getCredentialDetails(teamCredentialsDto);
			}else{
				teamCredentialsDto=iTeamCredentialDelegate.loadListValues(teamCredentialsDto);
			}


			//add for header
			String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
			teamCredentialsDto.setFullResoueceName(resourceName);

		} catch (DelegateException dle) {
			logger
			.info("Exception Occurred at TeamCredentialAction.loadCredentialDetails "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.loadCredentialDetails "
					+ dle.getMessage());
			return ERROR;
		}
		determineSLAdminFeature();
		return "ShowCredentialDetails";
	}

	@SkipValidation
	public String changeCredentialCatType() throws Exception{
		ActionContext.getContext().getSession().put("resourceCredId",resourceCredId);
		ActionContext.getContext().getSession().put("editFlag",editFlag);
		ActionContext.getContext().getSession().put("teamCredentialsDto",teamCredentialsDto);
		//SL-21003 : adding code to save the audit time logging id to request attribute
		String auditTimeLogId=  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
		if(StringUtils.isBlank(auditTimeLogId)){
			auditTimeLogId =teamCredentialsDto.getAuditTimeLoggingIdNew();
		}
		if(StringUtils.isNotBlank(auditTimeLogId) && StringUtils.isNumeric((auditTimeLogId))){
		auditTimeLoggingId =Integer.parseInt(auditTimeLogId);
		}
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		
		return "TabChangeCredentialType";
	}
	/***************************************************************************
	 * Loads the Credential Details From DB
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String changeCredCatType() throws Exception {
		try {
			int resourceCrdId=0;
			setContextDetails();
			teamCredentialsDto=(TeamCredentialsDto)ActionContext.getContext().getSession().get("teamCredentialsDto");
			if(teamCredentialsDto.getResourceCredId()>0){
				editFlag=true;
				resourceCrdId=teamCredentialsDto.getResourceCredId();
			}
			teamCredentialsDto.setResourceCredId(resourceCredId);
			teamCredentialsDto = iTeamCredentialDelegate.getCatListByTypeId(teamCredentialsDto);
			teamCredentialsDto.setResourceCredId(resourceCrdId);
			resourceCredId=resourceCrdId;
		} catch (DelegateException dle) {
			logger
			.info("Exception Occurred at TeamCredentialAction.changeCredCatType "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.changeCredCatType "
					+ dle.getMessage());
			return ERROR;
		}
		return "ShowCredentialDetails";
	}

	@SkipValidation
	public String doNextOnCredentialList() throws Exception {
		if(saveNoCred().equals("true")){
			return "ShowBackGroundDetails";
		}else{
			return "ShowCredentialListTab";
		}

	}
	@SkipValidation
	public String doPrevOnCredentialList() throws Exception {
		if(saveNoCred().equals("true")){
			return "ShowSkillDetails";
		}else{
			return "ShowCredentialListTab";
		}
	}


	/**
	 * GGanapathy
	 * Used to update profile after saving the Licences & Certification page
	 * @return String
	 * @throws Exception
	 */	
	public String updateProfile() throws Exception
	{
		if(saveNoCred().equals("true")){
			return "updateProfile";
		}else{
			return "ShowCredentialListTab";
		}

	}
	@SkipValidation
	public String checkCredentialList() throws Exception {

		try
		{
			setContextDetails();
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");

			if(resourceId!=null){
				teamCredentialsDto.setResourceId((new Integer(resourceId)).intValue());
			}

			String chkExists = request.getParameter("checkboxExists");
			if(chkExists != null && "true".equals(chkExists)){
				if(request.getParameter("chkDonotWant") != null){
					teamCredentialsDto.setPassCredentials(true);
				}else{
					teamCredentialsDto.setPassCredentials(false);
				}

				iTeamCredentialDelegate.saveNoCred(teamCredentialsDto);
				auditUserProfileLog(teamCredentialsDto);
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				if (baseTabDto!=null) {
					/**
					 * Added to Update Incomplete status.
					 * Fix for Sears00045965.
					 */
					if (teamCredentialsDto.isPassCredentials())
						baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"complete");
					else
						baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"incomplete");

					baseTabDto.getFieldsError().clear();
					getFieldErrors().clear();
				}
			}
		}catch(DelegateException dle)
		{
			logger
			.info("Exception Occurred at TeamCredentialAction.checkCredentialList "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.checkCredentialList "+ dle.getMessage());
			return ERROR;
		}
		return "ShowCredentialListTab";
	}

	/***************************************************************************
	 * Saves the no credential check box value details to DB
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String saveNoCred() throws Exception {
		try {
			setContextDetails();
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");

			// General Info Action has to set this into session
			if(resourceId!=null){
				teamCredentialsDto.setResourceId((new Integer(resourceId)).intValue());
			}

			String chkExists=request.getParameter("checkboxExists");
			//if the chkExists=true means check box exists else
			// it does not the if the user selected the check box then
			// it should be available in the parameter otherwise it will be null
			// throw the error messgae
			if(chkExists!=null&&"true".equals(chkExists)){
				if(request.getParameter("chkDonotWant")!=null){
					teamCredentialsDto.setPassCredentials(true);
					iTeamCredentialDelegate.saveNoCred(teamCredentialsDto);
					auditUserProfileLog(teamCredentialsDto);
					BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
					if (baseTabDto!=null) {
						baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"complete");
						baseTabDto.getFieldsError().clear();
						getFieldErrors().clear();
					}
					return "true";
				}else{
					addFieldError("teamCredentialsDto.passCredentials.error", "Please select I do not wish to add any licenses or certifications at this time.");
					BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
					baseTabDto.setDtObject(teamCredentialsDto);
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"errorOn");
					return "false";
				}
			}
		} catch (DelegateException dle) {
			logger
			.info("Exception Occurred at TeamCredentialAction.saveNoCred "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.saveNoCred "
					+ dle.getMessage());
			return ERROR;
		}
		return "true";
	}

	private void auditUserProfileLog(TeamCredentialsDto teamCredentialsDto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {TeamCredentialsDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(teamCredentialsDto);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_USER_PROFILE_EDIT");
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

	public String doCancelCredentialDetail() throws Exception {
		return "ShowCredentialListTab";
	}

	public String doPrevOnCredentialDetails() throws Exception {
		saveNoCred();
		return "ShowCredentialList";
	}

	/**
	 * Saves the Crdential type Details To DB
	 * 
	 * @throws Exception
	 */
	//Sl-20645 : modified the method to capture the end time in case if auditing the provider credential
	public String saveCredentilDetail() throws Exception {

		String auditTimeLogId =  (String)ServletActionContext.getRequest().getAttribute(AUDIT_TIME_LOGGING_ID);	
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLogId);
		if(StringUtils.isNotBlank(teamCredentialsDto.getAuditTimeLoggingIdNew())&&StringUtils.isNumeric(teamCredentialsDto.getAuditTimeLoggingIdNew())){
			auditTimeLoggingId =Integer.parseInt(teamCredentialsDto.getAuditTimeLoggingIdNew()) ;
		}
		//variable added to check whether new credential or existing credential
		boolean credIdPresent= false;
		
		String return_val = "input";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		try {
			if((String)getSessionMap().get("vendorId")!=null){
				teamCredentialsDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
			}
			setContextDetails();
			validateForm();
			validateBeforeDate();
			if (hasFieldErrors()) {
				logger.debug("Has Fields Erorrr");
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(teamCredentialsDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"errorOn");
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
				return return_val;
			}

			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if(resourceId!=null){
				teamCredentialsDto.setResourceId((new Integer(resourceId)).intValue());
			}
			/*changes for file upload -Starts */
			teamCredentialsDto = getUploadFiles(teamCredentialsDto);
			/*changes for file upload -Ends */
			
			//SL-21003 : checking for the new credential or existing credential
			if(teamCredentialsDto.getResourceCredId()!=0){
				credIdPresent=true;
			}
			
			teamCredentialsDto = iTeamCredentialDelegate.commitCredentialData(teamCredentialsDto);
			auditUserProfileLog(teamCredentialsDto);
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			if (baseTabDto!=null) {
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"complete");
				baseTabDto.getFieldsError().clear();
				getFieldErrors().clear();
			}
		} catch (DelegateException dle) {
			logger
			.info("Exception Occurred at TeamCredentialAction.loadCredentialDetails "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.loadCredentialDetails "
					+ dle.getMessage());
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			return ERROR;
		}

		return_val = "ShowCredentialListTab";
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}
		//SL-20645:updating 
		if(StringUtils.isNotBlank(teamCredentialsDto.getAuditTimeLoggingIdNew()) && StringUtils.isNumeric(teamCredentialsDto.getAuditTimeLoggingIdNew()))
		{
			Integer auditTimeId=Integer.parseInt(teamCredentialsDto.getAuditTimeLoggingIdNew());
			if(null!=auditTimeId)
			{
				//SL-20645:updating 
				AuditTimeVO auditVo=new AuditTimeVO();
				auditVo.setAuditTimeLoggingId(auditTimeId);
				auditVo.setEndTime(new Date());
				if(credIdPresent){
					auditVo.setEndAction(CommonConstants.TEAM_CREDENTIAL_END_ACTION);
				}
				else{
					auditVo.setEndAction(CommonConstants.TEAM_MEMBER_SAVE_CREDENTIAL);
					auditVo.setCredId(teamCredentialsDto.getResourceCredId());
					Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(String.valueOf(teamCredentialsDto.getResourceId()),true,teamCredentialsDto.getResourceCredId(),AuditStatesInterface.PROVIDER_AUDIT_LINK_ID);
					auditVo.setAuditTaskId(auditTaskId);
				}
				powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
			}
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		}
		return return_val;
	}

	@SkipValidation
	public String removeCredentilDetail() throws Exception {
		String return_val = "ShowCredentialListTab";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		try {
			if((String)getSessionMap().get("vendorId")!=null){
				teamCredentialsDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
			}
			setContextDetails();
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if(resourceId!=null){
				teamCredentialsDto.setResourceId((new Integer(resourceId)).intValue());
			}
			teamCredentialsDto = iTeamCredentialDelegate
					.deleteCredentialData(teamCredentialsDto);

			/**
			 * Added for showing 'Incomplete' State for zero credentials after removing
			 * G.Ganapathy
			 *
			 */
			teamCredentialsDto = iTeamCredentialDelegate
					.getCredentialList(teamCredentialsDto);
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			if(teamCredentialsDto.getCredentialsList() != null && teamCredentialsDto.getCredentialsList().size()==0){
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"incomplete");
			}
		} catch (DelegateException dle) {
			logger
			.info("Exception Occurred at TeamCredentialAction.loadCredentialDetails "
					+ dle.getMessage());
			addActionError("Exception Occurred at TeamCredentialAction.loadCredentialDetails "
					+ dle.getMessage());
			return ERROR;
		}
		return return_val;
	}

	@SkipValidation
	public String removeDocument() throws Exception
	{
		String return_val = "input";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		try
		{
			String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if(resourceId!=null){
				teamCredentialsDto.setResourceId(Integer.parseInt(resourceId));
			}

			if(resourceCredId == 0){
				Integer tempCredId=(Integer)ActionContext.getContext().getSession().get("resourceCredId");
				if(tempCredId!=null){
					resourceCredId = tempCredId.intValue();
				}
			}
			teamCredentialsDto.setResourceCredId(resourceCredId);

			iTeamCredentialDelegate.removeDocumentDetails(teamCredentialsDto);
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			return ERROR;
		}

		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
		if (baseTabDto != null && baseTabDto.getActionMessages() != null)
			baseTabDto.getActionMessages().clear();

		baseTabDto.getActionMessages().addAll(getActionMessages());
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"complete");

		return return_val;
	}

	@SkipValidation
	public String displayTheDocument() throws Exception {

		if((String)getSessionMap().get("vendorId")!=null){
			teamCredentialsDto.setVendorId(new Integer((String)getSessionMap().get("vendorId")).intValue());
		}
		String documentId = request.getParameter("docId");
		if (documentId != null)
		{
			teamCredentialsDto.setCredentialDocumentId(Integer.parseInt(documentId));
			teamCredentialsDto = iTeamCredentialDelegate.viewDocumentDetails(teamCredentialsDto);
			request.getSession().setAttribute("teamCredentialsDto", teamCredentialsDto);
			RequestDispatcher aRequestDispatcher = request
					.getRequestDispatcher("/jsp/providerRegistration/modules/display_resourceDocument.jsp");
			aRequestDispatcher.forward(request, response);
		}		
		return null;
	}

	@SkipValidation
	public String attachDocument() throws Exception
	{
		String return_val = "input";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		TeamCredentialsDto credentialsDto = getUploadFiles(teamCredentialsDto);

		if (credentialsDto == null 
				|| 	credentialsDto.getCredentialDocumentFileName() == null
				||	credentialsDto.getCredentialDocumentFileName().trim().length() <= 0)
		{
			addFieldError("teamCredentialsDto.file", "File not found. Please try again");
		}

		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(teamCredentialsDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
			return return_val;
		}

		int reourceCredID = 0;
		saveCredentilDetail();

		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(teamCredentialsDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"errorOn");
			return return_val;
		}

		if (teamCredentialsDto != null)
		{
			reourceCredID = teamCredentialsDto.getResourceCredId();
			ActionContext.getContext().getSession().put("resourceCredId",reourceCredID);
		}
		else
		{
			addActionError("Error while attaching the document. Please try again");

			return_val = "ShowCredentialListTab";
			if ("true".equals(isFromPA))
			{
				return_val = "returnToPA";
			}

			return "ShowCredentialListTab";
		}

		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
		if (baseTabDto != null && baseTabDto.getActionMessages() != null)
			baseTabDto.getActionMessages().clear();

		baseTabDto.getActionMessages().addAll(getActionMessages());
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.INSURANCE,"complete");

		return_val = "input";
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		return return_val;
	}

	public void setServletResponse(HttpServletResponse arg0)
	{
		response=arg0;
	}
	public ITeamCredentialDelegate getITeamCredentialDelegate() {
		return iTeamCredentialDelegate;
	}

	public void setITeamCredentialDelegate(
			ITeamCredentialDelegate teamCredentialDelegate) {
		iTeamCredentialDelegate = teamCredentialDelegate;
	}

	public TeamCredentialsDto getTeamCredentialsDto() {
		return teamCredentialsDto;
	}

	public void setTeamCredentialsDto(TeamCredentialsDto teamCredentialsDto) {
		this.teamCredentialsDto = teamCredentialsDto;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void prepare() throws Exception {

	}

	public String execute() throws Exception
	{	
		String action = teamCredentialsDto.getAction();
		if("Update".equals(action)){
			return updateProfile();
		}
		else
		{
			return updateProfile();
		}

	}



	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;		
	}	
	public Map getSessionMap() {
		return this.sSessionMap;		
	}

	public int getResourceCredId() {
		return resourceCredId;
	}

	public void setResourceCredId(int resourceCredId) {
		this.resourceCredId = resourceCredId;
	}

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	private void validateBeforeDate(){
		String startDate=teamCredentialsDto.getIssueDate();
		String endDate=teamCredentialsDto.getExpirationDate();
		Date dateStart=null;
		Date dateEnd=null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag=false;
		if(startDate==null||startDate.trim().length()==0){
			addFieldError("teamCredentialsDto.issueDate", "Credential Issue Date is required.");
		}
		else{
			try{
				dateStart = (Date)formatter.parse(startDate);

			}catch(Exception ex){
				addFieldError("teamCredentialsDto.issueDate", "Credential Issue Date should be in MM/dd/yy format.");
			}
		}
		if(endDate!=null&&endDate.trim().length()>0){
			flag=true;
			try{
				dateEnd = (Date)formatter.parse(endDate);
				flag=true;
			}catch(Exception ex){
				addFieldError("teamCredentialsDto.expirationDate", "Credential Expiration Date  should be in MM/dd/yy format.");
			}
			if(flag){
				try{
					if(dateStart.compareTo(dateEnd)>0) {       
						addFieldError("teamCredentialsDto.expirationDate", "Credential Expiration Date  should be greater than Credential Issue Date.");
					}
				}catch(Exception ex){
					logger.debug("Cant Parse ---EndDate");
				}
			}//if(flag){
		}//end of 	if(endDate!=null&&endDate.trim().length()>0){
	}

	private void validateForm(){
		if(teamCredentialsDto.getTypeId()<=0){
			addFieldError("teamCredentialsDto.typeId", "Credential Type id is required.");
		}
		if(teamCredentialsDto.getCategoryId()<=0){
			addFieldError("teamCredentialsDto.categoryId", "Credential Category id is required.");
		}

		if(teamCredentialsDto.getLicenseName()==null||teamCredentialsDto.getLicenseName().trim().length()==0){
			addFieldError("teamCredentialsDto.licenseName", "License name id is required.");
		}

		if(teamCredentialsDto.getTypeId() == 3 && teamCredentialsDto.getCategoryId() == 24){
			String credentialNumber = teamCredentialsDto.getCredentialNumber();
			if(credentialNumber == null || credentialNumber.trim().length()==0)
				addFieldError("teamCredentialsDto.credentialNumber", "Credential Number is required.");

		}

	}

	private void getSessionMessages(){
		logger.debug("--------------------------Error getSessionMessages-------");
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap());
			setActionErrors(baseTabDto.getActionErrors());
			baseTabDto.setActionErrors(new ArrayList<String>());
			setActionMessages(baseTabDto.getActionMessages());
			baseTabDto.setActionMessages(new ArrayList<String>());
		}
	}
	/*changes for file upload -Starts */
	private TeamCredentialsDto getUploadFiles(TeamCredentialsDto teamCredentialsDto) throws Exception{

		MultiPartRequestWrapper multiPartRequestWrapper  = (MultiPartRequestWrapper) ServletActionContext.getRequest();

		if (multiPartRequestWrapper != null)
		{
			logger.debug("----------- Inside Document IF - multiPartRequestWrapper !null");
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

					teamCredentialsDto.setCredentialDocumentBytes(fileUpload.getCredentialDocumentBytes());
					teamCredentialsDto.setCredentialDocumentExtention(fileUpload.getCredentialDocumentExtention());
					String fileExtn = FileUtils.getFileExtension(fileUpload.getCredentialDocumentFileName());

					String contentType = teamCredentialsDto.getCredentialDocumentExtention(); 

					if (contentType == null)
					{
						addFieldError("teamCredentialsDto.extension.error", "Preferred file types include: JPG | PDF | DOC | GIF ");
						if (hasFieldErrors()) {
							BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
							baseTabDto.setDtObject(teamCredentialsDto);
							baseTabDto.getFieldsError().putAll(getFieldErrors());
							baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_CREDENTIALS,"errorOn");
						}
						return teamCredentialsDto;
					}

					teamCredentialsDto.setCredentialDocumentFileName(fileUpload.getCredentialDocumentFileName());
					teamCredentialsDto.setCredentialDocumentFileSize(fileUpload.getCredentialDocumentFileSize());
					return teamCredentialsDto;
				}
			}
		}

		return teamCredentialsDto;

	}
	/*changes for file upload -Ends */

	@SkipValidation
	public boolean doInput() throws Exception {
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		TeamCredentialsDto credentialsDto = new TeamCredentialsDto();
		if (baseTabDto!= null) {
			setFieldErrors(baseTabDto.getFieldsError());
			baseTabDto.setFieldsError(new HashMap()); 
			if (!getFieldErrors().isEmpty())
			{	
				teamCredentialsDto = (TeamCredentialsDto)baseTabDto.getDtObject();

				//If there is any error, get the document details from DB.
				credentialsDto.setResourceCredId(teamCredentialsDto.getResourceCredId());
				credentialsDto.setResourceId(teamCredentialsDto.getResourceId());
				credentialsDto = iTeamCredentialDelegate.getCredentialDetails(credentialsDto);
				if (credentialsDto != null)
				{
					//Set the document related details from temp dto to existing dto
					teamCredentialsDto.setCredentialDocumentBytes(credentialsDto.getCredentialDocumentBytes());
					teamCredentialsDto.setCredentialDocumentExtention(credentialsDto.getCredentialDocumentExtention());
					teamCredentialsDto.setCredentialDocumentFileName(credentialsDto.getCredentialDocumentFileName());
					teamCredentialsDto.setCredentialDocumentFileSize(credentialsDto.getCredentialDocumentFileSize());
					teamCredentialsDto.setCredentialDocumentId(credentialsDto.getCredentialDocumentId());
				}
			}
		}
		if (getFieldErrors().isEmpty())
			return true;
		else
			return false;
	}


	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}


	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
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
/*
 * Maintenance History
 * $Log: TeamCredentialAction.java,v $
 * Revision 1.36  2008/04/26 01:13:50  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.33.6.1  2008/04/01 22:04:23  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.34  2008/03/27 18:58:06  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.33.10.4  2008/03/25 18:05:49  dmill03
 * complete sl admin features
 *
 * Revision 1.33.10.3  2008/03/19 13:19:37  dmill03
 * dmill03 check-point check in
 *
 * Revision 1.33.10.2  2008/03/11 23:06:29  dmill03
 * updated for auditor functions
 *
 * Revision 1.33.10.1  2008/03/11 14:47:42  dmill03
 * added enable switch to show auditor widgets
 *
 * Revision 1.33  2008/02/26 18:18:06  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.31.2.3  2008/02/25 19:28:26  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */