package com.newco.marketplace.web.action.provider;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.audit.AuditUserProfileVO;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IBusinessinfoDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.BusinessinfoDto;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.sears.os.utils.DateValidationUtils;
import com.thoughtworks.xstream.XStream;

@Validation
public class BusinessinfoAction extends ActionSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 8665642750799100394L;
	private IBusinessinfoDelegate iBusinessinfoDelegate;
	private BusinessinfoDto businessinfoDto;
	private IAuditLogDelegate auditLogDelegates;
	private String busStartDt;
	// see XW-371 -code from: http://svn.opensymphony.com/svn/xwork/trunk/src/java/com/opensymphony/xwork2/validator/validators/EmailValidator.java
    public static final String emailAddressPattern =
    	"\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
	Pattern pattern;
	Matcher matcher;
   private static final Logger logger = Logger.getLogger(GeneralInfoAction.class.getName());

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		//TODO remove when Admin password widget is ready
		//This is hack .. remove this hack as soon as we introduce new Password Reset Functionality for the Admin
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		boolean isAdmin = false ;
		if(securityContext != null) {
			isAdmin = securityContext.isSlAdminInd();
			if(isAdmin ){
				setFieldErrors(new HashMap());
			}
		}


		if (hasFieldErrors() ) {
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(businessinfoDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.BUSINESSINFO,"errorOn");
		}
	}



	/**
	 * @param businessinfoDelegate
	 * @param businessinfoDto
	 * @param activityRegistryDelegate
	 */
	public BusinessinfoAction(IBusinessinfoDelegate businessinfoDelegate,
			BusinessinfoDto businessinfoDto) {
		iBusinessinfoDelegate = businessinfoDelegate;
		this.businessinfoDto = businessinfoDto;

	}

	private boolean validateBusinessStateZip() throws Exception
	{
		try
		{
			businessinfoDto.setStateType("business");
			businessinfoDto = iBusinessinfoDelegate.loadZipSet(businessinfoDto);
			List stateTypeList = businessinfoDto.getStateTypeList();
			if(!validateStateZip(stateTypeList, businessinfoDto.getBusinessZip()))
			{
				addFieldError("businessinfoDto.businessZip", "Business Address Zip doesn't match selected state");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--Exception inside businessinfoDto.businessZip[validatebusinessZip]", a_Ex);
			throw a_Ex;
		}
		return true;
	}
	private boolean validateEmail() throws Exception
	{
		try
		{
			if (StringUtils.isBlank(businessinfoDto.getEmail())) {
				addFieldError("businessinfoDto.email","Please enter a Primary Email");
				return false;
			}else if(businessinfoDto.getEmail()!=null && StringUtils.isNotBlank(businessinfoDto.getEmail())){
				pattern = Pattern.compile(emailAddressPattern);
	            matcher = pattern.matcher(businessinfoDto.getEmail());
	            if (!matcher.matches()) {            	
	            	addFieldError("businessinfoDto.email", "Primary email not valid"); 
	            	return false;
	            }
	            
			}

			if(!businessinfoDto.getEmail().equalsIgnoreCase(businessinfoDto.getConfirmEmail()))
			{
				addFieldError("businessinfoDto.confirmEmail", "Email and ConfirmEmail should be same");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--businessinfoDto.confirmEmail[validateEmail]", a_Ex);
			throw a_Ex;
		}
		return true;
	}
	private boolean validateAltEmail() throws Exception
	{
		try
		{
			if (StringUtils.isBlank(businessinfoDto.getAltEmail())) {
				addFieldError("businessinfoDto.altEmail","Please enter a Alternate Email");
				return false;
			}else if(businessinfoDto.getAltEmail()!=null && StringUtils.isNotBlank(businessinfoDto.getAltEmail())){
				pattern = Pattern.compile(emailAddressPattern);
	            matcher = pattern.matcher(businessinfoDto.getAltEmail());
	            if (!matcher.matches()) {            	
	            	addFieldError("businessinfoDto.altEmail", "Alternate email not valid"); 
	            	return false;
	            }
	            
			}

			if(!businessinfoDto.getAltEmail().equalsIgnoreCase(businessinfoDto.getConfAltEmail()))
			{
				addFieldError("businessinfoDto.confAltEmail", "Alternate Email and ConfirmEmail should be same");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--businessinfoDto.confirmEmail[validateAltEmail]", a_Ex);
			throw a_Ex;
		}
		return true;
	}

	private boolean validateMailingStateZip() throws Exception
	{
		try
		{
			businessinfoDto.setStateType("mail");
			businessinfoDto = iBusinessinfoDelegate.loadZipSet(businessinfoDto);
			List stateTypeList = businessinfoDto.getStateTypeList();
			if(!validateStateZip(stateTypeList, businessinfoDto.getMailingZip()))
			{
				addFieldError("businessinfoDto.mailingZip", "Mail Address Zip doesn't match selected state");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--Exception inside businessinfoDto.mailingZip[validateMailingStateZip]", a_Ex);
			throw a_Ex;
		}
		return true;
	}

	private boolean validateStateZip(List list, String zip)
	{
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String validZip = ((LocationVO) itr.next()).getZip();
			if (zip.equals(validZip)) {
				return true;
			}
		}
		return false;
	}

	private boolean validateBusinessStartDate() throws Exception
	{
		try
		{
			if(DateUtils.isValidDate(businessinfoDto.getBusStartDt()))
			{
				boolean isCurrentDate=DateValidationUtils.fromDateGreaterThanCurrentDate(businessinfoDto.getBusStartDt());
				if(isCurrentDate)
				{
					addFieldError("businessinfoDto.busStartDt", "Business started date cannot be in future");
					return false;
				}
			}
			else
			{
				addFieldError("businessinfoDto.busStartDt", "Enter a valid business start date");
				return false;
			}
		}
		catch(Exception a_Ex)
		{
			logger.log(Level.ERROR, "--Exception inside businessinfoDto.busStartDt", a_Ex);
			throw a_Ex;
		}
		return true;
	}
	private boolean validateBusinessDescription() throws Exception
	{		
		businessinfoDto.setDescription(UIUtils.encodeSpecialChars(businessinfoDto.getDescription()));
		return true;
	}

	private BusinessinfoDto mapforAdminNameChange(BusinessinfoDto businessinfoDto){
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		businessinfoDto.setOldAdminResourceId((String)ActionContext.getContext().getSession().get("resourceId"));
		if(securityContext.isSlAdminInd())
			businessinfoDto.setModifiedBy(securityContext.getSlAdminUName());
		else
		businessinfoDto.setModifiedBy(securityContext.getUsername());
		return businessinfoDto;
	}
	private String save() throws Exception{
		String vendorId = (String)ActionContext.getContext().getSession().get("vendorId");
		businessinfoDto.setVendorId(vendorId);
		businessinfoDto = mapforAdminNameChange(businessinfoDto); // Changes for Admin name change
		
		//R11_2
		//SL-20421
		if(StringUtils.isNotBlank(businessinfoDto.getFirstName())){
			businessinfoDto.setFirstName(businessinfoDto.getFirstName().trim());
		}
		if(StringUtils.isNotBlank(businessinfoDto.getLastName())){
			businessinfoDto.setLastName(businessinfoDto.getLastName().trim());
		}
		if(StringUtils.isNotBlank(businessinfoDto.getMiddleName())){
			businessinfoDto.setMiddleName(businessinfoDto.getMiddleName().trim());
		}
		iBusinessinfoDelegate.save(businessinfoDto);
		auditUserProfileLog(businessinfoDto);
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.BUSINESSINFO,"complete");
		baseTabDto.getActionMessages().addAll(getActionMessages());
		
		if(null!=businessinfoDto.getNewAdminName() && (businessinfoDto.getNewAdminName()!="" && businessinfoDto.getNewAdminName().length()>0 && StringUtils.contains(businessinfoDto.getNewAdminName(),'('))){
			String newAdmin=businessinfoDto.getNewAdminName().substring(0, businessinfoDto.getNewAdminName().indexOf("("));
			ActionContext.getContext().getSession().put("providerName", newAdmin);
			}
		ActionContext.getContext().getSession().put("cityState", businessinfoDto.getBusinessCity()+","+businessinfoDto.getBusinessState());
		return "refresh";
	}
	
	private void auditUserProfileLog(BusinessinfoDto businessinfoDto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {BusinessinfoDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(businessinfoDto);
		AuditUserProfileVO auditUserProfileVO = new AuditUserProfileVO();
		auditUserProfileVO.setActionPerformed("PROVIDER_COMPANY_PROFILE_EDIT");
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

	public String execute() throws Exception {
		//TODO remove when Admin password widget is ready
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		boolean isAdmin = false ;
		if(securityContext != null) {
			isAdmin = securityContext.isSlAdminInd();
		}
		if(!isAdmin){
			validateBusinessStartDate();
		}
		validateBusinessStateZip();
		validateMailingStateZip();
		validateEmail();
		validateAltEmail();
		validateBusinessDescription();
		 if (hasFieldErrors())
			 {
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(businessinfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.BUSINESSINFO,"errorOn");

				//if(!isAdmin ){
					return INPUT;
				//}
			}

		 
		 	
			String action = businessinfoDto.getAction();
			if("Next".equals(action)){
				return doNext();
			}
			if("Update".equals(action)){
				return updateProfile();
			}
		save();

		businessinfoDto = iBusinessinfoDelegate.getData(businessinfoDto);
	return SUCCESS;
	}

	@SkipValidation
	public String doLoad() throws Exception {

		businessinfoDto.setVendorId((String)ActionContext.getContext().getSession().get("vendorId"));
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get("SecurityContext");
		ActionContext.getContext().getSession().put("isPermissionForAdminNameChange", securityContext.getRoles().isPermissionForAdminNameChange());
		businessinfoDto = iBusinessinfoDelegate.getData(businessinfoDto);
		// getting messages from session
		getSessionMessages();
		return "load";
	}

	@SkipValidation
	public String doInput() throws Exception {
		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		// getting messages from session
		getSessionMessages();
		if (baseTabDto!= null) {
			businessinfoDto = (BusinessinfoDto)baseTabDto.getDtObject();
			baseTabDto.setDtObject(null);
			BusinessinfoDto bDto = iBusinessinfoDelegate.getData(businessinfoDto);
			businessinfoDto.setBusinessName(bDto.getBusinessName());
			businessinfoDto.setDbaName(bDto.getDbaName());
			businessinfoDto.setBusStartDt(bDto.getBusStartDt());
			businessinfoDto.setEinNo(bDto.getEinNo());
			businessinfoDto.setManagerInd(bDto.getManagerInd());
		    businessinfoDto.setLocnId(bDto.getLocnId());
			businessinfoDto.setLocnIdB(bDto.getLocnIdB());
			businessinfoDto.setResID(bDto.getLocnIdB());
			businessinfoDto.setPrimaryIndList(bDto.getPrimaryIndList());
			businessinfoDto.setMapBusStructure(bDto.getMapBusStructure());
			businessinfoDto.setMapAnnualSalesRevenue(bDto.getMapAnnualSalesRevenue());
			businessinfoDto.setMapCompanySize(bDto.getMapCompanySize());
			businessinfoDto.setMapForeignOwnedPct(bDto.getMapForeignOwnedPct());
			businessinfoDto.setBusStartDt(bDto.getBusStartDt());


		}
		return "load";
	}

	public String doNext() throws Exception {
		validateBusinessStartDate();
		validateBusinessStateZip();
		validateMailingStateZip();
		validateEmail();
		validateAltEmail();
		validateBusinessDescription();
		 if (hasFieldErrors())
			{
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(businessinfoDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.BUSINESSINFO,"errorOn");
				return INPUT;
			}
		save();
		return "next";
	}
	/**
	 * author: G.Ganapathy
	 * Method to updateProfile
	 * @return
	 * @throws Exception
	 */
	public String updateProfile() throws Exception {
		save();
		return "updateProfile";
	}

	public BusinessinfoDto getBusinessinfoDto() {
		return businessinfoDto;
	}

	public void setBusinessinfoDto(BusinessinfoDto businessinfoDto) {
		this.businessinfoDto = businessinfoDto;
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

	/**
	 * @return the busStartDt
	 */
	public String getBusStartDt() {
		return busStartDt;
	}

	/**
	 * @param busStartDt the busStartDt to set
	 */
	public void setBusStartDt(String busStartDt) {
		this.busStartDt = busStartDt;
	}



	public IAuditLogDelegate getAuditLogDelegates() {
		return auditLogDelegates;
	}



	public void setAuditLogDelegates(IAuditLogDelegate auditLogDelegates) {
		this.auditLogDelegates = auditLogDelegates;
	}

}
