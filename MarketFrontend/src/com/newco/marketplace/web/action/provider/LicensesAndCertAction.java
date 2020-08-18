package com.newco.marketplace.web.action.provider;


import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.AUDIT_TIME_LOGGING_ID;
import static com.newco.marketplace.web.action.provider.PowerAuditorWorkflowConstants.CRED_ID;
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
import com.newco.marketplace.persistence.daoImpl.provider.LicensesAndCertDaoImpl;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.web.action.base.SLAuditableBaseAction;
import com.newco.marketplace.web.delegates.provider.IAuditLogDelegate;
import com.newco.marketplace.web.delegates.provider.IInsuranceTypeDelegate;
import com.newco.marketplace.web.delegates.provider.ILicensesAndCertDelegate;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;
import com.newco.marketplace.web.dto.provider.BaseTabDto;
import com.newco.marketplace.web.dto.provider.InsurancePolicyDto;
import com.newco.marketplace.web.dto.provider.LicensesAndCertDto;
import com.newco.marketplace.web.utils.FileUpload;
import com.newco.marketplace.web.utils.FileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.common.CommonConstants;
import com.thoughtworks.xstream.XStream;

@Validation 
public class LicensesAndCertAction extends SLAuditableBaseAction implements SessionAware,
ServletRequestAware,
ServletResponseAware{
	private static final Logger logger = Logger.getLogger(LicensesAndCertAction.class.getName());
	private ILicensesAndCertDelegate iLicensesAndCertDelegate;
	private  LicensesAndCertDto  licensesAndCertDto;
	LicensesAndCertDaoImpl LicensesAndCertDaoImpl =new LicensesAndCertDaoImpl();
	private Date issueDate1;
	private Map sSessionMap;
	private Date expirationDate1;
	private String noCredIndString = "false";//string from jsp form
	private HttpServletRequest request;
	private IInsuranceTypeDelegate iInsuranceDelegate;
	private static final long serialVersionUID = 1L;
	private InsurancePolicyDto insurancePolicyDto;
	private IAuditLogDelegate auditLogDelegates;
	private FileUpload fileUpload = null;
	private HttpServletResponse response;
	private static final String FILE_FIELD_NAME = "licensesAndCertDto.file";
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	private IPowerAuditorWorkflowDelegate powerAuditorWorkflowDelegate;

	String auditTimeLoggingId;
	public String getAuditTimeLoggingId() {
		return auditTimeLoggingId;
	}


	public void setAuditTimeLoggingId(String auditTimeLoggingId) {
		this.auditTimeLoggingId = auditTimeLoggingId;
	}


	@Override
	public void validate() {

		super.validate();
		if(hasFieldErrors()){
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(licensesAndCertDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
		}
	}


	@SkipValidation
	public String doInput() throws Exception {

		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		getSessionMessages();
		if (baseTabDto!= null) {
			licensesAndCertDto = (LicensesAndCertDto)baseTabDto.getDtObject();
			baseTabDto.setDtObject(null);
			LicensesAndCertDto lDto= iLicensesAndCertDelegate.getData(licensesAndCertDto);
			licensesAndCertDto.setMapCredentialType(lDto.getMapCredentialType());
			licensesAndCertDto.setMapCategory(lDto.getMapCategory());
			licensesAndCertDto.setMapState(lDto.getMapState());
			licensesAndCertDto.setCredentialDocumentBytes(lDto.getCredentialDocumentBytes());
			licensesAndCertDto.setCredentialDocumentExtention(lDto.getCredentialDocumentExtention());
			licensesAndCertDto.setCredentialDocumentFileName(lDto.getCredentialDocumentFileName());
			licensesAndCertDto.setCredentialDocumentFileSize(lDto.getCredentialDocumentFileSize());
			licensesAndCertDto.setCredentialDocumentId(lDto.getCredentialDocumentId());
		}
		setContextDetails();
		// New service live admin feature
		// determine is SLAdmin features are enabled
		determineSLAdminFeature();
		return "load";
	}
	/**
	 * @param  licensesAndCertDelegate
	 * @param  licensesAndCertDto
	 */
	public  LicensesAndCertAction(ILicensesAndCertDelegate licensesAndCertDelegate, LicensesAndCertDto licensesAndCertDto,
			IInsuranceTypeDelegate iInsuranceDelegate,InsurancePolicyDto insurancePolicyDto) {
		iLicensesAndCertDelegate = licensesAndCertDelegate;
		this.licensesAndCertDto = licensesAndCertDto;
		this.iInsuranceDelegate=iInsuranceDelegate;
		this.iInsuranceDelegate=iInsuranceDelegate;
		this.insurancePolicyDto=insurancePolicyDto;
	}

	//Sl-20465 : modified the method to capture the start time of auditing task of vendor licenses
	@SkipValidation
	public String doLoad() throws Exception {
	
		auditTimeLoggingId  = (String)ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
		if(StringUtils.isBlank(auditTimeLoggingId)){
			auditTimeLoggingId = licensesAndCertDto.getAuditTimeLoggingIdNew();
		}
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		licensesAndCertDto.setAuditTimeLoggingIdNew(auditTimeLoggingId);
		
		SecurityContext securityContext = (SecurityContext) ActionContext.getContext().getSession().get(SECURITY_CONTEXT);
		String tempId = ServletActionContext.getRequest().getParameter(CRED_ID);
		//checking for admin 
		if(securityContext.isSlAdminInd() && StringUtils.isBlank(auditTimeLoggingId)){
			
			AuditTimeVO auditTimeVo=new AuditTimeVO();
			//setting start action based on whether a new license or existing license	
			if(StringUtils.isNotBlank(tempId) && StringUtils.isNumeric(tempId)){
				Integer vendorCredId = Integer.parseInt(tempId);
				String firmId = (String)ActionContext.getContext().getSession().get(VENDOR_ID);
				Integer auditLinkId = AuditStatesInterface.COMPANY_AUDIT_LINK_ID;
				boolean userInd = false;
				Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(firmId,userInd,vendorCredId,auditLinkId);		
				auditTimeVo.setAuditTaskId(auditTaskId);
				auditTimeVo.setStartAction(CommonConstants.LICENCE_CERTIFICATE_START_ACTION);
				auditTimeVo.setCredId(vendorCredId);
			}
			else{
				auditTimeVo.setStartAction(CommonConstants.COMPANY_ADD_CREDENTIAL);
			}
			auditTimeVo.setStartTime(new Date());
			auditTimeVo.setAgentName(securityContext.getSlAdminUName());
			
			AuditTimeVO auditTimeSaveVo = powerAuditorWorkflowDelegate.saveAuditTime(auditTimeVo);

			if(null!=auditTimeSaveVo && auditTimeSaveVo.getAuditTimeLoggingId() >0)
			{
				ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeSaveVo.getAuditTimeLoggingId());
				Integer auditTimeLoggId = auditTimeSaveVo.getAuditTimeLoggingId();
				licensesAndCertDto.setAuditTimeLoggingIdNew(auditTimeLoggId.toString());
				auditTimeLoggingId = auditTimeLoggId.toString();
			}
		}
		setContextDetails();
		// New service live admin feature
		// determine is SLAdmin features are enabled
		determineSLAdminFeature();
		//Added for resource Name
		String resourceId = (String) ActionContext.getContext()
				.getSession().get("resourceId");

		if(resourceId!= null)
		{
			licensesAndCertDto.setResourceId(new Integer(resourceId).intValue());
		}

		if(getId()!= null && getId()>0)
			licensesAndCertDto.setVendorCredId(getId());
		else if (ServletActionContext.getRequest().getAttribute("credId") != null && (Integer)ServletActionContext.getRequest().getAttribute("credId") > 0  ) {
			licensesAndCertDto.setVendorCredId((Integer)ServletActionContext.getRequest().getAttribute("credId"));
		}
		else
		{
			//who the f$$k coded this lines ??????
			if (licensesAndCertDto.getVendorCredId() > 0)
				licensesAndCertDto.setVendorCredId(licensesAndCertDto.getVendorCredId());
		}

		getSessionMessages();
		licensesAndCertDto.setVendorId(Integer.parseInt((String)ActionContext.getContext().getSession().get("vendorId")));

		licensesAndCertDto = iLicensesAndCertDelegate.getData(licensesAndCertDto);

		if(getType() != null && getType().equals("cat")){
			licensesAndCertDto = iLicensesAndCertDelegate.getData(licensesAndCertDto);

			ActionContext.getContext().getSession().put("objLicensesAndCertDto",licensesAndCertDto);

			return "success";
		}

		return "load";
	}
	//modified method to capture the  auditTimeLoggingId and set it in the request 
	@SkipValidation
	public String doViewList() throws Exception {
		
		auditTimeLoggingId=  (String) ServletActionContext.getRequest().getParameter(AUDIT_TIME_LOGGING_ID);
		if(StringUtils.isBlank(auditTimeLoggingId)){
			auditTimeLoggingId =licensesAndCertDto.getAuditTimeLoggingIdNew();
		}
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		
		getSessionMessages();
		licensesAndCertDto = (LicensesAndCertDto)ActionContext.getContext().getSession().get("objLicensesAndCertDto");

		return "viewList";
	}
	//Sl-20645 : modified the method to capture the end time of vendor licenses
	public String doSave() throws Exception {

		auditTimeLoggingId = licensesAndCertDto.getAuditTimeLoggingIdNew();	
		ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		boolean credIdPresent =false;
		
		String return_val = "input";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}


		validateBeforeDate();
		if(hasFieldErrors()){
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(licensesAndCertDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			return return_val;
		}

		licensesAndCertDto = getUploadFiles(licensesAndCertDto);

		//Sears00048033
		//author - bnatara
		if(hasFieldErrors()){
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			baseTabDto.setDtObject(licensesAndCertDto);
			baseTabDto.getFieldsError().putAll(getFieldErrors());
			baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
			return return_val;
		}
		
		if(licensesAndCertDto.getVendorCredId()!=0){
			credIdPresent = true;
		}
		try{
			iLicensesAndCertDelegate.save(licensesAndCertDto);
		}catch(Exception e){
			logger.error("Exception Occurred at LicensesAndCertAction.doSave "
					+ e.getMessage());
			return ERROR;
		}

		auditUserProfileLog(licensesAndCertDto);

		BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"complete");

		baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
		baseTabDto.setDtObject(licensesAndCertDto);

		return_val = "forward";
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}
		if(StringUtils.isNotBlank(auditTimeLoggingId) && StringUtils.isNumeric(auditTimeLoggingId))
		{
			Integer auditTimeId=Integer.parseInt(auditTimeLoggingId);
			if(null!=auditTimeId)
			{
				//SL-20645:updating 
				AuditTimeVO auditVo=new AuditTimeVO();
				auditVo.setAuditTimeLoggingId(auditTimeId);
				auditVo.setEndTime(new Date());
				if(credIdPresent){
					auditVo.setEndAction(CommonConstants.COMPANY_CREDENTIAL_END_ACTION);
				}
				else{
					auditVo.setEndAction(CommonConstants.COMPANY_SAVE_CREDENTIAL);
					auditVo.setCredId(licensesAndCertDto.getVendorCredId());
					Integer auditTaskId = powerAuditorWorkflowDelegate.getAuditTaskId(String.valueOf(licensesAndCertDto.getVendorId()),false,licensesAndCertDto.getVendorCredId(),AuditStatesInterface.COMPANY_AUDIT_LINK_ID);
					auditVo.setAuditTaskId(auditTaskId);
				}
			
				powerAuditorWorkflowDelegate.updateAuditTime(auditVo);
			}
			ServletActionContext.getRequest().setAttribute(AUDIT_TIME_LOGGING_ID, auditTimeLoggingId);
		}
		return return_val;
	}

	private void auditUserProfileLog(LicensesAndCertDto licensesAndCertDto)
	{
		XStream xstream = new XStream();
		Class[] classes = new Class[] {LicensesAndCertDto.class}; 
		xstream.processAnnotations(classes);
		String xmlContent = xstream.toXML(licensesAndCertDto);
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
	@SkipValidation
	private void saveLicenses() throws Exception {

		licensesAndCertDto.setVendorId(new Integer(((String)ActionContext.getContext().getSession().get("vendorId"))).intValue());//UNCOMMENT FOR PRODUCTION
		int credsize = (Integer)ActionContext.getContext().getSession().get("credsize");
		if(licensesAndCertDto.getVendorId() != 0){//pre existing vendor - then update DB


			if(getNoCredIndString().equals("false"))
			{
				licensesAndCertDto.setAddCredentialToFile(0);
			}else if(getNoCredIndString().equals("true")){
				licensesAndCertDto.setAddCredentialToFile(1);
			}

			licensesAndCertDto.setCredSize(credsize);
			iLicensesAndCertDelegate.update(licensesAndCertDto);

			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");

			/**
			 * Added to Update Incomplete status.
			 * Fix for Sears00045965.
			 */

			if (licensesAndCertDto.getAddCredentialToFile() == 0 && credsize == 0){
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"incomplete");
			} else {
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"complete");
			}
			baseTabDto.getActionMessages().addAll(getActionMessages());
		}

	}

	@SkipValidation
	public String doNextLicenses() throws Exception{

		if(validateCheckBox().equals("inputLic")) {
			return "inputLic";
		}else{
			//saveLicenses();
			return "next";
		}
	}
	@SkipValidation
	public String doPrevLicenses() throws Exception{

		if(validateCheckBox().equals("inputLic")){
			return "inputLic";
		}else{
			//saveLicenses();
			return "prevLic";
		}
	}
	/**
	 * author: G.Ganapathy
	 * Method to updateProfile
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String updateProfile() throws Exception{

		if(validateCheckBox().equals("inputLic")) {
			return "inputLic";
		}else{
			//saveLicenses();
			return "updateProfile";
		}
	}

	@SkipValidation
	public String doCheckCredential() throws Exception{
		saveLicenses();
		return "inputLic";
	}

	@SkipValidation
	public String doViewList1() throws Exception {
		
		getSessionMessages();
		licensesAndCertDto.setVendorId(Integer.parseInt((String)ActionContext.getContext().getSession().get("vendorId")));
		licensesAndCertDto = iLicensesAndCertDelegate.getDataList(licensesAndCertDto);

		if(licensesAndCertDto.getAddCredentialToFile()==0 ||
				(licensesAndCertDto.getCredentials() != null && licensesAndCertDto.getCredentials().size() > 0))
		{
			setNoCredIndString("false");
		}else if(licensesAndCertDto.getAddCredentialToFile()==1){
			setNoCredIndString("true");
		}
		if(licensesAndCertDto.getCredentials() != null)
			ActionContext.getContext().getSession().put("credsize",licensesAndCertDto.getCredentials().size());

		return "viewList1";
	}


	private Integer id;
	private String type;

	public LicensesAndCertDto getLicensesAndCertDto() {
		return licensesAndCertDto;
	}

	public void setLicensesAndCertDto(LicensesAndCertDto licensesAndCertDto) {
		this.licensesAndCertDto = licensesAndCertDto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the issueDate1
	 */
	public Date getIssueDate1() {
		return issueDate1;
	}


	/**
	 * @param issueDate1 the issueDate1 to set
	 */
	public void setIssueDate1(Date issueDate1) {
		this.issueDate1 = issueDate1;
	}


	/**
	 * @return the expirationDate1
	 */
	public Date getExpirationDate1() {
		return expirationDate1;
	}


	/**
	 * @param expirationDate1 the expirationDate1 to set
	 */
	public void setExpirationDate1(Date expirationDate1) {
		this.expirationDate1 = expirationDate1;
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
	 * @return the noCredIndString
	 */
	public String getNoCredIndString() {
		return noCredIndString;
	}

	/**
	 * @param noCredIndString the noCredIndString to set
	 */
	public void setNoCredIndString(String noCredIndString) {
		if(noCredIndString == null){
			noCredIndString = "";
		}
		this.noCredIndString = noCredIndString;
	}

	private void validateBeforeDate(){

		Date dateStart=null;
		Date dateEnd=null;
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		boolean flag=true;
		if(licensesAndCertDto.getIssueDate()==null || licensesAndCertDto.getIssueDate().trim().length()==0){
			addFieldError("licensesAndCertDto.issueDate", "Credential Issue Date is required.");
			return;
		}

		if(licensesAndCertDto.getExpirationDate()!=null && !"".equals(licensesAndCertDto.getExpirationDate())){
			try{
				if(licensesAndCertDto.getIssueDate().compareTo(licensesAndCertDto.getExpirationDate())>0) {
					addFieldError("licensesAndCertDto.Expiration Date", "Credential Expiration Date  should be greater than Credential Issue Date.");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}//end of 	if(endDate!=null&&endDate.trim().length()>0){

	}



	private final boolean convertDate(final String date1){
		Date dateObj1=null;
		String format="MM/dd/yyyy";
		try {
			if(date1!=null){
				DateFormat dateFormat = new SimpleDateFormat(format);
				dateObj1 = dateFormat.parse(date1);
			}
		}//end of try
		catch (Exception errorexcep) {


			return false;
		}//end of catch
		return true;
	}

	private final boolean convertDateFormat(final String date1){
		Date dateObj1=null;
		String format="MM/dd/yyyy";
		try {
			if(date1!=null){
				DateFormat dateFormat = new SimpleDateFormat(format);
				dateObj1 = dateFormat.parse(date1);
			}
		}//end of try
		catch (Exception errorexcep) {
			//logger.log(ExceptionConstants.ERROR,"The Parse Error in compare date is:" + errorexcep);


			return false;
		}//end of catch
		return true;
	}
	public void setSession(Map ssessionMap) {
		this.sSessionMap=ssessionMap;
	}

	public void setServletResponse(HttpServletResponse arg0)
	{
		response=arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request=arg0;

	}
	/**
	 * Functions related to the Document Upload
	 */
	@SkipValidation
	public String displayTheDocument() throws Exception {

		String documentId = request.getParameter("docId");

		if (documentId != null)
		{
			licensesAndCertDto.setCredentialDocumentId(Integer.parseInt(documentId));
			licensesAndCertDto = iLicensesAndCertDelegate.viewDocumentDetails(licensesAndCertDto);
			request.getSession().setAttribute("licensesAndCertDTO", licensesAndCertDto);
			RequestDispatcher aRequestDispatcher = request
					.getRequestDispatcher("/jsp/providerRegistration/modules/display_LicDocument.jsp");

			if (aRequestDispatcher == null
					||	request == null
					||	response == null)
				return ERROR;

			aRequestDispatcher.forward(request, response);
		}
		return null;
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
			String venId = (String)ActionContext.getContext().getSession().get("vendorId");

			if (licensesAndCertDto != null)
			{
				int vendorCredId = licensesAndCertDto.getVendorCredId();

				if (venId != null)
					licensesAndCertDto.setVendorId(Integer.parseInt(venId));

				if (vendorCredId <= 0)
				{
					addFieldError("vendor.credit.missing", "Vendor details are found missing. Please try again.");
					BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
					return return_val;
				}
				licensesAndCertDto.setVendorCredId(vendorCredId);
				licensesAndCertDto = iLicensesAndCertDelegate.removeDocumentDetails(licensesAndCertDto);

				if (licensesAndCertDto != null)
				{
					BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
					if (baseTabDto != null)
					{
						if (baseTabDto.getActionMessages() != null)
							baseTabDto.getActionMessages().clear();
						baseTabDto.getActionMessages().addAll(getActionMessages());
						baseTabDto.setDtObject(licensesAndCertDto);
					}
				}
			}
		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			return ERROR;
		}
		return return_val;
	}

	private LicensesAndCertDto getUploadFiles(LicensesAndCertDto licensesAndCertDto) throws Exception{

		MultiPartRequestWrapper multiPartRequestWrapper = null;
		Object req = ServletActionContext.getRequest();

		if (req instanceof MultiPartRequestWrapper)
			multiPartRequestWrapper  = (MultiPartRequestWrapper) req;
		else
			return licensesAndCertDto;

		if (multiPartRequestWrapper != null)
		{

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

					licensesAndCertDto.setCredentialDocumentBytes(fileUpload.getCredentialDocumentBytes());
					licensesAndCertDto.setCredentialDocumentExtention(fileUpload.getCredentialDocumentExtention());
					String fileExtn = FileUtils.getFileExtension(fileUpload.getCredentialDocumentFileName());

					String contentType = licensesAndCertDto.getCredentialDocumentExtention();

					if (contentType == null)
					{
						addFieldError("licensesAndCertDto.extension.error", "Preferred file types include: JPG | PDF | DOC | GIF ");
						if (hasFieldErrors()) {
							BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
							baseTabDto.setDtObject(licensesAndCertDto);
							baseTabDto.getFieldsError().putAll(getFieldErrors());
							baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
						}
						return licensesAndCertDto;
					}

					licensesAndCertDto.setCredentialDocumentFileName(fileUpload.getCredentialDocumentFileName());
					licensesAndCertDto.setCredentialDocumentFileSize(fileUpload.getCredentialDocumentFileSize());
					return licensesAndCertDto;
				}
			}
		}

		return licensesAndCertDto;

	}

	public String attachDocument() throws Exception
	{
		String return_val = "input";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		try
		{
			LicensesAndCertDto credentialsDto = getUploadFiles(licensesAndCertDto);

			if (credentialsDto == null
					|| 	credentialsDto.getCredentialDocumentFileName() == null
					||	credentialsDto.getCredentialDocumentFileName().trim().length() <= 0)
			{
				addFieldError("teamCredentialsDto.file", "File not found. Please try again");
			}

			if (hasFieldErrors()) {
				BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(licensesAndCertDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
				return return_val;
			}

			int vandorResId = 0;
			String retVal = doSave();

			if (retVal != null && retVal.trim().length() > 0 && retVal.trim().equalsIgnoreCase("input"))
				return return_val;

			if (licensesAndCertDto != null)
			{
				vandorResId = licensesAndCertDto.getVendorCredId();
				if (vandorResId == 0)
				{
					addActionError("Error while attaching the document. Please try again");
					return return_val;
				}
				ActionContext.getContext().getSession().put("VendorCredId",vandorResId + "");
			}
			else
			{
				addActionError("Error while attaching the document. Please try again");
				return return_val;
			}

			BaseTabDto baseTabDto = (BaseTabDto) ActionContext.getContext().getSession().get("tabDto");
			if (baseTabDto.getActionMessages() != null)
				baseTabDto.getActionMessages().clear();

			if(hasFieldErrors()){
				baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(licensesAndCertDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
				return return_val;
			}
			else
			{
				baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
				baseTabDto.setDtObject(licensesAndCertDto);
				baseTabDto.getFieldsError().putAll(getFieldErrors());
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"complete");
			}

			return return_val;

		}catch(Exception a_Ex)
		{
			a_Ex.printStackTrace();
			return ERROR;
		}
	}


	public String doRemove() throws Exception {

		String return_val = "forward";
		String isFromPA = (String)request.getSession().getAttribute("isFromPA");
		if ("true".equals(isFromPA))
		{
			return_val = "returnToPA";
		}

		try {
			insurancePolicyDto.setVendorId(new Integer(((String)ActionContext.getContext().getSession().get("vendorId"))).intValue());
			insurancePolicyDto.setVendorCredentialId(licensesAndCertDto.getVendorCredId());
			insurancePolicyDto = iInsuranceDelegate	.deleteInsuranceDetails(insurancePolicyDto);

			/**
			 * Added for showing 'Incomplete' State for zero credentials after removing
			 * G.Ganapathy
			 */
			licensesAndCertDto.setVendorId(new Integer(((String)ActionContext.getContext().getSession().get("vendorId"))).intValue());
			licensesAndCertDto = iLicensesAndCertDelegate.getDataList(licensesAndCertDto);
			BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
			if (licensesAndCertDto.getCredentials() != null && licensesAndCertDto.getCredentials().size() == 0)
			{
				licensesAndCertDto.setVendorId(new Integer(((String)ActionContext.getContext().getSession().get("vendorId"))).intValue());
				licensesAndCertDto.setAddCredentialToFile(0);
				iLicensesAndCertDelegate.update(licensesAndCertDto);
				baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"incomplete");
			}

		} catch (DelegateException ex) {
			addActionError("Exception Occured while processing the request due to"
					+ ex.getMessage());
			return ERROR;
		}
		return return_val;
	}

	public InsurancePolicyDto getInsurancePolicyDto() {
		return insurancePolicyDto;
	}

	private String validateCheckBox(){
		if(licensesAndCertDto.getSize()==0){
			String stringValue=request.getParameter("noCredIndString");
			String stringhidden=request.getParameter("hiddenCheck");

			if(stringhidden!=null&&stringhidden.equals("true")){

				if(noCredIndString.equals("false")){
					addFieldError("noCredIndString", "Please select I do not wish to add any licenses or certifications at this time.");
					BaseTabDto baseTabDto =(BaseTabDto)ActionContext.getContext().getSession().get("tabDto");
					baseTabDto.getFieldsError().putAll(getFieldErrors());
					baseTabDto.getTabStatus().put(ActivityRegistryConstants.LICENSE,"errorOn");
					return "inputLic";
				}
			}
		}
		return "next";
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
}
