 
package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.web.delegates.provider.IActivityRegistryDelegate;
import com.newco.marketplace.web.delegates.provider.ITermsDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.TermsDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

 /**
  * $Revision: 1.21 $ $Author: gjacks8 $ $Date: 2008/05/29 16:24:10 $
  */

  @Validation
 public class TermsAction extends ActionSupport {

	private static final long serialVersionUID = 2431387510010312910L;
	private static final Logger logger = Logger.getLogger(TermsAction.class.getName());
	private ITermsDelegate iTermsDelegate;
	private IActivityRegistryDelegate iActivityRegistryDelegate;
    private TermsDto termsDto;
    private String method;
    private SecurityContext securityContext;
    private HttpSession session;

	@Override
 	public void validate() {
 		if((getMethod()==null) || !(getMethod().equals("cancel"))) {
	 		super.validate();
	 		if(hasFieldErrors()){
	 		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
	 		baseTabDto.setDtObject(termsDto);
	 		baseTabDto.getFieldsError().putAll(getFieldErrors());
	 		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND,"errorOn");
 			}
 		}
 	}

 	/**
 	 * 
 	 * @param termsDelegate
 	 * @param termsDto
 	 */
 	public TermsAction(ITermsDelegate termsDelegate, TermsDto termsDto, IActivityRegistryDelegate iActivityRegistryDelegate) {
 		iTermsDelegate = termsDelegate;
 		this.termsDto = termsDto;
 		this.iActivityRegistryDelegate = iActivityRegistryDelegate; 
 	}
 	
 	public String execute() throws Exception {
 		String action = termsDto.getAction();
 		if("Save".equals(action)){
 			return doSave();
 		}
 		if("Previous".equals(action)){
 			return doprevious();
 		}
 		if("Update".equals(action)){
 			return updateProfile();
 		}
 		return SUCCESS;
 	}
 	
 	@SkipValidation
	public String doCancel() throws Exception {
				return "cancel";
	}
 	public String doSave() throws Exception {
 		
 		setContextDetails();
 		String provUserName = null;
 		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
 		boolean statusFlag = false;
 		try{
	 		String vendorID = (String)ActionContext.getContext().getSession().get("vendorId");
	 		if (vendorID!=null)
	 			termsDto.setVendorId(Integer.parseInt(vendorID));
	 		
	 		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if (resourceId == null || resourceId.trim().length()==0) {
				addFieldError("terms.resourceId.missing", "ResourceId not found in Terms page. Contact Administrator");
			}
			
			if (hasFieldErrors() || hasActionErrors()) {
				baseTabDto.setDtObject(termsDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getActionErrors().addAll(getActionErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND ,"errorOn");
				return "load";
			}
			
			termsDto.setResourceId(resourceId);
	 		iTermsDelegate.save(termsDto);
	 		
	 		/*
	 		 * Added to redirect the Page based on the Profile status. 
	 		 */
	 		//Fetch the resource status from the DB. 
	 		String resourceStatus = iActivityRegistryDelegate.getResourceStatus(resourceId);
	 		String providerStatus = (String)ActionContext.getContext().getSession().get("providerStatus");
	 		String generalInfoStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_GENERALINFO);
	 		String marketStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_MARKETPLACE);
	 		String skillsStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_SKILLS);
	 		String credentialStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_CREDENTIALS);
	 		String backGrdStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
	 		
	 		if (resourceStatus != null && resourceStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
	 		&&	generalInfoStatus != null && generalInfoStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
	 		&&	marketStatus != null && marketStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
	 		&&	skillsStatus != null && skillsStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
	 		&&	credentialStatus != null && credentialStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
	 		&&	backGrdStatus != null && backGrdStatus.trim().equals(ActivityRegistryConstants.COMPLETE))
	 		{
	 			statusFlag = true;
	 		}
	 		
	 		//If the Resource Status is 'complete' and
	 		//all tabs are green email will be sent to the new service pro user.
	 		if (true == statusFlag)
	 		{
	 			//Gets the Provider Admin's User Name
	 			if (null != securityContext)
	 				provUserName = securityContext.getUsername();
	 			if (null != provUserName)
	 				iTermsDelegate.sendEmailForNewUser(termsDto, provUserName);
	 		}
	 		
	 		//If the Resource status is 'complete' and
	 		//Vendor status is 'complete' and all the tabs are green then perform the submit registration.
	 		if (providerStatus != null && providerStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
	 		&&  true == statusFlag)
	 		{
	 			return "updateProfile";
	 		}
	 		
	 		
	 		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND,"complete");
	 		addActionMessage("Successfully updated data!");
	 		baseTabDto.getActionMessages().addAll(getActionMessages());
 		}		
 		catch(Exception a_Ex)
		{	
	 		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND,"errorOn");
	 		addActionMessage("Successfully updated data!");
	 		baseTabDto.getActionMessages().addAll(getActionMessages());
		}
 		return SUCCESS;
 	}
 	
	public String doprevious() throws Exception {
		setContextDetails();
		String provUserName = null;
		try{
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
	 		String vendorID = (String)ActionContext.getContext().getSession().get("vendorId");
	 		if (vendorID!=null)
	 		termsDto.setVendorId(Integer.parseInt(vendorID));
	 		
	 		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
			if (resourceId == null || resourceId.trim().length()==0) {
				addFieldError("terms.resourceId.missing", "ResourceId not found in Terms page. Contact Administrator");
			}
			
			if (hasFieldErrors() || hasActionErrors()) {
				baseTabDto.setDtObject(termsDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getActionErrors().addAll(getActionErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND ,"errorOn");
				return "previous"; // if error then forward  error message to previous page
			}
			termsDto.setResourceId(resourceId);
	 		iTermsDelegate.save(termsDto);
	 		
	 		String resourceStatus = iActivityRegistryDelegate.getResourceStatus(resourceId);
	 		
	 		String generalInfoStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_GENERALINFO);
	 		String marketStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_MARKETPLACE);
	 		String skillsStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_SKILLS);
	 		String credentialStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_CREDENTIALS);
	 		String backGrdStatus = baseTabDto.getTabStatus().get(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
	 		/*
	 		 * Added to send email for new service pro users.
	 		 * Mail will be sent only when the Resource status is 'complete'.
	 		 * If Resource Status is 'Complete' and all tabs are green then email will be sent to new
	 		 * service pro user.
	 		 */
	 		if (resourceStatus != null && resourceStatus.trim().length() > 0 && resourceStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
		 		&&	generalInfoStatus != null && generalInfoStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
		 		&&	marketStatus != null && marketStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
		 		&&	skillsStatus != null && skillsStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
		 		&&	credentialStatus != null && credentialStatus.trim().equals(ActivityRegistryConstants.COMPLETE)
		 		&&	backGrdStatus != null && backGrdStatus.trim().equals(ActivityRegistryConstants.COMPLETE)) {
		 			if (null != securityContext)
		 				provUserName = securityContext.getUsername();
		 			if (null != provUserName)
		 				iTermsDelegate.sendEmailForNewUser(termsDto, provUserName);
	 		}
	 		
	  		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND,"complete");
	 		baseTabDto.getActionMessages().addAll(getActionMessages());
		} catch(Exception e)	{
			logger.error(e.getMessage(), e);
		}
 		return "previous";
 	}
 	
	
	public String updateProfile(){
			
		try{
			doSave();
		} catch(Exception e)	{
			logger.error(e.getMessage(), e);
		}
		return "updateProfile";
	}
	
	
 	 @SkipValidation
 	public String doLoad() throws Exception {
 		 
 		 try{
 			setContextDetails();
	 		termsDto.setVendorId(Integer.parseInt((String)ActionContext.getContext().getSession().get("vendorId")));
	 		
	 		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
	 		logger.debug("iActivityRegistry Action ==resourceId "+ resourceId);
			if (resourceId == null || resourceId.trim().length()==0) {
				addFieldError("terms.resourceId.missing", "ResourceId not found in Terms page.Contact Administrator");
			}
			
			if (hasFieldErrors() || hasActionErrors()) {
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(termsDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getActionErrors().addAll(getActionErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND ,"errorOn");
				return "load";
			}
			
			termsDto.setResourceId(resourceId);
	 		termsDto = iTermsDelegate.getData(termsDto);
	 		
	 		//add for header
			String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
			logger.debug("terms resourceName : "+resourceName);
			termsDto.setFullResoueceName(resourceName);

	 		getSessionMessages();
 	 	} catch(Exception e)	{
			logger.error(e.getMessage(), e);
		}	
 		return "load";
 	}

 	@SkipValidation
 	public String doInput() throws Exception {
 		logger.debug("Terms and Cond.doInput()");
 		TermsDto tmpTermsDto = new TermsDto();
 		try
 		{
 			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
 			if (baseTabDto!= null) {
	 			termsDto.setVendorId(Integer.parseInt((String)ActionContext.getContext().getSession().get("vendorId")));
		 		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
				if (resourceId == null || resourceId.trim().length()==0) {
					addFieldError("terms.resourceId.missing", "ResourceId not found in Terms page.Contact Administrator");
				}
				
				if (hasFieldErrors() || hasActionErrors()) {
					baseTabDto.setDtObject(termsDto);
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getActionErrors().addAll(getActionErrors());
					baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND ,"errorOn");
					return "load";
				}
				
				termsDto.setResourceId(resourceId);
				tmpTermsDto = iTermsDelegate.getData(termsDto);
		 		
	 			baseTabDto.setDtObject(null);
	 			if (tmpTermsDto != null)
	 			{
	 				termsDto.setTermsContent(tmpTermsDto.getTermsContent());
	 			}
	 			else
	 			{
	 				addFieldError("terms.termsContent.error", "Error while fetching the Terms and Condition content. Please try again or contact Administrator");
	 				baseTabDto.setDtObject(termsDto);
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getActionErrors().addAll(getActionErrors());
					baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_TERMSANDCOND ,"errorOn");
	 				return "load";
	 			}
	 				
	 			termsDto.setAcceptTerms(-1);
	 			//add for header
	 			String resourceName=(String)ActionContext.getContext().getSession().get("resourceName");
	 			logger.debug("terms resourceName : "+resourceName);
	 			termsDto.setFullResoueceName(resourceName);

	 			getSessionMessages();
	 			
	 		}
 		}catch(Exception e)	{
			logger.error(e.getMessage(), e);
 		}
 		return "load";
 	}

 	private String type=null;
 	
 	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}
 	
 	/**
 	 * @return the termsAndCondDto
 	 */
 	public TermsDto getTermsDto() {
 		return termsDto;
 	}
 	/**
 	 * @param termsAndCondDto the termsAndCondDto to set
 	 */
 	public void setTermsDto(TermsDto termsDto) {
 		this.termsDto = termsDto;
 	}

 	/**
 	 * @return the type
 	 */
 	public String getType() {
 		if(type==null)
 			type="";
 		
 		return type;
 	}

 	/**
 	 * @param type the type to set
 	 */
 	public void setType(String type) {
 		this.type = type;
 	}
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
 	private void getSessionMessages(){
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
 }
/*
 * Maintenance History
 * $Log: TermsAction.java,v $
 * Revision 1.21  2008/05/29 16:24:10  gjacks8
 * formatting
 *
 * Revision 1.20  2008/04/26 01:13:49  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.18.4.1  2008/04/23 11:41:40  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.19  2008/04/23 05:19:36  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.18  2008/02/29 03:42:54  hrajago
 * Sears00048524
 *
 * Revision 1.17  2008/02/26 18:18:04  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.16.2.1  2008/02/25 19:36:16  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */