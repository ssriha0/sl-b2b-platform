
package com.newco.marketplace.web.action.provider;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.web.action.login.LoginAction;
import com.newco.marketplace.web.delegates.provider.ITeamMemberDelegate;
import com.newco.marketplace.web.dto.provider.BackgroundCheckDTO;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author MTedder
 * $Revision: 1.27 $ $Author: akashya $ $Date: 2008/05/21 23:32:59 $
 */
public class BackgroundCheckAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5955543422927009156L;
	private static final Logger logger = Logger.getLogger(LoginAction.class.getName());
	private BackgroundCheckDTO teamProfileDto = null;
	private ITeamMemberDelegate teamProfileDelegate = null;
	// see XW-371 -code from: http://svn.opensymphony.com/svn/xwork/trunk/src/java/com/opensymphony/xwork2/validator/validators/EmailValidator.java
    public static final String emailAddressPattern =
    	"\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
	Pattern pattern;
	Matcher matcher;
	public int checkStatus = 0;
	private Map sSessionMap;
	private HttpSession session;
	private SecurityContext securityContext;
	
	/**
	 * Constructor with object parameters provided by Spring
	 */
	public BackgroundCheckAction(ITeamMemberDelegate iTeamMemberDelegate,BackgroundCheckDTO teamProfileDTO){
		this.teamProfileDto = teamProfileDTO;
		this.teamProfileDelegate = iTeamMemberDelegate;
	}

	@SkipValidation
	public String doLoad() throws Exception {
		boolean hasErrorFlag = false;
		logger.info("BackgroundCheckAction.doLoad()"+this.teamProfileDto.getBgCheckStatus());
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		if(baseTabDto.getFieldsError()!= null){
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
		}	
		
		/**
		 * bnatara - Added to pass Provider User Name to get the admin's email id.
		 */
		String provUserName = null;
		setContextDetails();
		if (securityContext != null)
			provUserName = (String)(securityContext.getUsername());
			teamProfileDto.setProvUserName(provUserName);
				
		getSessionMessages();//added by MTedder
		updateDTO();
		this.teamProfileDto = teamProfileDelegate.queryEmailForTeamMember(teamProfileDto);
		//populate confirm email and confirm emailAlt textbooxs
		this.teamProfileDto.setConfirmEmail(this.teamProfileDto.getEmail());
		this.teamProfileDto.setConfirmEmailAlt(this.teamProfileDto.getEmailAlt());
		this.teamProfileDto.setBackgroundConfirmInd(this.teamProfileDto.getBackgroundConfirmInd());	
		//SL-19667  share Background check status for resource having same personal info

		if(this.teamProfileDto.isBackgroundCheckShared()){
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
			return "share"; 

		}
		//SL-19667  recertify background check
		if(this.teamProfileDto.isBackgroundCheckRecertify()){
		
			this.teamProfileDto.setBackgroundCheckRecertifyShared(this.teamProfileDto.isBackgroundCheckRecertifyShared());
			return "recertify"; 
		}
		
		return "load"; 
	}
	
	@Override
	public String execute() throws Exception {
		logger.debug("BackgroundCheckAction.execute()");
		String action = teamProfileDto.getAction();
		if("Previous".equals(action)){
			return doPrev();
		}
		if("Next".equals(action)){
			return doNext();
		}
		if("Update".equals(action)){
			return updateProfile();
		}
		if("Save".equals(action)){
			return doSave();
		}
		if("Check".equals(action)){
			return doCheck();
		}
		if("ShareNext".equals(action)){
			return doShareBackgroundInfo();
		}
		if("Recertify".equals(action)){
			return doRecertify();
		}
		return SUCCESS;
	}

	@Override
	public void validate() {
		logger.debug("BackgroundCheckAction.validate()");
		super.validate();
			
		if (hasFieldErrors()) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(teamProfileDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_ERROR_ON);
		}		
	}

	private void save() throws Exception{
		logger.info("BackgroundCheckAction.save()");
		boolean hasErrorFlag = false;		
		/**
		 * bnatara - Added to pass Provider User Name to get the admin's email id.
		 */
		String provUserName = null;
		setContextDetails();
		if (securityContext != null)
			provUserName = (String)(securityContext.getUsername());
			teamProfileDto.setProvUserName(provUserName);	
		updateDTO();		
		teamProfileDelegate.saveBackgroundCheckData(this.teamProfileDto);		
	}
		
	/*
	 * MTedder
	 * validateServiceProviderEmail
	 */	
	private boolean validateServiceProviderEmail(){
		/*
		 * check if proper email -from: http://svn.opensymphony.com/svn/xwork/trunk/src/java/com/opensymphony/xwork2/validator/validators/RegexFieldValidator.java
		 */
		boolean hasErrorFlag = false;
		
		/*
		 * Email field required check
		 */
		if("".equalsIgnoreCase(teamProfileDto.getEmail())){
			addFieldError("teamProfileDto.email", "Service provider email required"); 
            hasErrorFlag = true;
		}else{		
            pattern = Pattern.compile(emailAddressPattern);
            matcher = pattern.matcher(teamProfileDto.getEmail());
            if (!matcher.matches()) {            	
            	addFieldError("teamProfileDto.email", "Service provider email not valid"); 
            	hasErrorFlag = true;
            }
            
            //check confirmation email
           /* matcher = pattern.matcher(teamProfileDto.getConfirmEmail());
			if (!matcher.matches()) {				
				addFieldError("teamProfileDto.confirmEmail", "Service provider confirmation email not valid");
				hasErrorFlag = true;
			}*/
		}
       		
		/*
		 * check if email is equal to confirmation email
		 */
        /*if(!teamProfileDto.getEmail().equals(teamProfileDto.getConfirmEmail())){        	
        	addFieldError("teamProfileDto.confirmEmail", "Service provider email and confirmation email are not the same");
        	hasErrorFlag = true;
        }*/
        //return false if errors exist
        if(hasErrorFlag){	        	
        	return false;
        }
        
		return true;
	}//end 
	
	/*
	 * MTedder
	 * validateServiceProviderEmail only if present (length > 0)
	 */	
	private boolean validateServiceProviderAltEmail(){
		/*
		 * check if proper email -from: http://svn.opensymphony.com/svn/xwork/trunk/src/java/com/opensymphony/xwork2/validator/validators/RegexFieldValidator.java
		 */
		boolean hasErrorFlag = false;//flag indicating errors found
				
		if(teamProfileDto.getEmailAlt().length() > 0 || teamProfileDto.getConfirmEmailAlt().length() > 0){//perform validations on if field is present (length >0)

			pattern = Pattern.compile(emailAddressPattern);

			matcher = pattern.matcher(teamProfileDto.getEmailAlt());
			if (!matcher.matches()) {
				addFieldError("teamProfileDto.emailAlt", "Service provider alternate email not valid"); 
				hasErrorFlag = true;
			}
			//check confirmation email
			/*matcher = pattern.matcher(teamProfileDto.getConfirmEmailAlt());
			if (!matcher.matches()) {
				addFieldError("teamProfileDto.confirmEmailAlt", "Service provider alternate confirmation email not valid");
				hasErrorFlag = true;
			}
			
			 * check if email is equal to confirmation email
			 
			if(!teamProfileDto.getEmailAlt().equals(teamProfileDto.getConfirmEmailAlt())){
				addFieldError("teamProfileDto.confirmEmailAlt", "Service provider alternate email and alternate confirmation email are not the same");
				hasErrorFlag = true;
			}*/
			//return false if errors exist
			if(hasErrorFlag){	        	
				return false;
			}
		}//end if field present if
		return true;//no errors exist
	}
	
	public String doNext() throws Exception {
		logger.info("BackgroundCheck.doNext()");		
		updateDTO();
		int marketPlaceIndicator = teamProfileDelegate.getMarketPlaceIndicator(this.teamProfileDto.getResourceId().toString());
		
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		if ((marketPlaceIndicator ==1)&&(teamProfileDto.getBackgroundCheckStatus().equalsIgnoreCase(OrderConstants.TEAM_BCKGRND_NOT_STARTED)) ) {
			addActionError(getText("backgroundCheck.marketPlaceIndicator.error"));
           	baseTabDto.getActionErrors().addAll(getActionErrors());
           	baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_ERROR_ON);
           	return "success";
		}
		else
		{			
			boolean recertify=this.teamProfileDto.isBackgroundCheckRecertify();

			if(recertify){
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_INCOMPLETE);
			}else{
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
			}
			return "next";
		}		
	}
	
	public String doPrev() throws Exception {
		logger.info("BackgroundCheck.doPrev()");		
		updateDTO();
		int marketPlaceIndicator = teamProfileDelegate.getMarketPlaceIndicator(this.teamProfileDto.getResourceId().toString());	
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");		
		if ((marketPlaceIndicator ==1)&&(teamProfileDto.getBackgroundCheckStatus().equalsIgnoreCase(OrderConstants.TEAM_BCKGRND_NOT_STARTED)) ) {			
            addActionError(getText("backgroundCheck.marketPlaceIndicator.error"));  
			baseTabDto.getActionErrors().addAll(getActionErrors());     
           	baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_ERROR_ON);
           	return "success";
		}
		else
		{			
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
			return "prev";	
		}			
			
	}
	/**
	 * GGanapathy
	 * Used to update profile after saving the Back Ground Check page
	 * @return String
	 * @throws Exception
	 */	
	public String updateProfile() throws Exception {
		logger.info("BackgroundCheck.updateProfile()");		
		updateDTO();
		int marketPlaceIndicator = teamProfileDelegate.getMarketPlaceIndicator(this.teamProfileDto.getResourceId().toString());	
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		if ((marketPlaceIndicator ==1)&&(teamProfileDto.getBackgroundCheckStatus().equalsIgnoreCase(OrderConstants.TEAM_BCKGRND_NOT_STARTED)) ) {			
            addActionError(getText("backgroundCheck.marketPlaceIndicator.error"));   
			baseTabDto.getActionErrors().addAll(getActionErrors());
           	baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_ERROR_ON);
           	return "success";
		}
		else
		{			
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
			return "updateProfile";
		}
		
	}
	
	
	public String doSave() throws Exception{
		logger.info("BackgroundCheck.doSave()");
		boolean hasErrorFlag = false;	
		boolean serviceProviderValidateEmailFlag = validateServiceProviderEmail();		
		boolean validateServiceProviderAltEmailFlag = validateServiceProviderAltEmail();
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		if(serviceProviderValidateEmailFlag && validateServiceProviderAltEmailFlag){//if no errors- save to DB	
			save();	
			boolean recertify=this.teamProfileDto.isBackgroundCheckRecertify();
    		addActionMessage(getText("success.save"));    		
    		baseTabDto.getActionMessages().addAll(getActionMessages()); 
    		if(recertify){
    		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_INCOMPLETE);
    		}else{
        		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
    		}
			return "success";
		}else{			
			updateDTO();		
			if (hasFieldErrors()) {//needed to persist fielderrors				
				baseTabDto.setDtObject(teamProfileDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_ERROR_ON);
			}	
			return "validate";				
		}
	} 
	
	
	private void updateDTO() throws DelegateException{
		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		if(StringUtils.isBlank(resourceId)){
			SecurityContext sc = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
			logger.debug("Vendor Id = " + sc.getCompanyId() + " , Resource Id = " + sc.getVendBuyerResId() );
			teamProfileDto.setResourceId(sc.getVendBuyerResId());
		}
		else 
		{
			teamProfileDto.setResourceId(Integer.valueOf(resourceId));
		}		
		ServletContext servletContext = ServletActionContext.getServletContext();
		LocalizationContext localizationContext = (LocalizationContext)servletContext.getAttribute("serviceliveCopyBundle");
		String plusOneUrl = localizationContext.getResourceBundle().getString("plusone.email.linkurl");	
		String resourcePlusOneKey = teamProfileDelegate.getEncryptedPlusOneKey(resourceId);		
		String backgroundCheckstatus = teamProfileDelegate.getBackgroundCheckStatus(resourceId);
		
		// check whether the recertification is needed for the resource
		
		BackgroundCheckVO backgroundCheck = new BackgroundCheckVO();
		backgroundCheck = teamProfileDelegate
				.isBackgroundCheckRecertification(Integer.valueOf(resourceId));
		if (null != backgroundCheck) {
			this.teamProfileDto.setCertificationDate(backgroundCheck
					.getCertificationDate());
			this.teamProfileDto.setRecertificationDate(backgroundCheck
					.getRecertificationDate());
			this.teamProfileDto.setBackgroundCheckRecertify(true);
			
			
			//R11_1
			//Jira SL-20434
			String ssnLastFour=null;
			String parm3=null;
			ssnLastFour = teamProfileDelegate.getResourceSSNLastFour(resourceId);
					
			if(ssnLastFour != null && ssnLastFour.trim().length() > 0 && resourceId != null && resourceId.trim().length()>0){

                String plusOne= resourceId.trim()+ssnLastFour.trim();

                parm3 = CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne);
                parm3=  URLEncoder.encode(parm3);
                this.teamProfileDto.setEncryptedResourceIdSsn(parm3);

         }
			
			
			// set a new booleean recertshared as true if it is a shared resource
			// fetch the query to find the shared resource.
			// if true set the valu as true.
			
			BackgroundCheckVO backgroundCheckSharedVo = new BackgroundCheckVO(); 
			backgroundCheckSharedVo = teamProfileDelegate
					.getBackgroundCheckInfo(Integer.valueOf(resourceId));
			if (null != backgroundCheckSharedVo) {
				this.teamProfileDto.setBackgroundCheckRecertifyShared(true);
			}
			
		} else {

			// get background check info if a resource(with same personal info)
			// with background check status not other than 'not started' exist.
			// then share the background check status
			BackgroundCheckVO backgroundCheckVo = new BackgroundCheckVO(); 
			backgroundCheckVo = teamProfileDelegate
					.getBackgroundCheckInfo(Integer.valueOf(resourceId));
			if (null != backgroundCheckVo) {
				
				
				this.teamProfileDto.setCertificationDate(backgroundCheckVo
						.getCertificationDate());
				this.teamProfileDto.setRecertificationDate(backgroundCheckVo
						.getRecertificationDate());
				this.teamProfileDto.setBackgroundCheckShared(true);
				// backgroundCheckStatus
				backgroundCheckstatus = backgroundCheckVo
						.getBackgroundCheckStatus();
				
				boolean isRecertificationDateDisplay=teamProfileDelegate.isRecertificationDateDisplay(resourceId);
				if(!isRecertificationDateDisplay){
					this.teamProfileDto.setRecertificationDate(null);	
				}
			}
			
			
			//R12_2
			//SL-20553
			
			String originalResourceId=null;
			originalResourceId = teamProfileDelegate.getBgOriginalResourceId(resourceId);
			if(StringUtils.isNotBlank(originalResourceId))
			{
			this.teamProfileDto.setOriginalResourceId(Integer.valueOf(originalResourceId));
			}
		}
		
		if( !(this.teamProfileDto.isBackgroundCheckRecertify() || this.teamProfileDto.isBackgroundCheckShared())){
			boolean isRecertificationDateDisplay=teamProfileDelegate.isRecertificationDateDisplay(resourceId);
			if(!isRecertificationDateDisplay){
				this.teamProfileDto.setRecertificationDate(null);	
			}
		}
		
		this.teamProfileDto.setPlusOneUrl(plusOneUrl);
		
		if(null!=resourcePlusOneKey)
		{
			this.teamProfileDto.setEncryptedPlusOneKey(URLEncoder.encode(resourcePlusOneKey));
		}
		this.teamProfileDto.setBackgroundCheckStatus(backgroundCheckstatus.trim());		
		
		
	}
	
	/*
	 * Performs validations on backgroundCheck page fields
	 */
	public String doSaveValidations() throws Exception{
		boolean serviceProviderValidateEmailFlag = validateServiceProviderEmail();
		//boolean validateServiceProviderAltEmailFlag = validateServiceProviderAltEmail();
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");		
		if( serviceProviderValidateEmailFlag){//if no errors- save to DB
			save();		
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
			return "success";
		}else{
			logger.info("BackgroundCheck.doSaveValidations()-Validation ERROR");
			if (hasFieldErrors()) {//needed to persist fielderrors				
				baseTabDto.setDtObject(teamProfileDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_ERROR_ON);
			}	
		return "validate";				
		}
	}//
	
	@SkipValidation
	public String doInput() throws Exception{
		updateDTO();
		logger.info("BackgroundCheckAction.doInput()"+this.teamProfileDto.getBackgroundCheckStatus());			
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		getSessionMessages();//added by MTedder
		
		if (baseTabDto!= null) {
			teamProfileDto = (BackgroundCheckDTO)baseTabDto.getDtObject();
			baseTabDto.setDtObject(null);
		}	
		if(this.teamProfileDto.getBackgroundCheckStatus().equals(OrderConstants.TEAM_BCKGRND_NOT_STARTED)){
			this.teamProfileDto.setBgCheckStatus(OrderConstants.TEAM_BACKGROUND_NOT_STARTED);
		}
		
		logger.info("BackgroundCheckAction.doInput()"+this.teamProfileDto.getBgCheckStatus());
		if(this.teamProfileDto.isBackgroundCheckRecertify()){
			return "recertify";
		} else {
			return "load";
		}
	}
	
	
	public String doCheck() throws Exception{
		checkStatus = 1;
		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		if(StringUtils.isBlank(resourceId)){
			SecurityContext sc = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
			logger.debug("Vendor Id = " + sc.getCompanyId() + " , Resource Id = " + sc.getVendBuyerResId() );
			teamProfileDto.setResourceId(sc.getVendBuyerResId());
		}
		else
		{
			teamProfileDto.setResourceId(Integer.valueOf(resourceId));
		}	
		//SL-19667 update background check status.
		teamProfileDelegate.updateBackgroundCheckStatus(resourceId);
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
		return "success";
	}
	
	//SL-19667 insert share background info comments in history
	public String doShareBackgroundInfo() throws Exception{
		logger.info("BackgroundCheck.doNext()");
		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		if(StringUtils.isBlank(resourceId)){
			SecurityContext sc = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
			logger.debug("Vendor Id = " + sc.getCompanyId() + " , Resource Id = " + sc.getVendBuyerResId() );
			teamProfileDto.setResourceId(sc.getVendBuyerResId());
		}
		else
		{
			teamProfileDto.setResourceId(Integer.valueOf(resourceId));
		}	
		updateDTO();
		int marketPlaceIndicator = teamProfileDelegate.getMarketPlaceIndicator(this.teamProfileDto.getResourceId().toString());
		
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_COMPLETE);
		//teamProfileDelegate.doShare(resourceId);
		return "next";
			
	}
	
	public String doRecertify() throws Exception{
		checkStatus = 1;
		String resourceId = (String)ActionContext.getContext().getSession().get("resourceId");
		if(StringUtils.isBlank(resourceId)){
			SecurityContext sc = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
			logger.debug("Vendor Id = " + sc.getCompanyId() + " , Resource Id = " + sc.getVendBuyerResId() );
			teamProfileDto.setResourceId(sc.getVendBuyerResId());
		}
		else
		{
			teamProfileDto.setResourceId(Integer.valueOf(resourceId));
		}	
		//SL-19667 update recertification status.
		teamProfileDelegate.recertify(resourceId);
		BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext()
		.getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,OrderConstants.ICON_INCOMPLETE);
		return "success";
	}
	
	
	/**
	 * @return the teamProfileDto
	 */
	public BackgroundCheckDTO getTeamProfileDto() {
		return teamProfileDto;
	}

	/**
	 * @param teamProfileDto the teamProfileDto to set
	 */
	public void setTeamProfileDto(BackgroundCheckDTO teamProfileDto) {
		this.teamProfileDto = teamProfileDto;
	}	
	
	/*
	 * Set session data in request.
	 */
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
	
	public void setSession(Map ssessionMap) {
		this.sSessionMap = ssessionMap;		
	}	
	public Map getSession() {
		return this.sSessionMap;		
	}
	
	private void setContextDetails(){
		session = ServletActionContext.getRequest().getSession();
		securityContext = (SecurityContext) session.getAttribute("SecurityContext");
	}
	
	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}
	
	protected String getParameter(String param) {
		return ServletActionContext.getRequest().getParameter(param);
	}
}
/*
 * Maintenance History
 * $Log: BackgroundCheckAction.java,v $
 * Revision 1.27  2008/05/21 23:32:59  akashya
 * I21 Merged
 *
 * Revision 1.26.6.2  2008/05/14 15:27:41  pjoy0
 * Fixed as per I-21 Admin Track - Enhance Background Check Process [id=31736]
 *
 * Revision 1.26.6.1  2008/05/13 20:10:25  ypoovil
 * I-21 Admin Track - Enhance Background Check Process [id=31736]
 *
 * Revision 1.26  2008/04/26 01:13:50  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.23.6.1  2008/04/01 22:04:23  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.24  2008/03/27 15:51:13  mhaye05
 * Merged I18_TWO_ENH to Head
 *
 * Revision 1.23.14.1  2008/03/21 14:40:56  paugus2
 * CR #  Sears00049367
 * Description: CC to be sent to Provider admin
 *
 * Revision 1.23  2008/02/26 18:18:06  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.22.2.1  2008/02/25 16:40:37  mhaye05
 * replaced system out println with logger.debug statements and some general code cleanup
 *
 */